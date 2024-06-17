package com.ltc.btl_javafx.model;

public class RoomAndTenant {
    protected HomeTown home;
    protected Room room;
    protected Tenant tenant;

    public RoomAndTenant(HomeTown home, Room room, Tenant tenant) {
        this.home = home;
        this.room = room;
        this.tenant = tenant;
    }

    public HomeTown getHome() {
        return this.home;
    }

    public void setHome(HomeTown home) {
        this.home = home;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Tenant getTenant() {
        return this.tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
}

