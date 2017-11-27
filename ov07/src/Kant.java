/**
 * Created by joaki on 10.10.2017.
 */
public class Kant {
    Kant neste;
    Node til;
    public Kant(Node n, Kant nst) {
        til = n;
        neste = nst;
    }
}
