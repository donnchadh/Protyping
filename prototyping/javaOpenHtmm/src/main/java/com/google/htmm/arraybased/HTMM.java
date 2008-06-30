package com.google.htmm.arraybased;

import java.io.FileInputStream;
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

	// Initializes data members and reads the train documents.
	void init(int topics, int words, double alpha, double beta, String fname,
			String data_dir) throws IOException {
		topics_ = topics;
		words_ = words;
		alpha_ = alpha;
		beta_ = beta;
		ReadTrainDocuments(fname, data_dir);
	}

	// Reads the train documents whose location is specified in the given file.
	void ReadTrainDocuments(String fname, String data_dir) throws IOException {
		String full_path = fname;
		FileInputStream in = new FileInputStream(full_path);
		// if (!in || in.fail()) {
		// ERROR("can't open file " << full_path << " for reading");
		// }
		Scanner scanner = new Scanner(in);
		String str;
		while (scanner.hasNext()) {
			str = scanner.next();
			Document d = new Document();
			str = data_dir + str;
			d.init(str);
			docs_.add(d);
		}
		in.close();
	}

} // namespace
