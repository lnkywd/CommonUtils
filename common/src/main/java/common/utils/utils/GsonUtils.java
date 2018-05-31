package common.utils.utils;

import android.net.Uri;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * CreateTime 2017/12/22
 * Email 18842602830@163.com
 * Description
 *
 * @author wd
 */

public class GsonUtils {

    private static Gson serializerGson = new GsonBuilder()
            .registerTypeAdapter(Uri.class, new UriSerializer())
            .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
            .create();

    private static Gson deserializerGson = new GsonBuilder()
            .registerTypeAdapter(Uri.class, new UriDeserializer())
            .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
            .create();

    public static <T> T fromJson(String json, Class<T> classT) {
        try {
            if (!TextUtils.isEmpty(json)) {
                return deserializerGson.fromJson(json, classT);
            }
            return null;
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public static <T> List<T> fromJsonToList(String json, Class<T> classT) {
        try {
            return deserializerGson.fromJson(json, new TypeToken<List<T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public static String toJson(Object src) {
        try {
            return serializerGson.toJson(src);
        } catch (JsonIOException e) {
            return null;
        }
    }

    static class UriDeserializer implements JsonDeserializer<Uri> {

        @Override
        public Uri deserialize(final JsonElement src, final Type srcType,
                               final JsonDeserializationContext context) throws JsonParseException {
            return Uri.parse(src.getAsString());
        }
    }

    static class UriSerializer implements JsonSerializer<Uri> {

        @Override
        public JsonElement serialize(Uri src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }
    }

    public static class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {

        @Override
        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != String.class) {
                return null;
            }
            return (TypeAdapter<T>) new StringNullAdapter();
        }
    }

    public static class StringNullAdapter extends TypeAdapter<String> {
        @Override
        public void write(JsonWriter writer, String value) throws IOException {
            // TODO Auto-generated method stub
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }

        @Override
        public String read(JsonReader reader) throws IOException {
            // TODO Auto-generated method stub
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }
    }

}
