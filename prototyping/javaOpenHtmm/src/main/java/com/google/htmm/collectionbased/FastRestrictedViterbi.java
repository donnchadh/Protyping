package com.google.htmm.collectionbased;

import java.util.List;

//Find the most probable path in a Hidden Markov Model
//with a special form transition matrix.
//The run time is O(T*N)

//Assumptions:
//(1) The transition matrix is of the special form described in the HTMM
//paper, i.e. consists of entries that depend on theta and epsilon only
//(2) best_path is a pre allocated vector of length T
//(3) theta is a probability vector of length topics.
//(4) local is a vector of T probability vectors, each of which is of length
//topics and contains the emissions probabilities.
//(5) pi is a probability vector of length states=2*topics that specifies the
//prior distribution on the first hidden state in the chain.
public class FastRestrictedViterbi {
	  // The length of the Markov chain
	  private int T_;
	  // Number of topics
	  private int topics_;
	  // Number of states which is always 2*topics_ (for psi=0/1)
	  private int states_;
		  public FastRestrictedViterbi() {}
		  // Run the Viterbi algorithm on the HMM specified by the arguments of the
		  // method (see details above) and return the MAP estimator in the vector
		  // best_path
		  public void Viterbi(double epsilon, final List<Double> theta,
		               final List<List<Double> > local,
		               final List<Double> pi,
		               final List<Integer> best_path) {
			  
		  }
		  // Find the state at which the most probable path up to a certain
		  // level ends.
		  private void FindBestInLevel(final List<Double> delta_t_1, int prev_best) {
			  
		  }
		  // Compute the probability of the most probable path ending in each node
		  // at a certain level t. For each node at level t store the previous
		  // node in the most probable path (the node at level t-1).
		  private void ComputeSingleDelta(final List<Double> local_t,
		                          final List<Double> theta, double epsilon,
		                          final List<Double> delta_t_1,
		                          final List<Double> delta_t, final List<Integer> best) {
			  
		  }
		  // Perofrm a single forward path of the Viterbi algorithm on the HMM
		  private void ComputeAllDeltas(final List<Double> pi,
				  final List<List<Double> > local,
				  final List<Double> theta, double epsilon,
				  final List<List<Double>> delta,
				  final List<List<Integer>> best) {
			  
		  }
		  // Initialize the Viterbi algorithm for level 0.
		  private void InitDelta(final List<Double> pi, final List<Double> local0,
				  final List<Double> delta0) {
			  
		  }
		  // After the forward pass has finished we track back to get all the nodes
		  // of the most probable series of states in the HMM
		  private void BackTrackBestPath(final List<Double> delta_T_1,
				  final List<List<Integer>> best,
				  final List<Integer> best_path) {
			  
		  }
		  // Divide all the numbers in the vector vec (scaled probabilities) by
		  // the factor norm (to sum to 1).
		  private void Normalize(double norm, List<Double> vec) {
			  
		  }
}
