package com.google.htmm.collectionbased;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Document {
	protected final List<Sentence> data; // the basic unit is a sentence,

	// it could also consist of a single word

	public Document() {
		data = new ArrayList<Sentence>();
	}

	// Initialization: reads a sequence of sentences from a file.
	public void init(String fname) throws IOException {
		FileInputStream in = new FileInputStream(fname);
		try {
		
		  Sentence s = new Sentence();
		  Scanner scanner = new Scanner(in);
		  for (ReadSentence(in, s); scanner.hasNext(); ReadSentence(in, s)) {
		    data.add(s);
		    s = new Sentence();
		  }
		  // The last sentence object corresponds to eof and was not added to the
		  // document.
		} finally {
		  in.close();
		}
	}

	// Retrieves the i sentence of the document.
	public Sentence GetSentence(int i) {
		return data.get(i);
	}

	// Returns the number of sentences in the document.
	public int size() {
		return data.size();
	}

	// not to be use in subclasses
	private void ReadSentence(InputStream in, Sentence s) {
		  Scanner scanner = new Scanner(in);
		  int words = scanner.nextInt();
		  if (!scanner.hasNext()) {
		    return;
		  }
		  s.init(words);
		  for (int i = 0; i < words; i++) {
		    int w = scanner.nextInt();
		    s.AddWord(i, w-1);  // We subtract one due to the range of input in
		                         // data (1..N)
		  }
	}
}
