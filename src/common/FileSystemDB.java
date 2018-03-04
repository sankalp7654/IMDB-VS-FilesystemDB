package common;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class FileSystemDB {

	public static void main(String[] args) {
		
		displayFileSystemDB();
		
		String userDetails[] = new String[7];
		userDetails[0] = "Samaas";
		userDetails[1] = "Samaas";
		userDetails[2] = "Samaas";
		userDetails[3] = "Samaas";
		userDetails[4] = "Samaas";
		userDetails[5] = "Srinivas";
		userDetails[6] = "Srinivas";
	
		FileSystemDB.insertIntoFileSystemDB(userDetails);

		
		
	}
	
	public static long insertIntoFileSystemDB(String userDetails[]) {
		long startTime = System.nanoTime();
		try {
			
			FileWriter fileWriter = new FileWriter("/Users/Sandeep/Downloads/test.csv", true);
			CSVWriter object = new CSVWriter(fileWriter);
			object.writeNext(userDetails);
					
			object.close();
			fileWriter.close();
			
			long endTime = System.nanoTime();
			System.out.println((endTime-startTime)/1000 + " µs");
			return (endTime-startTime);
		

		} catch (IOException e) {
			e.printStackTrace();
		}return 0L;
	}		
	
	public static long displayFileSystemDB() {
		long startTime = System.nanoTime();
		try {
				File file = new File("/Users/Sandeep/Downloads/test.csv");

				FileReader fileReader = new FileReader(file);
				CSVReader object = new CSVReader(fileReader);
		
				String userDetails[] = new String[10];
				while((userDetails = object.readNext()) != null) {
					for(String count : userDetails)
						System.out.println(count);
				}
				object.close();
		
				long endTime = System.nanoTime();
				System.out.println((endTime-startTime)/1000 + " µs");
				return (endTime-startTime);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return 0L;	
	}
}
