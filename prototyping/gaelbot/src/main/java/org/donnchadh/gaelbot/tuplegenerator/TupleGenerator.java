package org.donnchadh.gaelbot.tuplegenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csvreader.CsvReader;

public class TupleGenerator {
	public static void main(String[] args) throws IOException {
		Collection<String[]> tuples = new TupleGenerator().generateTuples(new File("focail.csv"), 4, 5000);
		writeTuples(tuples, new File("tuples.csv"));
	}
	
	private static void writeTuples(Collection<String[]> tuples, File file) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(file, "UTF-8");
		try {
			for (String[] tuple : tuples) {
				for (int i = 0; i < tuple.length; i++) {
					if (i != 0) {
						writer.print(',');
					}
					writer.print(tuple[i]);
				}
				writer.println();
			}	
		} finally {
			writer.close();
		}
	}

	public Collection<String[]> generateTuples(File wordFile, int tupleSize, int numTuples) {
		String[] words = readWords(wordFile);
		return generateTuples(words, tupleSize, numTuples);
	}
	
	private String[] readWords(File wordFile) {
		List<String> words = new ArrayList<String>();
		try {
			CsvReader csvReader = new CsvReader(new FileInputStream(wordFile), Charset.forName("UTF-8"));
			while (csvReader.readRecord()) {
	            String[] values = csvReader.getValues();
	            words.add(values[1]);
	        }
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
        return words.toArray(new String[words.size()]);
	}

	public Collection<String[]> generateTuples(String[] words, int tupleSize, int numTuples) {
		Map<String, String[]> tuples = new HashMap<String, String[]>();
		SecureRandom randomNumberGenerator = new SecureRandom(new SecureRandom().generateSeed(13));
		for (int i = 0; i < numTuples; ) {
			int[] wordIndices = new int[tupleSize];
			for (int j = 0; j < tupleSize; ) {
				int candidate = randomNumberGenerator.nextInt(words.length);
				boolean skip = false;
				for (int k = 0; k < tupleSize; k++) { 
					if (wordIndices[k] == candidate) {
						skip = true;
					}
				}
				if (!skip) {
					wordIndices[j] = candidate;
					j++;
				}
			}
			String[] tuple = new String[tupleSize];
			for (int j = 0; j < tupleSize; j++) {
				tuple[j] = words[wordIndices[j]];
			}
			if (!tuples.containsKey(Arrays.toString(tuple))) {
				tuples.put(Arrays.toString(tuple), tuple);
				i++;
			}
		}
		return tuples.values();
	}
}
