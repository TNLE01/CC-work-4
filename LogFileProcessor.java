/**
* Will Scan A User Inputted File For IP Addresses And Users
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
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogFileProcessor {

    /**
     * Makes the HashMaps
     */
    static HashMap<String, Integer> IPAddresses = new HashMap<>();
    static HashMap<String, Integer> Users = new HashMap<>();

    /**
     * Will scan each line of the novel file and add the total number of found regex to the HashMap
     * @param regex the regex pattern to scan for
     * @param fileToSearch the file to scan for said regexes
     * @param groupNumber the number of the capturing group to use as the HashMap key
     * @param map the HashMap to add the data to
     */
    public static void searchFile(String regex, File fileToSearch, int groupNumber, HashMap<String, Integer> map) throws FileNotFoundException, IOException {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        BufferedReader fileReader = new BufferedReader(new FileReader(fileToSearch));
        String line;
	    while((line = fileReader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            while(matcher.find()) {

                String format = matcher.group(groupNumber);
                map.putIfAbsent(format, 0);
                map.put(format, map.get(format)+1);

            }
	    }
        fileReader.close();

    }

    /**
     * Will count the total number of lines in a file
     * @param fileToCount the file to count the lines of
     */
    public static int lineCounter(File fileToCount) throws FileNotFoundException, IOException {
        int lineCount = 0;
        BufferedReader fileReader = new BufferedReader(new FileReader(fileToCount));
        String line;
	    while((line = fileReader.readLine()) != null) {
            lineCount++;
	    }
        fileReader.close();

        return lineCount;
    }

    /**
     * Will get the size of a HashMap
     * @param HashMapToGetSize the HashMap to get the size of
     * @return the size of the HashMap
     */
    public static int getHashMapSize(HashMap<String, Integer> HashMapToGetSize) {
        return HashMapToGetSize.size();
    }

    /**
     * Will print the HashMap
     * @param HashMapToPrint the HashMap to get the contents of
     */
    public static void getHashMap(HashMap<String, Integer> HashMapToPrint) {
        for (HashMap.Entry<String, Integer> entry : HashMapToPrint.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    /**
     * Ask user for the file and the commands to do on said file, then do the command
     * @param args The command line arguments.
     * @throws FileNotFoundException when file not found
     * @throws IOException when there is an IO error
     */
    public static void main(String[] args) throws FileNotFoundException, IOException{
		
	    Scanner scanInput = new Scanner(System.in);
	    System.out.println("Enter File To Process: ");
        File fileToProcess = new File(scanInput.next());

        System.out.println("Enter Command (0, 1, 2)\n" +
        "0 - print the default output\n" +
        "1 - print the hashmap of IP addresses and the default output\n" +
        "2 - print the hashmap of usernames and the default output\n" + 
        "else - print the default output");
        String flag = (scanInput.next());

        searchFile("(\\d+\\.){3}(\\d+)", fileToProcess, 0, IPAddresses);
        searchFile("user\\s(\\w+)\\s", fileToProcess, 1, Users);

        if (flag.equals("1")) {
            getHashMap(IPAddresses);
        }
        else if (flag.equals("2")) {
            getHashMap(Users);
        }

        System.out.println(lineCounter(fileToProcess) + " lines in the log file were parsed.");
        System.out.println("There are " + getHashMapSize(IPAddresses) +  " unique IP addresses in the log.");
        System.out.println("There are " + getHashMapSize(Users) + " unique users in the log.");

    }  
}
