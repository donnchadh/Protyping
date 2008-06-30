package com.google.htmm.arraybased;

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

public class Document {
	  List<Sentence> data_ = new ArrayList<Sentence>();  // the basic unit is a sentence,
      // it could also consist of a single word

	  // Retrieves the i sentence of the document.
	  Sentence GetSentence(int i) { return data_.get(i); }

	  // Returns the number of sentences in the document.
	  int size() { return data_.size(); }

	  // Initialization: Reads a sequence of sentences from a file.
void init(String fname) throws IOException {
  FileInputStream in = new FileInputStream(fname);
//  if (!in || in.fail()) {
//    ERROR("can't open file " << fname << " for reading");
//  }

  
  Sentence s = new Sentence();
  Scanner scanner = new Scanner(in);
  for (ReadSentence(in, s); scanner.hasNext(); ReadSentence(in, s)) {
    data_.add(s);
    s = new Sentence();
  }
  // The last sentence object corresponds to eof and was not added to the
  // document.

  in.close();
}

void ReadSentence(FileInputStream in, Sentence s) {
	Scanner scanner = new Scanner(in);
  int words, w;
  words = scanner.nextInt();
  if (!scanner.hasNext()) {
    return;
  }
  s.init(words);
  for (int i = 0; i < words; i++) {
    w = scanner.nextInt();
    s.AddWord(i, w-1);  // We subtract one due to the range of input in
                         // data (1..N)
  }
}

}  // namespace
