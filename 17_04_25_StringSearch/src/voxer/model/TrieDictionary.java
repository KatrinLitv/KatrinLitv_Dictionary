package voxer.model;

import java.util.HashMap;

public class TrieDictionary {
	public boolean isEnd;
	public HashMap<Character, TrieDictionary> series = new HashMap<>();
	public HashMap<Character, String> shortestWord = new HashMap<>();	
}
