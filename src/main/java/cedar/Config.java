package cedar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Config {
    // consider initializing these constants from a .config file w/ hardcoded default fallback
    // hardcoded for now
    public static final String VER = "0.0.1";
    public static final String DIVIDER = "♦├──────────────────────────────────────────────────┤♦";
    public static final String SHELLPROMPT = String.format("%s@Cedar:~$ ", System.getProperty("user.name"));

    public static void printGreeter() {
        // consider using classpath over abs_path to avoid gui packaging issues later on
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/cedarGreeter.config"))) {
            String bannerLines;
            while ((bannerLines = br.readLine()) != null) {
                if (bannerLines.contains("v%s")) {
                    // version-print detector, more fields to come ie. what's new box
                    System.out.format(bannerLines+"\n", VER);
                } else {
                    System.out.println(bannerLines);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void prompt() {
        System.out.print(SHELLPROMPT);
    }

    public static void printDivider() {
        System.out.println(DIVIDER);
    }
}
