/**
 * Created by joaki on 18.09.2017.
 */
public class Main2 {
    public static void main(String[] args) {
        int i = -1456890;
        int mask = 2147483646;
        System.out.println(i);
        System.out.println(Math.abs(i));
        mask = i >> 31;
        i = (i + mask) ^ mask;
        System.out.println(i);
    }
}
