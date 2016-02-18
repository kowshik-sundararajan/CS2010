import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/* Assignment 2 - CS2010 
 * "Transaction Monitoring"
 * 
 * Please put your name in all of your .java files
 * 
 * YOUR NAME: Kowshik Sundararajan
 * YOUR MATRICULATION #: A0132791E
 * 
 * COMMENTS:
 * You can modify this code anyway you'd like.  You can add additional class files, etc.
 * This code prompts the user at the command line to enter a filename (e.g. Visa.log) and the size of K.
 * It then each line of the input file as a string and prints that string out to the console.
 * 
 * 
 * Assigned: Feb 2 (Tuesday), 2016
 * Due: Feb 19 (Friday by 11.59pm), 2016
 */

public class TransMonitor {

	private static String fileName;
	private static int K;
	private static TreeMap<String, List<Double>> transactionAmounts = new TreeMap<String, List<Double>>();
	private static TreeMap<String, List<String>> transactionDates = new TreeMap<String, List<String>>();
	private static TreeMap<String, List<String>> transactionTimes = new TreeMap<String, List<String>>();
	private static TreeMap<String, Double> totalExpenditure = new TreeMap<String, Double>();

	private static ArrayList<String> biggestSpenders = new ArrayList<String>();

	private static String lastTransaction = "";

	private static Scanner sc = new Scanner(System.in);

	//Function to show user prompt
	public static void showUserPrompt() {
		System.out.print("Enter log file to open? ");
		acceptFileName();
		System.out.print("Number of top transactions to find (K)? ");
		acceptNumberOfTopTransactions();

	}

	//Function to accept the file name from the user
	public static void acceptFileName() {
		fileName = sc.next();
	}

	//Function to accept the value of K
	public static void acceptNumberOfTopTransactions() {
		K = sc.nextInt();
	}

	//Function to print top K transactions onto a file
	public static void printTopTransactions(PrintWriter pw, String inputFileStr) {
		pw.println(inputFileStr);
	}

	//Function to get transactions of each person
	public static void getTransactions(String inputFileStr) {
		String inputParts[] = inputFileStr.split(" ");
		Double amount = Double.parseDouble(inputParts[1].substring(1));

		ArrayList<Double> amounts = new ArrayList<Double>();
		ArrayList<String> dates = new ArrayList<String>();
		ArrayList<String> times = new ArrayList<String>();

		if(transactionAmounts.get(inputParts[0])!= null) {
			amounts = (ArrayList<Double>) transactionAmounts.get(inputParts[0]);
			dates = (ArrayList<String>) transactionDates.get(inputParts[0]);
			times = (ArrayList<String>) transactionTimes.get(inputParts[0]);
		}

		dates.add(inputParts[2]);
		times.add(inputParts[3]);
		amounts.add(amount);
		transactionAmounts.put(inputParts[0], amounts);
		transactionDates.put(inputParts[0], dates);
		transactionTimes.put(inputParts[0], times);
		lastTransaction = inputFileStr;
	}

	//Function to print the top K transactions of each person
	public static void printTopKTransactions(PrintWriter pw) {
		for(Map.Entry<String, List<Double>> entry : transactionAmounts.entrySet()) {
			PriorityQueue<Double> topKTransactions = new PriorityQueue<Double>();
			ArrayList<Double> temp = (ArrayList<Double>) entry.getValue();

			for(int j=0; j<temp.size(); j++) {
				topKTransactions.add(temp.get(j));
			}	
			int counter = 0;
			pw.print(entry.getKey() + ": ");
			while(topKTransactions.isEmpty() != true) {
				if(counter < (temp.size() - K)) {
					topKTransactions.poll();
				}
				else {
					pw.print(topKTransactions.poll() + " ");
				}
				counter++;
			}
			printTotalNumberOfTransactions(temp, pw);
			double average = printAverageDollarAmount(temp);
			pw.printf("avg = %.2f \n", average);
			pw.println();
			pw.flush();

		}
	}

	//Function to print the total number of transactions of a person
	public static void printTotalNumberOfTransactions(ArrayList<Double> temp, PrintWriter pw) {
		pw.print("# of transactions = " + temp.size()+ "; ");
		pw.flush();
	}

	//Function to print the average dollar amount for each person
	public static double printAverageDollarAmount(ArrayList<Double> temp) {
		double sum = 0.0;
		double average = 0.0;
		for(int i=0; i<temp.size(); i++) {
			sum += temp.get(i);
		}
		average = sum/temp.size();
		return average;
	}

