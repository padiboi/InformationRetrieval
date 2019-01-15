package InvertedIndex;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.io.File;
import java.util.Arrays;

public class QueryHandler {

	private HashMap<ArrayList<Integer>, ArrayList<Integer>> cache = new HashMap<>(); 
	
	public static void scheduleOperations(String query) {
		handleBrackets(query);		
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
		query = solveBrackets(query, maxLevel);
		System.out.println(query);
		return null;
	}

	public static String solveBrackets(String query, int maxLevel) {
		if (maxLevel == 0) {
			parseQuery(query.split(" "));
			return query;
		}
		LinkedList<Character> stack = new LinkedList<>();
		int stackLevel = 0;
		for (int i = 0; i < query.length(); ++i) {
			stackLevel = stack.size();
			if (stackLevel == maxLevel) {
				// search for next )
				int j = i;
				while (query.charAt(j) != ')') {
					++j;
				}
				String[] args = query.substring(i, j).split(" ");
				parseQuery(args);
				query = solveBrackets(query, maxLevel - 1);
			}
			char ch = query.charAt(i);
			if (ch == '(') {
				stack.push(ch);
			} else if (ch == ')') {
				stack.pop();
			}
			return "";
		}
	}

	public static void parseQuery(String[] args) {
		ArrayList<Integer> result = new ArrayList<>();
		if (args.length == 2) {
			if (args[0] == '!') {
				result = negate(args[1]);			
			}		
		} else if (args.length == 3) {
			if (args[1] == '&') {
				result = intersect(args[0], args[2]);
			} else {
				result = union(args[0], args[2]);
			}
		}
		System.out.println(result);
		cache.put(Arrays.asList(args), result);
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