package veryhard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AirportConnections {
    /*
    you were given a  list of airports (three letter codes like "JFK"), and a list of routes
    ["JFK" , "SFO"] and a starting point.

    write a function that returns minimum number of airport connections (one-way flights)
    that need to be added in order for someone to be able to reach any airport in the list,
    starting at the starting airport.

    the input given routes allow you to fly only in one direction, for eg: route ["JFK", "SFO"],
    only allows you to fly from "JFK" to "SFO"

    connections don't have to be direct, its ok, if an airport can only be reached from the
    starting airport by stopping at other airport first.

    Eg:

         airports = List.of("BGI", "CDG", "DEL", "DOH", "DSM", "EWR", "EYW", "HND", "ICN",
            "JFK", "LGA", "LHR", "ORD", "SAN", "SFO", "SIN", "TLV", "BUD");

        routes =  List.of(List.of("DSM", "ORD"),
                                    List.of("ORD", "BGI"),
                                    List.of("BGI", "LGA"),
                                    List.of("SIN", "CDG"),
                                    List.of("CDG", "SIN"),
                                    List.of("CDG", "BUD"),
                                    List.of("DSM", "ORD"),
                                    List.of("DEL", "DOH"),
                                    List.of("DEL", "CDG"),
                                    List.of("TLV", "DEL"),
                                    List.of("EWR", "HND"),
                                    List.of("HND", "ICN"),
                                    List.of("HND", "JFK"),
                                    List.of("ICN", "JFK"),
                                    List.of("JFK", "LGA"),
                                    List.of("EYW", "LHR"),
                                    List.of("LHR", "SFO"),
                                    List.of("SFO", "SAN"),
                                    List.of("SFO", "DSM"),
                                    List.of("SAN", "EYW"));

        String startingAirport = "LGA";

    OUTPUT:
    3 // ["LGA", TLV"] , ["LGA", SFO"] , ["LGA", EWR"]
    */

    // Time : O((a + r) * (a) + 2a + r + a(log a)), Space : O(3a + r)
    public static int airportConnections(
        List<String> airports, List<List<String>> routes, String startingAirport) {
        Map<String, Airport> airportGraph = createAirportGraph(airports, routes);
        List<Airport> unreachableAirportsFromStartingAirport = getUnreachableAirports(airportGraph, airports, startingAirport);
        findUnreachableConnections(airportGraph, unreachableAirportsFromStartingAirport);
        return getMinimumNumberOfConnections(airportGraph, unreachableAirportsFromStartingAirport);
    }

    // Time O(a log(a) + a + r) | Space : O(1)  // a log(a) is to sort the vertices in graph based on length of unreachable connections
    public static int getMinimumNumberOfConnections(Map<String, Airport> airportGraph,
                                                    List<Airport> unreachableAirportsFromStartingAirport) {
        Collections.sort(unreachableAirportsFromStartingAirport,
            Comparator.comparingInt(airport -> -airport.unreachableConnections.size()));

        int minNoOfNewConnections = 0;
        for (Airport unreachableAirport : unreachableAirportsFromStartingAirport) {
            if (unreachableAirport.isReachable == true)
                continue;
            minNoOfNewConnections += 1;
            for (String airport : unreachableAirport.unreachableConnections) {
                airportGraph.get(airport).isReachable = true;
            }
        }
        return minNoOfNewConnections;
    }
    //Time : O(a * (a + r)) | Space : O(a)
    public static void findUnreachableConnections(Map<String, Airport> airportGraph,
                                                  List<Airport> unreachableAirportsFromStartingAirport) {
        for (Airport unreachableAirport : unreachableAirportsFromStartingAirport) {
            String airportName = unreachableAirport.airport;
            List<String> unreachableConnections = unreachableAirport.unreachableConnections;
            dfsAddUnreachableConnections(airportGraph, airportName, unreachableConnections, new HashSet<String>());
        }
    }

    public static void dfsAddUnreachableConnections(Map<String, Airport> airportGraph,
                                                    String airportName, List<String> unreachableConnections,Set<String> visitedAirports) {
        Airport airportNode = airportGraph.get(airportName);
        if (airportNode.isReachable)
            return;
        if (visitedAirports.contains(airportName))
            return;
        visitedAirports.add(airportName);
        unreachableConnections.add(airportName);
        List<String> connections = airportNode.connections;
        for (String connection : connections)
            dfsAddUnreachableConnections(airportGraph, connection, unreachableConnections, visitedAirports);
    }

    /*
        DFS through the graph starting at startingAirport, and then grab all of the airports that we haven't
        visited, add them to the list of unreachable airports, and set their status flag 'isReachable' to 'False'
        Time : O(a + r) a = airports and r = routes | O(a)
    */
    public static List<Airport> getUnreachableAirports(Map<String, Airport> airportGraph, List<String>airports, String startingAirport) {
        Set<String> visitedAirports = new HashSet<>();
        List<Airport> unreachableAirportNodes = new ArrayList<>();
        dfsAirportsFromStartingAirport(airportGraph, startingAirport, visitedAirports);
        for (String airport : airports) {
            if (visitedAirports.contains(airport))
                continue;
            Airport unreachableAirport = airportGraph.get(airport);
            unreachableAirport.isReachable = false;
            unreachableAirportNodes.add(unreachableAirport);
        }
        return unreachableAirportNodes;
    }

    public static void dfsAirportsFromStartingAirport(Map<String, Airport> airportGraph, String airport,
                                                      Set<String> visitedAirports) {
        if (visitedAirports.contains(airport))
            return;
        visitedAirports.add(airport);
        List<String> directConnections = airportGraph.get(airport).connections;
        for (String connection : directConnections)
            dfsAirportsFromStartingAirport(airportGraph, connection, visitedAirports);
    }
    //Time : O(a + r), Space :  O(a + r)  // 'a' is no of airports and 'r' is no of routes
    public static Map<String, Airport> createAirportGraph(List<String> airports, List<List<String>> routes) {
        Map<String, Airport> airportGraph = new HashMap<>();
        for (String airport : airports) // adding vertices / nodes to graph
            airportGraph.put(airport, new Airport(airport));

        for (List<String> route : routes) { // adding edges / connection to airport
            String fromAirport = route.get(0);
            String toAirport = route.get(1);
            airportGraph.get(fromAirport).connections.add(toAirport);
        }
        return airportGraph;
    }

    static class Airport {
        private String airport;
        private boolean isReachable;
        private List<String> connections;
        private List<String> unreachableConnections;
        public Airport(String airport) {
            this.airport = airport;
            this.isReachable = true;
            this.connections = new ArrayList<>();
            this.unreachableConnections = new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        List<String> airports = List.of("BGI", "CDG", "DEL", "DOH", "DSM", "EWR", "EYW", "HND", "ICN",
            "JFK", "LGA", "LHR", "ORD", "SAN", "SFO", "SIN", "TLV", "BUD");

        List<List<String>> routes = List.of(List.of("DSM", "ORD"),
            List.of("ORD", "BGI"),
        List.of("BGI", "LGA"),
        List.of("SIN", "CDG"),
        List.of("CDG", "SIN"),
        List.of("CDG", "BUD"),
        List.of("DSM", "ORD"),
        List.of("DEL", "DOH"),
        List.of("DEL", "CDG"),
        List.of("TLV", "DEL"),
        List.of("EWR", "HND"),
        List.of("HND", "ICN"),
        List.of("HND", "JFK"),
        List.of("ICN", "JFK"),
        List.of("JFK", "LGA"),
        List.of("EYW", "LHR"),
        List.of("LHR", "SFO"),
        List.of("SFO", "SAN"),
        List.of("SFO", "DSM"),
        List.of("SAN", "EYW"));
        String startingAirport = "LGA";
        int result = airportConnections(airports, routes, startingAirport);
        System.out.println(result);
    }
}