	//Function to find double transactions
	public static void findDoubleTransactions(String inputFileStr, PrintWriter pw) throws ParseException {
		String[] inputParts = inputFileStr.split(" ");
		String currTransName = inputParts[0];
		Double currTransAmt = Double.parseDouble(inputParts[1].substring(1));
		String currTransDate = inputParts[2] + " " + inputParts[3];
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date d1 = formatter.parse(currTransDate);

		if(lastTransaction.isEmpty()!= true) {
			String[] oldInputParts = lastTransaction.split(" ");
			String pastTransName = oldInputParts[0];
			Double pastTransAmt = Double.parseDouble(oldInputParts[1].substring(1));
			String pastTransDate = oldInputParts[2] + " " + oldInputParts[3];
			Date d2 = formatter.parse(pastTransDate);

			if((currTransName.equals(pastTransName)) && (currTransAmt.equals(pastTransAmt))){				
				if(Math.abs(d1.getTime() - d2.getTime()) < 300000) {
					pw.println(inputParts[0] + " : $" + currTransAmt + "; " + formatter.format(d2) + "; " +
							" $" + currTransAmt + "; " + formatter.format(d1));
					pw.flush();
				}
			}
		}
	} 



	//Function to find suspicious transactions
	public static void findSuspiciousTransactions(PrintWriter pw) {
		for(Map.Entry<String, List<Double>> entry : transactionAmounts.entrySet()) {
			ArrayList<Double> temp = (ArrayList<Double>) entry.getValue();
			int index = 0;
			double max = 0.0;

			for(int i=0; i<temp.size(); i++) {
				if(temp.get(i) > max) {
					max = temp.get(i);
					index = i;
				}
			}

			double topTrans = temp.remove(index);
			double average = printAverageDollarAmount(temp);
			if(topTrans > (5*average)) {
				pw.print(entry.getKey() +": $" + topTrans + "; ");
				ArrayList<String> tempDate = (ArrayList<String>) transactionDates.get(entry.getKey());
				ArrayList<String> tempTime = (ArrayList<String>) transactionTimes.get(entry.getKey());
				pw.println(tempDate.get(index) + " " + tempTime.get(index));
				pw.flush();
			}
		}

	}

	//Function to find the total expenditure of each person
	public static void findTotalExpenditure(PrintWriter pw) {
		for(Map.Entry<String, List<Double>> entry : transactionAmounts.entrySet()) {
			ArrayList<Double> temp = (ArrayList<Double>)entry.getValue();
			double totalSum = 0;
			for(int i = 0; i<temp.size(); i++) {
				totalSum += temp.get(i);
			}
			totalExpenditure.put(entry.getKey(), totalSum);
		}
		findBiggestSpenders(pw);
	}

	//Function to find the biggest spenders
	public static void findBiggestSpenders(PrintWriter pw) {
		pw.println("Top 20 Spenders");
		int i=1;
		while(i <= 20) {
			double max = 0;
			String name = "";
			for(Map.Entry<String, Double> entry : totalExpenditure.entrySet()) {
				if(entry.getValue() > max) {
					max = entry.getValue();
					name = entry.getKey();
				}
			}
			ArrayList<String> tempDates = (ArrayList<String>) transactionDates.get(name);
			String startDate = tempDates.get(0);
			String endDate = tempDates.get(tempDates.size() - 1);
			
			pw.print(name + " : Total Amount = $");
			pw.printf("%.2f; ", max);
			pw.println("First Transaction on " + startDate + "; Last Transaction on " + endDate);
			totalExpenditure.remove(name);
			i++;
		}		
		pw.println("......");
		pw.flush();
	}
	//Function to read contents of the file
	public static void readFileContent() throws ParseException {
		// Read each line of the input file and write it to the console
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));  
			String inputFileStr;

			PrintWriter pw = new PrintWriter("Report_" + fileName + "_"+ K + ".txt");

			pw.println("......");
			pw.println("Double Transactions");
			while ((inputFileStr = in.readLine()) != null) {
				findDoubleTransactions(inputFileStr, pw);
				getTransactions(inputFileStr);
			}
			pw.println("......");
			printTopKTransactions(pw);
			pw.println("......");
			pw.println("Suspicious Transactions");
			findSuspiciousTransactions(pw);
			pw.println("......");
			findTotalExpenditure(pw);
			in.close();
		} catch (IOException e) {
			System.out.println("File read error for (" + fileName + ")! ");
			e.printStackTrace();
		}
	}

	//Function to print the transact
	public static void main(String[] args) throws ParseException {
		showUserPrompt();
		readFileContent(); 

	} /* end main */


} /* TransMonitor.java */
