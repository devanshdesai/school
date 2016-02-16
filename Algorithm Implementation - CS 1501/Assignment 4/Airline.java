/*  DEVANSH DESAI
    CS 1501 FALL 2015
    ASSIGNMENT 04
    Airline.java
*/

import java.io.*;
import java.util.*;

public class Airline
{
    private static int numCities;
    private static List<List <City>> routeList = new ArrayList<List <City>>();

    // Simply prints all of the routes in the route list
    public static void printRoutes(List<List <City>> routes)
    {
        System.out.println("");
        for (int i = 0; i < routes.size(); i++)
        {
            System.out.println("From:");
            System.out.println(routes.get(i).get(0).getName() + "\n\nTo:");

            for (int j = 1; j < routes.get(i).size(); j++)
            {
                System.out.println(routes.get(i).get(j).getName() + " --- $" + routes.get(i).get(j).getCost() + " / " + routes.get(i).get(j).getDistance() + " miles");
            }
            System.out.println("______________________\n");
        }
    }

    // Prints a minimum spanning tree of the route list
    public static void printMST(List<List <City>> routes, int start, int total)
    {
        List<List <City>> mst = new ArrayList<List<City>>();
        List<City> visitedCities = new ArrayList<City>();
        List<Integer> visitedCityIndices = new ArrayList<Integer>();
        boolean allNodes = false;
        int mstCities = 1;
        int fromCity = 0;
        int toCity = 0;
        mst.add(new ArrayList<City>());
        City minDist = null;

        mst.get(0).add(routes.get(start).get(0));
        visitedCities.add(mst.get(0).get(0));
        visitedCityIndices.add(start);

        while (!allNodes)
        {
            for (int i = 0; i < mstCities; i++)
            {
                for (int j = 1; j < routes.get(visitedCityIndices.get(i)).size(); j++)
                {
                    if (minDist == null)
                    {
                        minDist = routes.get(start).get(0);
                        minDist.setDistance(Integer.MAX_VALUE);
                    }
                    //System.out.println(checkVisitedCities(visitedCities, routes.get(visitedCityIndices.get(i)).get(j)));
                    if (minDist.getDistance() > routes.get(visitedCityIndices.get(i)).get(j).getDistance() && !checkVisitedCities(visitedCities, routes.get(visitedCityIndices.get(i)).get(j)))
                    {
                        minDist = routes.get(visitedCityIndices.get(i)).get(j);
                        fromCity = i;
                        toCity = j;
                    }
                }
            }

            // This checks if the current run of the MST search includes all of the graph.
            if (minDist.getDistance() == Integer.MAX_VALUE)
            {
                System.out.println("\n\n\n\nTHESE CITIES FORM A SET OF CONNECTED CITIES:\n");
                printRoutes(mst);
                allNodes = true;
                break;
            }

            mst.get(fromCity).add(minDist);
            mst.add(new ArrayList<City>());
            City temp = new City(minDist.getName(), 0, 0);
            mst.get(mstCities).add(temp);
            temp = new City(mst.get(fromCity).get(0).getName(), minDist.getDistance(), minDist.getCost());
            mst.get(mstCities).add(temp);
            visitedCities.add(minDist);
            visitedCityIndices.add(getCityIndex(minDist));
            mstCities++;
            minDist = null;
        }

        // If the MST search did not find all of the verticies, then that means that
        // the graph is not fully connected and so we have to search for other graphs inside the set
        // of verticies given as input.
        if (mstCities != total)
        {
            int unconnectedVertex = 0;
            for (int i = 0; i < total; i++)
            {
                if (!visitedCityIndices.contains(i))
                {
                    unconnectedVertex = i;
                    break;
                }
            }
            printMST(routes, unconnectedVertex, total - mstCities);
        }
    }

