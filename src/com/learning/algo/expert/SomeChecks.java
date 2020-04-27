import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SomeChecks {
    public static void main(String[] args) {
        String mainString = "check test there testtest some values testesttest strings test is";
        String subString = "test";
        String underScoreAdded = underScorify(mainString, subString);
        System.out.println(underScoreAdded);
    }
    public static String underScorify(String mainString, String subString) {
        List<int[]> locations = getLocations(mainString, subString);
        System.out.println("Actual Locations :: ");
        for (int[] each : locations) {
            System.out.println(Arrays.toString(each));
        }
        List<int[]> collapsedLocations = getCollapsedLocations(locations);
        System.out.println("Collapsed Locations :: ");
        for (int[] each : collapsedLocations) {
            System.out.println(Arrays.toString(each));
        }
        String underScoreAdded = addUnderScoresToString(mainString, collapsedLocations);
        return underScoreAdded;
    }
    public static String addUnderScoresToString(String str, List<int[]> locations) {
        if (locations == null || locations.isEmpty())
            return str;
        StringBuilder finalStr = new StringBuilder();
        int locationIdx = 0, subIdx = 0;
        int strIdx = 0;
        boolean inMiddle = false;
        while (strIdx < str.length() && locationIdx < locations.size()) {
            if (strIdx == locations.get(locationIdx)[subIdx]) {
                finalStr.append("_");
                inMiddle = (inMiddle == true)? false : true;
                subIdx = (subIdx == 0) ? 1 : 0;
                if (inMiddle == false)
                    locationIdx++;
            }
            finalStr.append(str.charAt(strIdx));
            strIdx++;
        }

        if (locationIdx < locations.size())
            finalStr.append("_");
        else if (strIdx < str.length())
            finalStr.append(str.substring(strIdx));

        return finalStr.toString();
    }
    public static List<int[]> getCollapsedLocations(List<int[]> locations) {
        if (locations == null || locations.isEmpty())
            return locations;
        List<int[]> collapsedLocations = new ArrayList<>();
        int[] previous = locations.get(0);
        collapsedLocations.add(locations.get(0));
        for (int idx = 1; idx < locations.size(); idx++) {
            int[] current = locations.get(idx);
            if (current[0] <= previous[1]) {
                previous[1] = current[1];
            } else {
                collapsedLocations.add(current);
                previous = current;
            }
        }
        return collapsedLocations;
    }
    public static List<int[]> getLocations(String mainStr, String subString) {
        List<int[]> locations = new ArrayList<>();
         int location = 0;
        while(location < mainStr.length() &&
            (location = mainStr.indexOf(subString, location)) != -1) {
            locations.add(new int[]{location, location + subString.length()});
            location++;
        }
        return locations;
    }
}
