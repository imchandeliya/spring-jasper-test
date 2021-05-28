package com.aditya.jaspertest.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OccupationDetails {
    private String company;
    private String jobTitle;
    private Double salary;
    private String companyAddress;
}
