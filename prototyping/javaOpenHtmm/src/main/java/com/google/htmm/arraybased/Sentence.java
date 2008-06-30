package com.google.htmm.arraybased;

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


public class Sentence {
	private int[] data_;
	
	public Sentence() {
		// TODO Auto-generated constructor stub
	}
	
Sentence(Sentence s) {
	data_ = s.data_;
}

// Sets the number of words in the sentence (allocates space for them).
void init(int words) {
  data_ = new int[words];
}

// Adds the word 'word' in the location i.
void AddWord(int i, int word) {
  data_[i] = word;
}

  // Returns the number of words in the sentence.
  int size() { return data_.length; }

  // Returns the i word of the sentence.
  int GetWord(int i) { return data_[i]; }


}  // namespace
