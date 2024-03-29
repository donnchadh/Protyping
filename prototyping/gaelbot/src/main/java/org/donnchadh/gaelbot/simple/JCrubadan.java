package org.donnchadh.gaelbot.simple;

public class JCrubadan {

	// Initially a small collection of "seed" texts are fed to the crawler (a
	// few hundred words of running text have been sufficient in practice).
	// Queries combining words from these texts are generated and passed to the
	// Google API which returns a list of documents potentially written in the
	// target language. 
	// These are downloaded, processed into plain text, and formatted.
	// A combination of statistical techniques bootstrapped from the initial seed
	// texts (and refined as more texts are added to the database)  is used to
	// determine which documents (or sections thereof) are written in the target
	// language.
	// The crawler then recursively follows links contained within documents that
	// are in the target language. 
	// When these run out, the entire process is repeated, with a new set of Google
	// queries generated from the new, larger corpus.
// -----
	// First, statistics measuring co-occurrence with the highest frequency
	// words in the target language are used to filter out sections written in
	// other languages or containing mostly noise (e.g. computer code, tabular
	// data, etc.). 
	// The remaining text is tokenized and used to generate a word
	// list sorted by frequency and the lowest frequency words are filtered out.
	// Then, depending on the target language, correctly-spelled words from one
	// or more "polluting" languages are filtered out to be checked by hand
	// later. Usually this means English, but I also filter Dutch from the
	// Frisian corpus, Spanish from Chamorro, etc. The remaining words are used
	// to generate 3-gram statistics for the target language. These are used to
	// flag as "suspect" any remaining words containing one or more improbable
	// 3-grams. Additional filters specific to certain languages can be applied
	// optionally; for instance, pairs of words differing only in the presence
	// or absence of diacritical marks can be flagged, or words with a capital
	// letter appearing after the first letter, words with no vowels, etc.
}
