package cedar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;

import cedar.tasks.LocalDateSerializer;
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

        File savedUserdata = new File(SAVED_USERDATA);
        if (savedUserdata.exists() && savedUserdata.isFile() && savedUserdata.length() != 0) {
            System.out.println("\uD83D\uDCDC Found and loaded previously saved tasklist (from last session). " +
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

    public static ArrayList<Task> getInternalTaskList() {
        return internalTaskList;
    }

    public static void setInternalTaskList(ArrayList<Task> internalTaskList) {
        Cedar.internalTaskList = internalTaskList;
    }

    public static void writeInternalListToJsonHandler() {
        try {
            Writer writeUserdata = new FileWriter(Cedar.SAVED_USERDATA);
            Gson dateJsonSerialize = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateSerializer()).create();
            dateJsonSerialize.toJson(internalTaskList, writeUserdata);
            writeUserdata.flush();
            writeUserdata.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("⚠ Error saving tasklist to disk. Check Logs.");
        }
    }
}