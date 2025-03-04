package com.flightplanner;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Graph flightGraph = new Graph();

        // Add flights (source, destination, cost, duration)
        flightGraph.addEdge("New York", "Chicago", 200, 120);
        flightGraph.addEdge("New York", "Los Angeles", 400, 300);
        flightGraph.addEdge("Chicago", "Los Angeles", 300, 200);
        flightGraph.addEdge("Chicago", "Houston", 250, 150);
        flightGraph.addEdge("Los Angeles", "Houston", 150, 100);
        flightGraph.addEdge("Houston", "Miami", 200, 180);

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter source city: ");
        String source = scanner.nextLine();
        System.out.print("Enter destination city: ");
        String destination = scanner.nextLine();

        System.out.print("Do you want to optimize for cost (C) or duration (D)? ");
        String optimization = scanner.nextLine().toUpperCase();

        boolean useDuration = optimization.equals("D");

        System.out.print("Do you have any layovers? (Y/N): ");
        String hasLayovers = scanner.nextLine().toUpperCase();

        if (hasLayovers.equals("Y")) {
            System.out.print("Enter layovers (comma-separated): ");
            String layoversInput = scanner.nextLine();
            List<String> layovers = Arrays.asList(layoversInput.split(","));
            Dijkstra.findRouteWithLayovers(flightGraph, source, destination, layovers, useDuration);
        } else {
            Dijkstra.findShortestPath(flightGraph, source, destination, useDuration);
        }
    }
}