    // Prints the shortest path based on distance
    public static void printSPDistance(List<List <City>> routes, int startIndex, String start, String end)
    {
        List<Integer> cityDistances = new ArrayList<Integer>();
        List<City> visitedCities = new ArrayList<City>();
        List<Integer> visitedCityIndices = new ArrayList<Integer>();
        List<List<City>> path = new ArrayList<List<City>>();
        int visited = 1;
        int index = -1;
        int savedIndex = -1;
        int pathCounter = 1;
        String prevCityName = "";
        City min = routes.get(startIndex).get(0);
        City temp = new City(start);
        min.setDistance(Integer.MAX_VALUE);

        visitedCities.add(temp);
        visitedCityIndices.add(startIndex);
        path.add(new ArrayList<City>());
        path.get(0).add(temp);

        // This checks if the entered two cities even exist in the route list.
        for (int i = 0; i < routes.size(); i++)
        {
            City x = new City(start);
            if (getCityIndex(x) == -1)
            {
                System.out.println("\n\nERROR: The starting city does not exist in the route list.");
                return;
            }
            x.setName(end);
            if (getCityIndex(x) == -1)
            {
                System.out.println("\n\nERROR: The ending city does not exist in the route list.");
                return;
            }
        }

        // This initializes all of the veritices to have MAX_INT distances initially.
        for (int i = 0; i < routes.size(); i++)
        {
            if (i == startIndex)
            {
                cityDistances.add(0);
            }
            else
            {
                cityDistances.add(Integer.MAX_VALUE);
            }
        }

        boolean found = false;
        while (!found)
        {
            for (int i = 0; i < visited; i++)
            {
                for (int j = 1; j < routes.get(visitedCityIndices.get(i)).size(); j++)
                {
                    index = visitedCityIndices.get(i);
                    temp = routes.get(index).get(j);

                    if (cityDistances.get(getCityIndex(temp)) > (cityDistances.get(index) + temp.getDistance()) )
                    {
                        cityDistances.set(getCityIndex(temp), cityDistances.get(index) + temp.getDistance());
                    }

                    if (min.getDistance() > (cityDistances.get(index) + temp.getDistance()) && !checkVisitedCities(visitedCities, temp))
                    {
                        min = temp;
                        savedIndex = getCityIndex(min);
                        prevCityName = routes.get(index).get(0).getName();
                    }
                }
            }

            // If the min is still MAX_INT, there is no other city we can visit.
            // This means that a path cannot exist between the starting city and ending city.
            if (min.getDistance() == Integer.MAX_VALUE)
            {
                System.out.println("\n\nSorry, a route does not exist " + start + " and " + end + ".");
                return;
            }

            boolean prevCityFound = false;

            // This adjusts the stack based on what city the algorithm chooses to go to next, making sure
            // we have the correct path at the end of the search.
            while (!prevCityFound)
            {
                for (int i = 0; i < pathCounter; i++)
                {
                    if (path.get(i).get(path.get(i).size()-1).getName().equals(prevCityName))
                    {
                        path.add(new ArrayList<City>());
                        for (int j = 0; j < path.get(i).size(); j++)
                        {
                            path.get(pathCounter).add(path.get(i).get(j));
                        }
                        pathCounter++;
                        path.get(path.size()-1).add(min);
                        prevCityFound = true;
                        break;
                    }
                }
            }

            // Adds the necessary data to the visited city arrays so that we don't visit them again and can access
            // information easily about these visited cities.
            visitedCities.add(min);
            visitedCityIndices.add(savedIndex);
            visited++;

            // This stops the search if we choose the end city as our next city to visit
            if (min.getName().equals(end))
            {
                found = true;
            }

            // Resets the min to have MAX_INT distance so a new min can be chosen later.
            min = new City(path.get(path.size()-1).get(0).getName(), Integer.MAX_VALUE, Double.MAX_VALUE);
        }

        // This reverses the path that is saved in a stack so it can be printed from starting city to ending city
        List<City> pathToPrint = new ArrayList<City>();
        pathToPrint = path.get(path.size()-1);

        // This prints the path of the shortest route that is saved in a stack
        System.out.println("\n\nShortest route (by distance) from " + start + " to " + end + ":\n");
        int pathC = 0;
        System.out.println(pathToPrint.get(pathC).getName());
        pathC++;
        City toPrint;
        int totalDistance = 0;
        double totalCost = 0.0;
        while(pathC != pathToPrint.size())
        {
            System.out.println("--->");
            toPrint = pathToPrint.get(pathC);
            System.out.println(toPrint.getName() + " --- $" + toPrint.getCost() + " / " + toPrint.getDistance() + " miles");
            totalDistance += toPrint.getDistance();
            totalCost += toPrint.getCost();
            pathC++;
        }

        System.out.println("\nTotal Distance: " + totalDistance + " miles\nTotal Cost: $" + totalCost);
    }


