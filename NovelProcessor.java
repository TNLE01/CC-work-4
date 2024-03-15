/**
* Will Scan The Inputed File Using Regex And Output A File With The Total Count
* @author Truong Le
* @version 1.0
* Assignment 4
* CS322 - Compiler Construction
* Spring 2024
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.image.Image;

public class NovelProcessor {

    /**
     * This is the String that is going to be written to the output file
     */
    static String outputFileOutput = "";
	
    /**
     * Will scan each line of the novel file and add the total number of found regex to the outputFileOutput
     * @param patternToMatch the regex pattern to scan for
     * @param novelToRead the file with the novel to scan
     */
	public static void patternCounter(String patternToMatch, File novelToRead) throws IOException {
        int count = 0;

		Pattern pattern = Pattern.compile(patternToMatch, Pattern.CASE_INSENSITIVE);

        BufferedReader readerNovel = new BufferedReader(new FileReader(novelToRead));

        String fileLine;
	    while((fileLine = readerNovel.readLine()) != null) {
            Matcher matcher = pattern.matcher(fileLine);
            while(matcher.find()) {
                count++;
            }
	    }
        readerNovel.close();

        outputFileOutput += "|" + count + "\n";
	}
	
    /**
     * Ask user for the novel file and the regex file, will loop through the regex file while calling the patternCounter method for each regex in that regex file, and finally writes the total count to a new file
     * @param args The command line arguments.
     * @throws FileNotFoundException when file not found
     * @throws IOException when there is an IO error
     */
	public static void main(String[] args) throws FileNotFoundException, IOException{
		
	    Scanner scanInput = new Scanner(System.in);
	    System.out.println("Enter Novel File (.txt): ");
	    File novelFile = new File(scanInput.next());
        System.out.println("Enter Regex File (.txt): ");
	    File regexFile = new File(scanInput.next());
        
        BufferedReader readerRegex = new BufferedReader(new FileReader(regexFile));
        String regexLine;
	    while((regexLine = readerRegex.readLine()) != null) {
            outputFileOutput += regexLine;
            patternCounter(regexLine, novelFile);
	    }
        readerRegex.close();

        BufferedWriter writerFile = new BufferedWriter(new FileWriter(novelFile.getName().replace(".txt", "") + "_wc.txt"));
        writerFile.write(outputFileOutput);
        writerFile.close();

	}

}
