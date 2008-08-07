package org.donnchadh.esslli2008.statmt;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.csvreader.CsvReader;

public class IBMModel1 {
	public static void main(String[] args) throws IOException {
		//
		// initialize t(e|f) uniformly
//		do until convergence
//		  set count(e|f) to 0 for all e,f
//		  set total(f) to 0 for all f
//		  for all sentence pairs (e_s,f_s)
//		    for all words e in e_s
//		      total_s(e) = 0
//		      for all words f in f_s
//		        total_s(e) += t(e|f)
//		    for all words e in e_s
//		      for all words f in f_s
//		        count(e|f) += t(e|f) / total_s(e)
//		        total(f)   += t(e|f) / total_s(e)
//		  for all f
//		    for all e
//		      t(e|f) = count(e|f) / total(f)

		String[][][] sentencePairs = {
				{{"He", "opened", "the", "door"},{"d'oscail","sé", "an", "doras"}},
				{{"He", "is", "cold"},{"Tá","sé", "fuar"}},
				{{"The", "weather", "is", "cold"},{"Tá", "an", "aimsir","fuar"}},
				{{"It", "is", "cold"},{"Tá", "sé", "fuar"}},
				{{"He", "is", "hot"},{"Tá", "sé", "teith"}},
				{{"The", "dog", "is", "hot"},{"Tá", "an", "madra", "teith"}},
				{{"Close","the","door"},{"Dún","an", "doras"}},
				{{"Switch","off","the","light"},{"Múch","an", "solas"}},
				{{"Switch","on","the","light"},{"Las","an", "solas"}},
				};
		sentencePairs = readSentencePairs();
		double[][] result = evaluate(sentencePairs);
		printResults(sentencePairs, result);
		//
	}

	private static String[][][] readSentencePairs() throws IOException {
        CsvReader csvReader = new CsvReader(new FileInputStream("sentencePairs.csv"), Charset.forName("UTF-8"));
//        csvReader.readHeaders();
//        String[] header = csvReader.getHeaders();
    	List<String[][]> pairs = new ArrayList<String[][]>();
        while (csvReader.readRecord()) {
            String[] values = csvReader.getValues();
            pairs.add(new String[][]{values[0].split("\\s"),values[1].split("\\s")});
        }
		return pairs.toArray(new String[pairs.size()][][]);
	}

	private static void printResults(String[][][] sentencePairs,
			double[][] result) {
		String[][] e_s = englishSentences(sentencePairs);
		String[][] f_s = foreignSentences(sentencePairs);
		String[] ew = uniqueWords(e_s);
		String[] fw = uniqueWords(f_s);
		for (int i =0; i < result.length; i++) {
			System.out.print(ew[i]);
			System.out.print(": ");
			for (int j = 0; j < result[i].length; j++) {
				if (result[i][j] > 0.01d) {
					System.out.print(fw[j]);
					System.out.print("(");
					System.out.print(result[i][j]);
					System.out.print(")");
				}
			}
			System.out.println();
		}
	}

	public static double[][] evaluate(SentencePair[] sentencePairs) {
		String[][][] sentencePairArrays = new String[sentencePairs.length][2][];
		for (int i = 0; i < sentencePairArrays.length; i++) {
			sentencePairArrays[i][0] = sentencePairs[i].getEnglishSentence().getWords();
			sentencePairArrays[i][1] = sentencePairs[i].getForeignSentence().getWords();
		}
		return evaluate(sentencePairArrays);
	}
	
