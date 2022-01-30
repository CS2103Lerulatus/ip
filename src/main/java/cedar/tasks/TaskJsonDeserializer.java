package cedar.tasks;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class TaskJsonDeserializer implements JsonDeserializer<Task> {

    @Override
    public Task deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement jsonType = jsonObject.get("taskType");
        String type = jsonType.getAsString();

        Task typeModel = null;

        if("DeadlineTask".equals(type)) {
            typeModel = new DeadlineTask(jsonObject.get("desc").getAsString(),
                    LocalDate.parse(jsonObject.get("duedate").getAsString()));
        } else if("EventTask".equals(type)) {
            typeModel = new EventTask(jsonObject.get("desc").getAsString(),
                    LocalDate.parse(jsonObject.get("timestamp").getAsString()));
        } else {
            typeModel = new TodoTask(jsonObject.get("desc").getAsString());
        }

        return typeModel;
    }
}