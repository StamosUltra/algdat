import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by joaki on 15.09.2017.
 */
public class CleanUp {
    public static void closeBufferedReader(BufferedReader br) {
        try {
            if (br != null) {
                br.close();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void closeFileReader (FileReader fr) {
        try  {
            if (fr != null) {
                fr.close();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
