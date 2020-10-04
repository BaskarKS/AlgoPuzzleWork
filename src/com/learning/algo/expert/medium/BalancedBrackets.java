package medium;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class BalancedBrackets {
    public static void main(String[] args) {

        System.out.println(balancedBrackets("(()){}"));
    }

    public static boolean balancedBrackets(String str) {
        // Write your code here.
        String openBracket = "[{(";
        String closeBracket = "]})";
        Map<Character, Character> bracketMap = new HashMap<>();
        bracketMap.put('{', '}');
        bracketMap.put('[', ']');
        bracketMap.put('(', ')');
        Stack<Character> brackets = new Stack<>();
        int idx = 0;
        while (idx < str.length()) {
            char ch = str.charAt(idx);
            if (openBracket.indexOf(ch) != -1) {
                brackets.push(ch);
            }
            else if (closeBracket.indexOf(ch) != -1) {
                if (!brackets.isEmpty() && bracketMap.get(brackets.peek()) == ch) {
                    brackets.pop();
                } else
                    return false;
            }
            idx += 1;
        }
        return brackets.isEmpty();
    }
}
