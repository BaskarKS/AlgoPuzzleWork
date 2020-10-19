package hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class ShortenPath {
    /*
    function input is a non-empty string representing a valid Unix-shell path
    returns a shortened version of the path.

    path is a notation which represents the location of a file / directory in a file system.

    path can be absolute path (starts at root directory in a file system) or a relative path(starts
    at current directory in a file system).

    Unix-Operating system rules:
    1. root directory is represented by /. If a path starts with '/' then its absolute path,
    else its relative path.
    2. otherwise '/' represents directory separator, path /foo/bar/ is location of directory
     bar inside the directory foo. which itself located inside the root directory
    3. symbol .. represents the parent directory. means accessing files or directories in
        /foo/bar/.. is equivalent to accessing files or directories in /foo
    4. symbol . represents the current directory, means accessing files or directories
    in /foo/bar/. is equivalent to accessing files or directories in /foo/bar

    symbols / an . can be repeated sequentially without consequence , but .. will have
    consequences because repeating it sequentially means going further up in parent
    directories.
    eg: foo/bar/baz/././. and foo/bar/baz are equivalent paths.
    but /foo/bar/baz/../../../ and foo/bar/baz definitely aren't
    another exception with root directory is /../../../ and / are equivalent, because root
    directory has no parent directory. means repeatedly accessing parent directories
    does nothing.

    shortened version of path must be equivalent to original path, which means it must
    point to the same file or directory as the original path.

    Edge cases:
    We should keep track of whether the input string is absolute path or relative path
    which will be valid for final string construction,

    Ip : /foo/../test/../test/../foo///bar/./baz
    Op :    /foo/bar/baz   => equivalent to input path

input is absolute path
        "/foo/../test/../test/../foo///bar/./baz"
        ["", foo, .. , test, .. , test, .. , foo, "", bar, . , baz]
        [foo, .. , test , .. , test , .. , foo, bar, baz]
final value in stack ["", foo, bar, baz] => can be used to construct final string

input is relative path
"../../foo" => initial .. can be ignores as it starts with .. which means it's relative path
final value in stack [.. , .. , foo] => can be used to construct final string

input is absolute path but with unwanted ..
    "/../../../foo" => here we could see it starts with / means its absolute path, but successive .. means nothing
    "/../../../foo" => /foo => we cant move previous to root directory

    If we use string concatenation to compute the final string using + it leads to
    O(n^2) time complexity, better to use join() to compute final string in O(n) time

    * */


    public static void main(String[] args) {
        String path = "/foo/../test/../test/../foo//bar/./baz";
        shortenPath(path);
    }
    public static boolean validPath(String token) {
        return token.compareTo("") != 0 && token.compareTo(".") != 0; // '/.' is split into . which can be avoided, // is split into ' ' which can be avoided
    }

    // TIme : O(n) and Space: O(n)
    public static String shortenPath(String path) {
        // Write your code here;
        boolean isAbsolutePath = path.charAt(0) == '/'; // if the first char is / then it should be absolute path which should be added in output string
        String[] splits = path.split("/");
        List<String> tokenList = Arrays.stream(splits).filter(ShortenPath::validPath).collect(Collectors.toList()); // removing unwanted entries and filtering out the tokens to be parsed

        Stack<String> result = new Stack<>();
        if (isAbsolutePath)
            result.push(""); // adding first entry as "" if the input string is found to be absolute path

        for (String token : tokenList) {
            if (token.compareTo("..") == 0) { // if .. is encountered, the previous folder should be removed because it indicates to move to parent directory
                if (result.empty() || result.peek().compareTo("..") == 0) // edge case, if the stack is empty means we deal with relative path ("../foo/bas" ), for relative path .. we should consider, the last element in a .. parent directory "../../foo/bas"
                    result.push(token);
                else if (result.peek() == "") { //stack is not empty, encountered token is .. but prior to that we have "" in stack which means "/..", we have to avoid considering .. because we cant go to parent of root(/)
                    continue;
                } else { // if the stack is not empty and top of stack is not / which is "" or .. Hence we pop the item from stack to process ..
                    result.pop();
                }
            } else {
                result.push(token); // otherwise we push the item to stack
            }
        }

//        List<String> check = new ArrayList<>();
//        int idx = 0;
//        int length = result.size();
//        while (idx < length) {
//            check.add(result.get(idx));
//            idx++;
//        }
        if (result.size() == 1 && result.peek().compareTo("") == 0) // if the parsed result is "" which means only / is provided as input, hence we return the result as "/" since "" will not be processed as "/" in String.join()
            return "/";

        return String.join("/", result); //join all the strings using "/", we dont use String + concatenation as it takes O(n^2) time complexity, we use String.join() as it takes O(n) time, StringBuilder also uses O(n), String.join internally uses StringBuilder for concatenation
    }
}
