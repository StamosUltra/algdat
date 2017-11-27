/**
 * Created by joaki on 16.10.2017.
 */
public class Heap {
    int len;
    Node[] node;

    public Heap(Node[] noder) {
        len = noder.length;
        node = noder;
        lag_heap();
    }

    public void fix_heap(int i) {
        int m = (i<<1) + 1; // venstre
        if (m < len) {
            int h = m+1; // høyre
            int vDist = ((Forgj)node[m].d).dist;
            if (h  < len && ((Forgj)node[h].d).dist > vDist) m = h;
            if (vDist > ((Forgj)node[i].d).dist) {
                Node help = node[m];
                node[m] = node[i];
                node[i] = help;
                fix_heap(m);
            }
        }
    }

    public void lag_heap() {
        int i = len/2;
        while(i-- > 0) fix_heap(i);
    }

    public Node hent_min(int index) {
        for (int i = 0; i < len; i++) {

        }
        return null;
    }
}
