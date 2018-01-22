/**
 * @author Dheeraj D Kamath
 * Written in response to assignment given by mentor Mr. Naveenchandu Annam
 * Date created: 18/01/2018
 *
 * Observations: Questions (a) and (b) working efficiently.  Question (c) single queries getting executed efficiently. Batch processing doesn't work due to less shared pool memory. Working on a fix. 
 *
 * Results: Logfile attached with mail. 
 *
 * Output on console: 
				Here's the solution: 

				Unzipping in progress: 
				Files unzipped to: D:\Internship Files\Assignments\\Statistics_adwmeufran2frontenda_171228171227/
				Folder created!

				Files unzipped to: D:\Internship Files\Assignments\\Statistics_adwmeufran2frontenda_171228171227/statistic_3790.out
				Number of lines: 1570Number of lines in the file: 1570

				Files unzipped to: D:\Internship Files\Assignments\\Statistics_adwmeufran2frontenda_171228171227/statistic_3791.out
				Number of lines: 1558Number of lines in the file: 1558

				Files unzipped to: D:\Internship Files\Assignments\\Statistics_adwmeufran2frontenda_171228171227/statistic_3792.out
				Number of lines: 1518Number of lines in the file: 1518

				Files unzipped to: D:\Internship Files\Assignments\\Statistics_adwmeufran2frontenda_171228171227/statistic_3793.out
				Number of lines: 1474Number of lines in the file: 1474


				Process completed!
				Number of folders created: 1
				Number of files extracted is: 4


 */
 
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Assignment1 {

    //Specify zip file location
    private static String zipLocation = "D:\\Internship Files\\Assignments\\Statistics_adwmeufran2frontenda_171228171227.zip";

    //Specify extraction file destination
    private static String extractedFilesDest = "D:\\Internship Files\\Assignments\\";

    public static void main(String args[]){

        //Display process start
        System.out.println("Assignment 1: To unzip the attached file and find the number of files in that \n\n Here's the solution: \n");

        //Begin unzipping, catch error in case of failure!
        try{
            System.out.println("Unzipping in progress: ");
            unZip(zipLocation, extractedFilesDest);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void unZip(String filePath, String fileDes) throws IOException{

        //Create destination directory if it doesn't exist
        File destDir = new File(fileDes);
        if (!destDir.exists()) {
            destDir.mkdir();
        }

        //Define local variables
        int fileCount = 0;
        int folderCount = 0;
        int lineCount = 0;

        //Define input streams
        FileInputStream fis = new FileInputStream(filePath);
        ZipInputStream zis = new ZipInputStream(fis);
        ZipEntry zipEntry = zis.getNextEntry();

        //Unzip files until all zip entries are extracted!
        while(zipEntry!= null){

            //Define the pathlocation to unzip
            String file = fileDes + File.separator + zipEntry.getName();

            //Print on console for debugging
            System.out.println("Files unzipped to: " + file);

            if (!zipEntry.isDirectory()) {
                // if the entry is a file, extracts it
                fileCount++;
                lineCount = countLines(file);
                extractFile(zis, file);
                //mapMetaFile(file, zipEntry.getName());
                System.out.println("Number of lines in the file: " + lineCount + "\n");
            } else {
                // If the entry is a directory, make the directory
                folderCount++;
                File dir = new File(file);
                dir.mkdir();
                System.out.println("Folder created!" + "\n");
            }

            //Close current zip entry
            zis.closeEntry();

            //Begin next zip entry
            zipEntry = zis.getNextEntry();
        }

        //Close stream
        zis.close();

        //Display termination message
        System.out.println("\nProcess completed!" + "\nNumber of folders created: " + folderCount + "\nNumber of files extracted is: " + fileCount);
    }


    //Function for extraction of files
    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {

        //Create output streams
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));

        //Create a buffer
        byte[] bytesIn = new byte[1024*4];

        //Define local variables
        int read = 0;

        //Until all contents of file are read keep writing into buffer
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }

        //Close stream
        bos.close();
    }

    private static int countLines(String fileName) throws IOException{

        //Define input stream
        InputStream inputStream = new BufferedInputStream(new FileInputStream(fileName));
        try {
            byte[] c = new byte[1024*4];  //Define buffer

            //Define local variables
            int count = 0;
            int readChars = 0;

            //Read stream until end
            while ((readChars = inputStream.read(c)) != -1) {
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;  //For every new line, increase count!
                    }
                }
            }
            return (count == 0) ? 1 : count;
        } finally {
            inputStream.close();
        }
    }

    /* Function name: readMetaData()
       Mapping details:
       Timestamp[1]; Time taken [2]; Action name[3]; Site code [4]; OfficeID [6]; SessionId[7]; Chained action flag[20]; Client IP address[22];
     */
    private static void mapMetaFile(String dataFile, String fileName) throws IOException{
        String[] metaArray = {"Timestamp", "Time taken", "Action name", "Site code", "OfficeID", "SessionId", "Chained action flag", "Client IP address"};
        StringBuffer tableLine = new StringBuffer();
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(dataFile)));

        String[] datalines = data.split("\n");
        System.out.print("Number of lines: " + datalines.length);

        for(int i = 0; i < datalines.length; i++){
            String[] dataArray = datalines[i].split(";");

			//Converting timestamp to proper format
            String timestamp = "";
            timestamp += dataArray[0].substring(0,4) + "-";
            timestamp += dataArray[0].substring(4,6) + "-";
            timestamp += dataArray[0].substring(6,8) + " ";
            timestamp+= dataArray[0].substring(8,10) + ":";
            timestamp+= dataArray[0].substring(10,12) + ":";
            timestamp+= dataArray[0].substring(12,14) + ".";
            timestamp+= dataArray[0].substring(14,17);

			//Generating HashMap having key value pairs
            HashMap hashMap = new HashMap();
            hashMap.put(metaArray[0], timestamp);
            hashMap.put(metaArray[1], dataArray[1]);
            hashMap.put(metaArray[2], dataArray[2]);
            hashMap.put(metaArray[3], dataArray[3]);
            hashMap.put(metaArray[4], dataArray[5]);
            hashMap.put(metaArray[5], dataArray[6]);
            hashMap.put(metaArray[6], dataArray[19]);
            hashMap.put(metaArray[7], dataArray[21]);

			//Generate sql statements to be queried
            tableLine.append("INSERT INTO assignment1log VALUES (TIMESTAMP '");
            for (int j = 0; j < hashMap.size()-1; j++){
                tableLine.append(hashMap.get(metaArray[j]).toString() + "','");
            }
            tableLine.append(hashMap.get(metaArray[hashMap.size()-1]).toString());
            tableLine.append("');\n");
			
        }

		//Write log file: Contains all sql statements seperated by newline
        writeToFile(tableLine, fileName); 
    }

	
	//Generate log file
    private static void writeToFile(StringBuffer content, String FileName){
        BufferedWriter bufferedWriter = null;
        try {
            File myFile = new File(extractedFilesDest + File.separator + "logreport.txt" );   //Create a logfile
            // check if file exist, otherwise create the file before writing
            if (!myFile.exists()) {
                myFile.createNewFile();
            }
            Writer writer = new FileWriter(myFile);
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try{
                if(bufferedWriter != null) bufferedWriter.close();
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }