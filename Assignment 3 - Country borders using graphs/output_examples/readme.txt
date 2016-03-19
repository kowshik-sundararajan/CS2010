Requirements and descriptions of outputs of your result

3.1(a) List all countries by integer ID [0 ~ N-1] and its neighboring countries
The ID [0 ~ N-1] of countries are the order of countries in input file ("countries_borders.dat").
	Format: 
		[Name of the country] (country ID): [Name of the country] (ID), [Name of the country] (ID), ... [Name of the country] (ID)
	Example:
		Adelie Land (France) (0): Australian Antarctic Territory (Australia) (17), Australian Antarctic Territory (Australia) (17)

		
3.1(b) Find shortest path between two countries
You should print the number of countries on the path (including source and destination) and the path itself. If there is no path between two countries, output "no path".
	Format:
		[number of countries on the path] : [Name of the country] (ID) -> [Name of the country] (ID) -> ... -> [Name of the country] (ID)
	Example:
		6 : Albania (3) -> Greece (99) -> Turkey (256) -> Iran (119) -> Pakistan (193) -> People's Republic of China (53)


3.2 List all countries with N neighbors
If there is no country with N neighbors, print "No country with [N] neighbors";
	Format:
		[Name of the country], [Name of the country], ... [Name of the country]
	Example:
		Austria, France, Iran, Serbia, Tanzania, Turkey, Zambia

		
3.3 Block a country
The path cannot go through the blocked countries. In the console interface, once you add a blocked country, it cannot be unblocked and will be always in the list of blocked countries when you find the shortest path. If there is no country being blocked, print nothing after "Blocked Country: ".
	Format: (Same as 3.1(b) part)
	Example:
		Blocked Country: Greece (99)
		7 : Albania (3) -> Montenegro (172) -> Serbia (225) -> Romania (209) -> Ukraine (261) -> Russia (211) -> People's Republic of China (53)
		
		
3.4 Find countries with borders larger than X
You need to print countries with borders larger than X as well as their length of borders and their all neighboring countries with length of borders. All lengths remain one decimal.
	Format: 
		[Name of the country] [length of borders] km -> [Name of the country] [length of borders] km, [Name of the country] [length of borders] km, ... [Name of the country] [length of borders] km
	Example:
		Russia 20017.0 km -> Ukraine 1576.0 km, Poland 206.0 km, Norway 196.0 km, Mongolia 3485.0 km, Lithuania 227.0 km, Latvia 217.0 km, North Korea 19.0 km, Kazakhstan 6846.0 km, Georgia 723.0 km, Finland 1340.0 km, Estonia 294.0 km, People's Republic of China 3645.0 km, Belarus 959.0 km, Azerbaijan 284.0 km
		
		
3.5 Show all connected components
You need to sort components from largest to smallest. If the number of components are same, sort in ascending order of the ID of first country in this component. Assign a component ID (from 0) for each component in this order. Print from component 0 to the last component. For each component, print all countries belonging to this component in ascending order of countries' ID. 
	Format:
		Component [component ID]: [number of countries in this component]
		  [Name of the country], [Name of the country], ... [Name of the country]
	Example
		Component 3: 3
		  Akrotiri and Dhekelia (United Kingdom), Cyprus, Northern Cyprus
		Component 4: 2
		  Dominican Republic, Haiti
		Component 5: 2
		  Ireland, United Kingdom
		  
		  
3.6 Show border around a path
You can finish this part when you find the shortest path. The length remains one decimal.
	Format (Print right after 3.1(b) or 3.3):
		length of border around this path = [length] km
	Example:
		Blocked Country: Greece (99)
		10 : Albania (3) -> Macedonia (152) -> Bulgaria (42) -> Turkey (256) -> Syria (245) -> Israel (123) -> Egypt (77) -> Sudan (239) -> South Sudan (236) -> Democratic Republic of the Congo (59)
		length of border around this path = 19542.0 km