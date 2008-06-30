package com.google.htmm.arraybased;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

import com.google.htmm.util.Pair;

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

public class ReadTopWords {

	public static void main(String[] args) throws Exception {
  if (args.length != 4) {
    System.err.println("Usage: " + args[0] + " res_file words_file words_num");
  }
  double[][] phi;
  phi = ReadPhi(args[1]);
  List<String> words = new ArrayList<String>(phi[0].length);
  ReadWords(args[2],words);
  PrintTopWords(phi,words,Integer.valueOf(args[3]));
}


private static double[][] ReadPhi(String fname) throws IOException {
  FileInputStream in = new FileInputStream(fname);
// if (!in || in.fail()) {
// ERROR("can't open file " << fname << " for reading");
// }
  Scanner scanner = new Scanner(in);
  int topics, words;
  topics = scanner.nextInt();
  words = scanner.nextInt();
  double[][] phi = new double[topics][];
  for (int i = 0; i < topics; i++) {
    phi[i] = new double[words];
    for (int j = 0; j < words; j++) {
      phi[i][j] = scanner.nextDouble();
    }
  }
  in.close();
  return phi;
}

static void ReadWords(String fname, List<String> words) throws IOException {
  FileInputStream in = new FileInputStream(fname);
// if (!in || in.fail()) {
// ERROR("can't open file " << fname << " for reading");
// }
  // words is already allocated.
  int num;
  String w;
  Scanner scanner = new Scanner(in);
  while (scanner.hasNext()) {
	  num = scanner.nextInt();
	  w= scanner.next();
      words.set(num-1, w);
  }
  in.close();
}

static void PrintTopWords(double[][] phi, 
                   List<String> words, int N) {
  // for each topic find the most probable words using a priority queue for
  // the sort
  for (int i = 0; i < phi.length; i++) {
    PriorityQueue<Pair<Double,Integer> > pq = new PriorityQueue<Pair<Double,Integer>>();
    for (int j = 0; j < phi[i].length; j++) {
      Pair<Double, Integer> p = new Pair<Double, Integer>(phi[i][j],j);
      pq.add(p);
    }
	System.out.println("TOPIC " + i);
	System.out.println("---------");
    for (int n = 0; n < N; n++) {
        Pair<Double, Integer> q = pq.peek();
      pq.remove();
      System.out.println(words.get(q.getSecond()) + "\t" + q.getFirst());
// cout << words[q.second] << "\t" << q.first << endl;
    }
    System.out.println();
    System.out.println();
  }
}
}