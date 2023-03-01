package ru.clevertec.ilkevich.JsonParser.util;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

public class JsonParser {

    private final static String BRACE_LEFT = "{";
    private final static String BRACE_RIGHT = "}";
    private final static String QUOTE = "\"";
    private final static String COMMA = ",";
    private final static String COLON = ":";

    /**
     * json to object conversion
     * return - object (Object)
     */
    public static Object jsonToObjectConversion(Object object, String source) {

        Map<String, Object> fieldValueMap = jsonToMapConversion(source);
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (field.getType() == String.class) {
                    field.set(object, fieldValueMap.get(field.getName()));
                } else if (field.getType().equals(Byte.class)) {
                    field.set(object, Byte.parseByte((String) fieldValueMap.get(field.getName())));
                } else if (field.getType().equals(Short.class)) {
                    field.set(object, Short.parseShort((String) fieldValueMap.get(field.getName())));
                } else if (field.getType().equals(Integer.class)) {
                    field.set(object, Integer.parseInt((String) fieldValueMap.get(field.getName())));
                } else if (field.getType().equals(Long.class)) {
                    field.set(object, Long.parseLong((String) fieldValueMap.get(field.getName())));
                } else if (field.getType().equals(Float.class)) {
                    field.set(object, Float.parseFloat((String) fieldValueMap.get(field.getName())));
                } else if (field.getType().equals(Double.class)) {
                    field.set(object, Double.parseDouble((String) fieldValueMap.get(field.getName())));
                } else if (field.getType().equals(Character.class)) {
                    field.set(object, ((String) fieldValueMap.get(field.getName())).charAt(0));
                } else if (field.getType().equals(Boolean.class)) {
                    field.set(object, Boolean.parseBoolean((String) fieldValueMap.get(field.getName())));
                }
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return object;

    }

    /**
     * converting a json to map( key, value)
     * return - Map<String, Object>
     */
    private static Map<String, Object> jsonToMapConversion(String json) {

        Map<String, Object> mopObjects = stream(json.substring(1, json.length() - 1).split(COMMA))
                .map(o -> o.replaceAll(QUOTE, "").split(COLON))
                .collect(toMap(i -> i[0], i -> i[1]));
        return mopObjects;

    }

    /**
     * object to json conversion
     * return - JSON (String)
     */
    public static String objectToJsonConversion(Object object) {

        Map<String, String> fieldValueMap = objectToMapConversion(object);
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : fieldValueMap.entrySet()) {
            stringBuilder.append(QUOTE)
                    .append(entry.getKey())
                    .append(QUOTE)
                    .append(COLON)
                    .append(entry.getValue())
                    .append(COMMA);
        }
        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        stringBuilder.insert(0, BRACE_LEFT).append(BRACE_RIGHT);
        return String.valueOf(stringBuilder);

    }


    /**
     * converting an object to map( key, value)
     * return - Map<String, String>
     */
    private static Map<String, String> objectToMapConversion(Object object) {

        Map<String, String> mapObject = stream(object.getClass().getDeclaredFields())
                .collect(toMap(
                        Field::getName,
                        fields -> {
                            fields.setAccessible(true);
                            try {
                                return fields.getType() == String.class || fields.getType() == Character.class ?
                                        "\"" + fields.get(object) + "\"" : String.valueOf(fields.get(object));
                            } catch (IllegalAccessException e) {
                                return "";
                            }
                        },
                        (k, v) -> k, LinkedHashMap::new
                ));
        return mapObject;
    }

}
