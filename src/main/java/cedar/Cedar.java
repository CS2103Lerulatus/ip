package cedar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Cedar {
    public static void main(String[] args) {
        // consider using classpath over abs_path to avoid packaging issues later on
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/cedarGreeter.txt"))) {
            String bannerLines;
            while ((bannerLines = br.readLine()) != null) {
                System.out.println(bannerLines);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}