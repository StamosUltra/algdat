import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by joaki on 16.10.2017.
 */
public class Graf {
    int N, K;
    Node[] noder;

    public void ny_vgraf(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String[] line = (br.readLine().trim()).split(" "); // Splitter på mellomrom i fila
        N = Integer.parseInt(line[0]);
        K = Integer.parseInt(line[1]);
        noder = new Node[N];
        for (int i = 0; i < N; i++) noder[i] = new Node(i);   // Fyller opp noder-tabellen
        for (int i = 0; i < K; i++) {
            line = (br.readLine().trim()).split(" ");
            int fra = Integer.parseInt(line[0]);
            int vekt = Integer.parseInt(line[line.length-1]);
            int til = -1;
            for (int j = 0; j < line.length-1; j++) {
                if (line[j] != null && !(line[j].equals(""))) {
                    til = Integer.parseInt(line[j]);
                }
            }
            Vkant k = new Vkant(noder[til], (Vkant)noder[fra].kant1, vekt);
            noder[fra].kant1 = k;
            //System.out.println(fra + " " + til + " " + vekt);
        }
    }

    private void initforgj(Node s) {
        for (int i = N ; i-- > 0;) {
            noder[i].d = new Forgj();
        }
        ((Forgj)s.d).dist = 0;
    }

    public ArrayList<Node> toArraylist(Node[] noder) {
        ArrayList<Node> kopi = new ArrayList<>(noder.length);
        for (int i = 0; i < noder.length; i++) {
            Node n = noder[i];
            kopi.add(n);
        }
        return kopi;
    }

    public void forkort(Node n, Vkant k) {
        Forgj nd = (Forgj)n.d;
        Forgj md = (Forgj)k.til.d;

        if (md.dist > nd.dist + k.vekt) {
            md.dist = nd.dist + k.vekt;
            md.forgj = n;
        }
    }

    public void dijkstra(Node s) {
        initforgj(s);
        ArrayList<Node> kopi = toArraylist(noder);
        PriorityQueue<Node> queue = new PriorityQueue<>(N, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return (((Forgj)o1.d).dist - ((Forgj)o2.d).dist);
            }
        });
        // init queue
        queue.addAll(kopi);
        for (int i = N; i > 1; i--) {
            Node n = queue.poll();
            for (Vkant k = (Vkant)n.kant1; k != null; k = (Vkant)k.neste) {
                Forgj nd = (Forgj)n.d;
                Forgj md = (Forgj)k.til.d;

                if (md.dist > nd.dist + k.vekt) {
                    md.dist = nd.dist + k.vekt;
                    md.forgj = n;
                    queue.remove(k.til);
                    queue.add(k.til);
                }
            }
        }
    }

    private void initpforgj(Node s) {
        for (int i = N ; i-- > 0;) {
            noder[i].d = new Pforgj();
        }
        ((Pforgj)s.d).dist = 0;
    }

    public void prim(){
        initpforgj(noder[0]);
        ArrayList<Node> kopi = toArraylist(noder);
        PriorityQueue<Node> queue = new PriorityQueue<>(N, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return (((Forgj)o1.d).dist - ((Forgj)o2.d).dist);
            }
        });
        // init queue
        queue.addAll(kopi);
        for (int i = N; i-- > 1 ;) {
            Node n = queue.poll();
            ((Pforgj)n.d).i_mst = true;
            for (Vkant k = (Vkant)n.kant1; k != null; k = (Vkant)k.neste) {
                Pforgj md = (Pforgj)k.til.d;
                if (!md.i_mst && k.vekt < md.dist){
                    md.forgj = n;
                    md.dist = k.vekt;
                    // Oppdaterer køen
                    queue.remove(k.til);
                    queue.add(k.til);
                }
            }
        }
    }

    public String toString() {
        String s = "Antall noder: " + noder.length + "\n" +
                "Node Forgj Dist \n";
        for (Node n : noder) {
            if (n != null) {
                s += n.toString();
                s += "\n";
            }
        }
        return s;
    }

    public String stringDjiktras(Node start) {
        String s = "Antall noder: " + noder.length + "\n" +
                "Node Forgj Dist \n";
        for (Node n : noder) {
            if (n != null) {
                s += n.stringDjiktras(start);
                s += "\n";
            }
        }
        return s;
    }

    public String stringPrim() {
        String s = "Kanter:\n" +
                "Fra - Til  vekt\n";
        for (Node n : noder) {
            if (n != null) {
                s += n.stringPrim();
                s += "\n";
            }
        }
        return s;
    }
}
