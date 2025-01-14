package cedar.tasks;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.lang.reflect.Type;

public class LocalDateSerializer implements JsonSerializer<LocalDate> {

    public JsonElement serialize(LocalDate date, final Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE)); // "yyyy-mm-dd"
    }
}