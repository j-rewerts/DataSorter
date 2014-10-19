import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;


public class Executive {
	
	boolean var1Success = true;
	boolean var2Success = true;
	String inputFile;
	String outputFile;
	double var1Failure;
	double var2Failure;	
	long timeLimit;
	
	public Executive(String inputFile, String outputFile, double var1, double var2, long timeLimit) {
		this.inputFile = inputFile;
		this.outputFile = outputFile;
		this.var1Failure = var1;
		this.var2Failure = var2;
		this.timeLimit = timeLimit;
	}
	
	public void run() {
		Adjudicator at = new Adjudicator();
		// Attempt to open the inputFile and parse it into an arrayList of integers.
		ArrayList<Integer> unsortedInts = new ArrayList<Integer>();
		String fileString = readFile(inputFile);
		
		unsortedInts = parseToInts(fileString);
		
		HeapSort variant1 = new HeapSort(unsortedInts);
		InsertionSort variant2 = new InsertionSort();
		
		//Create the watchdog
		WatchDogTimer timer = new WatchDogTimer(this);
		timer.SetRunnable(variant1);
		timer.SetTimeout(timeLimit);
		timer.start();
		try {
			timer.getThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Address the results of the sorting. First, did it timeout:
		if (var1Success) {
			// Verify that the results were good.
			ArrayList<Integer> results = variant1.getList();
			if (at.testInputs(unsortedInts, results)) {
				String outputString = "";
				for (Integer i : results) {
					outputString += i.toString() + "\n";
				}
				
				writeToFile(outputFile, outputString);
				return;
			}
			else {
				// use variant 2
			}
		}
		else {
			//use variant 2
		}
	}
	
	public void notifyTimeout(Runnable r) {
		if (r.getClass() != null && r.getClass() == HeapSort.class) {
			var1Success = false;
			System.out.println("Error: HeapSort failed to process the inputs in " + timeLimit + " millseconds");
		}
		else if (r.getClass() != null && r.getClass() == InsertionSort.class) {
			var2Success = false;
			System.out.println("Error: InsertionSort failed to process the inputs in " + timeLimit + " millseconds");
		}
	}
	
	private String readFile(String file) {
		String readString = null;
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.out.println("Error: failed in locating file " + file);
			System.exit(0);
		}
		
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			
			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			
			readString = sb.toString();
		} catch(IOException ioe) {
			System.out.println("Error: failed in reading line from file " + file);
			System.exit(0);
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				System.out.println("Error: failed in closing file " + file);
				System.exit(0);
			}
		}
		
		return readString;
	}
	
	private ArrayList<Integer> parseToInts(String input) {
		ArrayList<Integer> ints = new ArrayList<Integer>();
		
		String[] strings = input.split("\n");
		for (String s : strings) {
			try {
				ints.add(new Integer(Integer.parseInt(s)));
			} catch (NumberFormatException nfe) {
				System.out.println("Error: failed in parsing line in input file into integer");
				System.exit(0);
			}
		}
		
		return ints;
	}
	
	private void writeToFile(String outputFile, String output) {
		Writer writer = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "utf-8"));
			writer.write(output);
		} catch (IOException ex) {
			System.out.println("Error: failed writing to file.");
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
				System.out.println("Error: failed closing output file.");
			}
		}
	}
}
