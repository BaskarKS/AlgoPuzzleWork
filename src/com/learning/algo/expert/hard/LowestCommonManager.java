package hard;

import java.util.ArrayList;
import java.util.List;

public class LowestCommonManager {
    /*
        Three given inputs all are instance of OrgChart,
        each OrgChart instances have List<OrgChart> of direct reportees

        The first input is the top manager (CEO / some head of Organization)
        second and third input is the common employees in the organization

        The two reportees ar guaranteed to be distinct. function should return the
        lowest common manager between them.

        TopManager : A
        reportOne : E
        reportTwo : I
                                        A
                                  B          C
                             D      E    F     G
                         H      I

        Output is : B (common manager between E and I
    */

    /* We are suppose to return OrgChart value , each node is represented as OrgChar Value.
     To find the common manager among the two reportees we use (take help of OrgInfo class)

     At each recursive call we compute the result that the manager is found at this point, if the manager
     is found for both the reportees then we return immediately. If only one reportee is found in the
     organization tree then we need to keep track of only one reportee is found or both the reportee
     is found with 'numImportantReports' property of OrgInfo class instance.

     OrgInfo class is used like a just as information package for processing the traversal
     data. With the data supplied by the getLowestCommonManager method, its tougher to
     find the common manager.

    we need some data(manager found  or not at any point of recursive call,
    both the reportees are found,
    if one reportee is found then to keep track that one reportee is
    found )
    to travel across the recursive calls, Hence we use OrgInfo Data structure
    */
    // Time : O(n),  Space : O(d) d is the depth
    public static OrgChart getLowestCommonManager(
        OrgChart topManager, OrgChart reportOne, OrgChart reportTwo) {
        return getCommonManager(topManager, reportOne, reportTwo).lowestCommonManager;
    }

    // recursive function
    public static OrgInfo getCommonManager(OrgChart topManager,
                                           OrgChart reportOne, OrgChart reportTwo) {

        int noOfReportee = 0; // at any point of node the number of Reportee is 0 initially

        for (OrgChart reportee : topManager.directReports) { // doing DFS(post-order)
            OrgInfo orgInfo = getCommonManager(reportee, reportOne, reportTwo);
            if (orgInfo.lowestCommonManager != null) // at every node we check that manager is found/not, if found(value is not-null) we return immediately. not found we continue updating count
                return orgInfo;
            noOfReportee += orgInfo.numImportantReports; // the return value of all the child nodes from this point is updated/tracked
        }
        if (topManager.name == reportOne.name
                        || topManager.name == reportTwo.name)
            noOfReportee += 1; // if the current manager is matched with any of the one reportees we increment the reportee found
        OrgChart commonManager = (noOfReportee == 2) ? topManager : null; // after checking whether our current node is matching with any of the reportees, if we could see the value is 2, which means both the reportees are found, Hence the current node should be the lowest common manager
        return new OrgInfo(noOfReportee, commonManager); // return the OrgInfo for previous method call to process.
    }

    static class OrgInfo {
        public int numImportantReports;
        public OrgChart lowestCommonManager;

        public OrgInfo(int noOfReports, OrgChart lowestCommonManager) {
            this.numImportantReports = noOfReports;
            this.lowestCommonManager = lowestCommonManager;
        }
    }

    static class OrgChart {
        public char name;
        public List<OrgChart> directReports;

        OrgChart(char name) {
            this.name = name;
            this.directReports = new ArrayList<OrgChart>();
        }

        // This method is for testing only.
        public void addDirectReports(OrgChart[] directReports) {
            for (OrgChart directReport : directReports) {
                this.directReports.add(directReport);
            }
        }
    }
}
