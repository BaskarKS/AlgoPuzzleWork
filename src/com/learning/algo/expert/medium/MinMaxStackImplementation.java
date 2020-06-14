package medium;

import java.util.ArrayList;
import java.util.List;

public class MinMaxStackImplementation {
    public static void main(String[] args)
    {
        MinMaxStack stack  = new MinMaxStack();
        stack.push(5);
        System.out.println("Stack Min : " + stack.getMin());
        System.out.println("Stack Max : " + stack.getMax());
        System.out.println("Stack Top : " + stack.peek() );
        stack.push(7);
        System.out.println("Stack Min : " + stack.getMin());
        System.out.println("Stack Max : " + stack.getMax());
        System.out.println("Stack Top : " + stack.peek() );
        stack.push(2);
        System.out.println("Stack Min : " + stack.getMin());
        System.out.println("Stack Max : " + stack.getMax());
        System.out.println("Stack Top : " + stack.peek() );

        System.out.println("Stack Popped : " + stack.pop());
        System.out.println("Stack Popped : " + stack.pop());

        System.out.println("Stack Min : " + stack.getMin());
        System.out.println("Stack Max : " + stack.getMax());
        System.out.println("Stack Top : " + stack.peek() );

        System.out.println("Checking Stack Empty  Case");
        System.out.println("Stack Popped : " + stack.pop());
        System.out.println("Stack Popped : " + stack.pop());

        System.out.println("Stack Min : " + stack.getMin());
        System.out.println("Stack Max : " + stack.getMax());
        System.out.println("Stack Top : " + stack.peek() );

        stack.push(5);
        System.out.println("Stack Min : " + stack.getMin());
        System.out.println("Stack Max : " + stack.getMax());
        System.out.println("Stack Top : " + stack.peek() );


    }

    static class MinMaxStack {
        List<int[]> stack = new ArrayList<>();
        private final int VALUE_IDX = 0;
        private final int MIN_VAL_IDX = 1;
        private final int MAX_VAL_IDX = 2;
        private int[] createAndGetElement(int value) {
            if (stack.isEmpty())
                return new int[]{value, Integer.MAX_VALUE, Integer.MIN_VALUE};
            else
                return new int[]{value,
                                            stack.get(getIdx())[MIN_VAL_IDX],
                                            stack.get(getIdx())[MAX_VAL_IDX]};
        }
        private int getIdx() {
            return stack.size() - 1;
        }
        //  O(1) Time and O(1) Space
        public int peek() {
            // Write your code here.
            if (!stack.isEmpty())
                return stack.get(getIdx())[VALUE_IDX];
            else
                return -1;
        }
        //  O(1) Time and O(1) Space
        public int pop() {
            // Write your code here.
            if (stack.isEmpty())
                return -1;
            else {
                int value = stack.get(getIdx())[VALUE_IDX];
                stack.remove(getIdx());
                return value;
            }
        }
        //  O(1) Time and O(1) Space
        public void push(Integer number) {
            // Write your code here.
            int[] element = createAndGetElement(number);
            if (number <= element[MIN_VAL_IDX])
                element[MIN_VAL_IDX] = number;
            if (number >= element[MAX_VAL_IDX])
                element[MAX_VAL_IDX] = number;
            stack.add(element);
        }
        //  O(1) Time and O(1) Space
        public int getMin() {
            // Write your code here.
            if (!stack.isEmpty())
                return stack.get(getIdx())[MIN_VAL_IDX];
            else
                return -1;
        }
        //  O(1) Time and O(1) Space
        public int getMax() {
            // Write your code here.
            if (!stack.isEmpty())
                return stack.get(getIdx())[MAX_VAL_IDX];
            else
                return -1;
        }
    }
}
