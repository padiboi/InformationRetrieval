package InvertedIndex;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.io.File;

public class QueryHandler {
	
	public static void scheduleOperations(String query) {
		QueryHandler.handleBrackets(query);		
	}

	public static String handleBrackets(String query) {
		int maxLevel = 0;
		LinkedList<Character> stack = new LinkedList<>();
		for (int i = 0; i < query.length(); ++i) {
			char ch = query.charAt(i);
			if (ch == '(') {
				stack.push(ch);
			} else if (ch == ')') {
				stack.pop();
			}
			maxLevel = Math.max(maxLevel, stack.size());
		}
		System.out.println(maxLevel);
		return null;
	}

	public static ArrayList<Integer> intersect(String term1, String term2) {
		// TODO: Change this to read from JSON file
		HashMap<String, ArrayList<Integer>> map = new HashMap<>();
		List<Integer> list1 = map.get(term1);
		List<Integer> list2 = map.get(term2);
		List<Integer> intersection = new ArrayList<Integer>();
		int length1 = list1.size();
		int length2 = list2.size();
		int i = 0, j = 0;
		while(i < length1 && j < length2) {
			if (list1.get(i) == list2.get(j)) {
				intersection.add(list1.get(i));
				++i;
				++j;
			} else if (list1.get(i) < list2.get(j)) {
				++i;
			} else {
				++j;
			}
		}
		return intersection;
	}

	public static ArrayList<Integer> union(String term1, String term2) {
		// TODO: Change this to read from JSON file
		HashMap<String, ArrayList<Integer>> map = new HashMap<>();
		List<Integer> list1 = map.get(term1);
		List<Integer> list2 = map.get(term2);
		Set<Integer> union = new HashSet<>();
		int length1 = list1.size();
		int length2 = list2.size();
		for (Integer item : list1) {
			union.add(item);
		}
		for (Integer item : list2) {
			union.add(item);	
		}
		return union;
	}

	public static ArrayList<Integer> negate(String term) {
		// TODO: Change this to read from JSON file
		HashMap<String, ArrayList<Integer>> map = new HashMap<>();
		List<Integer> list = map.get(term);
		// get number of files
		File folder = new File("../../preprocessing/data/");
        File[] listOfFiles = folder.listFiles();
        boolean[] flags = new boolean[listOfFiles.length];
        for (Integer docId: list) {
        	flags[docId] = true;
        }
        List<Integer> negation = new ArrayList<>();
        for (int i = 0; i < flags.length; ++i) {
        	if (!flags[i]) {
        		negation.add(i);
        	}
        }
        return negation;
	}
}