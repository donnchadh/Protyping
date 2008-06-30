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

public class HTMM {

	protected List<Document> docs_ = new ArrayList<Document>(); // the set of
																// train
																// documents
	protected int topics_;
	protected int words_;
	protected double alpha_;
	protected double beta_;
	
	public HTMM() {
	}
	
	public HTMM(int topics, int words, double alpha, double beta, List<Document> docs) {
		topics_ = topics;
		words_ = words;
		alpha_ = alpha;
		beta_ = beta;
		this.docs_.addAll(docs);
	}
	

	// Initializes data members and reads the train documents.
	void init(int topics, int words, double alpha, double beta, String fname,
			String data_dir) throws IOException {
		topics_ = topics;
		words_ = words;
		alpha_ = alpha;
		beta_ = beta;
		readTrainDocuments(fname, data_dir);
	}

	// Reads the train documents whose location is specified in the given file.
	void readTrainDocuments(String fname, String data_dir) throws IOException {
		String full_path = fname;
		FileInputStream in = new FileInputStream(full_path);
		// if (!in || in.fail()) {
		// ERROR("can't open file " << full_path << " for reading");
		// }
		Scanner scanner = new Scanner(in);
		docs_.addAll(readTrainDocuments(data_dir, scanner));
		in.close();
	}

	static List<Document> readTrainDocuments(String data_dir, Scanner scanner)
			throws IOException {
		List<Document> docs = new ArrayList<Document>();
		while (scanner.hasNext()) {
			String str = scanner.next();
			Document d = new Document();
			str = data_dir + str;
			d.init(str);
			docs.add(d);
		}
		return docs;
	}

} // namespace
