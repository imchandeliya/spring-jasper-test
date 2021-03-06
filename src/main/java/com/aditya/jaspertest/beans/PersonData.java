package com.aditya.jaspertest.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonData {

    private NameModel name;
    private OccupationDetails occupationDetails;
    private Integer age;
    private String hobby;
}
