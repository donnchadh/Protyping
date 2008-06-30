package com.google.htmm.arraybased.refactored;

import java.io.FileNotFoundException;
import java.io.IOException;

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

// Driver for EM approximate inference for HTMM
// Analyses train data and saves the parameters and posterior distributions

public class HTMMDriver {
	public static void main(String[] args) throws IOException {
	  if (args.length != 9) {
	    System.err.print("Usage: " + args[0]
	         + " topics words alpha beta iters train_file out_file working_dir\n");
	    System.exit(1);
	  }
	  int topics = Integer.parseInt(args[1]);           // number of topics
	  int words = Integer.parseInt(args[2]);            // number of words in vocabulary
	  double alpha = Double.parseDouble(args[3]);         // Dirichlet prior
	  double beta = Double.parseDouble(args[4]);          // Dirichlet prior
	  int iters = Integer.parseInt(args[5]);            // number of EM iterations
	  String train = args[6];          // file with a list of train docs
	  String out_file = args[7];       // base name for output files
	  String working_dir = args[8];
	  EM em = new EM();
	  // Use clock to initialise random seed.
	  em.init(topics,words,alpha,beta,iters,train,working_dir,0);
	  em.infer();
	  em.SaveAll(out_file);
	}
}