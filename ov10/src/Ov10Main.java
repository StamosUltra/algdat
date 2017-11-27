import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by joaki on 31.10.2017.
 */
public class Ov10Main {
    public static void main(String[] args) {
        // String to be scanned to find the pattern.
        String numberLine = "This order was placed for QT 3000! OK";
        String dateLine = "This is a date 14/05/2007 and this something else";
        String thirtythreeCharsLine = "this line have more than 10 chars";
        String lessThanTenCharsLine = "not 10";
        String noLetters = "1673894987389";
        String oneLetter = "37485378423823k7402478945824";

        String containsNumber = "(.*)(\\d+)(.*)";  //Innholder et tall?
        String containsDate = "(.*)(\\d{2})/(\\d{2})/(\\d{4})(.*)";
        String contains10chars = "(.{10})(.*)";
        String containsLetters = "(.*)(\\p{javaUpperCase}|\\p{javaLowerCase})(.*)";

        // Create a Pattern object
        Pattern r1 = Pattern.compile(containsNumber);
        Pattern r2 = Pattern.compile(containsDate);
        Pattern r3 = Pattern.compile(contains10chars);
        Pattern r4 = Pattern.compile(containsLetters);

        // Now create matcher object.
        Matcher m1 = r1.matcher(numberLine);
        Matcher m2 = r2.matcher(dateLine);
        Matcher m3 = r3.matcher(thirtythreeCharsLine);



        if (m1.find( )) {
            for (int i = 0; i < m1.groupCount(); i++) {
                System.out.println("Found value: " + m1.group(i));
            }
        } else {
            System.out.println("NO MATCH");
        }
        System.out.println();
        if (m2.find( )) {
            for (int i = 0; i < m2.groupCount(); i++) {
                System.out.println("Found value: " + m2.group(i));
            }
        } else {
            System.out.println("NO MATCH");
        }
        System.out.println();
        if (m3.find( )) {
            for (int i = 0; i < m3.groupCount(); i++) {
                System.out.println("Found value: " + m3.group(i));
            }
        } else {
            System.out.println("NO MATCH");
        }
        System.out.println();

        if(Pattern.matches(containsLetters, noLetters)) {
            System.out.println("Found letters");
        } else {
            System.out.println("No Letters");
        }
        if(Pattern.matches(containsLetters, oneLetter)) {
            System.out.println("Found letters");
        } else {
            System.out.println("No Letters");
        }

    }
}
