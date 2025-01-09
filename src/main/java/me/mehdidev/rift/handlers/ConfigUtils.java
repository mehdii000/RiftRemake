package me.mehdidev.rift.handlers;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigUtils {

    private static final Gson gson = new Gson();

    // Convert any List<?> into a List<String> where each element is serialized as JSON string
    public static List<String> listToStringList(List<?> list) {
        List<String> stringList = new ArrayList<>();
        for (Object obj : list) {
            if (obj != null) {
                stringList.add(gson.toJson(obj)); // Serialize each object into JSON string
            } else {
                stringList.add("null");
            }
        }
        return stringList;
    }

    // Convert a List<String> back into a List of original type using Gson
    public static <T> List<T> stringListToList(List<String> stringList, Class<T> clazz) {
        List<T> objectList = new ArrayList<>();
        for (String str : stringList) {
            if (str.equals("null")) {
                objectList.add(null);
            } else {
                T obj = gson.fromJson(str, clazz); // Deserialize JSON string back to the original object
                objectList.add(obj);
            }
        }
        return objectList;
    }

    public static <E extends Enum<E>> List<String> getEnumNames(Class<E> enumClass) {
        return java.util.Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

}
