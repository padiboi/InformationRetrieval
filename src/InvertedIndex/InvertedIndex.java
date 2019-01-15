/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InvertedIndex;
import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
/**
 *
 * @author gautam
 */
public class InvertedIndex {
    
    TreeMap dictionary = new TreeMap();
    TreeMap docs = new TreeMap();
    ArrayList<StringTokenizer> tokenArray = new ArrayList();
    InvertedIndex(){
        constructDictionary(this.dictionary);
    }
    
    public static void main(String[] args){
        new InvertedIndex();
    }              
    
    private void constructDictionary(TreeMap dict){
        File folder = new File("/Users/gautam/Desktop/SNU_Study/SEM6/IR/");
        File[] listOfFiles = folder.listFiles();
        
        int i=0;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                docs.put(file.getName(), i++);
                System.out.println(file.getName());
                // tokenize and add to dict
                try {
                    String content = new String(Files.readAllBytes(Paths.get("/Users/gautam/Desktop/SNU_Study/SEM6/IR/"+file.getName())));
                    StringTokenizer st1 = new StringTokenizer(content);
                    tokenArray.add(st1);
                }catch (Exception e) {
                    System.out.println("EXCEPTION");  
                    }
            }
        }
        
        for(StringTokenizer st : tokenArray){
            while(st.hasMoreTokens()){
                String temp = st.nextToken();
                System.out.println(temp);
                dictionary.put(temp, null);
            }
        }
    }
}
