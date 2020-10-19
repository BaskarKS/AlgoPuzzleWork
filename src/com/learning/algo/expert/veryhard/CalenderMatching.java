package veryhard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CalenderMatching {
    /*
    Want to schedule a meeting of certain duration with a co-worker,
    have access to calenders(for both workers) which contains meeting in a day
    in the form of [startTime, endTime]
    as well as daily bounds (time available in office) in form of
    [earliestTime, LatestTime]

    write a function which take calenders and daily bounds of two employees and
    and the duration of the meeting which needs to be schedules.
    Function returns a list of time blocks [startTime, endTime] about the time
    in which we can schedule the meeting.

    ordering from earliest of time to latest.

    Time given and the returned list should be in 24Hrs clock format. Eg: 9:00, 14:00, 22:00

    Note: given calender times are sorted by start time in ascending order,

    Eg:
    Input:
        calender1 = [['9:00', '10:30'], ['12:00', '13:00'], ['16:00', '18:00']]
        dailyBound1 = ['9:00', '20:00']
        calender1 = [['10:00', '11:30'], ['12:30', '14:30'], ['14:30', '15:00'], ['16:00', '17:00']]
        dailyBound1 = ['10:00', '18:30']
        meetingDuration = 30

    Output:
    ['11:30', '12:00'], ['15:00', '16:00'], ['18:00', '18:30']]

    * */
    public static void main(String[] args) {
        List<StringMeeting> calender1 = new ArrayList<>();
        calender1.add(new StringMeeting("09:00", "10:30"));
        calender1.add(new StringMeeting("12:00", "13:00"));
        calender1.add(new StringMeeting("16:00", "18:00"));
        StringMeeting dailyBounds1 = new StringMeeting("09:00", "20:00");
        List<StringMeeting> calender2 = new ArrayList<>();
        calender2.add(new StringMeeting("10:00", "11:30"));
        calender2.add(new StringMeeting("12:30", "14:30"));
        calender2.add(new StringMeeting("14:30", "15:00"));
        calender2.add(new StringMeeting("16:00", "17:00"));
        StringMeeting dailyBounds2 = new StringMeeting("10:00", "18:30");
        int duration = 30;
        List<StringMeeting> freeSlots = calendarMatching(calender1, dailyBounds1, calender2, dailyBounds2, duration);
        System.out.println(freeSlots);
    }

    // Time : O(c1 + c2) and Space: O(c1 + c2)
    // c1 is length of calender1 and c2 is length of c2 calender
    public static List<StringMeeting> calendarMatching(
        List<StringMeeting> calendar1,
        StringMeeting dailyBounds1,
        List<StringMeeting> calendar2,
        StringMeeting dailyBounds2,
        int meetingDuration) {
        // Write your code here.
        List<IntMeeting> calenderOne = getFinalMeetings(calendar1, dailyBounds1); // adding daily bounds to the respective calender, convert the string timings to integer values for better processing
        List<IntMeeting> calenderTwo = getFinalMeetings(calendar2, dailyBounds2);
        List<IntMeeting> mergedCalender = mergeMeetings(calenderOne, calenderTwo); // merge both the calenders in ascending based on start time
        List<IntMeeting> flattenedCalender = flattenCalender(mergedCalender); // shrunk the meeting which overlap in timings
        return getMeetingAvailabilities(flattenedCalender, meetingDuration); // find the meeting timings which matches athe duration and re convert the timings in int to string(as provided in input
    }

    // get the timings if satisfies the duration, returned result in expected format as strings
    public static List<StringMeeting> getMeetingAvailabilities(List<IntMeeting> calender, int duration) {
        List<StringMeeting> result = new ArrayList<>();
        for (int idx = 1; idx < calender.size(); idx++) {
            IntMeeting prevMeeting = calender.get(idx - 1);
            IntMeeting currentMeeting = calender.get(idx);
            if (currentMeeting.start - prevMeeting.end >= duration) { // gap between current start and previous end timing is the duration, compared with the requirement duration
                result.add(new StringMeeting(convertMinutesToTime(prevMeeting.end),
                                                        convertMinutesToTime(currentMeeting.start))); // result composed in string representations
            }
        }
        return result;
    }

    public static String convertMinutesToTime(int timeInMins) { // convert the integer timings back into string
        int hours = timeInMins / 60;
        int mins = timeInMins % 60;
        String minsString = (mins < 10) ? "0"+Integer.toString(mins) : Integer.toString(mins);
        return hours + ":" + minsString;
    }

    // timings which overlap is flattened to single timing
    public static List<IntMeeting> flattenCalender(List<IntMeeting> mergedCalender) {
        List<IntMeeting> flattenedCalender = new ArrayList<>();
        flattenedCalender.add(mergedCalender.get(0)); // add first entry to result
        for (int idx = 1; idx < mergedCalender.size(); idx++) { // parse from first entry to last
            IntMeeting currentMeeting = mergedCalender.get(idx);
            int lastIdx = flattenedCalender.size() - 1;
            IntMeeting previousMeeting = flattenedCalender.get(lastIdx);
            if (previousMeeting.end >= currentMeeting.start) { // if previous meeting end time is more that current meeting starting time, its overlapping we can merge these two meetings
                flattenedCalender.set(lastIdx, new IntMeeting(
                    previousMeeting.start, Math.max(previousMeeting.end, currentMeeting.end)));
            } else {
                flattenedCalender.add(new IntMeeting(currentMeeting.start, currentMeeting.end)); // current meeting start time is much ahead of previous meeting entry, hence add the current entry
            }
        }
        return flattenedCalender;
    }

    //merge both the calenders in ascending order of start time
    public static List<IntMeeting> mergeMeetings(List<IntMeeting> calenderOne,
                                                 List<IntMeeting> calenderTwo) {
        List<IntMeeting> mergedList = new ArrayList<>();
        int calOneIdx = 0, calTwoIdx = 0;
        while (calOneIdx < calenderOne.size() &&
                    calTwoIdx < calenderTwo.size()) {
            IntMeeting meetingOne = calenderOne.get(calOneIdx);
            IntMeeting meetingTwo = calenderTwo.get(calTwoIdx);
            if (meetingOne.start < meetingTwo.start) {
                mergedList.add(meetingOne);
                calOneIdx += 1;
            } else {
                mergedList.add(meetingTwo);
                calTwoIdx += 1;
            }
        }
        while (calOneIdx < calenderOne.size()) {
            mergedList.add(calenderOne.get(calOneIdx));
            calOneIdx += 1;
        }
        while (calTwoIdx < calenderTwo.size()) {
            mergedList.add(calenderTwo.get(calTwoIdx));
            calTwoIdx += 1;
        }
        return mergedList;
    }
    // add the daily bounds into the calender and convert all timings in string format to integer for processing
    public static List<IntMeeting> getFinalMeetings(List<StringMeeting> calender, StringMeeting dailyBounds) {
        List<StringMeeting> updatedCalender = new ArrayList<>();
        updatedCalender.add(new StringMeeting("00:00", dailyBounds.start)); // daily bound start value added first
        for (StringMeeting meeting : calender) {
            updatedCalender.add(new StringMeeting(meeting.start, meeting.end)); // other calender timing entries
        }
        updatedCalender.add(new StringMeeting(dailyBounds.end, "23:59")); // daily bound end value added at last entry
        return updatedCalender.stream().map(CalenderMatching::convertToInt).collect(Collectors.toList()); // convert all the string format timings to int
    }

    public static IntMeeting convertToInt(StringMeeting meeting) {
        return new IntMeeting(convertTimeToMins(meeting.start), convertTimeToMins(meeting.end));
    }

    // convert the string time to int value
    public static int convertTimeToMins(String time) {
        String[] start = time.split(":"); // separate the hours and minutes
        int hours = Integer.parseInt(start[0]); // parse string into int which represent hours
        int minutes = Integer.parseInt(start[1]); // parse string into int which represent minutes
        return  (hours * 60) + minutes;
    }

    static class IntMeeting {
        public int start;
        public int end;

        public IntMeeting(int start, int end) {
            this.start = start;
            this.end = end;
        }
    /*
        @Override
        public String toString() {
            return "start=" + start +
                ", end=" + end;
        }*/
    }

    static class StringMeeting {
        public String start;
        public String end;

        public StringMeeting(String start, String end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "[\'"+ start + "\'" + ", " +"\'" +end +"\'"+ "]";
        }
    }

/*
    public static int compareTime(String first, String second) {
//        13:30, 12:00
//        12:00, 13:30
        String[] one = first.split(":");
        String[] two = second.split(":");

        int one_hours = Integer.parseInt(one[0]);
        int one_mins = Integer.parseInt(one[1]);
        int two_hours = Integer.parseInt(two[0]);
        int two_mins = Integer.parseInt(two[1]);

        if (one_hours == two_hours)
            if(one_mins == two_mins)
                return 0;
            else
                return (one_mins < two_mins) ? +1 : -1;
        else if (one_hours < two_hours)
            return +1;
        else
            return -1;
    }

    public static int timeInBetween(String first, String second) {
        String[] one = first.split(":");
        String[] two = second.split(":");

        int one_hours = Integer.parseInt(one[0]);
        int one_mins = Integer.parseInt(one[1]);
        int two_hours = Integer.parseInt(two[0]);
        int two_mins = Integer.parseInt(two[1]);

        if (one_hours == two_hours)
            if(one_mins == two_mins)
                return 0;
            else
                return (one_mins < two_mins) ? (one_mins-two_mins) : -(one_mins-two_mins);

        if (two_hours > one_hours) { // second time is greater than first time
            return ((two_hours - one_hours) * 60) + (two_mins - one_mins);
        }
        else
            return -(((one_hours - two_hours) * 60) + (one_mins - two_mins));
    }
*/
}
