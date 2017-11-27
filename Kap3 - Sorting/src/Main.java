import java.util.Arrays;
import java.util.Random;

/**
 * Created by joaki on 28.08.2017.
 */
public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        int arraySize = 10;
        int[] array = {1,6,8,3,5,4,2,7,9,10};
        for (int i = 0; i < arraySize; i++) {
            //array[i] = random.nextInt(10);
            System.out.println("i: " + i + " = " + array[i]);
        }

        int[] sortedArray = SortingAlgorithmns.insertionSortWithBinarySearch(array);
        for (int i = 0; i < sortedArray.length; i++) {
            System.out.println(sortedArray[i]);
        }

    }
}
