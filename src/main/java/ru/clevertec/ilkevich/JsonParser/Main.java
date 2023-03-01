package ru.clevertec.ilkevich.JsonParser;

import ru.clevertec.ilkevich.JsonParser.model.Person;
import ru.clevertec.ilkevich.JsonParser.util.JsonParser;

public class Main {
    public static void main(String[] args) {

        Person person = new Person(12L, "Nastya", "ncvjhckjv", 12.6);
        System.out.println("object to json conversion -> " + JsonParser.objectToJsonConversion(person));

        String jsonPerson = "{\"id\":12,\"name\":\"Nastya\",\"password\":\"ncvjhckjv\",\"money\":12.6,}";
        Person person2 = new Person();
        System.out.println("json to object conversion -> " + JsonParser.jsonToObjectConversion(person2, jsonPerson));
    }
}
