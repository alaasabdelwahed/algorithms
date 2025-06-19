package com.alaa.exercises;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;


class GraphTraversal {

    private static final long INFINITE_DISTANCE = Long.MAX_VALUE / 4;

    public GraphTraversal() {}

    public void depthFirstSearch(HashMap<String, List<String>> graph, String source) {
      Stack<String> stack = new Stack<>();

      stack.add(source);

      while(!stack.isEmpty()) {
        String current = stack.pop();
        System.out.println(current);

        for (String neighbour : graph.get(current)) {
          stack.add(neighbour);
        }
      }
    }

    public void breadthFirstSearch(HashMap<String, List<String>> graph, String source) {
      ArrayDeque<String> queue = new ArrayDeque<>();
      queue.offer(source);

      while (queue.iterator().hasNext()) {
        String current = queue.poll();
        System.out.println(current);

        for (String neighbour : graph.get(current)) {
          queue.add(neighbour);
        }
      }
    }

    record NodeDistance (String node, Integer distance) {}

    public int findShortestPathInGraph(List<List<String>> edges, String source, String destination) {
      HashMap<String, List<String>> graph = convertEdgesToGraph(edges);
      Set<String> visitedNodes = new HashSet<>();

      ArrayDeque<NodeDistance> queue = new ArrayDeque<>();
      queue.offer(new NodeDistance(source, 0));

      while (queue.iterator().hasNext()) {
         NodeDistance current = queue.poll();

         if (current.node.equals(destination)) {
            return current.distance;
         }

         visitedNodes.add(current.node);
         List<String> neighbours = graph.get(current.node);

         if (neighbours != null) {
            for (String neighbour : neighbours) {
              if (neighbour.equals(destination)) {
                  return current.distance + 1;
              }

              if (!visitedNodes.contains(neighbour)) {
                queue.offer(new NodeDistance(neighbour, current.distance + 1));
              }
            }
         }
      }
      return -1;
    }

    record Edge (int node, long weight) {}
    record NodeDistance2 (int node, long distanceFromSource) {}

    public List<Integer> dijkstraShortestPath(int nodes, List<List<Integer>> edges, int source) {
       HashMap<Integer, List<Edge>> graph = new HashMap<>();

       for (List<Integer> edge : edges) {
        int nodeA = edge.get(0);
        int nodeB = edge.get(1);
        long weight = edge.get(2);

        addWeightedEdgeToGraph(graph, nodeA, nodeB, weight);
        addWeightedEdgeToGraph(graph, nodeB, nodeA, weight);
       }

       PriorityQueue<NodeDistance2> pqueue = new PriorityQueue<>(Comparator.comparing(e -> e.distanceFromSource));
       long[] shortestDistance = new long[nodes+1];
       Arrays.fill(shortestDistance, INFINITE_DISTANCE);
       shortestDistance[source] = 0L;

       pqueue.add(new NodeDistance2(source, 0L));
       Set<Integer> visited = new HashSet<>();

       while (pqueue.iterator().hasNext()) {
        NodeDistance2 currentNode = pqueue.poll();
        int currentNodeIndex = currentNode.node;
        long currentNodeDistance = currentNode.distanceFromSource;

        visited.add(currentNodeIndex);

        List<Edge> neighbours = graph.get(currentNodeIndex);

        for (Edge neighbour : neighbours) {
          int neighbourIndex = neighbour.node;
          long neighbourDistance = neighbour.weight;

          if (!visited.contains(neighbourIndex)) {
            long newDistance = neighbourDistance + currentNodeDistance;
            if (newDistance < shortestDistance[neighbourIndex]) {
              shortestDistance[neighbourIndex] = newDistance;
            }
            pqueue.add(new NodeDistance2(neighbourIndex, newDistance));
          }
        }
      }

      List<Integer> result = new ArrayList<>();
      for (int i = 1; i < nodes + 1; i++) {
        if (i == source) continue;

        if (shortestDistance[i] == INFINITE_DISTANCE) {
          result.add(-1);
        } else {
          result.add((int) shortestDistance[i]);
        }

      }
      return result;
    }

