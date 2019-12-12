public class Main {
    public static void main(String[] args) {
        String pi = "3141592653589793238462643383279";
        String[] numbers = {"314159265358979323846", "26433", "8", "3279", "314159265",
                "35897932384626433832", "79"};
        String[] numbers1 = {"3", "314", "49", "9001", "15926535897", "14", "9323", "8462643383279",
                "4", "793"};
        String[] numbers2 = {"3", "1", "4", "592", "65", "55", "35", "8", "9793", "2384626", "83279"};
        int minSeparators = AlgoExpert.numbersInPiBest2(pi, numbers2);
        System.out.println(minSeparators);
    }
}
