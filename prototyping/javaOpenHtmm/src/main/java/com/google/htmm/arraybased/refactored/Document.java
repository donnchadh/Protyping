package com.google.htmm.arraybased.refactored;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Copyright 2007 Google Inc.
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not ue this file except in compliance with the License.
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

public class Document {
	List<Sentence> data_ = new ArrayList<Sentence>(); // the basic unit is a
														// sentence,

	public Document() {
	}
	
	public Document(List<Sentence> data_) {
		this.data_.addAll(data_);
	}
	
	// it could also consist of a single word

	// Retrieves the i sentence of the document.
	Sentence getSentence(int i) {
		return data_.get(i);
	}

	// Returns the number of sentences in the document.
	int size() {
		return data_.size();
	}

	// Initialization: Reads a sequence of sentences from a file.
	void init(String fname) throws IOException {
		FileInputStream in = new FileInputStream(fname);
		// if (!in || in.fail()) {
		// ERROR("can't open file " << fname << " for reading");
		// }

		Scanner scanner = new Scanner(in);
		init(scanner);
		// The last sentence object corresponds to eof and was not added to the
		// document.

		in.close();
	}

	private void init(Scanner scanner) {
		List<Sentence> sentences = readSentences(scanner);
		data_.addAll(sentences);
	}

	static List<Sentence> readSentences(Scanner scanner) {
		List<Sentence> sentences =  new ArrayList<Sentence>();
		while (scanner.hasNext()) {
			Sentence s = readSentence(scanner);
			sentences.add(s);
		}
		return sentences;
	}

	Sentence readSentence(FileInputStream in) {
		Scanner scanner = new Scanner(in);
		return readSentence(scanner);
	}

	private static Sentence readSentence(Scanner scanner) {
		Sentence s = new Sentence();
		int words = scanner.nextInt();
		if (!scanner.hasNext()) {
			return null;
		}
		s.init(words);
		for (int i = 0; i < words; i++) {
			int w = scanner.nextInt();
			s.addWord(i, w - 1); // We subtract one due to the range of input
									// in
			// data (1..N)
		}
		return s;
	}

} // namespace
