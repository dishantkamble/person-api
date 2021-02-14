package com.dishant.person.api.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
public class Person {

    @JsonProperty(access = Access.READ_ONLY)
    private UUID id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private Integer age;

    @JsonProperty("favourite_colour")
    private String favouriteColour;
}
