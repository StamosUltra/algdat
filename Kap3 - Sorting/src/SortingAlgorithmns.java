/**
 * Created by joaki on 28.08.2017.
 */
public class SortingAlgorithmns {
    public static int[] insertionSortWithBinarySearch(int[] array){
        int top = array.length;
        for (int i = 1; i < array.length; i++) {
            int value = array[i];
            int j = i-1;
            if (value < array[j]) {
                int low = 0;
                int high = j;
                int mid = (low + high) / 2;
                while (low < high) {
                    if (mid == 0 || value == array[mid] || value >= array[mid-1] && value <= array[mid]) {
                        int help = array[mid];
                        array[mid] = array[i];
                        for (int k = mid + 1; k <= j ; k++) {
                            array[i] = array[k];
                            array[k] = help;
                            if (j-k == 1) {
                                help = array[j];
                                array[j] = array[i];
                                array[i] = help;
                                break;
                            }
                        }
                        break;
                    }
                    else if (value < array[mid]) high = mid;
                    else low = mid;
                    mid = (low + high) / 2;
                }
            }
        }
        return new int[0];
    }

    public static int binarySearch (int[] array, int target) {
        int mid = array.length/2;
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            if (array[mid] == target) return mid;
            else if (array[mid] > target) high = mid;
            else low = mid;

            mid = (high + low) / 2;
        }
        return -1;                                      // no index found
    }

    public static int[] movesAnArrayIndex(int[] array, int start, int end){
        int help = array[start];
        array[start] = array[end];
        for (int i = start+1; i < end; i++) {
            array[end] = array[i];
            array[i] = help;
        }
        return array;
    }
}