    private HashMap<Integer, List<Edge>> addWeightedEdgeToGraph(HashMap<Integer, List<Edge>> graph,
                                                                    int src, int dst, long weight) {
      Edge neighbourNode = new Edge(dst, weight);

      if (graph.containsKey(src)) {
          List<Edge> neighbours = graph.get(src);

          if (!neighbours.contains(neighbourNode)) {
            neighbours.add(neighbourNode);
            graph.replace(src, neighbours);
          }
        } else {
          List<Edge> neighbours = new ArrayList<>();
          neighbours.add(neighbourNode);
          graph.put(src, neighbours);
        }

        return graph;
    }

    record Coordinates(int row, int col) {}

    public Integer islandCount(List<List<String>> grid) {
      // PriorityQueue<Integer> islands = new PriorityQueue<>(); // for solving minimum island problem

      Integer islands = 0;
      Integer rows = grid.size();
      Integer columns = grid.get(0).size();
      Set<String> visited = new HashSet<>();
      Integer islandSize = 0;

      for (Integer row = 0; row < rows; row++) {
        for (Integer col = 0; col < columns; col++) {
          if (!visited.contains(coordinatesAsString(row, col))) {

            if (grid.get(row).get(col).equals("L")) {
              islands++;

              ArrayDeque<Coordinates> stack = new ArrayDeque<>();
              stack.push(new Coordinates(row, col));

              while (stack.iterator().hasNext()) {
                Coordinates current = stack.pop();

                if (!visited.contains(coordinatesAsString(current.row, current.col))) {
                  islandSize++;
                  visited.add(coordinatesAsString(current.row, current.col));

                  if (current.row > 0 && isLand(grid, current.row - 1, current.col)) {
                    stack.push(new Coordinates(current.row - 1, current.col));
                  }

                  if (current.row < rows - 1 && isLand(grid, current.row + 1, current.col)) {
                    stack.push(new Coordinates(current.row + 1, current.col));
                  }

                  if (current.col > 0 && isLand(grid, current.row, current.col - 1)) {
                    stack.push(new Coordinates(current.row, current.col - 1));
                  }

                  if (current.col < columns - 1 && isLand(grid, current.row, current.col + 1)) {
                    stack.push(new Coordinates(current.row, current.col + 1));
                  }
                }
              }
              // islands.add(islandSize);
              islandSize = 0;
            }
          }
        }
      }

      return islands;
      // return islands.element();
    }

    private String coordinatesAsString(Integer row, Integer column) {
      return row.toString().concat(column.toString());
    }

    private boolean isLand(List<List<String>> grid, int x, int y) {
      return grid.get(x).get(y).equals("L");
    }

    public HashMap<String, List<String>> convertEdgesToGraph(List<List<String>> edges) {
      HashMap<String, List<String>> undirectedGraph = new HashMap<>();

      for(List<String> edge : edges) {
        String nodeA = edge.get(0);
        String nodeB = edge.get(1);

        mapNodesToNeighbours(undirectedGraph, nodeA, nodeB);
        mapNodesToNeighbours(undirectedGraph, nodeB, nodeA);
      }

      return undirectedGraph;
    }

    private HashMap<String, List<String>> mapNodesToNeighbours(HashMap<String, List<String>> graph,
    String node, String neighbour) {
      if (graph.get(node) != null) {
          List<String> neighboursList = graph.get(node);
          if(!neighboursList.contains(neighbour)) {
            neighboursList.add(neighbour);
            graph.replace(node, neighboursList);
          }
        } else {
          List<String> neighboursList = new ArrayList<>();
          neighboursList.add(neighbour);
          graph.put(node, neighboursList);
        }
        return graph;
    }
}
