/** Assignment 3 - CS2010 
 * "Undirected Graphs"
 * 
 * Please put your name in all of your .java files
 * 
 * YOUR NAME: 
 * YOUR MATRICULATION #: 
 * 
 * COMMENTS:
 * You are not allowed to change any existing codes for reading and writing. You
 * may add your own reading code or printing code to print your results of each part.
 * 
 * 
 * Assigned: Feb 29 (Monday), 2016
 * Due: Mar 18 (Friday by 11.59pm), 2016
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class A3Main {

	private static String fileName;
	private static Scanner sc = new Scanner(System.in);
	private static String countries[];
	private static int numberOfBorderCountries[];
	private static ArrayList<Integer> blockedCountries;
	private static double[] borderLengths;

	public static void showUserPrompt() {
		System.out.print("Enter log file to open? ");
		acceptFileName();

	}

	//Function to accept the file name from the user
	public static void acceptFileName() {
		fileName = sc.next();
	}


	public static void main(String[] args) throws IOException {    
		/*
		 * Your code: read and parse the file "countries_borders.dat"
		 */
		showUserPrompt();
		int numberOfCountries = 0;
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		BufferedReader br1 = new BufferedReader(new FileReader(fileName));
		BufferedReader br2 = new BufferedReader(new FileReader(fileName));

		while(br.readLine()!= null) {
			numberOfCountries++;
		}
		countries = new String[numberOfCountries];
		numberOfBorderCountries = new int[numberOfCountries];
		blockedCountries = new ArrayList<Integer>();
		borderLengths = new double[numberOfCountries];

		int count = 0;
		AdjListGraph adjListGraph = new AdjListGraph(numberOfCountries);

		String line;
		while((line = br1.readLine())!= null) {
			String temp = "";
			String inputParts[] = line.split(" ");

			for(int i = 0; i<inputParts.length; i++) {
				if(inputParts[i].equals(">>")) {
					countries[count] = temp.trim();
					count++;
					temp = "";
				}
				else {
					temp += inputParts[i] + " ";
				}
			}
		}

		parseBorderCountries(numberOfCountries, br2, adjListGraph);
		computeBorderLengths(numberOfCountries, adjListGraph);

		boolean exit = false;
		String menuInfo = 
				"================ Assignment 3 ==================\n"
						+ "1. List all countries and bordering countries \n"
						+ "2. Find shortest path \n"
						+ "3. List all countries with N neighbors \n"
						+ "4. Block a country \n"
						+ "5. Find countries with borders larger than X km \n"
						+ "6. Show all connected components (largest to smallest) \n"
						+ "7. Exit \n> ";

		while (!exit) {
			System.out.print(menuInfo);
			int menu = sc.nextInt();
			System.out.println("================================================");

			switch (menu) {
			case 1:
				// List all countries and bordering countries
				for(int i = 0; i<countries.length; i++) {
					Iterable<double[]> adj = adjListGraph.adj(i);
					System.out.print(countries[i] + " (" + i + "): ");

					int id = 0;
					int[] info = new int[numberOfBorderCountries[i]];
					int counter = info.length - 1;
					if(numberOfBorderCountries[i] > 0) {
						for(double[] temp : adj) {
							id = (int)temp[0];
							info[counter--] = id;
						}

						for(int j = 0; j<info.length - 1; j++) {
							System.out.print(countries[info[j]]+ " (" + info[j] + "), ");
						}
						System.out.println(countries[info[info.length - 1]]+ " (" + info[info.length - 1] + ")");
					}
					else {
						System.out.println();
					}
				}
				System.out.println();
				break;

			case 2: // find shortest path
				System.out.println(">>> 3.1(b) Find shortest path");
				while (true) {
					System.out.format(">>> Enter source and destination pair (or -1 to escape): ");
					int source = sc.nextInt();
					if (source == -1) // -1 to escape
						break;
					int destination = sc.nextInt();  // destination id
					printBlockedCountries();
					double totalBorderLength = 0;
					BreadthFirstPaths bfsPath = new BreadthFirstPaths(adjListGraph, source, blockedCountries);
					if(bfsPath.hasPathTo(destination)) {
						System.out.print(bfsPath.distTo(destination)+1 + " : ");
						int[] info = new int[bfsPath.distTo(destination) + 1];
						int counter = 0;
						Iterable<Integer> path = bfsPath.pathTo(destination);
						for(int w : path) {
							info[counter++] = w;
						}
						totalBorderLength = findBorderAroundShortestPath(adjListGraph, totalBorderLength, info);
					}
					else {
						System.out.println("no path");
					}
					/*
					 * Your code: find shortest path and print the result
					 * ** For requirement 3.6, you can compute and print 
					 * in this part
					 */

				} // end while
				System.out.println();
				break;

			case 3:
				System.out.println("\n>>> 3.2 List all countries with N neighbors");
				while (true) {
					System.out.print(">>> Enter N (or -1 to escape): ");
					int n = sc.nextInt();
					if (n == -1) // -1 to escape
						break;
					int[] list = new int[numberOfCountries];
					for(int element : list) {
						element = -1;
					}
					int index = 0;
					for(int i = 0; i<countries.length; i++) {
						int counter = 0;
						Iterable<double[]> adj = adjListGraph.adj(i);
						for(double[] temp : adj) {
							counter++;
							if(counter > n) {
								break;
							}
						}
						if(counter == n) {
							list[index++] = i;
						}
					}
					printCountriesWithNNeighbours(list, index);
				}
				/*
				 * Your code: find countries with n neighbors and print
				 * the result
				 */
				// end while
				System.out.println();
				break;

			case 4:
				System.out.println("\n>>> 3.3 Block countries");
				while (true) {
					System.out.format(">>> Enter ID of the country you want to block (or -1 to escape): ");
					int id = sc.nextInt();
					if (id == -1) // -1 to escape
						break;				
					blockedCountries.add(id);									
					/*
					 * Your code: 
					 * (1) you can find shortest path here OR
					 * (2) 
					 * list and finish this part by modifying your code in case 2
					 * You are allowed to jump to other part of switch branch
					 * to help you finish this part
					 */

				} // end while
				System.out.println();
				break;

			case 5:
				System.out.println("\n>>> 3.4 Find country with borders larger than X");
				while (true) {
					System.out.print(">>> Enter border length X (or -1 to escape): ");
					double x = sc.nextDouble();
					if (x < 0) // -1 to escape
						break;

					for(int i = 0; i<borderLengths.length; i++) {
						if(borderLengths[i] > x) {
							System.out.print(countries[i] + " " + borderLengths[i] + " km -> ");

							Iterable<double[]> adj = adjListGraph.adj(i);
							String toBePrinted = "";
							for(double[] temp : adj) {
								toBePrinted += countries[(int)temp[0]] + " " + temp[1] + " km, ";
							}
							toBePrinted = toBePrinted.substring(0, toBePrinted.length() - 2);
							System.out.println(toBePrinted);
						}
					}
					/*
					 * Your code: find country with borders larger than X
					 * and print the result
					 */

				} // end while
				break;

			case 6:
				System.out.println("\n>>> 3.5 Show all connected components");
				ArrayList<ArrayList<Integer>> superList = new ArrayList<ArrayList<Integer>>();
				ArrayList<Integer> listOfCountries = new ArrayList<Integer>();
				for(int i = 0; i<numberOfCountries; i++) {
					if(listOfCountries.contains(i) != true){
						ArrayList<Integer> currentConnected = new ArrayList<Integer>();
						Queue<Integer> tempQ = new Queue<Integer>();
						tempQ.enqueue(i);
						while(tempQ.isEmpty() != true) {
							Integer number = tempQ.dequeue();
							if(currentConnected.contains(number) != true) {
								currentConnected.add(number);
								listOfCountries.add(number);
								Iterable<double[]> neighbours = adjListGraph.adj(number);
								for(double[] temp : neighbours) {
									tempQ.enqueue((int)temp[0]);
								}
							}
						}
						Collections.sort(currentConnected);
						superList.add(currentConnected);
					}
				}
				
				sortSuperList(superList);
				
				for(int i = 0; i<superList.size(); i++) {
					System.out.println("Component " + i + ": " + superList.get(i).size());
					for(int j = 0; j<superList.get(i).size() - 1; j++) {
						System.out.print(countries[superList.get(i).get(j)] + ", ");
					}
					System.out.println(countries[superList.get(i).get(superList.get(i).size() - 1)]);
				}
				/*
				 * Your code: do connected components analysis and print the result
				 */

				break;

			case 7:
				// exit
				exit = true;
				System.out.println(">>> Exit");
				break;

			default:
				System.out.println("** Wrong command **");
				break;

			} // end switch (menu)

		} // end while

	}

	public static void sortSuperList(ArrayList<ArrayList<Integer>> superList) {
		for(int i = 0; i<superList.size(); i++) {
			for(int j = 0; j<superList.size() - i - 1; j++) {
				if(superList.get(j).size() < superList.get(j+1).size()) {
					Collections.swap(superList, j, j+1);
				}
			}
		}
	}

	public static void parseBorderCountries(int numberOfCountries, BufferedReader br2, AdjListGraph adjListGraph)
			throws IOException {
		int count;
		String line;
		count = -1;
		while((line = br2.readLine())!= null) {
			String temp = "";
			String inputParts[] = line.split(" ");
			count++;
			for(int i = 0; i<inputParts.length; i++) {
				if(inputParts[i].equals(">>")) {
					temp = "";	
				}

				else if(inputParts[i].contains(":")) {
					temp += inputParts[i];
					String nameOfBorderCountry = temp.substring(0, temp.length() - 1);
					String distance = "";

					int idOfBorderCountry = 0;
					for(int j = 0; j<numberOfCountries; j++) {
						if(countries[j].equals(nameOfBorderCountry)) {
							idOfBorderCountry = j;
							break;
						}
					}

					double distanceToBorderCountry = 0;
					distance = inputParts[i+1];
					distance = distance.replaceAll(",", "");
					distanceToBorderCountry = Double.parseDouble(distance);

					adjListGraph.addEdge(count, idOfBorderCountry, distanceToBorderCountry);
					temp="";
					numberOfBorderCountries[count]++;
				}
				else if(inputParts[i].contains("km")) {
					temp ="";
				}
				else if(inputParts[i].contains(";") && (inputParts[i].length() > 1)) {
					temp += inputParts[i];
					String nameOfBorderCountry = temp.substring(0, temp.length() - 1).trim();

					int idOfBorderCountry = 0;
					for(int j = 0; j<numberOfCountries; j++) {
						if(countries[j].equals(nameOfBorderCountry)) {
							idOfBorderCountry = j;
							break;
						}
					}
					adjListGraph.addEdge(count, idOfBorderCountry, 0);
					temp = "";
					numberOfBorderCountries[count]++;
				}
				else if(inputParts[i].equals(";")) {
					break;
				}
				else {
					temp += inputParts[i] + " ";
				}
			}
		}
	} // public static void main(String[] args)

	public static double findBorderAroundShortestPath(AdjListGraph adjListGraph, double totalBorderLength, int[] info) {
		String toBePrinted = "";
		for(int i = 0; i<info.length; i++) {
			totalBorderLength += borderLengths[info[i]];
			Iterable<double[]> adj = adjListGraph.adj(info[i]);
			for(double[] temp : adj) {
				if((i>0) && (temp[0] == info[i-1])) {
					totalBorderLength -= temp[1];
				}
				if((i<info.length - 1) && ((int)temp[0] == info[i+1])) {
					totalBorderLength -= temp[1];
				}	
			}
			toBePrinted += countries[info[i]] + " (" + info[i] + ") -> ";
		}
		System.out.println(toBePrinted.substring(0, toBePrinted.length() - 3));
		System.out.println("length of border around this path: " + totalBorderLength + " km");
		return totalBorderLength;
	}

	public static void printCountriesWithNNeighbours(int[] list, int index) {
		for(int i = 0; i<index - 1; i++) {
			System.out.print(countries[list[i]] + ", ");
		}
		System.out.println(countries[list[index - 1]]);
	}

	public static void computeBorderLengths(int numberOfCountries, AdjListGraph adjListGraph) {
		for(int i = 0; i<numberOfCountries; i++) {
			Iterable<double[]> adj = adjListGraph.adj(i);
			double countryBorderLength = 0;
			for(double[] temp : adj) {
				countryBorderLength += temp[1];
			}
			borderLengths[i] = countryBorderLength;
		}
	}

	public static void printBlockedCountries() {
		if(blockedCountries.size() > 0) {
			System.out.print("Blocked Country : ");
			for(int i = 0; i<blockedCountries.size() - 1; i++) {
				System.out.print(countries[blockedCountries.get(i)] + " (" + blockedCountries.get(i)+ ") , ");
			}
			System.out.println(countries[blockedCountries.get(blockedCountries.size() - 1)] + " (" + blockedCountries.get(blockedCountries.size() - 1)+ ")");
		}
	}

} // class A3Main
