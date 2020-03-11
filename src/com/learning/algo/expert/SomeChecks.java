public class SomeChecks {
    public static void main(String[] args) {
        String str = "Baskar";
        var strArray  = str.split("");
        System.out.println(strArray.length);
        for (var each: strArray)
            System.out.println(each);
    }
}
