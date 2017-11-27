/**
 * Created by joaki on 05.11.2017.
 */
public class Test {
    public static void main(String[] args) {
        int[] acceptedStates = {2};
        char[] inputArray = new char[2];
        inputArray[0] = '0';
        inputArray[1] = '1';
        int[][] nextStateArray =
                {{1,3},
                {1,2},
                {2,3},
                {3,3}};



        Automat automat1 = new Automat(inputArray, acceptedStates, nextStateArray);

        char[] input0 = {};
        char[] input1 = {'0','1','0'};
        char[] input2 = {'1','1','1'};
        char[] input3 = {'0','1','0','1','1','0'};
        char[] input4 = {'0','0','1','0','0','0'};

        System.out.println(automat1.checkInput(input0)); // false
        System.out.println(automat1.checkInput(input1)); // true
        System.out.println(automat1.checkInput(input2)); // false
        System.out.println(automat1.checkInput(input3)); // false
        System.out.println(automat1.checkInput(input4)); // true
        System.out.println();

        int[] as = {3};
        inputArray[0] = 'a';
        inputArray[1] = 'b';
        int[][] nsa = {
                {1,2},
                {4,3},
                {3,4},
                {3,3},
                {4,4}};

        Automat automat2 = new Automat(inputArray, as, nsa);

        char[] input5 = {'a','b', 'b', 'b'};
        char[] input6 = {'a','a', 'a', 'b'};
        char[] input7 = {'b','a', 'b', 'a', 'b'};

        System.out.println(automat2.checkInput(input5)); // true
        System.out.println(automat2.checkInput(input6)); // false
        System.out.println(automat2.checkInput(input7)); // true

    }
}
