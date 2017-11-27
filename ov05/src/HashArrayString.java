/**
 * Created by joaki on 15.09.2017.
 *
 * m = 2^23
 * A = 
 */
public class HashArrayString {
    private String [] array;
    private int m;
    private int powerOf;
    private final int A = 1327217866;
    private int n;

    public HashArrayString() {
        m = 8388608;
        array = new String[m];
        powerOf = 23;
    }

    public HashArrayString(int powerOf) {
        if (powerOf < 0) throw new IllegalArgumentException("Array size must be grater than 0");
        m = (int)Math.pow(2,powerOf);
        this.powerOf = powerOf;
        array = new String[m];
    }

    public String[] getArray() {
        return array;
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    public int add(String s) {
        try {
            if (s != null && n < m) {   // Checks to see if the String is valid

                /**
                 * Finds A suitable key by multiplying A prime with another prime plus every character of the String
                 */
                int k = 7;
                for (int i = 0; i < s.length(); i++) {
                    k = k * 31 + (int) s.charAt(i);
                }

                /**
                 * Find A suitable index by multiplying with A big integer, then right shifting
                 * This is possible because the size of the array is always an exponent of 2
                 */
                int key = k* A;
                while (key < 0) {
                    key *= A;
                }
                int index = key >> (32-powerOf);

                if (array[index] == null) {     // If the index is available
                    array[index] = s;
                    n++;
                    return 0;

                } else {
                    int collisions = 0;
                    /**
                     * Creates an an odd number
                     */
                    int key2 = (2*k +1);
                    key2 = (key2 < 0) ? -key2: key2;
                    key2 = key2 & ((m-1)>>1);
                    while (true) {
                        /**
                         * Checks the first 16 next indexes to see if there is an open spot
                         * For every 16 indexes, There is A double hash
                         */
                        collisions++;
                        System.out.println("Kollisjoner: " + collisions + " s: " + s+ " name: " + array[index]);


                        int startIndex = index;
                        while (index - startIndex < 16) {
                            index++;
                            if (array[index] == null) {
                                array[index] = s;
                                n++;
                                return collisions;
                            }
                            collisions++;
                            System.out.println("Kollisjoner: " + collisions + " s: " + s+ " name: " + array[index]);
                        }

                        /**
                         * Finner ny index ved å addere h1 og h2 og maskere med m-1
                         */
                        index = (index + key2) & (m-1);
                        if (array[index] == null) {
                            array[index] = s;
                            n++;
                            return collisions;
                        }
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println(e.toString() + "\n" +
                    "name: " + s);
        }
        return -1;
    }

    public int find(String s) {
        if (s != null && s.trim().length() > 0) {   // Checks to see if it's A valid input
            /**
             * Finds A key by multiplying A prime with another prime plus every character of the String
             */
            int k = 7;
            for (int i = 0; i < s.length(); i++) {
                k = k * 31 + (int) s.charAt(i);
            }
            int key = k* A;
            /**
             * Because Java is A handicapped language I have to check to see that the key isn't A negative number.
             * If it is, I keep multiplying until I get another Integer Overflow
             */
            while (key < 0) {
                key *= A;
            }
            /**
             * Find the index by multiplying with A big integer, then right shifting
             * This is possible because the size of the array is always an exponent of 2
             */
            int index = key >> (32-powerOf);

            if(array[index] != null && array[index].equals(s)) return index;
            /**
             * Keeps looking for an index in the same fashion as the add() method.
             */
            else {
                int collisions = 0;
                int key2 = (2*k +1) * A;
                do {
                    collisions++;
                    System.out.println("Kollisjoner: " + collisions + " s: " + s+ " name: " + array[index]);
                    int start = index;
                    while ((index - start < 16) && (index < m-1)) {
                        index++;
                        if (array[index] != null && array[index].equals(s)) {
                            return index;
                        }
                    }

                    while (key2 < 0) {
                        key2 = (2*key2+1) * A;
                    }

                    index = (((key) >> (32 - powerOf)) + (collisions * ((key2) >> (32-powerOf)))) % m;
                    if(array[index] != null && array[index].equals(s))  {
                        return index;
                    }
                    /**
                     * Hva burde være avbruddskriteriet?
                     */
                } while (collisions < m*16);
            }
        }
        return -1;
    }
}


