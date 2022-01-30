package cedar;

import java.io.*;
import java.util.ArrayList;

import cedar.tasks.Task;
import cedar.tasks.TaskJsonDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class Cedar {
    // PS I don't really like writing enterprise-grade code
    protected static boolean activityState = true;
    protected static ArrayList<Task> internalTaskList = new ArrayList<>();

    // this should be editable in a .config file, and give export options in future
    public static final String SAVED_USERDATA = "src/main/userdata/savedTaskList.json";

    public static void main(String[] args) throws IOException {
        cedar.Config.printGreeter();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        File savedUserdata = new File("src/main/userdata/savedTaskList.json");
        if (savedUserdata.exists() && savedUserdata.isFile() && savedUserdata.length() != 0) {
            System.out.println("\uD83D\uDCDC Found and loaded previously saved tasklist. " +
                    "Type \"nuke\" if you want to erase it.");
            loadSavedTasklist();
        }

        while (activityState) {
            cedar.Config.prompt();
            String[] userInput = in.readLine().split(" ");
            cedar.CliParse.parse(userInput);
        }
    }

    private static void loadSavedTasklist() throws IOException {
        Gson gsonParser = new GsonBuilder().registerTypeAdapter(Task.class, new TaskJsonDeserializer()).create();
        internalTaskList = gsonParser.fromJson(new JsonReader(new FileReader(SAVED_USERDATA)),
                new TypeToken<ArrayList<Task>>(){}.getType());
    }

    public static void setActivityState(boolean state) {
        activityState = state;
    }
}