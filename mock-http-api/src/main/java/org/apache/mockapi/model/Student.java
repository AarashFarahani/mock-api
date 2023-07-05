package org.apache.mockapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student extends Person {
    private int id;
    private String firstName;
    private String lastName;
    private String uid;
    private String studentNo;
    private School school;
    private Course course;
    private int[] ss;
    private List<String> bb;
    private boolean isCustomer;
    private float price;
    private double totalPrice;
    private long totalAmount;
    private char ch;
}
