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
import com.google.gson.reflect.TypeToken;

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
            .create();

    private static Gson deserializerGson = new GsonBuilder()
            .registerTypeAdapter(Uri.class, new UriDeserializer())
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

}
