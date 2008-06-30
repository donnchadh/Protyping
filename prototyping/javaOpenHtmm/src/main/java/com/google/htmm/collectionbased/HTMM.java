package com.google.htmm.collectionbased;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public abstract class HTMM {
	  // Data members:
	  protected int topics;                          // number of topic
	  protected int words;                           // number of words in vocabulary
	  protected List<Document> docs;             // the set of train documents
	  protected double alpha;                        // the symmetric dirichlet prior
	  protected double beta;                         // the symmetric dirichlet prior

	  public HTMM() {}
	  
	  public abstract void infer();  //  this can be EM inference,
	                             //  Collapsed Gibbs sampling or whatever.
	  // init can now be called only by superclasses
	  // fname is the name of the file that specifies the locations of
	  // the train documents.
	  protected void init(int topics, int words, double alpha, double beta,
	            String fname, String data_dir) throws IOException {
		  this.topics = topics;
		  this.words = words;
		  this.alpha = alpha;
		  this.beta = beta;
		  ReadTrainDocuments(fname, data_dir);
	  }

	  // Reads the train documents whose location is specified in the given file.
	  protected void ReadTrainDocuments(String fname, String data_dir) throws IOException {
		  String full_path = fname;
		  FileInputStream in = new FileInputStream(full_path);
//		  if (!in || in.fail()) {
//		    ERROR("can't open file " << full_path << " for reading");
//		  }
		  Scanner scanner = new Scanner(in);
		  String str;
		  for (str = scanner.next(); scanner.hasNext(); str = scanner.next()) {
		    Document d = new Document();
		    str = data_dir + str;
		    d.init(str);
		    docs.add(d);
		  }
		  in.close();
	  }

}
