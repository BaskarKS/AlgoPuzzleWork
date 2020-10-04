package medium;

/*
Each element in the passed array mentions the jump of its value
in the array. Eg: integer 2 represents 2 elements forward in array,
and -4 represents jump of 4 elements backward in array.

jump goes out of bound, -1 at index-0 goes to last element in array
jump of 1 at last element will goes to first element in array(index-0)

define a function to find a single cycle. single cycle is start at any index in
array and follow the jumps, every element in array is visited exactly once
before landing back on start index.
* */
public class SingleCycleCheck {
    public static void main(String[] args) {
        int[] array = new int[]{2, 3, 1, -4, -4, 2};
        boolean hasCycle = hasSingleCycle(array);
        System.out.println(hasCycle);
    }

    // Time : O(n) , Space O(1)
    public static boolean hasSingleCycle(int[] array) {
        // Write your code here.
        int idx = 0, startIdx = 0;
        int minItemVisit = 0;
        while (minItemVisit < array.length) {
            if (minItemVisit > 0 && idx == startIdx) // apart from first element no other element should lead to starting position
                return false; //minItemVisit > 0 - allow all other element apart from starting element
            idx = jumpToIndex(array, idx);
            minItemVisit += 1;
        }
        return idx == startIdx; // last index should be the starting index
    }

    public static boolean hasSingleCycleModified(int[] array) {
        // Write your code here.
        int idx = 0, startIdx = 0;
        int minItemVisit = array.length;
        while(minItemVisit > 0) {
            if (minItemVisit != array.length && idx == startIdx)
                return false;
            idx = jumpToIndex(array, idx);
            minItemVisit -= 1;
        }
        return idx == startIdx;
    }

    public static int jumpToIndex(int[] array,int idx) {
        int maxIdx = array.length;
        int jumpIdx =  (idx + array[idx]) % maxIdx;
        return (jumpIdx < 0) ? maxIdx + jumpIdx : jumpIdx;
    }
}
