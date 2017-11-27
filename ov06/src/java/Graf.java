package java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by joaki on 10.10.2017.
 */
public class Graf {
    int N, K;
    Node[] noder;

    public Graf() {
    }

    public void ny_ugraf(File file) throws IOException {
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
            int til = Integer.parseInt(line[line.length-1]);
            Kant k = new Kant(noder[til], noder[fra].kant1);
            noder[fra].kant1 = k;
            // System.out.println(fra + " " + til);
        }
    }

    private void initforgj(Node s) {
        for (int i = N ; i-- > 0;) {
            noder[i].d = new Forgj();
        }
        ((Forgj)s.d).dist = 0;
    }

    public void bfs(Node s) {
        initforgj(s);
        Queue q = new Queue(N-1);
        q.add(s);
        while(!q.empty()) {
            Node n = (Node) q.next();
            for(Kant k = n.kant1; k != null; k=k.neste){
                Forgj f = (Forgj)k.til.d;
                if (f.dist == f.infinity) {
                    f.dist = ((Forgj)n.d).dist + 1;
                    f.forgj = n;
                    q.add(k.til);
                }
            }
        }
    }

    public Node df_topo(Node n, Node l) {
        Topo_lst nd = (Topo_lst)n.d;
        if(nd.funnet) return l;

        nd.funnet = true;
        for (Kant k = n.kant1; k != null; k = k.neste){
            l = df_topo(k.til, l);
        }
        nd.neste = l;
        return n;
    }

    public Node topologisksort() {
        Node l = null;
        for (int i = N; i-- > 0;) {
            noder[i].d = new Topo_lst();
        }
        for (int i = N; i-- > 0;) {
            l = df_topo(noder[i], l);
        }
        return l;
    }

    public String toString() {
        String s = "Node Forgj Dist \n";
        for (Node n : noder) {
            if (n != null) {
                s += n.toString();
                s += "\n";
            }
        }
        return s;
    }
}
