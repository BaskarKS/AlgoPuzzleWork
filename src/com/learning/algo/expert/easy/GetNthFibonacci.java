package easy;

import java.util.HashMap;
import java.util.Map;

public class GetNthFibonacci {
    //Time Complexity = O(2^n) //Space Complexity - O(n)
    public static int getNthFib(int n) {
        // Write your code here.
        if (n < 0)
            return -1;
        if (n == 1)
            return 1;
        if (n == 0)
            return 0;
        return getNthFib(n-1) + getNthFib(n-2);
    }
    //Time Complexity = O(n) //Space Complexity - O(1)
    public static int getNthFibIterative(int n) {
        int result = 0, prev = 1, lastPrev = 0, index = 2;
        while (index <= n) {
            result = prev + lastPrev;
            lastPrev = prev;
            prev = result;
            index++;
        }
        return result;
    }
    public static int getNthFibDynamicProg(int n, HashMap<Integer, Integer> fibValues) {
        int result = 0, index = 2;
        if (!fibValues.containsKey(0)) fibValues.put(0, 0);
        if (!fibValues.containsKey(1)) fibValues.put(1, 1);
        if (!fibValues.containsKey(n)) {
            while (index <= n) {
                if (fibValues.containsKey(index)) {
                    index++;
                    continue;
                }
                result = fibValues.get(index - 1) + fibValues.get(index - 2);
                fibValues.put(index, result);
                index++;
            }
        }
        return fibValues.get(n);
    }
    public static int getNthFibDynamicProgNoCache(int n) {
        int result = 0, index = 2;
        Map<Integer, Integer> fibCache = new HashMap<>();
        fibCache.put(0, 0);
        fibCache.put(1, 1);
        while (index <= n) {
            result = fibCache.get(index - 1) + fibCache.get(index - 2);
            fibCache.put(index, result);
            index++;
        }
        return fibCache.get(n);
    }
    //Time Complexity - O(n) - Space Complexity - O(n)
    public static int getNthFibDynamicProgRecursive(int n) {
        Map<Integer, Integer> fibCache = new HashMap<>();
        fibCache.put(0, 0);
        fibCache.put(1, 1);
        return getNthFibRecursive(n, fibCache);
    }
    //0  1  2  3  4  5  6  7  8   9
    //0  1  1  2  3  5  8  13  21 34
    public static int getNthFibRecursive(int n, Map<Integer, Integer> fibCache) {
        if (fibCache.containsKey(n))
            return fibCache.get(n);
        else
            fibCache.put(n, getNthFibRecursive(n - 1, fibCache)
                + getNthFibRecursive(n - 2, fibCache));
        return fibCache.get(n);
    }

    public static void main(String[] args) {
        //0  1  2  3  4  5  6  7  8   9
        //0  1  1  2  3  5  8  13  21 34
        int fibInput = 9;
        int fibRet = getNthFibDynamicProgRecursive(fibInput);
        System.out.println("Fib Result : " + fibRet);
        HashMap<Integer, Integer> fibCache = new HashMap<>();
        int fibResult = getNthFibDynamicProg(fibInput, fibCache);
        System.out.println("Fib Dynamic : " + fibInput +" : " + fibResult);
        int newFibInput = 5;
        int fibResultOne = getNthFibDynamicProg(newFibInput, fibCache);
        System.out.println("Fib Dynamic : " + newFibInput +" : " + fibResultOne);

    }
}
