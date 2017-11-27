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

    public String stringDjiktras(Node s) {
        if (this.equals(s)) return nummer + "   start  0";
        if (d!= null) {
            return nummer + "    " + ((Forgj)d).stringDjiktras(s);
        }
        else return String.valueOf(nummer);
    }

    public String stringPrim() {
        if(d != null && ((Forgj)d).forgj != null) {
            return nummer + " - " + ((Forgj)d).stringPrim();
        } else return "";
    }
}
