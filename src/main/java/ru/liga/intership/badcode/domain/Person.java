package ru.liga.intership.badcode.domain;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private Integer id;
    private String sex;
    private String name;
    private Integer age;
    private Integer weight;
    private Integer height;
}
