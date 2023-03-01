package ru.clevertec.ilkevich.JsonParser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private Long id;
    private String name;
    private String password;
    private Double money;

}