	private static double[][] evaluate(String[][][] sentencePairWords) {
		String[][] e_s = englishSentences(sentencePairWords);
		String[][] f_s = foreignSentences(sentencePairWords);
		String[] ew = uniqueWords(e_s);
		String[] fw = uniqueWords(f_s);
		int[][] e_s_i = buildWordIndexArray(e_s, ew);
		int[][] f_s_i = buildWordIndexArray(f_s, fw);
		System.out.println(ew.length + " english words, " + fw.length + " irish words.");
		// initialize t(e|f) uniformly
		double[][] wordTranslationProbabilities = new double[ew.length][fw.length];
		for (int i = 0; i < wordTranslationProbabilities.length; i++) {
			for (int j = 0; j < wordTranslationProbabilities[i].length; j++) {
				wordTranslationProbabilities[i][j] = 1.0d/(double)ew.length;
			}
		}
		double[][] previousWordTranslationProbabilities = new double[ew.length][fw.length];
//		do until convergence
		int iteration = 0;
		do {
			for (int i = 0; i < wordTranslationProbabilities.length; i++) {
				System.arraycopy(wordTranslationProbabilities[i], 0, previousWordTranslationProbabilities[i], 0, 
						wordTranslationProbabilities[i].length);
			}
//			  set count(e|f) to 0 for all e,f
			double counts [][] = new double[ew.length][fw.length];
//		  set total(f) to 0 for all f
			double totals[] = new double[fw.length];
//		  for all sentence pairs (e_s,f_s)
			for (int x = 0; x < sentencePairWords.length; x++) {
				if ((x%500) == 0) {
					System.out.print(x);
				}
					String[][] sentencePair = sentencePairWords[x];
					String[] englishSentence = sentencePair[0];
					String[] foreignSentence = sentencePair[1];
					double[] total_s = new double[englishSentence.length]; 
//				    for all words e in e_s
					for (int e = 0; e < englishSentence.length; e++) {
//				      total_s(e) = 0
						total_s[e] = 0;
//				      for all words f in f_s
						for (int f = 0; f < foreignSentence.length; f++) {
//				        total_s(e) += t(e|f)
							int englishWordIndex = e_s_i[x][e];
							int foreignWordIndex = f_s_i[x][f];
							total_s[e] += wordTranslationProbabilities[englishWordIndex][foreignWordIndex];
						}
					}
//				    for all words e in e_s
						for (int e = 0; e < englishSentence.length; e++) {
//				      for all words f in f_s
							for (int f = 0; f < foreignSentence.length; f++) {
//				        count(e|f) += t(e|f) / total_s(e)
								int englishWordIndex = e_s_i[x][e];
								int foreignWordIndex = f_s_i[x][f];
								double product = wordTranslationProbabilities[englishWordIndex][foreignWordIndex]/total_s[e];
								counts[englishWordIndex][foreignWordIndex] += product;
//				        total(f)   += t(e|f) / total_s(e)
								totals[foreignWordIndex] += product;
						}
				}
			}
//		    for all e
				for (int _e = 0; _e < ew.length; _e++) {
//		  for all f
					for (int _f = 0; _f < fw.length; _f++) {
//		      t(e|f) = count(e|f) / total(f)
				  wordTranslationProbabilities[_e][_f] = counts[_e][_f]/totals[_f];
			  }
		  }
			System.out.println(".");
			System.out.println(iteration++);
		} while (!converged(wordTranslationProbabilities, previousWordTranslationProbabilities));
		return wordTranslationProbabilities;
	}

	private static int[][] buildWordIndexArray(String[][] e_s, String[] ew) {
		int[][] e_s_i = new int[e_s.length][];
		for (int i = 0; i < e_s_i.length; i++) {
			e_s_i[i] = new int[e_s[i].length];
			for (int j = 0; j < e_s_i[i].length; j++) {
				e_s_i[i][j] = indexOf(e_s[i][j], ew);
			}
		}
		return e_s_i;
	}

	private static int indexOf(String string, String[] ew) {
		return Arrays.binarySearch(ew, string);
	}

	private static boolean converged(double[][] wordTranslationProbabilities,
			double[][] previousWordTranslationProbabilities) {
		double count = 0;
		double totalSquares = 0;
		for (int i = 0; i < wordTranslationProbabilities.length; i++) {
			for (int j = 0; j < wordTranslationProbabilities[i].length; j++) {
				count += 1;
				double absoluteChange = wordTranslationProbabilities[i][j]-previousWordTranslationProbabilities[i][j];
				if (absoluteChange > 0.0000000000001d) {
					double change = absoluteChange/((previousWordTranslationProbabilities[i][j]+wordTranslationProbabilities[i][j])/2);
					totalSquares += change*change;
				}
			}
		}
		double rmsDifference = Math.sqrt(totalSquares/count);
		System.out.println(rmsDifference);
		return rmsDifference < 0.0005;
	}

	private static String[] uniqueWords(String[][] e_s) {
		Set<String> words = new TreeSet<String>(); 
		for (String[] s : e_s) {
			for (String w : s) {
				words.add(w);
			}
		}
		return words.toArray(new String[words.size()]);
	}

	private static String[][] englishSentences(String[][][] sentencePairs) {
		return listSentences(sentencePairs, 0);
	}

	private static String[][] foreignSentences(String[][][] sentencePairs) {
		return listSentences(sentencePairs, 1);
	}

	private static String[][] listSentences(String[][][] sentencePairs, int index) {
		String[][] englishSentences = new String[sentencePairs.length][];
		for (int i = 0; i < englishSentences.length; i++) {
			englishSentences[i] = sentencePairs[i][index];
		}
		return englishSentences;
	}

}
