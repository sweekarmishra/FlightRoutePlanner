package com.flightplanner;

package com.flightplanner;

import java.io.*;

public class FileHandler {
    public static void saveGraph(Graph graph, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String node : graph.getNodes()) {
                for (Edge edge : graph.getNeighbors(node)) {
                    writer.write(node + "," + edge.destination + "," + edge.cost + "," + edge.duration + "\n");
                }
            }
        }
    }

    public static Graph loadGraph(String filename) throws IOException {
        Graph graph = new Graph();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                graph.addEdge(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
            }
        }
        return graph;
    }
}