    // Prints the shortest path based on cost
    public static void printSPCost(List<List <City>> routes, int startIndex, String start, String end)
    {
        List<Double> cityCosts = new ArrayList<Double>();
        List<City> visitedCities = new ArrayList<City>();
        List<Integer> visitedCityIndices = new ArrayList<Integer>();
        List<List<City>> path = new ArrayList<List<City>>();
        int visited = 1;
        int index = -1;
        int savedIndex = -1;
        int pathCounter = 1;
        String prevCityName = "";
        City min = routes.get(startIndex).get(0);
        City temp = new City(start);
        min.setCost(Double.MAX_VALUE);

        visitedCities.add(temp);
        visitedCityIndices.add(startIndex);
        path.add(new ArrayList<City>());
        path.get(0).add(temp);

        // This checks if the entered two cities even exist in the route list.
        for (int i = 0; i < routes.size(); i++)
        {
            City x = new City(start);
            if (getCityIndex(x) == -1)
            {
                System.out.println("\n\nERROR: The starting city does not exist in the route list.");
                return;
            }
            x.setName(end);
            if (getCityIndex(x) == -1)
            {
                System.out.println("\n\nERROR: The ending city does not exist in the route list.");
                return;
            }
        }

        // This initializes all of the veritices to have MAX_INT distances initially.
        for (int i = 0; i < routes.size(); i++)
        {
            if (i == startIndex)
            {
                cityCosts.add(0.0);
            }
            else
            {
                cityCosts.add(Double.MAX_VALUE);
            }
        }

        boolean found = false;
        while (!found)
        {
            for (int i = 0; i < visited; i++)
            {
                for (int j = 1; j < routes.get(visitedCityIndices.get(i)).size(); j++)
                {
                    index = visitedCityIndices.get(i);
                    temp = routes.get(index).get(j);

                    if (cityCosts.get(getCityIndex(temp)) > (cityCosts.get(index) + temp.getCost()) )
                    {
                        cityCosts.set(getCityIndex(temp), cityCosts.get(index) + temp.getCost());
                    }

                    if (min.getCost() > (cityCosts.get(index) + temp.getCost()) && !checkVisitedCities(visitedCities, temp))
                    {
                        min = temp;
                        savedIndex = getCityIndex(min);
                        prevCityName = routes.get(index).get(0).getName();
                    }
                }
            }

            // If the min is still MAX_INT, there is no other city we can visit.
            // This means that a path cannot exist between the starting city and ending city.
            if (min.getCost() == Double.MAX_VALUE)
            {
                System.out.println("\n\nSorry, a route does not exist " + start + " and " + end + ".");
                return;
            }

            boolean prevCityFound = false;

            // This adjusts the stack based on what city the algorithm chooses to go to next, making sure
            // we have the correct path at the end of the search.
            while (!prevCityFound)
            {
                for (int i = 0; i < pathCounter; i++)
                {
                    if (path.get(i).get(path.get(i).size()-1).getName().equals(prevCityName))
                    {
                        path.add(new ArrayList<City>());
                        for (int j = 0; j < path.get(i).size(); j++)
                        {
                            path.get(pathCounter).add(path.get(i).get(j));
                        }
                        pathCounter++;
                        path.get(path.size()-1).add(min);
                        prevCityFound = true;
                        break;
                    }
                }
            }

            // Adds the necessary data to the visited city arrays so that we don't visit them again and can access
            // information easily about these visited cities.
            visitedCities.add(min);
            visitedCityIndices.add(savedIndex);
            visited++;

            // This stops the search if we choose the end city as our next city to visit
            if (min.getName().equals(end))
            {
                found = true;
            }

            // Resets the min to have MAX_INT distance so a new min can be chosen later.
            min = new City(path.get(0).get(0).getName(), Integer.MAX_VALUE, Double.MAX_VALUE);
        }

        // This reverses the path that is saved in a stack so it can be printed from starting city to ending city
        List<City> pathToPrint = new ArrayList<City>();
        pathToPrint = path.get(path.size()-1);

        // This prints the path of the shortest route that is saved in a stack
        System.out.println("\n\nShortest route (by cost) from " + start + " to " + end + ":\n");
        int pathC = 0;
        System.out.println(pathToPrint.get(pathC).getName());
        pathC++;
        City toPrint;
        int totalDistance = 0;
        double totalCost = 0.0;
        while(pathC != pathToPrint.size())
        {
            System.out.println("--->");
            toPrint = pathToPrint.get(pathC);
            System.out.println(toPrint.getName() + " --- $" + toPrint.getCost() + " / " + toPrint.getDistance() + " miles");
            totalDistance += toPrint.getDistance();
            totalCost += toPrint.getCost();
            pathC++;
        }

        System.out.println("\nTotal Cost: $" + totalCost + "\nTotal Distance: " + totalDistance + " miles");
    }

