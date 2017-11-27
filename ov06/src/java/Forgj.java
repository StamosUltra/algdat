package java;
/**
 * Created by joaki on 10.10.2017.
 */
public class Forgj {
    int dist;
    Node forgj;
    static int infinity = 1000000000;

    public Forgj() {
        dist = infinity;
    }
    public int getDist() {
        return dist;
    }

    public Node getForgj() {
        return forgj;
    }

    public String toString() {
        if (forgj != null) {
            return forgj.nummer + "     " + dist;
        }
        else return "      0";
    }
}

