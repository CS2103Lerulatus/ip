package cedar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cedar.Config;

public class Cedar {
    protected static boolean activityState = true;
    protected static ArrayList<String> internalTaskList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        cedar.Config.printGreeter();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (activityState) {
            cedar.Config.prompt();
            String[] userInput = in.readLine().split(" ");
            cedar.CliParse.parse(userInput);
        }
    }

    public static void setActivityState(boolean state) {
        activityState = state;
    }
}