    // Prints the shortest path based on distance
    public static void printSPHops(int index, int endIndex, String start, String end, List<City> path, int hops)
    {
        /*
        Queue vertices = new LinkedList();
        vertices.add(index);


        for (int i = 1; i < routeList.get(index).size(); i++)
        {
            if (getCityIndex(routeList.get(index).get(i) == endIndex))
            {
                path.add(getCityIndex(routeList.get(index).get(i)));
                int pathC = 0;
                System.out.println("\n" + path.get(pathC).getName());
                pathC++;
                City toPrint;
                int totalDistance = 0;
                double totalCost = 0.0;
                while(pathC != path.size())
                {
                    System.out.println("--->");
                    toPrint = path.get(pathC);
                    System.out.println(toPrint.getName() + " --- $" + toPrint.getCost() + " / " + toPrint.getDistance() + " miles");
                    totalDistance += toPrint.getDistance();
                    totalCost += toPrint.getCost();
                    pathC++;
                }
                System.out.println("Total Distance: " + totalDistance + " miles\nTotal Cost: $" + totalCost);
                return;
            }
            vertices.add(getCityIndex(routeList.get(index).get(i)));
        }
        */
    }

    public static void printPathsLessThan(double total, double current, List<City> path, int index)
    {
        if (current < total && current != 0.0)
        {
            int pathC = 0;
            System.out.println("\n" + path.get(pathC).getName());
            pathC++;
            City toPrint;
            int totalDistance = 0;
            double totalCost = 0.0;
            while(pathC != path.size())
            {
                System.out.println("--->");
                toPrint = path.get(pathC);
                System.out.println(toPrint.getName() + " --- $" + toPrint.getCost() + " / " + toPrint.getDistance() + " miles");
                totalDistance += toPrint.getDistance();
                totalCost += toPrint.getCost();
                pathC++;
            }
            System.out.println("Total Distance: " + totalDistance + " miles\nTotal Cost: $" + totalCost);
        }
        else if (current > total)
        {
            return;
        }

        City temp;
        for (int i = 1; i < routeList.get(index).size(); i++)
        {
            temp = routeList.get(index).get(i);
            if (!checkVisitedCities(path, temp))
            {
                path.add(temp);
                int x = getCityIndex(temp);
                printPathsLessThan(total, current + temp.getCost(), path, x);
                path.remove(path.size()-1);
            }
        }
    }

    public static void printPathsLessThan(double total)
    {
        List<City> path = new ArrayList<City>();
        System.out.println("\n\nHere are all of the routes less with a cost less than $" + total + ":");
        for (int i = 0; i < routeList.size(); i++)
        {
            path.add(routeList.get(i).get(0));
            printPathsLessThan(total, 0, path, i);
            path.remove(0);
        }
    }

    // Checks if the given city is in the route list
    public static boolean checkVisitedCities(List<City> visitedCities, City toCheck)
    {
        for (int i = 0; i < visitedCities.size(); i++)
        {
            if (visitedCities.get(i).getName().equals(toCheck.getName()))
            {
                return true;
            }
        }
        return false;
    }

