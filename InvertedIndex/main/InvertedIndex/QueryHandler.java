package InvertedIndex;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.Arrays;
import java.util.Collections;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class QueryHandler {

	private static TreeMap<String, ArrayList<Integer>> cache = new TreeMap<>();
	private static int nodeLevel;

	public static void parseSimpleQuery(String[] args, TreeMap<String, List<Integer>> map) {
		String simpleResult = parseQuery(args, map);
		/*System.out.println(simpleResult);
		System.out.println(cache.get(simpleResult));*/
	}

	public static void scheduleOperations(String query, TreeMap<String, List<Integer>> map) {
		// System.out.println(query);
		String args[] = query.split(" ");
		parseSimpleQuery(query.split(" "), map);
	}

	public static String parseQuery(String[] args, TreeMap<String, List<Integer>> map) {
		System.out.println("Parsing query...");
		List<Integer> result = new ArrayList<>();
		if (args.length == 2) {
			if (args[0].equals("!")) {
				result = negate(args[1], map);			
			}		
		} else if (args.length == 3) {
			if (args[1].equals("&")) {
				result = intersect(args[0], args[2], map);
			} else {
				result = union(args[0], args[2], map);
			}
		}
		cache.put("$$" + nodeLevel++ + "$$", (ArrayList<Integer>) result);
		System.out.println(result);
		return "$$" + nodeLevel + "$$";
	}

	public static List<Integer> intersect(String term1, String term2, TreeMap<String, List<Integer>> map) {
		List<Integer> list1 = term1.startsWith("$$") ? cache.get(term1) : map.get(term1);
		List<Integer> list2 = term2.startsWith("$$") ? cache.get(term2) : map.get(term2);
		List<Integer> intersection = new ArrayList<Integer>();
		int length1 = list1 != null ? list1.size() : 0;
		int length2 = list2 != null ? list2.size() : 0;
		int i = 0, j = 0;
		System.out.println("List for " + term1 + " " + list1);
		System.out.println("List for " + term2 + " " + list2);
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

	public static List<Integer> union(String term1, String term2, TreeMap<String, List<Integer>> map) {
		List<Integer> list1 = map.get(term1);
		List<Integer> list2 = map.get(term2);
		Set<Integer> union = new TreeSet<>();
		System.out.println("List for " + term1 + " " + list1);
		System.out.println("List for " + term2 + " " + list2);
		for (Integer item : list1) {
			union.add(item);
		}
		for (Integer item : list2) {
			union.add(item);	
		}
		List<Integer> result = new ArrayList<>();
		result.addAll(union);
		return result;
	}

	public static List<Integer> negate(String term, TreeMap<String, List<Integer>> map) {
		List<Integer> list = map.get(term);
		// get number of files
		File folder = new File("C:\\Users\\kanis\\Documents\\GitHub\\InformationRetrieval\\InvertedIndex\\preprocessing\\data");
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