import java.io.File;

/**
 * Created by joaki on 12.11.2017.
 */
public class Ov12Main {
    public static void main(String[] args) throws Exception{
        //File input = new File("src\\input\\opg12.txt");
        File bigFile = new File("src\\input\\big.txt");
        File smallFile = new File("src\\input\\small");
        //System.out.println(Huffman.compress(bigFile));
        Huffman.expand(bigFile);
    }
}
