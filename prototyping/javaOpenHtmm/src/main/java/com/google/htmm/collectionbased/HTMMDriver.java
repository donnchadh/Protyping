package com.google.htmm.collectionbased;

public class HTMMDriver {
	public static void main(String[] args) {
		  if (args.length != 9) {
		    System.err.println("Usage: <appname> topics words alpha beta iters train_file out_file working_dir\n");
		    System.exit(1);
		  }
		  int topics = Integer.parseInt(args[0]);           // number of topics
		  int words = Integer.parseInt(args[1]);            // number of words in vocabulary
		  double alpha = Double.parseDouble(args[2]);         // Dirichlet prior
		  double beta = Double.parseDouble(args[3]);          // Dirichlet prior
		  int iters = Integer.parseInt(args[4]);            // number of EM iterations
		  String train = args[5];          // file with a list of train docs
		  String out_file = args[6];       // base name for output files
		  String working_dir = args[7];
		  EM em = new EM();
		  // Use clock to initialise random seed.
		  em.init(topics,words,alpha,beta,iters,train,working_dir,0);
		  em.infer();
		  em.SaveAll(out_file);
		}
}
