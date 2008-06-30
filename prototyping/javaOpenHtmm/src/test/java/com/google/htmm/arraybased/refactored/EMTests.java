package com.google.htmm.arraybased.refactored;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class EMTests extends TestCase {
	private EM em;
	
	@Override
	protected void setUp() throws Exception {
		int topics = 20;
		int words = 1000;
		double alpha = 0;
		double beta = 0;
		List<Document> docs = new ArrayList<Document>();
		List<Sentence> sentences = new ArrayList<Sentence>();
		sentences.add(new Sentence(new int[]{3,5,99,5,5,66,2,1}));
		docs.add(new Document(sentences));
		int iters = 100;
		int seed = 0;
		em = new EM(topics, words, alpha, beta, docs, iters, seed);
	}
	
	public void testInfer() throws Exception {
		em.infer();
	}
}
