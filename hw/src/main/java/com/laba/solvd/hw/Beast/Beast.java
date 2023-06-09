package com.laba.solvd.hw.Beast;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Beast {
    public String name;
    public LocalDate DOB;
    static private Period age;
    protected boolean furry;
    protected String breed;

    public Beast(String name, String DOB, boolean furry) {
        this.name = name;
        setDOB(DOB);
        this.furry = furry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age.getYears();
    }

    public LocalDate getDOB() {
        return DOB;
    }

    public void setDOB(String inputDOB) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(inputDOB, formatter);
        this.DOB = date;
        LocalDate currentDate = LocalDate.now();
        age = Period.between(DOB, currentDate);
    }
}