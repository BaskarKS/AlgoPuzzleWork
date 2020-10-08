package hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DiskStacking {
    /*
    given non empty array of arrays, each sub array holds 3 integers that represent a disk.
    (disk width, disk depth, disk height),

    Goal: Stack up the disks to maximize the total height of stack.
    Note: disk must have smaller width, depth, height, than any disk below it.

    write a function returns array of disks in final stack, starting from top disk and end with
    bottom disk.

    Assume there will be only one stack with greatest total height.

    Eg:
    Ip : [[2, 1, 2], [3, 2, 3], [2, 2, 8], [2, 3, 4], [1, 3, 1], [4, 4, 5]]
    Op: [[2, 1, 2], [3, 2, 3], [4, 4, 5]]
    // 2 + 3 + 5 => 10 is the max height of disk stack that can be constructed with the
    above constraints

    * */
    public static void main(String[] args) {
        List<Integer[]> input = new ArrayList<>();
            input.add(new Integer[]{2, 1, 2});
            input.add(new Integer[]{3, 2, 3});
            input.add(new Integer[]{2, 2, 8});
            input.add(new Integer[]{2, 3, 4});
            input.add(new Integer[]{1, 3, 1});
            input.add(new Integer[]{4, 4, 5});
        List<Integer[]> result = diskStacking(input);
        for (Integer[] disk : result)
            System.out.println(Arrays.toString(disk));
    }
    private static void prepareForProcessing(List<Integer[]> disks, int[] heights, int[] sequences) {
        // sort the disks based on height, as the requirement is to get max height
        Collections.sort(disks, new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] t1, Integer[] t2) {
                return t1[2] - t2[2];
            }
        });
        // initialize heights[] with respective heights of disks currently
        // initialize sequences with some init value (-1) to track the end
        for (int idx = 0; idx < disks.size(); idx++) {
            heights[idx] = disks.get(idx)[2];
            sequences[idx] = -1;
        }
    }
    // Time : O(n^2) and Space : O(n)
    public static List<Integer[]> diskStacking(List<Integer[]> disks) {
        // Write your code here.
        //disks.sort((a, b) -> a[2] - b[2]);
        //disks.sort(Comparator.comparing(item -> item[2]));
        int[] heights = new int[disks.size()];
        int[] sequences = new int[disks.size()];
        int maxIdx = 0;
        prepareForProcessing(disks, heights, sequences);

        for (int currIdx = 1; currIdx < disks.size(); currIdx++) {
            Integer[] currDisk = disks.get(currIdx); // for each disk
            for (int prevIdx = 0; prevIdx < currIdx; prevIdx++) {
                Integer[] prevDisk = disks.get(prevIdx); // iterate all disk till the current disk
                if (canStackDisk(prevDisk, currDisk)) { // if we find the disk prev to current disk is eligible to place over
                    if (heights[currIdx] <= heights[prevIdx] + currDisk[2]) { // current disk height and max height of prev disk is greater than max height computed till now for current disk
                        heights[currIdx] = heights[prevIdx] + currDisk[2]; // current disk height + max height of previous disk is updated to max height of current disk
                        sequences[currIdx] = prevIdx; // we updating the max height for current disk, keeping track of prev disk idx(position)
                    }
                }
            }
            if (heights[currIdx] >= heights[maxIdx]) { // current max height is greater than max Height
                maxIdx = currIdx; // keeping track of index of max height
            }
        }
        return constructStackedDisks(disks, sequences, maxIdx);
    }
    private static List<Integer[]> constructStackedDisks(List<Integer[]> disks, int[] sequences, int maxIdx) {
        List<Integer[]> result = new ArrayList<>();
        while (maxIdx != -1) {
            result.add(0, disks.get(maxIdx));
            maxIdx = sequences[maxIdx];
        }
        return result;
    }
    private static boolean canStackDisk(Integer[] prevDisk, Integer[] currDisk) {
        return prevDisk[0] < currDisk[0] &&
                    prevDisk[1] < currDisk[1] &&
                    prevDisk[2] < currDisk[2];
    }
}
