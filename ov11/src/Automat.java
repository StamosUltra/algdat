/**
 * Created by joaki on 05.11.2017.
 */
public class Automat {
    private char[] inputArray;
    private int[] acceptedState;
    private int[][] nextStateArray;

    public Automat(char[] inputArray, int[] acceptedState, int[][] nextStateArray) {
        this.inputArray = inputArray;
        this.acceptedState = acceptedState;
        this.nextStateArray = nextStateArray;
    }

    public boolean checkInput(char[] input){
        int state = 0;
        if (input != null && input.length > 0) {
            int counter = 0;
            char c;
            while (counter < input.length) {
                c = input[counter];
                int i = 0;
                for (i = 0; i < inputArray.length; i++) {
                    if (c == inputArray[i]) break;
                }
                state = nextStateArray[state][i];
                counter++;
            }
        }
        for (int i = 0; i < acceptedState.length; i++) {
            if (acceptedState[i] == state) return true;
        }
        return false;
    }
}
