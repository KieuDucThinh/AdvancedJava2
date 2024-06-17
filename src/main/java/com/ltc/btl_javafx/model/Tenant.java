package com.ltc.btl_javafx.model;

import java.time.LocalDate;
import java.util.Objects;

public class Tenant extends Person {
    private String tenantID;
    private LocalDate rentDate;
    private Room room;
    private HomeTown homeTown;
    
    public Tenant() {
    }

    public Tenant(String tenantID, String name, String sex, LocalDate birthdate, String citizenID, String phoneNum, String placeOrigin, LocalDate rentDate) {
        super(name, sex, birthdate, citizenID, phoneNum, placeOrigin);
        this.tenantID = tenantID;
        this.placeOrigin = placeOrigin;
        this.rentDate = rentDate;
    }

    public Tenant(String tenantID, LocalDate rentDate, Room room, HomeTown homwTown, String name, String sex, LocalDate birthdate, String citizenID, String phoneNum, String placeOrigin) {
        super(name, sex, birthdate, citizenID, phoneNum, placeOrigin);
        this.tenantID = tenantID;
        this.rentDate = rentDate;
        this.room = room;
        this.homeTown = homeTown;
    }

    public String getTenantID() {
        return tenantID;
    }

    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    public LocalDate getRentDate() {
        return rentDate;
    }

    public void setRentDate(LocalDate rentDate) {
        this.rentDate = rentDate;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public HomeTown getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(HomeTown homwTown) {
        this.homeTown = homwTown;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            Tenant other = (Tenant)obj;
            return Objects.equals(this.birthdate, other.birthdate) && Objects.equals(this.citizenID, other.citizenID) && Objects.equals(this.tenantID, other.tenantID) && Objects.equals(this.name, other.name) && Objects.equals(this.phoneNum, other.phoneNum) && Objects.equals(this.placeOrigin, other.placeOrigin) && Objects.equals(this.rentDate, other.rentDate) && Objects.equals(this.sex, other.sex);
        }
    }

    @Override
    public String toString() {
        return "Tenant{" + "tenantID=" + tenantID  +/* ", rentDate=" + rentDate + ", room=" + room + ", homeTown=" + homeTown + */'}';
    }
    
    
}
