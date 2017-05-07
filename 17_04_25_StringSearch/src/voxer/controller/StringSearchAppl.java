package voxer.controller;

import java.io.*;
import java.util.*;

import voxer.model.TrieDictionary;
/**
 * 
 * @author Katerina Davidov
 *
 */
public class StringSearchAppl {
	public static void main(String[] args) {
		//C:\Mars2\17_04_25_StringSearch\src\dict.txt - the format of the fileName
		System.out.println("Enter the full path and name of dictionary file");
		Scanner scanner = new Scanner(System.in);
		String fileName = scanner.nextLine();
		
		File file = new File(fileName);
		TrieDictionary dictionary = createDictionary(file);		
		if (dictionary==null)
			return;
		
		while (true){
			System.out.println("Enter the word to find or EXIT:");
			String line = scanner.nextLine();
			if (line.equals("EXIT")) 
				break;
			System.out.println(checkWord(line,dictionary));			
		}
	}
/**
 * !!! to make application case-sensitive remove "toLowerCase()" in char[]word initialization
 * @param line - the word entered by the user
 * @param dictionary was created from txt-file
 * @return the shortest of the matching words
 */
	private static String checkWord(String line, TrieDictionary dictionary) {
		String shWord = "";
		char[] word = line.toLowerCase().toCharArray();
		for (int i=0 ; i< word.length ; i++){
			if (dictionary.series.get(word[i])==null)
				return "Matches not found";			
			if ((word.length-1 == i)&&(dictionary.series.get(word[i]).isEnd)){
				return "Matches not found";
			}
			shWord = dictionary.shortestWord.get(word[i]);
			dictionary = dictionary.series.get(word[i]);
		}
		return shWord;
	}
/**
 * 
 * @param file - full name (path+name) to the txt-file entered by user
 * @return the dictionary, containing the words from the file
 */
	private static TrieDictionary createDictionary(File file) {
		TrieDictionary dictionary = new TrieDictionary();
		try(BufferedReader reader = new BufferedReader(new FileReader(file))){
			while (true){
				String line = reader.readLine();
				if (line==null)
					break;
				addWord(line,dictionary);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			dictionary = null;
		}		
		return dictionary;
	}
/**
 * !!! to make application case-sensitive remove "toLowerCase()" in char[]word initialization
 * @param line - the word from txt-file 
 * @param dictionary
 */
	private static void addWord(String line, TrieDictionary dictionary) {
		//to make this caseSensitive delete toLowerCase;
		char[] word = line.toLowerCase().toCharArray();
		for (int i=0 ; i< word.length; i++){
			if (dictionary.series.get(word[i])!=null){
				//check the shortest word
				String shWord = dictionary.shortestWord.get(word[i]);
				dictionary.shortestWord.put(word[i], chooseShorter(shWord,line));
				//check the end of the word
				if (word.length-1 == i)
					dictionary.isEnd = true;
				dictionary = dictionary.series.get(word[i]);
			}else{
				TrieDictionary trie = new TrieDictionary();
				trie.isEnd = (word.length - 1 == i ? true : false);
				dictionary.shortestWord.put(word[i], line);
				dictionary.series.put(word[i], trie);
				dictionary = trie;
			}
		}
		
	}
/**
 * Required: 	1. Check the length
 * 				2. For equal length compare strings
 * @param shWord - the current shortest word for this node
 * @param line - the new candidate for the shortest word
 * @return
 */
	private static String chooseShorter(String shWord, String line) {
		if (shWord==null || shWord.isEmpty())
			return line;
		int res = shWord.length()-line.length();
		res = res!=0 ? res : shWord.compareTo(line);
		return res<0 ? shWord : line;
	}

}
