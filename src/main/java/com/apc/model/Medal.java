package com.apc.model;

/**
 * Created by esdrasbb on 25/11/15.
 */
public class Medal implements Comparable<Medal> {

    private String studentName;
    private Integer amount;

    public Medal(String studentName, Integer amount) {
        this.studentName = studentName;
        this.amount = amount;
    }

    public String getStudentName() {
        return studentName;
    }

    public Integer getAmount() {
        return amount;
    }

    @Override
    public int compareTo(Medal m1) {
        return m1.amount.compareTo(this.amount);
    }

}
