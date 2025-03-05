package com.reactive.liquibase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Demo {
    /**
     * @param intervals
     * */
    public static int[][] merge(int[][] intervals) {
        // Step 1: Sort intervals by start time
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

//        Arrays.sort(intervals,(a,b)-> Integer.compare(a[0],b[0]));

        List<int[]> merged = new ArrayList<>();

        for (int[] interval : intervals) {
            // If merged is empty or no overlap, add the interval
            if (merged.isEmpty() || merged.get(merged.size() - 1)[1] < interval[0]) {
                merged.add(interval);
            } else {
                // Overlapping case: merge intervals
                merged.get(merged.size() - 1)[1] = Math.max(merged.get(merged.size() - 1)[1], interval[1]);
            }
        }

        // Convert List<int[]> to int[][]
        return merged.toArray(new int[merged.size()][]);
    }

    public static void main(String[] args) {
        int[][] intervals1 = {{1,3},{2,6},{8,10},{15,18}};
        int[][] result1 = merge(intervals1);
        System.out.println(Arrays.deepToString(result1)); // Output: [[1,6],[8,10],[15,18]]

        int[][] intervals2 = {{1,4},{4,5}};
        int[][] result2 = merge(intervals2);
        System.out.println(Arrays.deepToString(result2)); // Output: [[1,5]]
    }
}
