package com.mastercard.mockapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
//    private int eid;
    private String id;
//    private String firstName;
    private String lastName;
    private School school;
    private Course course;
//    private boolean isCustomer;
//    private float price;
//    private double totalPrice;
//    private long totalAmount;
//    private char gender;
}
