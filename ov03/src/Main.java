import java.util.Random;

/**
 * Created by joaki on 04.09.2017.
 */
public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        int arrayLength = 10000000;
        int[] a = new int[arrayLength];
        //int[] a = {7,3,5,9,1,6,2,8,4};
        for (int i = 0; i < arrayLength; i++) {
            a[i] = random.nextInt(100);
        }
        long start = System.nanoTime();
        SortingAlgorithms.quickSortWInnsetting(a, 0, arrayLength-1);
        long time = System.nanoTime() - start;
        System.out.println(SortingAlgorithms.checkSort(a) + " " + time);
    }
}
