package com.alaa.exercises;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AppTest
{
    private final Algorithms algorithms = new Algorithms();
    private final GraphTraversal graph = new GraphTraversal();

    @Test
    public void shorestPathinGraph_happyScenario()
    {
        List<List<String>> edges = new ArrayList<>();

        edges.add(List.of("w", "x"));
        edges.add(List.of("x", "y"));
        edges.add(List.of("z", "y"));
        edges.add(List.of("z", "v"));
        edges.add(List.of("w", "v"));

        assertEquals(2, graph.findShortestPathInGraph(edges, "w", "z"));
    }

    @Test
    public void shortestPathInGraph_noPathFound() {
        List<List<String>> edges = new ArrayList<>();

        edges.add(List.of("a", "c"));
        edges.add(List.of("a", "b"));
        edges.add(List.of("c", "b"));
        edges.add(List.of("c", "d"));
        edges.add(List.of("b", "d"));
        edges.add(List.of("e", "d"));
        edges.add(List.of("g", "f"));

        assertEquals(-1, graph.findShortestPathInGraph(edges, "a", "f"));
    }

    @Test
    public void dijkstraShortestPath() {
        List<List<Integer>> edges = List.of(
            List.of(1, 2, 24),
            List.of(1, 4, 20),
            List.of(3, 1, 3),
            List.of(4, 3, 12)
        );

        List<Integer> result = List.of(24,3,15);
        assertEquals(result, graph.dijkstraShortestPath(4, edges, 1));
    }

    @Test
    public void findRowInPascalTriangle_happyScenario() {
        algorithms.calculateRowInPascalTriangle(5);
    }

    @Test
    public void depthFirstSearch_happyScenario() {
        HashMap<String, List<String>> graphItems = new HashMap<>();

        graphItems.put("a", List.of("c", "b"));
        graphItems.put("b", List.of("d"));
        graphItems.put("c", List.of("e"));
        graphItems.put("d", List.of("f"));
        graphItems.put("e", List.of());
        graphItems.put("f", List.of());

        graph.depthFirstSearch(graphItems, "a");
    }

    @Test
    public void breadthFirstSearch_happyScenario() {
        HashMap<String, List<String>> graphItems = new HashMap<>();

        graphItems.put("a", List.of("c", "b"));
        graphItems.put("b", List.of("d"));
        graphItems.put("c", List.of("e"));
        graphItems.put("d", List.of("f"));
        graphItems.put("e", List.of());
        graphItems.put("f", List.of());

        graph.breadthFirstSearch(graphItems, "a");
    }

    @Test
    public void islandCount_happyScenario() {
        List<List<String>> grid = List.of(
            List.of("W", "L", "W", "W", "W"),
            List.of("W", "L", "W", "W", "W"),
            List.of("W", "W", "W", "L", "W"),
            List.of("W", "W", "L", "L", "W"),
            List.of("L", "W", "W", "L", "L"),
            List.of("L", "L", "W", "W", "W")
        );

        assertEquals(Integer.valueOf(3), graph.islandCount(grid));
    }

    @Test
    public void mergeLinkedLists_happyScenario() {
        LinkedList<Integer> listA = new LinkedList<>();
        LinkedList<Integer> listB = new LinkedList<>();

        listA.add(1);
        listA.add(2);
        listA.add(5);
        listA.add(10);

        listB.add(3);
        listB.add(7);
        listB.add(15);

        algorithms.mergeLinkedLists(listA, listB);
    }

    @Test
    public void mergeStringsAlternatively_happyScenario() {
        algorithms.mergeAlternately("abcd", "pq");
    }

    @Test
    public void findTopKNums_happyScenario() {
        List<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(1);
        nums.add(1);
        nums.add(1);
        nums.add(1);
        nums.add(5);
        nums.add(5);
        nums.add(5);
        nums.add(7);
        nums.add(10);
        nums.add(26);

        algorithms.findTopK(nums, 1);
    }

    @Test
    public void plusMinusRatios_happyScenario() {
        List<Integer> numsArray = List.of(0,2,5,2,5, -6, -2, 3, 0, -5, 1);

        algorithms.plusMinusRatios(numsArray);
    }

}
