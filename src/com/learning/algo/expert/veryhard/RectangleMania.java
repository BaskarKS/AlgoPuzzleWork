package veryhard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RectangleMania {
    /*
    Function takes in a list of Cartesian co-ordinates(x,y) and returns the no of
    rectangles formed by these coordinates.

    A Rectangle must have its four corners amongst the coordinates in order to
    be counted. we only care about the rectangles with sides parallel to the x and y
    axis(Rectangle with horizontal and vertical sides - no diagonal sides)

    assume that no coordinates will be farther than 100 units from the origin

    Eg: Ip : [[0, 0], [0, 1], [1, 1], [1, 0], [2, 1], [2, 0], [3, 1], [3, 0]]
    Op : 6
    */
    public static void main(String[] args) {
        Point[] allCoOrd = new Point[]{new Point(0, 0),
            new Point(0, 1),
            new Point(1, 1),
            new Point(1, 0),
            new Point(2, 1),
            new Point(2, 0),
            new Point(3, 1),
            new Point(3, 0)};
        System.out.println(rectangleMania(allCoOrd));
    }

    // SOLUTION TWO:
    //Bit Tougher Solution : Time : O(N^2), Space O(n) we are not storing relevant all direction coordinates for every coordinate
    public static int rectangleMania(Point[] coords) {
    Map<String, Map<Integer, List<Point>>> coordsTable = getCoordinatesTable(coords);
    return getRectangleCount(coords, coordsTable);
    }
    public static Map<String, Map<Integer, List<Point>>> getCoordinatesTable(Point[] coords) {
        Map<String, Map<Integer, List<Point>>> coordsTable = new HashMap<>(); // contains all entries of x coordinate map and y coordinate map
        Map<Integer, List<Point>> xMap = new HashMap<>(); // contains all identical x values and its corresponding points
        Map<Integer, List<Point>> yMap = new HashMap<>();// contains all identical y values and its corresponding points
        for (Point point : coords) {
            int x = point.x;
            if (!xMap.containsKey(x))
                xMap.put(x, new ArrayList<>());
            xMap.get(x).add(point);

            int y = point.y;
            if (!yMap.containsKey(y))
                yMap.put(y, new ArrayList<>());
            yMap.get(y).add(point);
        }

        coordsTable.put("x", xMap);
        coordsTable.put("y", yMap);
        return coordsTable;
    }

    enum Direction {
        UP, RIGHT, DOWN
    }

    public static int getRectangleCount(Point[] coords, Map<String, Map<Integer, List<Point>>> coordsTable) {
        int rectangleCount = 0;
        for (Point point : coords) {
            int lowerLeftY = point.y;
            rectangleCount += clockwiseCountRectangle(point, coordsTable, Direction.UP, lowerLeftY); // every point is assumed as potential lower left point and start navigating UP, starting point y is send in recursive call to find the lower right point y value  match with the starting y value (both lie in same y)
        }
        return rectangleCount;
    }

    public static int clockwiseCountRectangle(Point point,
                                              Map<String, Map<Integer, List<Point>>> coordsTable,
                                              Direction direction, int lowerLeftY) {
        int x1 = point.x;
        int y1 = point.y;

        if (direction == Direction.DOWN) {
            List<Point> relevantCoordinates = coordsTable.get("x").get(x1); // x1 is potential upper right x value, getting all point lie in the same 'x' plain
            for (Point potentialDown : relevantCoordinates) { // investigate each collected point
                int lowerRightY = potentialDown.y;
                if (lowerRightY == lowerLeftY) // potential lower right y point value will be the same y value of the starting point. which concludes the lower right point will join with lower left point to form a rectangle
                    return 1;
            }
            return 0; // couldn't find a point  which has a 'y' value matching the starting point 'y' value, lowerRight point 'y' should lie in the same Y plain of starting point 'y' value
        } else {
            int rectangleCount = 0;
            if (direction == Direction.UP) {
                List<Point> relevantCoordinates = coordsTable.get("x").get(x1); // get all point lie in the same x plain
                for (Point potentialUp : relevantCoordinates) { // DFS of all potential points
                    int y2 = potentialUp.y;
                    boolean isAbove = y2 > y1; // check the point is greater than input point in y plain to conclude the point is  above the input point
                    if (isAbove)
                        rectangleCount += clockwiseCountRectangle(potentialUp, coordsTable, Direction.RIGHT, lowerLeftY); // potential up point detected, from that point navigate to right
                }
            } else if (direction == Direction.RIGHT) {
                List<Point> relevantCoordinates = coordsTable.get("y").get(y1); // get all points lie in same y plain, will get different x points
                for (Point potentialRight : relevantCoordinates) { // DFS of all potential points
                    int x2 = potentialRight.x;
                    boolean isRight = x2 > x1; // potential point should be to right of input point only if potential point x value is greater than input point
                    if (isRight)
                        rectangleCount += clockwiseCountRectangle(potentialRight, coordsTable, Direction.DOWN, lowerLeftY); // potential right point detected, from that point navigate down
                }
            }
            return rectangleCount;
        }
    }
    /*
    //Simpler Solution : Time : O(N^2), Space O(n)..

    // Each point is considered as potential lower left point and finding out each
    other point as potential top right point. once both the corners are found, predict
    the upper left point and lower left point values from the lower left and upper right values
    and check for their existence in the total given points. if all four points are found we can
    potentially form a rectangle which is accounted.

    public static int rectangleMania(Point[] coords) {
      int rectangleCount = 0;
      Set<String> allPoints = new HashSet<>();
      for (Point point : coords)
          allPoints.add(getPointInString(point));

      for (int srcIdx = 0; srcIdx < coords.length; srcIdx++) {
          for (int destIdx = 0; destIdx < coords.length; destIdx++) {
            Point lowerLeft = coords[srcIdx];
            int x1 = lowerLeft.x;
            int y1 = lowerLeft.y;
            Point pointToMatch = coords[destIdx];
            int x2 = pointToMatch.x;
            int y2 = pointToMatch.y;
            if (!PointIsNotUpperRight(lowerLeft, pointToMatch))
                continue;
            //control comes here only if pointToMatch is a upperRight CoOrdinate
            if (allPoints.contains(getPointStringFromCoOrds(x1, y2)) && // check all points contain upperLeft CoOrd
                allPoints.contains(getPointStringFromCoOrds(x2, y1))) // check all points contain lowerRight CoOrd
                rectangleCount++;

          }
      }
      return rectangleCount;
    }
    public static String getPointStringFromCoOrds(int x, int y) {
        return getPointInString(new Point(x, y));
    }
    public static boolean PointIsNotUpperRight(Point lowerLeft, Point pointToMatch) {
        return pointToMatch.x > lowerLeft.x && pointToMatch.y > lowerLeft.y;
    }
    public static String getPointInString(Point point) {
        return point.x+":"+point.y;
    }
    */

    static class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
