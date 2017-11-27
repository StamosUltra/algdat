/**
 * Created by joaki on 17.09.2017.
 */
public class HashArrayInteger {
    private int[] array;
    private int m;
    private int powerOf;
    private final int A = 1824261409; //A big prime number
    private int n;

    public HashArrayInteger() {
        m = 8388608;    //2^23
        array = new int[m];
        powerOf = 23;
    }

    public HashArrayInteger(int powerOf) {
        if (powerOf < 0) throw new IllegalArgumentException("Array size must be grater than 0");
        m = (int)Math.pow(2,powerOf);
        this.powerOf = powerOf;
        array = new int[m];
    }

    public int[] getArray() {
        return array;
    }

    public int getM() {
        return m;
    }

    public int getPowerOf() {
        return powerOf;
    }

    public int getN() {
        return n;
    }

    public int add(int k) {
        if (k > 0 && n < m) {
            int key = k* A;
            /*
            int mask = key >> 31;
            key = (key + mask) ^ mask;
            */

            key = (key < 0) ? -key: key;

            /*
            while (key < 0) {
                key *= A;
            }
            */
            int index = key >> (32-powerOf);

            if(array[index] == 0) {
                array[index] = k;
                n++;
                return 0;
            }
            else {
                int collisions = 0;
                int key2 = 2*k +1;
                /*
                mask = key2 >> 31;
                key2 = (key2 + mask) ^ mask;
                */

                key2 = (key2 < 0) ? -key2: key2;
                key2 = key2 & ((m-1)>>1);

                while (true) {
                    collisions++;
                    // System.out.println("Kollisjoner: " + collisions + " k: " + k + " int: " + array[index]);


                    int startIndex = index;
                    while (index - startIndex < 16) {
                        index++;
                        if (array[index] == 0) {
                            array[index] = k;
                            n++;
                            return collisions;
                        }
                        collisions++;
                    }
                    /*
                    while (key2 < 0) {
                        key2 = (2*key2+1) * A;
                    }
                    */
                    index = (index + key2) & (m-1);
                    if (array[index] == 0) {
                        array[index] = k;
                        n++;
                        return collisions;
                    }
                }
            }
        }
        return -1;
    }

    public int find(int k) {
        if (k > 0 && n < m) {
            int key = k*A;
            /*
            int mask = key >> 31;
            key = (key + mask) ^ mask;
            */

            key = (key < 0) ? -key: key;

            /*
            while (key < 0) {
                key *= A;
            }
            */
            int index = key >> (32-powerOf);

            if(array[index] == k) {
                return index;
            }
            else {
                int collisions = 0;
                int key2 = 2*A*k +1;
                /*
                mask = key2 >> 31;
                key2 = (key2 + mask) ^ mask;
                */

                key2 = (key2 < 0) ? -key2: key2;

                do {
                    collisions++;
                    // System.out.println("Kollisjoner: " + collisions + " k: " + k + " int: " + array[index]);


                    int startIndex = index;
                    while (index - startIndex < 16) {
                        index++;
                        if (array[index] == k) {
                            return index;
                        }
                    }
                    /*
                    while (key2 < 0) {
                        key2 = (2*key2+1) * A;
                    }
                    */
                    index = (((key) >> (32 - powerOf)) + (collisions * ((key2) >> (32-powerOf)))) % m;
                    if (array[index] == k) {
                        return index;
                    }
                } while (collisions < 16*m);
            }
        }
        return -1;
    }
}
