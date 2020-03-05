package codesignal;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/*
* [A, A, A, B, B, B, C, C, D, D]
* 3, 3, 2, 2
* 2 * 2 - 4 - 3 => 1
*  2, 2, 3
* A-B-C-D-i-A-B-C-D-i-A-B
* A-B-I-A-B-I-A-B
*
* [[9:00, 10:30], [11:00, 14:00], [15:30, 17:00],[17:30, 18:30]]
* [9:00, 20:00]
*
* [[7:30, 9:30], [11:00, 12:30], [14:00, 15:30], [18:00, 19:00]
* [7:00, 20:00]
*
* 30
*
* 9 * 60 => 540 / 60 => 9
* 10 * 60 => 600 + 30 => 630 / 60 => 10, => 630 % 60 => 30
* */
public class TaskScheduler {
    public static void main(String[] args) {
        System.out.println(630 % 60);
        ArrayList<String> time = new ArrayList<>();
        time.add("10:30");
        time.add("12:00");
//        System.out.println(time);

        List<Integer> timeInInt = convertTimeToInt(time);
        System.out.println(timeInInt);

        List<String> timeInStr = convertTimeToString(timeInInt);
        System.out.println(timeInStr);
    }
    private static List<Integer> convertTimeToInt(List<String> timeInString) {
        List<Integer> intTime = new ArrayList<>();
        for (String strTime : timeInString) {
            String[] time = strTime.split(":");
            int minutes = Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]);
            intTime.add(minutes);
        }
        return intTime;
    }
    private static List<String> convertTimeToString(List<Integer> timeInInt) {
        List<String> strTime = new ArrayList<>();
        for (Integer intTime : timeInInt) {
            int hours = (intTime / 60);
            int mins = (intTime % 60);
            String time = String.join(":",
                                    String.format("%02d", hours),
                                    String.format("%02d", mins));
            strTime.add(time);
        }
        return strTime;
    }

}