    // Returns the index of the given city in the route list
    public static int getCityIndex(City toCheck)
    {
        for (int i = 0; i < routeList.size(); i++)
        {
            if (routeList.get(i).get(0).getName().equals(toCheck.getName()))
            {
                return i;
            }
        }
        return -1;
    }

    public static int getCityIndex(String toCheck)
    {
        for (int i = 0; i < routeList.size(); i++)
        {
            if (routeList.get(i).get(0).getName().equals(toCheck))
            {
                return i;
            }
        }
        return -1;
    }

    // Adds the route into the route list
    public static boolean addRoute(List<List <City>> routes, String routeInfo)
    {
        String[] info = routeInfo.split(" ");
        if (info.length != 4)
        {
            System.out.println("ERROR: Incorrect route formatting.");
            return false;
        }

        int firstCity = Integer.parseInt(info[0]);
        int secondCity = Integer.parseInt(info[1]);
        int distance = Integer.parseInt(info[2]);
        double cost = Double.parseDouble(info[3]);

        routes.get(firstCity - 1).add(new City(routes.get(secondCity - 1).get(0).getName(), distance, cost));
        routes.get(secondCity - 1).add(new City(routes.get(firstCity - 1).get(0).getName(), distance, cost));
        return true;
    }

    // Removes route from the route list
    public static boolean removeRoute(List<List <City>> routes, String routeInfo)
    {
        String[] info = routeInfo.split(" ");
        if (info.length != 2)
        {
            System.out.println("ERROR: Incorrect route formatting.");
            return false;
        }

        int firstCity = Integer.parseInt(info[0]);
        int secondCity = Integer.parseInt(info[1]);

        for (int i = 1; i < routes.get(firstCity - 1).size(); i++)
        {
            if (routes.get(firstCity - 1).get(i).getName().equals(routes.get(secondCity - 1).get(0).getName()))
            {
                routes.get(firstCity - 1).remove(i);
            }
        }

        for (int i = 1; i < routes.get(secondCity - 1).size(); i++)
        {
            if (routes.get(secondCity - 1).get(i).getName().equals(routes.get(firstCity - 1).get(0).getName()))
            {
                routes.get(secondCity - 1).remove(i);
            }
        }

        return true;
    }

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException
    {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("\nWELCOME TO THE CS 1501 AIRLINE ROUTE TRACKER!\n\nPlease enter the name of the file containing the Airline routes: ");
        String input = keyboard.nextLine();
        File data = new File(input);

        if (!data.isFile())
        {
            System.out.println("ERROR: File does not exist.");
            System.exit(0);
        }

        String filename = input;

        Scanner reader = new Scanner(data);

        numCities = Integer.parseInt(reader.nextLine());

        List<List <City>> routes = new ArrayList<List<City>>();
        String line;
        for (int i = 0; i < numCities; i++)
        {
            line = reader.nextLine();
            City temp = new City(line);
            routes.add(new ArrayList<City>());
            routes.get(i).add(temp);
        }


        String[] values;
        int firstCity;
        int secondCity;
        int dist;
        double price;

        while (reader.hasNextLine())
        {
            line = reader.nextLine();
            values = line.split(" ");
            firstCity = Integer.parseInt(values[0]);
            secondCity = Integer.parseInt(values[1]);
            dist = Integer.parseInt(values[2]);
            price = Double.parseDouble(values[3]);

            String fCity = routes.get(firstCity - 1).get(0).getName();
            String sCity = routes.get(secondCity - 1).get(0).getName();
            City toSecond = new City(sCity, dist, price);
            City toFirst = new City(fCity, dist, price);

            routes.get(firstCity - 1).add(toSecond);
            routes.get(secondCity - 1).add(toFirst);
        }

        routeList = routes;
        System.out.println("\nRoute list has been loaded.");


        boolean quit = false;
        while (!quit)
        {
            System.out.print("\n\nWhat would you like to do? ");
            System.out.println("Enter one of these commands:\n'd' - Display the entire list of routes\n'mst' - Minimum spanning tree of the routes\n'spd' - Shortest path by distance\n'spc' - Shortest path by cost\n'sph' Shortest path by hops\n'plt' - Paths less than a dollar amount\n'a' - Add a route\n'r' - Remove a route\n'q' - Quit\n");
            input = keyboard.nextLine();
            input = input.toLowerCase();
            if (input.equals("d"))
            {
                printRoutes(routes);
            }
            else if (input.equals("mst"))
            {
                printMST(routes, 0, numCities);
            }
            else if (input.equals("spd"))
            {
                while (true)
                {
                    System.out.println("\nEnter the two cities you would like to find the shortest path between in this format (capitalized, separate by a space):");
                    System.out.println("NewYork Rome\n");
                    input = keyboard.nextLine();
                    String[] cities = input.split(" ");
                    if (cities.length == 2)
                    {
                        printSPDistance(routes, getCityIndex(cities[1]), cities[1], cities[0]);
                        break;
                    }
                    else
                    {
                        System.out.println("\nPlease enter the cities in the correct format.");
                    }
                }
            }
            else if (input.equals("spc"))
            {
                while (true)
                {
                    System.out.println("\nEnter the two cities you would like to find the shortest path between in this format (capitalized, separate by a space):");
                    System.out.println("NewYork Rome\n");
                    input = keyboard.nextLine();
                    String[] cities = input.split(" ");
                    if (cities.length == 2)
                    {
                        printSPCost(routes, getCityIndex(cities[1]), cities[1], cities[0]);
                        break;
                    }
                    else
                    {
                        System.out.println("\nPlease enter the cities in the correct format.");
                    }
                }
            }
            else if (input.equals("sph"))
            {
                System.out.println("\nSorry, shortest path search by hops is not implemented.");
                /*
                while (true)
                {
                    System.out.println("\nEnter the two cities you would like to find the shortest path between in this format (capitalized, separate by a space):");
                    System.out.println("NewYork Rome\n");
                    input = keyboard.nextLine();
                    String[] cities = input.split(" ");
                    if (cities.length == 2)
                    {
                        printSPHops(routes, getCityIndex(cities[0]), cities[0], cities[1]);
                        break;
                    }
                    else
                    {
                        System.out.println("\nPlease enter the cities in the correct format.");
                    }
                }
                */
            }
            else if (input.equals("plt"))
            {
                System.out.println("\nEnter a dollar amount:");
                int amount = keyboard.nextInt();
                printPathsLessThan(amount);
            }
            else if (input.equals("a"))
            {
                while (true)
                {
                    System.out.println("\nEnter the route that you would like to add in this format (StartCityIndex EndCityIndex Distance Cost):");
                    System.out.println("1 8 600 950.00\n");
                    input = keyboard.nextLine();
                    if (addRoute(routes, input))
                    {
                        System.out.println("\nThe new route has been added.");
                        break;
                    }
                    else
                    {
                        System.out.println("\nPlease enter the new route information in the correct format.");
                    }
                }
            }
            else if (input.equals("r"))
            {
                while (true)
                {
                    System.out.println("\nEnter the route that you would like to remove in this format (StartCityIndex EndCityIndex):");
                    System.out.println("5 4\n");
                    input = keyboard.nextLine();
                    if (removeRoute(routes, input))
                    {
                        System.out.println("\nThe route has been removed.");
                        break;
                    }
                    else
                    {
                        System.out.println("\nPlease enter the route information in the correct format.");
                    }
                }
            }
            else if (input.equals("q"))
            {
                PrintWriter writer = new PrintWriter(filename, "UTF-8");
                writer.println(numCities);
                for (int i = 0; i < numCities; i++)
                {
                    writer.println(routes.get(i).get(0).getName());
                }

                City c = routes.get(0).get(0);
                List<City> printedCities = new ArrayList<City>();
                for (int i = 0; i < numCities; i++)
                {
                    for (int j = 1; j < routes.get(i).size(); j++)
                    {
                        c = routes.get(i).get(j);
                        if (!checkVisitedCities(printedCities, c))
                        {
                            writer.println((i+1) + " " + (getCityIndex(c)+1) + " " + c.getDistance() + " " + c.getCost());
                        }
                    }
                    printedCities.add(routes.get(i).get(0));
                }
                writer.close();
                quit = true;
            }
        }
    }
}
