package com.google.htmm.arraybased;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Random;

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



class EM  extends HTMM {
	  // Data members:
	  int iters_;                           // number of EM iterations
	  double[][] theta_;       // estimated theta
	  double[][] phi_;         // estimated phi
	  double epsilon_;                      // estimated epsilon
	  double[][][] p_dwzpsi_;  // The state probabilities,
	           // that is Pr(z,psi | d,w). The weird name reflects the arrangements
	           // of these probabilities in this 3D array.
	  double loglik_;                       // loglikelihood
	private Random random;

	  // Initialization: The documents are read throught the HTMM init class,
// the parameters are set to random variables.
// If we want to repeat a specific run the argument seed is used to specify
// the random seed. Otherwise we specify seed=0 and the random generator is
// initialized according to the clock.
void init(int topics, int words, double alpha, double beta, int iters,
              String fname, String data_dir, int seed) throws IOException {
  super.init(topics, words, alpha, beta, fname, data_dir);
  iters_ = iters;
  // allocate and initialize phi, theta, state probs:
  RandInitParams(seed);
}

// Randomly initializes the parameters (phi, theta and epsilon) and allocates
// space for the state probabilities.
void RandInitParams(long seed) {
  // If seed is 0 use the clock to initialize the random generator.
  if (seed == 0) {
    seed = (int) new Date().getTime();
  }
  srand48(seed);
  epsilon_ = drand48();
  theta_ = new double[docs_.size()][];
  for (int i = 0; i < docs_.size(); i++) {
    theta_[i] = new double[topics_];
    RandProb(theta_[i]);
  }
  phi_ = new double[topics_][];
  for (int i = 0; i < topics_; i++) {
    phi_[i] = new double[words_];
    RandProb(phi_[i]);
  }
  p_dwzpsi_ = new double[docs_.size()][][];
  for (int d = 0; d < docs_.size(); d++) {
    p_dwzpsi_[d] = new double[docs_.get(d).size()][];
    for (int i = 0; i < docs_.get(d).size(); i++) {
      p_dwzpsi_[d][i] = new double[2*topics_];
      // This one is not initialized because we begin with the E-step
    }
  }
}

private void srand48(long seed) {
	random = new Random(seed);
}

// Draws a random probability vector (uniformly distriubted).
void RandProb(double[] vec) {
  double norm = 0;
  for (int i = 0; i < vec.length; i++) {
    vec[i] = drand48();
    norm += vec[i];
  }
  Normalize(norm, vec);
}

private double drand48() {
	// TODO Auto-generated method stub
	return random.nextDouble();
}

// EM inference
void Infer() {
  for (int i = 0; i < iters_; i++) {
    EStep();
    MStep();
//    cout << "iteration " << i << ", loglikelihood = " << loglik_ << endl;
  }
}

// Prepares things for the Forward Backward and use it.
void EStep() {
  loglik_ = 0;
  for (int d = 0; d < docs_.size(); d++) {
    double ll = 0;
    EStepSingleDoc(d, ll);
    loglik_ += ll;
  }
  // Here we take into account the priors of the parameters when computing
  // the likelihood:
  IncorporatePriorsIntoLikelihood();
}

// Adds the Dirichlet priors to the likelihood computation.
// *loglik contains the observations likelihood computed using the forward
// backward algorithm. We now consider also the Dirichlet priors of the
// parameters.
void IncorporatePriorsIntoLikelihood() {
  // The prior on theta, assuming a symmetric Dirichlet distirubiton
  for (int d = 0; d < docs_.size(); d++) {
    for (int z = 0; z < topics_; z++) {
      loglik_ += (alpha_-1)*Math.log(theta_[d][z]);
    }
  }
  // The prior on phi, assuming a symmetric Dirichlet distirubiton
  for (int z = 0; z < topics_; z++) {
    for (int w = 0; w < words_; w++) {
      loglik_ += (beta_-1)*Math.log(phi_[z][w]);
    }
  }
}

// Given the parameters, find the most probable sequence of hidden states
void MAPTopicEstimate(int d, int[] best_path) {
  FastRestrictedViterbi f = new FastRestrictedViterbi();
  double[] dummy = new double[topics_];
  double[][] local = new double[docs_.get(d).size()][];
  double dummy_ll = 0.0;        // we don't care about the likelihood in this case.
  ComputeLocalProbsForDoc(d, local, dummy_ll);
  double[] init_probs = new double[topics_*2];
  for (int i = 0; i < topics_; i++) {
    init_probs[i] = theta_[d][i];
    init_probs[i+topics_] = 0;  // document must begin with a topic transition
  }
  f.Viterbi(epsilon_, theta_[d], local, init_probs, best_path);
}

// Performs the Estep for a single document, namely computes the posterior
// state probabilities using the efficient forward backward algorithm (the
// parameters are given from the Mstep).
void EStepSingleDoc(int d, double ll) {
  double[] dummy = new double[topics_];
  double[][] local = new double[docs_.get(d).size()][];
  double local_ll = 0;
  ComputeLocalProbsForDoc(d, local, local_ll);
  double[] init_probs = new double[topics_*2];
  for (int i = 0; i < topics_; i++) {
    init_probs[i] = theta_[d][i];
    init_probs[i+topics_] = 0;  // Document must begin with a topic transition.
  }
  FastRestrictedHMM f =  new FastRestrictedHMM();
  f.ForwardBackward(epsilon_, theta_[d], local, init_probs,
                    p_dwzpsi_[d], ll);
  ll = ll + local_ll;
}

// Computes the local probabilites for all the sentences of a particular
// document.
void ComputeLocalProbsForDoc(int d, double[][] locals,
                                 double ll) {
  for (int i = 0; i < docs_.get(d).size(); i++) {
    ComputeLocalProbsForItem(docs_.get(d).GetSentence(i), locals[i], ll);
  }
}

// This method is used to compute local probabilities for a word or for a
// sentece.
// Actually, we compute potentials rather than emission probabilities
// because we have to normalize them
void ComputeLocalProbsForItem(Sentence sen,
                                  double[]  local,
                                  double ll) {
  for (int z = 0; z < topics_; z++) {
    local[z] = 1;
  }
  Normalize(topics_, local);
  ll += Math.log(topics_);
  for (int i = 0; i < sen.size(); i++) {
    double norm = 0;
    int word = sen.GetWord(i);
    for (int z = 0; z < topics_; z++) {
      local[z] *= phi_[z][word];
      norm += local[z];
    }
    Normalize(norm, local);  // to prevent underflow
    ll += Math.log(norm);
  }
}

// The M-step of the EM algorithm for HTMM.
void MStep() {
  FindEpsilon();
  FindPhi();
  FindTheta();
}

// Finds the theta for all documents in the train set.
void FindTheta() {
  for (int d = 0; d < docs_.size(); d++) {
    FindSingleTheta(d);
  }
}

// Finds the MAP estimator for theta_d
void FindSingleTheta(int d) {
  double norm = 0;
  double[] Cdz = new double[topics_];
  CountTopicsInDoc(d, Cdz);
  for (int z = 0; z < topics_; z++) {
    theta_[d][z] = Cdz[z] + alpha_ - 1;
    norm += theta_[d][z];
  }
  Normalize(norm, theta_[d]);
}

// We count only the number of times when a new topic was drawn according to
// theta, i.e. when psi=1 (this includes the beginning of a document).
void CountTopicsInDoc(int d, double[] Cdz) {
  for (int z = 0; z < topics_; z++) {
    Cdz[z] = 0;
  }
  for (int i = 0; i < docs_.get(d).size(); i++) {
    for (int z = 0; z < topics_; z++) {
      // only psi=1
      Cdz[z] += p_dwzpsi_[d][i][z];
    }
  }
}

// This is code duplication!
// There's already a normalize method in the FastHMM class
void Normalize(double norm, double[] v) {
  double invnorm = 1.0 / norm;
  for (int i = 0; i < v.length; i++) {
    v[i] *= invnorm;
  }
}

// Finds the MAP estimator for epsilon.
void FindEpsilon() {
  int total = 0;
  double lot = 0;
  for (int d = 0; d < docs_.size(); d++) {
    //  we start counting from the second item in the document
    for (int i = 1; i < docs_.get(d).size(); i++) {
      for (int z = 0; z < topics_; z++) {
        // only psi=1
        lot += p_dwzpsi_[d][i][z];
      }
    }
    total += docs_.get(d).size()-1;      // Psi is always 1 for the first
                                      // word/sentence
  }
  epsilon_ = lot/total;
}

// Czw is already allocated and all its entries are initialized to 0.
void CountTopicWord(double[][] Czw) {
  // iterate over all sentences in corpus
  for (int d = 0; d < docs_.size(); d++) {
    for (int i = 0; i < docs_.get(d).size(); i++) {
      CountTopicWordInSentence(docs_.get(d).GetSentence(i), p_dwzpsi_[d][i], Czw);
    }
  }
}

// Counts how many times the pair (z,w) for a certain topic z and a certain
// word w appears in a certain sentence,
void CountTopicWordInSentence(Sentence sen,
                                  double[] topic_probs,
                                  double[][] Czw) {
  // Iterate over all the words in a sentence
  for (int n = 0; n < sen.size(); n++) {
    int w = sen.GetWord(n);
    for (int z = 0; z < topics_; z++) {
      // both psi=1 and psi=0
      Czw[z][w] += topic_probs[z]+topic_probs[z+topics_];
    }
  }
}

// Finds the MAP estimator for phi
void FindPhi() {
  double[] dummy = new double [words_];
  double[][] Czw = new double[topics_][];
  CountTopicWord(Czw);   // Czw is allocated and initialized to 0
  for (int z = 0; z < topics_; z++) {
    double norm = 0;
    for (int w = 0; w < words_; w++) {
      phi_[z][w] = Czw[z][w] + beta_ - 1;
      norm += phi_[z][w];
    }
    Normalize(norm, phi_[z]);
  }
}

// Saves theta in a file with extension .theta.
void SaveTheta(String fname) throws FileNotFoundException {
  String str = fname + ".theta";
  PrintWriter out = new PrintWriter(new FileOutputStream(str));
  out.println(docs_.size() + "\t" + topics_);
  for (int d = 0; d < docs_.size(); d++) {
    for (int z = 0; z < topics_; z++) {
      out.print(theta_[d][z] + "\t");
    }
    out.println();
  }
  out.close();
}

// Saves phi in a file with extension .phi.
void SavePhi(String fname) throws FileNotFoundException {
  String str = fname +  ".phi";
  PrintWriter out = new PrintWriter(new FileOutputStream(str));
  out.println(topics_ + "\t" + words_);
  for (int i = 0; i < topics_; i++) {
    for (int j = 0; j <words_; j++) {
      out.println(phi_[i][j] + "\t");
    }
    out.println();
  }
  out.close();
}

// Saves epsilon in a file with extension .eps.
void SaveEpsilon(String fname) throws FileNotFoundException {
  String str = fname +  ".eps";
  PrintWriter out = new PrintWriter(new FileOutputStream(str));
  out.println(epsilon_);
  out.close();
}

// Saves the log likelihood in a file with extension .ll.
void SaveLogLikelihood(String fname) throws FileNotFoundException {
  String str = fname +  ".ll";
  PrintWriter out = new PrintWriter(new FileOutputStream(str));
  out.println(loglik_);
  out.close();
}

// Saves all parameters and the distribution on hidden states.
void SaveAll(String base_name) throws FileNotFoundException {
  SaveTheta(base_name);
  SavePhi(base_name);
  SaveEpsilon(base_name);
  SaveTopicTransProbs(base_name);
  SaveLogLikelihood(base_name);
}

// Save the latent states probabilities.
void SaveTopicTransProbs(String fname) throws FileNotFoundException {
  String str = fname +  ".pdwz";
  PrintWriter out = new PrintWriter(new FileOutputStream(str));
  for (int d = 0; d < docs_.size(); d++) {
    out.println("d = " + d);
    for (int i = 0; i < docs_.get(d).size(); i++) {
      out.println("i = " + i);
      for (int z = 0; z < topics_*2; z++) {
        if (z >= topics_) {
          out.print("*");
        }
        out.print(p_dwzpsi_[d][i][z] + "\t");
      }
      out.println();
    }
    out.println();
  }
  out.close();
}

}  // namespace
