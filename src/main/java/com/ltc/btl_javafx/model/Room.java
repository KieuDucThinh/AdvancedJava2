package com.ltc.btl_javafx.model;

public class Room {
    private String roomID;
    private String typeroom;
    private String stateroom;
    private String priceroom;

    public Room() {
    }
    
    public Room(String roomID, String typeroom, String stateroom, String priceroom) {
        this.roomID = roomID;
        this.typeroom = typeroom;
        this.stateroom = stateroom;
        this.priceroom = priceroom;
    }

    public String getRoomID() {
        return this.roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getTyperoom() {
        return this.typeroom;
    }

    public void setTyperoom(String typeroom) {
        this.typeroom = typeroom;
    }

    public String getStateroom() {
        return this.stateroom;
    }

    public void setStateroom(String stateroom) {
        this.stateroom = stateroom;
    }

    public String getPriceroom() {
        return this.priceroom;
    }

    public void setPriceroom(String priceroom) {
        this.priceroom = priceroom;
    }

    @Override
    public String toString() {
        return "Room{" + "roomID=" + roomID + ", typeroom=" + typeroom + ", stateroom=" + stateroom + ", priceroom=" + priceroom + '}';
    }
    
    
}
