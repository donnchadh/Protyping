package com.google.htmm.arraybased.refactored;

// Copyright 2007 Google Inc.
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//      http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
// Author: agdevhtmm@gmail.com (Amit Gruber)

//The FastRestrictedHMM class is an implementation of an efficient algorithm
//for inference (computing marginal probabilities and likelihood) in a special
//case of HMM.
//It is designed for HMMs with matrix transition of a special form as
//described in the paper "Hidden Topic Markov Models" available at:
//http://www.cs.huji.ac.il/~amitg/aistats07.pdf
//Throughout this code we use the standard alpha and beta recursions:
//alpha_t(i) = Pr(y_1,...,y_t,q_t=i ; param).
//beta_t(i)  = Pr(y_{t+1},...,y_T | q_t=i; param).
//T is the length of the markov chain,
//y_t are the observations at time i, q_t is the (discrete) state at time t,
//param is the set of parmeters of the markov model.
//We assume the reader is familiar with inference in HMMs and with the
//above mentioned paper.

//Assumptions:
//(1) The transition matrix is of the special form described in the HTMM paper,
//i.e. consists of entries that depend on theta and epsilon only
//(2) sprobs is a vector of T vectors, each of which has states=2*topics
//entries and is already allocated when Forward Backward is called.
//(3) Theta is a probability vector of length topics.
//(4) Local is a vector of T probability vectors, each of which is of length
//topics and contains the emissions probabilities.
//(5) pi is a probability vector of length states=2*topics and specifies the
//prior distribution on the first hidden state in the chain.

//Design Issues:
//We might want to have a base HMM class and derive FastRestrictedHMM from it.
//pros:
//we can easily switch between different implementations of HMM.
//cons:
//this is not a general HMM implementation, it is not suitable whereever HMM is
//suitable.
//the interface of the initialization is different from standard HMM.

public class FastRestrictedHMM {
	int topics_; // The number of topics.
	int states_; // The number of possible states.

	// This method computes posterior probabilities according to the
	// Forward Backward algorithm for HMM.
	// Notice that due to the special structure of our problem we have an
	// effective
	// number of states which is twice the number of topics.
	// See http://www.cs.huji.ac.il/~amitg/aistats07.pdf
	// We assume the reader is familiar with inference in HMMs and with the
	// above mentioned paper.
	// See the comments at the start of fast_restricted_hmm.h for definitions of
	// variables and notations used throughout this file.

	void ForwardBackward(double epsilon, double[] theta, double[][] local,
			double[] pi, double[][] sprobs, double loglik) {
		topics_ = theta.length;
		states_ = 2 * topics_;
		double[] norm_factor = new double[local.length];
		double[] dummy = new double[states_];
		assert pi.length == states_;
		assert sprobs.length == local.length;
		for (int i = 0; i < local.length; i++) {
			assert sprobs[i].length == states_;
			assert local[i].length == topics_;
		}
		double[][] alpha = new double[local.length][];
		for (int i = 0; i < alpha.length; i++) {
			alpha[i] = new double[states_];
		}
		double[][] beta = new double[local.length][];
		for (int i = 0; i < beta.length; i++) {
			beta[i] = new double[states_];
		}
		InitAlpha(pi, local[0], (norm_factor[0]), (alpha[0]));
		ComputeAllAlphas(local, theta, epsilon, norm_factor, alpha);
		InitBeta(norm_factor[local.length - 1], (beta[local.length - 1]));
		ComputeAllBetas(local, theta, epsilon, norm_factor, beta);
		CombineAllProbs(alpha, beta, sprobs);
		ComputeLoglik(norm_factor, loglik);
	}

	// This method initializes alpha[0] to be Pr(y_0 | q_0; param) * Pr(q_0).
	void InitAlpha(double[] pi, double[] local0, double norm, double[] alpha0) {
		norm = 0;
		for (int i = 0; i < topics_; i++) {
			(alpha0)[i] = local0[i] * pi[i];
			(alpha0)[i + topics_] = local0[i] * pi[i + topics_];
			norm += (alpha0)[i] + (alpha0)[i + topics_];
		}

		Normalize(norm, alpha0);
	}

	// This method initializes beta[T-1] to be all ones.
	void InitBeta(double norm, double[] beta_T_1) {
		for (int i = 0; i < states_; i++) {
			(beta_T_1)[i] = 1;
		}

		Normalize(norm, beta_T_1);
	}

