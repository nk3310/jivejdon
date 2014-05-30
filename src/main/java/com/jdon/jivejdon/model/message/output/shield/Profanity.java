/*
 * Copyright 2003-2005 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.jdon.jivejdon.model.message.output.shield;

import java.util.StringTokenizer;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.message.MessageRenderSpecification;
import com.jdon.jivejdon.model.message.MessageVO;
import com.jdon.util.Debug;

/**
 * A ForumMessageFilter that filters out user-specified profanity.
 */
public class Profanity implements MessageRenderSpecification {
	private final static String module = Bodymasking.class.getName();

	/**
	 * Array of all the bad words to filter.
	 */
	private String[] words = null;

	/**
	 * Comma delimited list of words to filter.
	 */
	private String wordList = null;

	/**
	 * Indicates if case of words should be ignored.
	 */
	private boolean ignoringCase = true;

	/**
	 * Clones a new filter that will have the same properties and that will wrap
	 * around the specified message.
	 * 
	 * @param message
	 *            the ForumMessage to wrap the new filter around.
	 */
	public ForumMessage render(ForumMessage message) {
		applyProperties();
		try {
			MessageVO messageVO = message.getMessageVO();
			messageVO.setSubject(filterProfanity(messageVO.getSubject()));
			messageVO.setBody(filterProfanity(messageVO.getBody()));
		} catch (Exception e) {
			Debug.logError("" + e, module);
		}
		return message;
	}

	/**
	 * Returns ture if the filter will ignore case when filtering out profanity.
	 * For example, when "ignore case" is turned on and "dog" is in your word
	 * filter list, then "Dog" and "DOG" will also be filtered.
	 * 
	 * @return true if case will be ignored when filtering words.
	 */
	public boolean isIgnoringCase() {
		return ignoringCase;
	}

	/**
	 * Toggles the filter to ignore case when filtering words or not. For
	 * example, when "ignore case" is turned on and "dog" is in your word filter
	 * list, then "Dog" and "DOG" will also be filtered.
	 * 
	 * @param ignoringCase
	 *            true if case should be ignored when filtering.
	 */
	public void setIgnoringCase(boolean ignoringCase) {
		this.ignoringCase = ignoringCase;
	}

	/**
	 * Returns the comma delimited list of words that will be filtered.
	 * 
	 * @return the list of words to be filtered.
	 */
	public String getWordList() {
		return wordList;
	}

	/**
	 * Sets the list of words to be filtered. Each word must seperated by a
	 * comma.
	 * 
	 * @param the
	 *            comma delimited list of words to filter.
	 */
	public void setWordList(String wordList) {
		this.wordList = wordList;
	}

	// FROM THE FORUMMESSAGE INTERFACE//

	/**
	 * Applies new property values so the filter is ready for futher processing.
	 */
	private void applyProperties() {
		if (wordList == null || wordList.equals("")) {
			words = null;
			return;
		}
		StringTokenizer tokens = new StringTokenizer(wordList, ",");
		String[] newWords = new String[tokens.countTokens()];
		for (int i = 0; i < newWords.length; i++) {
			if (ignoringCase) {
				newWords[i] = tokens.nextToken().toLowerCase().trim();
			} else {
				newWords[i] = tokens.nextToken().trim();
			}
		}
		words = newWords;
	}

	/**
	 * Filters out bad words.
	 */
	private String filterProfanity(String str) {
		// Check to see if the string is null or zero-length
		if (str == null || "".equals(str) || wordList == null) {
			return str;
		}
		String lower;
		if (ignoringCase) {
			lower = str.toLowerCase();
		} else {
			lower = str;
		}
		for (int i = 0; i < words.length; i++) {
			str = replace(str, lower, words[i], cleanWord(words[i].length()));
		}
		return str;
	}

	/**
	 * Generates a string of characters of specified length. For example: !@%$
	 * or %!@$%!@@ or *****
	 */
	private String cleanWord(int length) {
		char[] newWord = new char[length];
		for (int i = 0; i < newWord.length; i++) {
			newWord[i] = '*';
		}
		return new String(newWord);
	}

	/**
	 * Replaces all instances of oldString with newString in the String line.
	 */
	private String replace(String line, String lowerCaseLine, String oldString, String newString) {
		int i = 0;
		if ((i = lowerCaseLine.indexOf(oldString, i)) >= 0) {
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line.length() + 15);
			buf.append(line.substring(0, i)).append(newString);
			i += oLength;
			int j = i;
			while ((i = lowerCaseLine.indexOf(oldString, i)) > 0) {
				buf.append(line.substring(j, i)).append(newString);
				i += oLength;
				j = i;
			}
			buf.append(line.substring(j));
			return buf.toString();
		}
		return line;
	}
}
