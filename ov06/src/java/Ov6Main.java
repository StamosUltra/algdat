package java;
import java.io.File;
import java.io.IOException;

/**
 * Created by joaki on 10.10.2017.
 */
public class Ov6Main {
    public static void main(String[] args) throws IOException{
        /* -- Oppg1 -- */
        File f = new File("src\\resources\\L7g1");
        Graf g1 = new Graf();
        g1.ny_ugraf(f);
        g1.bfs(g1.noder[3]);
        System.out.println(" -- Oppg1 -- \n" + g1.toString());

        Graf g2 = new Graf();
        g2.ny_ugraf(new File("src\\resources\\L7g2"));
        g2.bfs(g2.noder[3]);
        System.out.println(g2.toString());

        /* -- Oppg2 -- */
        Graf g3 = new Graf();
        g3.ny_ugraf(new File("src\\resources\\L7g5"));
        Node node = g3.topologisksort();
        System.out.println(" -- Oppg2 -- \n" + node.topo());
    }
}
