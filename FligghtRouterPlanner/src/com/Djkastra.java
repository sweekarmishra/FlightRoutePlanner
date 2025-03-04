package com.flightplanner;

import java.util.*;

public class Dijkstra {
    // Find the shortest path based on cost or duration
    public static void findShortestPath(Graph graph, String start, String end, boolean useDuration) {
        PriorityQueue<Path> pq = new PriorityQueue<>(Comparator.comparingInt(p -> useDuration ? p.duration : p.cost));
        pq.add(new Path(0, 0, start, new ArrayList<>()));

        Set<String> visited = new HashSet<>();

        while (!pq.isEmpty()) {
            Path current = pq.poll();

            if (visited.contains(current.city)) {
                continue;
            }

            visited.add(current.city);
            List<String> path = new ArrayList<>(current.path);
            path.add(current.city);

            if (current.city.equals(end)) {
                System.out.println("Route from " + start + " to " + end + ": " + String.join(" -> ", path));
                System.out.println("Total cost: $" + current.cost);
                System.out.println("Total duration: " + current.duration + " minutes");
                return;
            }

            for (Edge neighbor : graph.getNeighbors(current.city)) {
                if (!visited.contains(neighbor.destination)) {
                    pq.add(new Path(
                            current.cost + neighbor.cost,
                            current.duration + neighbor.duration,
                            neighbor.destination,
                            path
                    ));
                }
            }
        }

        System.out.println("No route found from " + start + " to " + end);
    }

    // Find route with layovers
    public static void findRouteWithLayovers(Graph graph, String start, String end, List<String> layovers, boolean useDuration) {
        String current = start;
        int totalCost = 0;
        int totalDuration = 0;
        List<String> fullPath = new ArrayList<>();

        for (String layover : layovers) {
            int cost, duration;
            List<String> path;
            cost, duration, path = findShortestPathSegment(graph, current, layover, useDuration);
            if (cost == Integer.MAX_VALUE) {
                System.out.println("No route found from " + current + " to " + layover);
                return;
            }
            totalCost += cost;
            totalDuration += duration;
            fullPath.addAll(path);
            current = layover;
        }

        int cost, duration;
        List<String> path;
        cost, duration, path = findShortestPathSegment(graph, current, end, useDuration);
        if (cost == Integer.MAX_VALUE) {
            System.out.println("No route found from " + current + " to " + end);
            return;
        }
        totalCost += cost;
        totalDuration += duration;
        fullPath.addAll(path);

        System.out.println("Route with layovers: " + String.join(" -> ", fullPath));
        System.out.println("Total cost: $" + totalCost);
        System.out.println("Total duration: " + totalDuration + " minutes");
    }

    // Helper method to find the shortest path segment
    private static int[], List<String> findShortestPathSegment(Graph graph, String start, String end, boolean useDuration) {
        PriorityQueue<Path> pq = new PriorityQueue<>(Comparator.comparingInt(p -> useDuration ? p.duration : p.cost));
        pq.add(new Path(0, 0, start, new ArrayList<>()));

        Set<String> visited = new HashSet<>();

        while (!pq.isEmpty()) {
            Path current = pq.poll();

            if (visited.contains(current.city)) {
                continue;
            }

            visited.add(current.city);
            List<String> path = new ArrayList<>(current.path);
            path.add(current.city);

            if (current.city.equals(end)) {
                return new int[]{current.cost, current.duration}, path;
            }

            for (Edge neighbor : graph.getNeighbors(current.city)) {
                if (!visited.contains(neighbor.destination)) {
                    pq.add(new Path(
                            current.cost + neighbor.cost,
                            current.duration + neighbor.duration,
                            neighbor.destination,
                            path
                    ));
                }
            }
        }

        return new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE}, new ArrayList<>();
    }
}

// Path class to store the cost, duration, current city, and path
class Path {
    int cost;
    int duration;
    String city;
    List<String> path;

    public Path(int cost, int duration, String city, List<String> path) {
        this.cost = cost;
        this.duration = duration;
        this.city = city;
        this.path = path;
    }
}