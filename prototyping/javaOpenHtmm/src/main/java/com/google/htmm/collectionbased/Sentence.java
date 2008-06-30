package com.google.htmm.collectionbased;

import java.util.ArrayList;
import java.util.List;

public class Sentence {
	 private final List<Integer> data;                    // The words of the sentence.
	 public Sentence() {
		 data = new ArrayList<Integer>();
	 }

	 public Sentence(Sentence s) {
		 data  = new ArrayList<Integer>(s.data);
	 }

		  // Sets the number of words in the sentence (allocates space for them).
	 public void init(int words) {
		 data.set(words-1, Integer.valueOf(0));
	 }

		  // Returns the number of words in the sentence.
	 public int size() { return data.size(); }

		  // Returns the i word of the sentence.
	 public int GetWord(int i) { return data.get(i); }

		  // Adds the word 'word' as the i'th word of the sentence.
	 public void AddWord(int i, int word) {
		 
	 }

}
