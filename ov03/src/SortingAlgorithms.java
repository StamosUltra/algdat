/**
 * Created by joaki on 04.09.2017.
 */
public class SortingAlgorithms {

    /**
     *
     * @param a The array that needs sorting
     * @param l left most index of the array
     * @param r right most index of the array
     */
    public static void quickSortWInnsetting(int[]a, int l, int r) {
        if (r-l > 7) {
            int il, ir, help;

            // Median3Sort
            int m = (l + r) / 2;
            if (a[l] > a[m]) {
                help = a[l];
                a[l] = a[m];
                a[m] = help;
            }
            if (a[m] > a[r]) {
                help = a[m];
                a[m] = a[r];
                a[r] = help;
                if (a[l] > a[m]) {
                    help = a[l];
                    a[l] = a[m];
                    a[m] = help;
                }
            }
            int dv = a[m];

            // puts m aside
            help = a[m];
            a[m] = a[r - 1];
            a[r-1] = help;

            for (il = l, ir = r - 1; ; ) {
                while (a[++il] < dv) ;
                while (a[--ir] > dv) ;
                if (il >= ir) break;

                help = a[il];
                a[il] = a[ir];
                a[ir] = help;
            }
            help = a[il];
            a[il] = a[r - 1];
            a[r - 1] = help;

            // il == splitposition
            // splits array on iv
            quickSortWInnsetting(a, l, il - 1);
            quickSortWInnsetting(a, il + 1, r);
        }
        /* Median3sort
        int m = (l + r) / 2;
        if (a[l] > a[m]) {
            int help = a[l];
            a[l] = a[m];
            a[m] = help;
        }
        if (a[m] > a[r]) {
            int help = a[m];
            a[m] = a[r];
            a[r] = help;
            if (a[l] > a[m]) {
                help = a[l];
                a[l] = a[m];
                a[m] = help;
            }
        }
        */
        // innsetting
        else {
            for (int i = l + 1; i <= r; i++) {
                for (int j = i; j > l; j--) {
                    if (a[j] < a[j - 1]) {
                        int help = a[j];
                        a[j] = a[j - 1];
                        a[j - 1] = help;
                    }
                }
            }
        }
    }

    public static boolean checkSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i-1] > a[i]) {
                System.out.println("i: " + i);
                System.out.println(a[i-1] + " " + a[i]);
                return false;
            }
        }
        return true;
    }
}
