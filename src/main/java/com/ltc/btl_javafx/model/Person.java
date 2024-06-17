package com.ltc.btl_javafx.model;

import java.time.LocalDate;

public class Person {
    protected String name;
    protected String sex;
    protected String citizenID;
    protected String phoneNum;
    protected String placeOrigin;
    protected LocalDate birthdate;

    public Person() {
    }

    public Person(String name, String sex, LocalDate birthdate, String citizenID, String phoneNum, String placeOrigin) {
        this.name = name;
        this.sex = sex;
        this.birthdate = birthdate;
        this.citizenID = citizenID;
        this.phoneNum = phoneNum;
        this.placeOrigin = placeOrigin;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCitizenID() {
        return this.citizenID;
    }

    public void setCitizenID(String citizenID) {
        this.citizenID = citizenID;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPlaceOrigin() {
        return this.placeOrigin;
    }

    public void setPlaceOrigin(String placeOrigin) {
        this.placeOrigin = placeOrigin;
    }

    public LocalDate getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "Name= " + name + ", Sex= " + sex + ", CCCD= " + citizenID + ", PN= " + phoneNum + ", PO= " + placeOrigin + ", BD= " + birthdate + '}';
    }
    
    
}
