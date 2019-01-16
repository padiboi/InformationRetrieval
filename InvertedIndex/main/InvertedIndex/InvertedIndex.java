/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InvertedIndex;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.google.gson.Gson;

/**
 *
 * @author gautam
 */
public class InvertedIndex {
    private static TreeMap<String, List<Integer>> dictionary = new TreeMap<>();
    private static TreeMap<String, Integer> docs = new TreeMap<>();
    private static ArrayList<StringTokenizer> tokenArray = new ArrayList<>();
    
    public static void main(String[] args) throws IOException {
        constructDictionary(dictionary);
        System.out.println("Enter query: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String query = br.readLine();
        query = query.trim();
        StringTokenizer stringTokenizer = new StringTokenizer(query);
        String normalizedQuery = "";
        while (stringTokenizer.hasMoreTokens()) {
            normalizedQuery += Normalizer.normalizeQuery(stringTokenizer.nextToken()) + " ";
        }
        normalizedQuery = normalizedQuery.trim();
        System.out.println(normalizedQuery);
        QueryHandler.scheduleOperations(normalizedQuery, dictionary);
    }
    
    private static void constructDictionary(TreeMap dict) {
        File folder = new File("C:\\Users\\kanis\\Documents\\GitHub\\InformationRetrieval\\InvertedIndex\\preprocessing\\data");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; ++i) {
            File file = listOfFiles[i];
            if (file.isFile()) {
                docs.put(file.getName(), i++);
                // tokenize and add to dict
                try {
                    String content = new String(Files.readAllBytes(Paths.get("C:\\Users\\kanis\\Documents\\GitHub\\InformationRetrieval\\InvertedIndex\\preprocessing\\data\\" + file.getName())));
                    StringTokenizer st = new StringTokenizer(content);
                    tokenArray.add(st);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        ArrayList<Integer> docList;
        for (int i = 0; i < tokenArray.size(); i++) {
            StringTokenizer st = tokenArray.get(i);
            while (st.hasMoreTokens()) {
                String term = st.nextToken();
                term = Normalizer.normalize(term);
                if (term == null) {
                    continue;
                }
                docList = (ArrayList<Integer>) dictionary.getOrDefault(term, new ArrayList<Integer>());
                if (docList.size() == 0 || docList.get(docList.size() - 1) != i) {
                    docList.add(i);
                }
                dictionary.put(term, docList);
            }
        }
        // TODO: MOVE BELOW OP TO PAGED MECHANISM (WHAT IF SIZE OF INDEX > RAM?)
        // index built in memory, save to .json file
        String indexJson = new Gson().toJson(dictionary).toString();
        String docsJson = new Gson().toJson(docs).toString();
        File indexFile = new File("C:\\Users\\kanis\\Documents\\GitHub\\InformationRetrieval\\InvertedIndex\\index.json");
        File docsFile = new File("C:\\Users\\kanis\\Documents\\GitHub\\InformationRetrieval\\InvertedIndex\\docs.json");
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(indexFile));
            bufferedWriter.write(indexJson);
            bufferedWriter = new BufferedWriter(new FileWriter(docsFile));
            bufferedWriter.write(docsJson);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
