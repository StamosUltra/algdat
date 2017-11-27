package java;
/**
 * Created by joaki on 10.10.2017.
 */
public class Node {
    int nummer;
    Kant kant1;
    Object d;

    public Node(int nummer) {
        this.nummer = nummer;
    }

    public String toString() {
        if (d!= null) {
            return nummer + "    " + d.toString();
        }
        else return "";
    }

    public String topo(){
        if (d != null) {
            Topo_lst t = (Topo_lst)d;
            if (t.neste != null) {
                return nummer + " " + t.neste.topo();
            }
            else return "" + nummer;
        }
        else return "" + nummer;
    }
}
