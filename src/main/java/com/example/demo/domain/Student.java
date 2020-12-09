package com.example.demo.domain;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class Student {
    private int id;

    //validator not null
    @NotNull (message = "id can't be empty")
    //validator the number of digital between 1 and 30
    @Length( max = 50, message = "your surname is too long")


    private String surname;


    @NotNull
    @Length(max = 50, message = "your name is too long")
    private String name;

    @NotNull
    @Length(max = 1, message ="dont lie to me")
    private String course;

    //@PatientBooleanConstraint
    @StudentAwardsConstraint
    private String isHavingDept;

   // @PatientBooleanConstraint
    private String awards;

    public Student() {
    }

    public Student(int id, String surname, String name,  String course, String isHavingDept, String awards) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.course = course;
        this.isHavingDept = isHavingDept;
        this.awards = awards;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getIsHavingDept() {
        return isHavingDept;
    }

    public void setIsHavingDept(String isHavingDept) {
        this.isHavingDept = isHavingDept;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }


    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", course='" + course + '\'' +
                ", isHavingDept='" + isHavingDept + '\'' +
                ", awards='" + awards + '\'' +
                '}';
    }
}
