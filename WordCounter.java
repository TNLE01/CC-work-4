/**
* Will Scan The Files In The Current Directory For _wc.txt Files And Will Add The Values In Those Files To A HashMap
* @author Truong Le
* @version 1.0
* Assignment 4
* CS322 - Compiler Construction
* Spring 2024
*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCounter {
    
    /**
     * Will loop through files and store the total count in a HashMap
     * @param args The command line arguments.
     * @throws FileNotFoundException when file not found
     * @throws IOException when there is an IO error
     */
	public static void main(String[] args) throws FileNotFoundException, IOException{
        
        /**
         * Makes the HashMap
         */
        HashMap<String, Integer> totalWords = new HashMap<>();

        /**
         * Get the current directory where all the _wc.txt files are stored, and add all the _wc.txt files to a ArrayList
         */
	    File currentDir = new File(System.getProperty("user.dir"));
        ArrayList<File> novelWcFiles = new ArrayList<File>();
        File[] currentDirFiles = currentDir.listFiles();
        Pattern wcTxt = Pattern.compile("_wc.txt");
        for (File file : currentDirFiles) {
            Matcher matcherWcTxt = wcTxt.matcher(file.getName());
            if (matcherWcTxt.find()) {
                novelWcFiles.add(file);
            }
        }

        /**
         * Loop through the files and split it into key and value, then add those to the HashMap
         */
        for (File file : novelWcFiles) {
            BufferedReader lineReader = new BufferedReader(new FileReader(file));
            String fileLine;
	        while((fileLine = lineReader.readLine()) != null) {

                int dataValue = fileLine.lastIndexOf("|");
                String[] dataSplit = {fileLine.substring(0, dataValue), fileLine.substring(dataValue).replace("|", "")};

                totalWords.putIfAbsent(dataSplit[0], 0);
                totalWords.put(dataSplit[0], totalWords.get(dataSplit[0])+Integer.valueOf(dataSplit[1]));
                
	        }
            lineReader.close();
        }

        /**
         * Print the HashMap
         */
        for (HashMap.Entry<String, Integer> entry : totalWords.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

    }
    
}