	// This method computes alpha[i] for i>=1 (after alpha[0] has been
	// initialized).
	// Notice that this is not the standard alpha computation, we are taking
	// advantage of the special structure of our problem!
	void ComputeAllAlphas(double[][] local, double[] theta, double epsilon,
			double[] norm_factor, double[][] alpha) {
		for (int i = 1; i < local.length; i++) {
			ComputeSingleAlpha(local[i], theta, epsilon, ((norm_factor)[i]),
					(alpha)[i - 1], ((alpha)[i]));
		}
	}

	// This method computes the alphas for a single level after alpha has
	// been computed for the previous level. (See the comments at the
	// start of fast_restricted_hmm.h for a description of alpha and
	// beta.)
	// alpha[t][s] corresponds to a new lottery when picking the topic s at time
	// t.
	// alpha[t][s+topics] corresponds to setting the topic at time t by copying
	// the
	// topic s from time t-1.
	void ComputeSingleAlpha(double[] local_t, double[] theta, double epsilon,
			double norm, double[] alpha_t_1, double[] alpha_t) {
		norm = 0;
		for (int s = 0; s < topics_; s++) {
			(alpha_t)[s] = epsilon * theta[s] * local_t[s]; // regardless of the
															// previous
			// topic - remember that sum_k alpha[t-1][k] is 1 (because of the
			// norm).
			(alpha_t)[s + topics_] = (1 - epsilon)
					* (alpha_t_1[s] + alpha_t_1[s + topics_]) * local_t[s];
			norm += (alpha_t)[s] + (alpha_t)[s + topics_];
		}

		Normalize(norm, alpha_t);
	}

	// Computes beta[i] for i<=T-2 (beta[T-1] has been initialized). Use same
	// normalizing factors as in the alpha computation.
	// Notice that this is not the standard beta computation, we are taking
	// advantage of the special structure of our problem!
	void ComputeAllBetas(double[][] local, double[] theta, double epsilon,
			double[] norm_factor, double[][] beta) {
		for (int i = local.length - 2; i >= 0; i--) {
			ComputeSingleBeta(local[i + 1], theta, epsilon, norm_factor[i],
					(beta)[i + 1], ((beta)[i]));
		}
	}

	// This method computes the betas for a single level after beta has been
	// computed for the next level.
	void ComputeSingleBeta(double[] local_t_1, double[] theta, double epsilon,
			double norm, double[] beta_t1, double[] beta_t) {
		double trans_sum = 0;
		for (int i = 0; i < topics_; i++) {
			trans_sum += epsilon * theta[i] * local_t_1[i] * beta_t1[i];
		}

		for (int s = 0; s < topics_; s++) {
			// Recall that beta_t1[s] == beta_t1[s+topics_]
			(beta_t)[s] = trans_sum + (1 - epsilon) * local_t_1[s] * beta_t1[s];
			(beta_t)[s] /= norm;
			(beta_t)[s + topics_] = (beta_t)[s];
		}
		// we've already normalized the betas!
	}

	// This method combines the previously computed alpha and beta to get the
	// posterior probabilities.
	void CombineAllProbs(double[][] alpha, double[][] beta, double[][] sprobs) {
		for (int i = 0; i < alpha.length; i++) {
			CombineSingleProb(alpha[i], beta[i], ((sprobs)[i]));
		}
	}

	// This method combines the alpha and beta to get probabilities for a
	// single level.
	void CombineSingleProb(double[] alpha, double[] beta, double[] sprobs) {
		double norm = 0;
		for (int s = 0; s < states_; s++) {
			(sprobs)[s] = alpha[s] * beta[s];
			norm += (sprobs)[s];
		}
		Normalize(norm, sprobs);
	}

	// This method normalizes the vector vec to according to the factor norm.
	void Normalize(double norm, double[] vec) {
		for (int i = 0; i < vec.length; i++) {
			vec[i] /= norm;
		}
	}

	// This method computes the log likelihood for the given observations
	// sequence
	// while averaging over all possible states.
	// Alpha and beta must have been computed before calling this method.
	// Summming the posterior state probs is useless, as they are normalized to
	// 1.
	// The alphas are normalized, hence we need to consider the normalizing
	// factors.
	double ComputeLoglik(double[] norm_factor, double loglik) {
		loglik = 0;
		for (int t = 0; t < norm_factor.length; t++) {
			loglik += Math.log(norm_factor[t]);
		}
		return loglik;
	}

} // namespace
