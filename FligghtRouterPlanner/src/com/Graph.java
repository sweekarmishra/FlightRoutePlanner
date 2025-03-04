package com.flightplanner;

import java.util.*;

public class Graph {
    private Map<String, List<Edge>> graph = new HashMap<>();

    // Add an edge between two cities with cost and duration
    public void addEdge(String source, String destination, int cost, int duration) {
        graph.putIfAbsent(source, new ArrayList<>());
        graph.get(source).add(new Edge(destination, cost, duration));
    }

    // Get neighbors of a city
    public List<Edge> getNeighbors(String node) {
        return graph.getOrDefault(node, new ArrayList<>());
    }

    // Get all nodes (cities) in the graph
    public Set<String> getNodes() {
        return graph.keySet();
    }
}

// Edge class to represent flights
class Edge {
    String destination;
    int cost;
    int duration; // in minutes

    public Edge(String destination, int cost, int duration) {
        this.destination = destination;
        this.cost = cost;
        this.duration = duration;
    }
}