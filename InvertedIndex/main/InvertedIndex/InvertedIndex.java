/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InvertedIndex;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 *
 * @author gautam
 */
public class InvertedIndex {
    private TreeMap<String, ArrayList<Integer>> dictionary = new TreeMap<>();
    private TreeMap<String, Integer> docs = new TreeMap<>();
    private ArrayList<StringTokenizer> tokenArray = new ArrayList<>();

    public InvertedIndex() throws IOException {
        constructDictionary(this.dictionary);
    }
    
    public static void main(String[] args) {
        try {
            new InvertedIndex();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }              
    
    private void constructDictionary(TreeMap dict) {
        File folder = new File("../../preprocessing/data/");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; ++i) {
            File file = listOfFiles[i];
            if (file.isFile()) {
                docs.put(file.getName(), i++);
                // tokenize and add to dict
                try {
                    String content = new String(Files.readAllBytes(Paths.get("../../preprocessing/data/" + file.getName())));
                    StringTokenizer st = new StringTokenizer(content);
                    tokenArray.add(st);
                } catch (IOException e) {
                    System.out.println("Sorry, an error occurred.");
                }
            }
        }
        ArrayList<Integer> docList;
        for(int i = 0; i < tokenArray.size(); i++) {
            StringTokenizer st = tokenArray.get(i);
            while(st.hasMoreTokens()) {
                String term = st.nextToken();
                term = Normalizer.normalize(term);
                if (term == null) {
                    continue;
                }
                docList = dictionary.getOrDefault(term, new ArrayList<Integer>());
                docList.add(i);
                dictionary.put(term, docList);
            }
        }
        // TODO: MOVE BELOW OP TO PAGED MECHANISM (WHAT IF SIZE OF INDEX > RAM?)
        // index built in memory, save to .json file
        String indexJson = new Gson().toJson(dictionary).toString();
        File indexFile = new File("../index.json");
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(indexFile));
            bufferedWriter.write(indexJson);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
