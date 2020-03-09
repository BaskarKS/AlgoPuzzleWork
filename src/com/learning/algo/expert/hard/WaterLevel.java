package hard;

/*
* WaterArea:
* You are given an array of Integers, Each non-zero integer
* represents the height of a pillar of width 1. Imagine water
* being poured over all of the pillars  and return the surface
* area of the water trapped between the pillars viewed from the
* front. Note that spilled water should be ignored.
*
* Ip : {0, 8, 0,  0, 5, 0,  0, 10, 0,  0,  1, 1,  0,  3}
* Op : 48
*  L   { 0, 8, 8,  8, 8, 8,  8, 10,10, 10, 10,10, 10, 10}
*  R   {10, 10,10,10,10,10, 10, 10, 3,  3,  3, 3,  3,  3}
* min(l , R) - pillar height
*       {0, 0, 8,  8, 3, 8 , 8, 0,  3,  3,  2, 2,  3, 0}
* */
public class WaterLevel {
    public static void main(String[] args) {
     int[] heights = new int[]
             {0, 8, 0, 0, 5, 0, 0, 10, 0, 0, 1, 1, 0, 3};
     int waterArea = waterAreaWithoutMemory(heights);
     System.out.println(waterArea);
    }

    public static int waterArea(int[] heights) {
        int totalWater = 0;
        if (heights == null || heights.length == 0)
            return totalWater;

        int length = heights.length;

        int[] maxHeightLeft = new int[length];
        maxHeightLeft[0] = heights[0];

        int[] maxHeightRight = new int[length];
        maxHeightRight[length - 1] = heights[length - 1];

        for (int idx = 1; idx < length; idx++)
            maxHeightLeft[idx] = max(heights[idx],
                    maxHeightLeft[idx - 1]);

        for (int idx = length - 2; idx >= 0; idx--)
            maxHeightRight[idx] = max(heights[idx],
                    maxHeightRight[idx + 1]);

        for (int idx = 0; idx < length; idx++)
            totalWater += (min(maxHeightLeft[idx], maxHeightRight[idx]) - heights[idx]);

        return totalWater;
    }

    public static int max(int valOne, int valTwo) {
        return valOne >= valTwo ? valOne : valTwo;
    }
    public static int min(int valOne, int valTwo) {
        return valOne <= valTwo ? valOne : valTwo;
    }

    public static int waterAreaWithoutMemory(int[] heights) {
        int totalWaterSize = 0;
        if (heights == null || heights.length == 0)
            return totalWaterSize;

        int length = heights.length;
        int[] maxLeft = new int[length];
        maxLeft[0] = heights[0];
        for (int idx = 1; idx < length; idx++) {
            maxLeft[idx] = max(heights[idx], maxLeft[idx - 1]);
        }
        int maxRight = heights[length - 1];
        for (int idx = length - 1; idx >= 0; idx--) {
            maxRight = max(maxRight, heights[idx]);
            totalWaterSize += (min(maxLeft[idx], maxRight) - heights[idx]);
        }
        return totalWaterSize;
    }
}
