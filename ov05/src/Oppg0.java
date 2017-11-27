import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by joaki on 15.09.2017.
 */
public class Oppg0 {
    public static void main(String[] args) throws IOException{
        HashArrayString ha = new HashArrayString(7);

        /*
        Reads a file to a ArralyList s and adds it to the HashArrayString
         */
        ArrayList<String> s = new ArrayList<>();
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader("src\\resources\\navn.txt");
            br = new BufferedReader(fr);
            String name;
            while ((name = br.readLine()) != null) {
                s.add(name);
            }
        } catch (Exception e) {
            CleanUp.closeBufferedReader(br);
            CleanUp.closeFileReader(fr);
            System.out.println(e.toString());
        } finally {
            CleanUp.closeBufferedReader(br);
            CleanUp.closeFileReader(fr);
        }

        int collisions = 0;
        for (String name  : s) {
            int c;
            if ((c = ha.add(name)) != -1) {
                collisions += c;
            }
        }
        System.out.println("Lastfaktor: " + (double)ha.getN()/(double)ha.getM() + "\n" +
                "Collisons: " + collisions);

        //System.out.println(ha.find("Elias Brattli Sørense"));

    }
}
