import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by joaki on 17.09.2017.
 */
public class Main {
    public static void main(String[] args) {
        int arraySize = 5000000;
        HashArrayInteger hai = new HashArrayInteger();
        HashMap<Integer, Integer> hashMap = new HashMap();
        Random random = new Random(3692815566379L);
        int[] ints = new int[arraySize];
        int[] placeholder = new int[arraySize];

        for (int i = 0; i < arraySize; i++) {
            placeholder[i] = random.nextInt(90000000) + 100000000;
        }

        long start = System.nanoTime();
        for (int i = 0; i < arraySize; i++) {
            ints[i] = placeholder[i];
        }
        System.out.println("Tid for innsetting i tabell: " + (System.nanoTime() - start));

        start = System.nanoTime();
        int collisions = 0;
        for (int i = 0; i < arraySize; i++) {
            collisions += hai.add(placeholder[i]);
        }
        System.out.println("Tid for innsetting i hashtabell: " + (System.nanoTime() - start));
        int n = hai.getN();
        int m = hai.getM();
        System.out.println("n: " + n + " m: " + m);
        System.out.println("Lastfaktor: " + ((double)n/(double)m) + "\n" +
                "Kollisjoner: " + collisions);

        start = System.nanoTime();
        for (int i = 0; i < arraySize - 1950000; i++) {
            hashMap.put(placeholder[i], placeholder[i]);
        }
        System.out.println("Tid for innsetting i HashMap: " + (System.nanoTime() - start));

        int value = ints[1999999];

        ArrayList<Integer> al = new ArrayList<>();
        start = System.nanoTime();
        for (int i = 0; i < arraySize; i++) {
            if (ints[i] == value) {
                al.add(i);
            }
        }
        System.out.println("Time to find index in array: " + (System.nanoTime() - start));
        for (int index:al) {
            System.out.println("index: " + index);
        }

        start = System.nanoTime();
        int index = hai.find(value);
        System.out.println("Time to find index in Hash array: " + (System.nanoTime() - start));
        System.out.println("index: " + index);

        start = System.nanoTime();
        index = hashMap.get(value);
        System.out.println("Time to find index in HashMap: " + (System.nanoTime() - start));
        System.out.println("index: " + index);
    }
}
