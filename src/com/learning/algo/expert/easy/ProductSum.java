package easy;

import java.util.ArrayList;
import java.util.List;

public class ProductSum {
    /*
    function takes in a special array, returns a product sum,
    special array is a non empty array, either integers or other special arrays.
    product sum of "special" array is sum of its elements , special array inside it
    are summed themselves and then multiplied by their level of depth

    eg : product sum of [x, y] is x + y, product sum of [x, [y, z]] is x + 2 * (y + z)

    eg:
    Ip: [5, 2, [7, -1], 3, [6, [-13, 8], 4]
    op: 12 => 5  + 2 + 2 * (7 - 1) + 3 + 2 * ( 6 + 3 * (-13 + 8) + 4)
    * */
    public static void main(String[] args) {
        List<Object> elements = new ArrayList<>();
        elements.add(5);
        elements.add(2);
        elements.add(List.of(7, -1));
        elements.add(3);
        elements.add(List.of(6, List.of(-13, 8), 4));
        int result = productSum(elements);
        System.out.println(result);
    }
    public static int productSum(List<Object> array) {
        // Write your code here.
        if (array == null || array.size() == 0)
            return 0;

        return recursiveProductSum(array, 1);
    }
    public static int recursiveProductSum(List<Object> array, int multiplier) {
        Integer sum = 0;
        for (Object element : array) {
            if (element instanceof Integer) {
                sum += (Integer) element;
            } else {
                sum += recursiveProductSum((List<Object>) element, multiplier + 1);
            }
        }
        return sum * multiplier;
    }
}
