package com.alaa.exercises;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class Algorithms {

  public Algorithms() {}

    public String mergeAlternately(String word1, String word2) {
      StringBuilder result = new StringBuilder();

      int word1Length = word1.length();
      int word2Length = word2.length();

      int i = 0;
      int j = 0;

      while (i < word1Length || j < word2Length) {
        if (i < word1Length) {
          result.append(word1.charAt(i++));
        }

        if (j < word2Length) {
          result.append(word2.charAt(j++));
        }
      }

      return result.toString();
    }

    public void plusMinusRatios(List<Integer> arr) {
      List<Integer> pos = new ArrayList<>();
      List<Integer> neg = new ArrayList<>();
      List<Integer> zeros = new ArrayList<>();

      for (Integer value : arr) {
        if (value > 0) {
          pos.add(value);
        } else if (value < 0) {
          neg.add(value);
        } else {
          zeros.add(value);
        }
      }

      double posRatio = pos.size()/arr.size();
      double negRatio = neg.size()/arr.size();
      double zerosRatio = zeros.size()/arr.size();

      System.out.println(String.format("%.6f", posRatio));
      System.out.println(String.format("%.6f", negRatio));
      System.out.println(String.format("%.6f", zerosRatio));
    }

    public int[] findTopK(List<Integer> nums, int k) {
      HashMap<Integer, Integer> elementCountMap = new HashMap<>();

      for (Integer item : nums) {
        if (elementCountMap.get(item) != null) {
          Integer currentItemCount = elementCountMap.get(item);
          elementCountMap.put(item, currentItemCount+1);
        } else {
          elementCountMap.put(item, 1);
        }
      }

    List<Map.Entry<Integer, Integer>> listOfEntries = new ArrayList<> (elementCountMap.entrySet());
    listOfEntries.sort((a,b) -> b.getValue().compareTo(a.getValue()));

    listOfEntries.subList(0, Math.min(listOfEntries.size(), k));

    return null;
  }


    public LinkedList<Integer> mergeLinkedLists(LinkedList<Integer> listA, LinkedList<Integer> listB) {
      Iterator<Integer> itA = listA.iterator();
      Iterator<Integer> itB = listB.iterator();

      LinkedList<Integer> mergedList = new LinkedList<>();

      Integer valA = itA.hasNext() ? itA.next() : null;
      Integer valB = itB.hasNext() ? itB.next() : null;

      while (valA != null && valB != null) {
        if (valA <= valB) {
          mergedList.add(valA);
          valA = itA.hasNext() ? itA.next() : null;
        } else {
          mergedList.add(valB);
          valB = itB.hasNext() ? itB.next() : null;
        }
      }

      while (valA != null) {
        mergedList.add(valA);
        valA = itA.hasNext() ? itA.next() : null;
      }

      while (valB != null) {
        mergedList.add(valB);
        valB = itB.hasNext() ? itB.next() : null;
      }

      return mergedList;
    }

    public List<Integer> calculateRowInPascalTriangle(int rowIndex) {
      if (rowIndex < 1) {
        return List.of(1);
      }

      List<Integer> elementsInPreviousRow = calculateRowInPascalTriangle(rowIndex - 1);
      List<Integer> elementsInCurrentRow = new ArrayList<>();
      for (int i=0; i < rowIndex; i++) {
        if (i == 0 || i == rowIndex - 1) {
          elementsInCurrentRow.add(1);
        } else {
          elementsInCurrentRow.add(elementsInPreviousRow.get(i-1) + elementsInPreviousRow.get(i));
        }
      }

      return elementsInCurrentRow;
    }

}
