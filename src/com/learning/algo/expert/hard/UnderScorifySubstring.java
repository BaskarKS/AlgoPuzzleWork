package hard;

import java.util.ArrayList;
import java.util.List;

/*
Function should return a version of main string with every instance of substring
wrapped between underscores
if two or more instance of substring in main string overlap each other or sit
side by side , underscores relevant to these substring should only appear
on far left of the left most substring and on the far right of the right most
substring. if main string doesn't contain the substring, function should return the
main string.
String mainString = "check testtestthis is a testtest to
                                                        see if testtestest it workstest";
String substring = "test";
result = check _testtest_this is a _testtest_ to see if _testtestest_ it works_test_
*/

public class UnderScorifySubstring {
    public static void main(String[] args) {
        String str = "check testtestthis is a testtest to see if testtestest it workstest";
        String substring = "test";
        System.out.println(str);
        String underscoreAdded = underscorifySubstring(str, substring);
        System.out.println(underscoreAdded);

        System.out.println("workstest".indexOf("test"));
    }


    public static String underscorifySubstring(String str, String substring) {
        // Write your code here.
        List<Integer[]> finalLocations = collapseLocations(getLocations(str, substring));
        return getUnderScorifyString(str, finalLocations);
    }

    private static String getUnderScorifyString(String str, List<Integer[]> indexes) {
        int strIdx = 0, locationIdx = 0;
        StringBuilder finalString = new StringBuilder();
        boolean isInMiddle = false;
        int locIdx = 0;
        while(strIdx < str.length() && locationIdx < indexes.size()) {
            if (strIdx == indexes.get(locationIdx)[locIdx]) {
                finalString.append("_");
                isInMiddle = (isInMiddle == false) ? true: false;
                locIdx = (locIdx == 0) ? 1 : 0;
                if (isInMiddle == false)
                    locationIdx++;
            }
            finalString.append(str.charAt(strIdx));
            strIdx++;
        }
        if (locationIdx < indexes.size())
            finalString.append("_");
        else if (strIdx < str.length())
            finalString.append(str.substring(strIdx));
        return finalString.toString();
    }

    private static List<Integer[]> collapseLocations(List<Integer[]> locations) {
        if (locations == null || locations.isEmpty())
            return locations;
        List<Integer[]> collapsedLocations = new ArrayList<>();
        int loopLen = locations.size();
        Integer[] previous = locations.get(0);
        collapsedLocations.add(previous);
        for (int listIdx = 1; listIdx < locations.size(); listIdx++) {
            Integer[] current = locations.get(listIdx);
            if (current[0] <= previous[1]) {
                previous[1] = current[1];
            } else {
                collapsedLocations.add(current);
                previous = current;
            }
        }
        return collapsedLocations;
    }

    private static List<Integer[]> getLocations(String str, String substring) {
        int idx = 0;
        int subStrLength = substring.length();
        List<Integer[]> indexes = new ArrayList<>();
        while((idx = str.indexOf(substring, idx)) != -1) {
            indexes.add(new Integer[] {idx,  idx + subStrLength});
            idx++;
        }
        return indexes;
    }
}
