package com.ltc.btl_javafx.model;

import java.time.LocalDate;

public class Member extends Person {
    private String memberID;
    private Tenant tenant;
    
    public Member() {
    }
    
    public Member(String memberID, String name, String sex, LocalDate birthdate, String citizenID, String phoneNum, String placeOrigin) {
        super(name, sex, birthdate, citizenID, phoneNum, placeOrigin);
        this.memberID = memberID;
    }

    public Member(String memberID, Tenant tenant) {
        this.memberID = memberID;
        this.tenant = tenant;
    }

    public Member(String memberID, Tenant tenant, String name, String sex, LocalDate birthdate, String citizenID, String phoneNum, String placeOrigin) {
        super(name, sex, birthdate, citizenID, phoneNum, placeOrigin);
        this.memberID = memberID;
        this.tenant = tenant;
    }
    
    public String getMemberID() {
        return this.memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    @Override
    public String toString() {
        return "Member{" + "MI= " + memberID + ", TI= " + tenant.getTenantID() +" "+ super.toString() +'}';
    }

    
}

