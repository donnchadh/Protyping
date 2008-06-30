package com.google.htmm.collectionbased;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EM extends HTMM {
	  // Data members:
	  private int iters_;                           // number of EM iterations
	  private List<List<Double> > theta_;       // estimated theta
	  private List<List<Double> > phi_;         // estimated phi
	  private double epsilon_;                      // estimated epsilon
	  private List<List<List<Double> > > p_dwzpsi_;  // The state probabilities,
	           // that is Pr(z,psi | d,w). The weird name reflects the arrangements
	           // of these probabilities in this 3D array.
	  private double loglik_;                       // loglikelihood

		  public EM() {}

		  // Initialization: The documents are read, hyper parameters and number of
		  // iterations are set and the parameters are set to random variables.
		  // If we want to repeat a specific run, the argument seed is used to specify
		  // the random seed. Otherwise we specify seed=0 and the random generator is
		  // initialized according to the clock.

		  public void init(int topics, int words, double alpha, double beta, int iters,
		            String fname, String data_dir, int seed) {
			  super.init(topics, words, alpha, beta, fname, data_dir);
			  iters_ = iters;
			  // allocate and initialize phi, theta, state probs:
			  RandInitParams(seed);
		  }

		  // EM inference for HTMM
		  public  void infer() {
			  for (int i = 0; i < iters_; i++) {
				    EStep();
				    MStep();
//				    cout << "iteration " << i << ", loglikelihood = " << loglik_ << endl;
				  }
		  }

		  // Saves all parameters and the distribution on hidden states.
		  public void SaveAll(String base_name) {
			  SaveTheta(base_name);
			  SavePhi(base_name);
			  SaveEpsilon(base_name);
			  SaveTopicTransProbs(base_name);
			  SaveLogLikelihood(base_name);
		  }

		  // Methods:
		  // E-step of the algorithm
		  protected void EStep() {
			  loglik_ = 0;
			  for (int d = 0; d < docs.size(); d++) {
			    double ll = 0;
			    ll = EStepSingleDoc(d, ll);
			    loglik_ += ll;
			  }
			  // Here we take into account the priors of the parameters when computing
			  // the likelihood:
			  IncorporatePriorsIntoLikelihood();
		  }

		  // Computes expectations and contribution to the likelihood for a single
		  // document.
		  protected double EStepSingleDoc(int d, double ll) {
			  List<Double> dummy = new ArrayList<Double>(topics);
//			  List<List<Double> > local = new ArrayList<List<Double>>(docs.get(d).size(), dummy);
			  List<List<Double> > local = new ArrayList<List<Double>>(docs.get(d).size());
			  double local_ll = 0;
			  ComputeLocalProbsForDoc(d, local, local_ll);
			  List<Double> init_probs = new ArrayList<Double>(topics*2);
			  for (int i = 0; i < topics; i++) {
			    init_probs.set(i, theta_.get(d).get(i));
			    init_probs.set(i+topics, Double.valueOf(0));  // Document must begin with a topic transition.
			  }
			  FastRestrictedHMM f = new FastRestrictedHMM();
			  f.ForwardBackward(epsilon_, theta_.get(d), local, init_probs,
			                    (p_dwzpsi_.get(d)), ll);
			  ll = ll + local_ll;
			  return ll;
		  }

		  // M-step of the algorithm
		  protected void MStep() {
			  FindEpsilon();
			  FindPhi();
			  FindTheta();
		  }

		  // Finds the MAP estimator for all thetas.
		  protected void FindTheta() {
			  for (int d = 0; d < docs.size(); d++) {
				    FindSingleTheta(d);
				  }
		  }

		  // Finds the MAP estimator for theta_d.
		  protected void FindSingleTheta(int d) {
			  double norm = 0;
			  List<Double> Cdz = new ArrayList<Double>(topics);
			  CountTopicsInDoc(d, Cdz);
			  for (int z = 0; z < topics; z++) {
			    theta_[d][z] = Cdz[z] + alpha_ - 1;
			    norm += theta_[d][z];
			  }
			  Normalize(norm, theta_.get(d));
		  }

		  // Finds the MAP estimator for epsilon.
		  protected void FindEpsilon() {
			  int total = 0;
			  double lot = 0;
			  for (int d = 0; d < docs.size(); d++) {
			    //  we start counting from the second item in the document
			    for (int i = 1; i < docs.get(d).size(); i++) {
			      for (int z = 0; z < topics; z++) {
			        // only psi=1
			        lot += p_dwzpsi_.get(d).get(i).get(z);
			      }
			    }
			    total += docs.get(d).size()-1;      // Psi is always 1 for the first
			                                      // word/sentence
			  }
			  epsilon_ = lot/total;
		  }

		  // Finds the MAP estimator for all phi.
		  protected void FindPhi() {
			  List<Double> dummy = new ArrayList<Double>(words);
			  List<List<Double> > Czw = new ArrayList<List<Double>>(topics);
			  CountTopicWord(Czw);   // Czw is allocated and initialized to 0
			  for (int z = 0; z < topics_; z++) {
			    double norm = 0;
			    for (int w = 0; w < words_; w++) {
			      phi_[z][w] = Czw[z][w] + beta_ - 1;
			      norm += phi_[z][w];
			    }
			    Normalize(norm, &(phi_[z]));
			  }
		  }

		  // Counts (the expectation of) how many times a topic was drawn from
		  // theta in a certain document.
		  void CountTopicsInDoc(int d, List<Double> Cdz) {
			  for (int z = 0; z < topics; z++) {
				    (*Cdz)[z] = 0;
				  }
				  for (int i = 0; i < docs[d]->size(); i++) {
				    for (int z = 0; z < topics; z++) {
				      // only psi=1
				      (*Cdz)[z] += p_dwzpsi_[d][i][z];
				    }
				  }
		  }

		  // Counts (the expectation of) how many times the each pair (topic, word)
		  // appears in a certain sentence.
		  void CountTopicWordInSentence(Sentence sen,
		                                List<Double> topic_probs,
		                                List<List<Double> > Czw) {
			  // Iterate over all the words in a sentence
			  for (int n = 0; n < sen.size(); n++) {
			    int w = sen.GetWord(n);
			    for (int z = 0; z < topics; z++) {
			      // both psi=1 and psi=0
			      (*Czw)[z][w] += topic_probs[z]+topic_probs[z+topics_];
			    }
			  }
		  }

		  // Counts (the expectation of) how many times the each pair (topic, word)
		  // appears in the whole corpus.
		  protected void CountTopicWord(List<List<Double> > Czw) {
			  // iterate over all sentences in corpus
			  for (int d = 0; d < docs.size(); d++) {
			    for (int i = 0; i < docs[d]->size(); i++) {
			      CountTopicWordInSentence(docs_[d]->GetSentence(i), p_dwzpsi_[d][i], Czw);
			    }
			  }
		  }

		  // Compute the emission (local) probabilities for a certain document.
		  protected double ComputeLocalProbsForDoc(int d, List<List<Double> > probs,
		                               double ll) {
			  for (int i = 0; i < docs.get(d).size(); i++) {
				    ll = ComputeLocalProbsForItem(docs.get(d).GetSentence(i), &((*locals)[i]), ll);
				  }
			  return ll;
		  }

		  // Compute the emission (local) probabilities for a certain sentence.
		  protected double ComputeLocalProbsForItem(Sentence sen,
		                                List<Double>  local, double ll) {
			  for (int z = 0; z < topics; z++) {
				    (*local)[z] = 1;
				  }
				  Normalize(topics_, local);
				  *ll += log(topics_);
				  for (int i = 0; i < sen.size(); i++) {
				    double norm = 0;
				    int word = sen.GetWord(i);
				    for (int z = 0; z < topics_; z++) {
				      (*local)[z] *= phi_[z][word];
				      norm += (*local)[z];
				    }
				    Normalize(norm, local);  // to prevent underflow
				    *ll += log(norm);
				  }
				return ll;
		  }

		  // Saves theta in a file with extension .theta
		  protected void SaveTheta(String fname) {
			  
		  }

		  // Saves phi in a file with extension .phi.
		  protected void SavePhi(String fname) {
			  
		  }

		  // Saves epsilon in a file with extension .eps.
		  protected void SaveEpsilon(String fname) {
			  
		  }

		  // Saves the latent states probabilities.
		  protected void SaveTopicTransProbs(String fname) {
			  
		  }

		  // Saves the log likelihood.
		  protected void SaveLogLikelihood(String fname) {
			  
		  }

		  // Randomly initializes the parameters (phi, theta and epsilon) and allocates
		  // space for the state probabilities.
		  // See comments above (for init) regarding the random seed.
		  protected void RandInitParams(int seed) {
			  // If seed is 0 use the clock to initialize the random generator.
			  if (seed == 0) {
			    seed = time(0);
			  }
			  srand48(seed);
			  epsilon_ = drand48();
			  theta_.resize(docs_.size());
			  for (int i = 0; i < docs_.size(); i++) {
			    theta_[i].resize(topics_);
			    RandProb(&(theta_[i]));
			  }
			  phi_.resize(topics_);
			  for (int i = 0; i < topics_; i++) {
			    phi_[i].resize(words_);
			    RandProb(&(phi_[i]));
			  }
			  p_dwzpsi_.resize(docs_.size());
			  for (int d = 0; d < docs_.size(); d++) {
			    p_dwzpsi_[d].resize(docs_[d]->size());
			    for (int i = 0; i < docs_[d]->size(); i++) {
			      p_dwzpsi_[d][i].resize(2*topics_);
			      // This one is not initialized because we begin with the E-step
			    }
			  }
		  }

		  // Draws a random probability vector (uniformly distriubted).
		  protected void RandProb(List<Double> vec) {
			  double norm = 0;
			  for (int i = 0; i < vec.size(); i++) {
			    double randomValue = new Random().nextDouble();
				vec.set(i, randomValue);
			    norm += randomValue;
			  }
			  Normalize(norm, vec);
		  }

		  // Given the parameters, finds the most probable sequence of hidden states.
		  protected void MAPTopicEstimate(int d, List<Integer> best_path) {
			  FastRestrictedViterbi f = new FastRestrictedViterbi();
			  List<Double> dummy = new ArrayList<Double>(topics);
			  List<List<Double> > local = new ArrayList<List<Double>>(docs.get(d).size());
			  double dummy_ll;        // we don't care about the likelihood in this case.
			  ComputeLocalProbsForDoc(d, local, dummy_ll);
			  List<Double> init_probs = new ArrayList<Double>(topics*2);
			  for (int i = 0; i < topics; i++) {
			    init_probs[i] = theta_[d][i];
			    init_probs[i+topics_] = 0;  // document must begin with a topic transition
			  }
			  f.Viterbi(epsilon_, theta_[d], local, init_probs, best_path);
		  }

		  // Adds the Dirichlet priors to the likelihood computation.
		  protected void IncorporatePriorsIntoLikelihood() {
			  // The prior on theta, assuming a symmetric Dirichlet distirubiton
			  for (int d = 0; d < docs.size(); d++) {
			    for (int z = 0; z < topics; z++) {
			      loglik_ += (alpha-1)*log(theta_[d][z]);
			    }
			  }
			  // The prior on phi, assuming a symmetric Dirichlet distirubiton
			  for (int z = 0; z < topics_; z++) {
			    for (int w = 0; w < words_; w++) {
			      loglik_ += (beta_-1)*log(phi_[z][w]);
			    }
			  }
		  }

		  // I'm not sure if that's the place for this method!
		  protected void Normalize(double norm, List<Double> v) {
			  double invnorm = 1.0 / norm;
			  // TODO apply closure
			  for (int i = 0; i < v.size(); i++) {
			    v.set(i, invnorm*v.get(0));
			  }
		  }

}
