import java.io.File;
import java.io.IOException;

/**
 * Created by joaki on 16.10.2017.
 */
public class Ov7Main {
    public static void main(String[] args) throws IOException{
        Graf g = new Graf();
        g.ny_vgraf(new File("resources\\vg1"));
        g.dijkstra(g.noder[0]);
        //g.prim();
        System.out.println(g.stringDjiktras(g.noder[0]));
        //System.out.println(g.stringPrim());
    }
}
