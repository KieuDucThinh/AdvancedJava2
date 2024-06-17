package com.ltc.btl_javafx.model;

import java.util.Objects;

public class HomeTown {
    private String townID;
    private String address;

    public HomeTown() {
    }
    
    public HomeTown(String townID, String address) {
        this.townID = townID;
        this.address = address;
    }

    public String getTownID() {
        return this.townID;
    }

    public void setTownID(String townID) {
        this.townID = townID;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            HomeTown other = (HomeTown)obj;
            return Objects.equals(this.address, other.address) && Objects.equals(this.townID, other.townID);
        }
    }

    public String toString() {
        return this.townID + " - Địa chỉ: " + this.address;
    }
}
