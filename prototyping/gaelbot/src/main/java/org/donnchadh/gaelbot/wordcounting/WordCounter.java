package org.donnchadh.gaelbot.wordcounting;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class WordCounter {
    private String targetLanguage = "GA";
    private Set<String> ignoreWords = Collections.emptySet(); 
    
    public WordCounter() {
    }
    
    public WordCounter(String targetLanguage) {
        this.targetLanguage = targetLanguage.toUpperCase();
    }
    
    public WordCounter(String targetLanguage, Set<String> ignoreWords) {
        this.ignoreWords = ignoreWords;
        this.targetLanguage = targetLanguage.toUpperCase();
    }
    
    public Map<String, Integer> countWords(String input) {
        HashMap<String, Integer> result = new HashMap<String, Integer>();

        return countWords(input, result);
    }

    public Map<String, Integer> countWords(String input, Map<String, Integer> result) {
        //print each word in order
    	List<String> words = getWords(input);
        for (String word : words) {
            countWord(result, word);
		}
        //print each sentence in reverse order
//        boundary = BreakIterator.getSentenceInstance(Locale.UK);
//        boundary = BreakIterator.getSentenceInstance(new Locale("GA", "IE"));
//        boundary.setText(input);
//        printEachBackward(boundary, input);
//        printFirst(boundary, input);
//        printLast(boundary, input);
        return result;
    }

	public List<String> getWords(String input) {
		List<String> words = new ArrayList<String>();
        BreakIterator boundary = BreakIterator.getWordInstance(new Locale(targetLanguage, "IE"));
        boundary.setText(input);
        int start = boundary.first();
        for (int end = boundary.next();
             end != BreakIterator.DONE;
             start = end, end = boundary.next()) {
             String word = input.substring(start,end).toLowerCase();
             if (isWord(word)) {
            	 words.add(word);
            }
        }
		return words;
	}

	private void countWord(Map<String, Integer> result, String word) {
		if (result.containsKey(word)) {
		     int previousCount = result.get(word).intValue();
		     result.put(word, Integer.valueOf(previousCount + 1));
		 } else {
		     result.put(word, Integer.valueOf(1));
		 }
	}
    
    private boolean isWord(String word) {
        boolean result = true;
        for (int i = 0; i < word.length(); i++) {
            if (!Character.isLetter(word.charAt(i))) {
                return false;
            }
        }
        if (ignoreWords.contains(word)) {
            return false;
        }
        return result;
    }

    public static void printEachForward(BreakIterator boundary, String source) {
        int start = boundary.first();
        for (int end = boundary.next();
             end != BreakIterator.DONE;
             start = end, end = boundary.next()) {
             System.out.println(source.substring(start,end));
        }
    }

}
