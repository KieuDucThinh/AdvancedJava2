package com.ltc.btl_javafx.model;

import java.time.LocalDate;

public class Bill {
    private int billID;
    private String accountID;
    private String HomeTownID;
    private String RoomID;
    private String TenantID;
    private int electricNumber;
    private LocalDate invoiceDate;
    private String billPrice;

    public Bill() {
    }

    public Bill(String accountID, String homeTownID, String roomID, String tenantID, int electricNumber, LocalDate invoiceDate, String billPrice) {
        this.accountID = accountID;
        this.HomeTownID = homeTownID;
        this.RoomID = roomID;
        this.TenantID = tenantID;
        this.electricNumber = electricNumber;
        this.invoiceDate = invoiceDate;
        this.billPrice = billPrice;
    }

    public Bill(int billID, String accountID, String homeTownID, String roomID, String tenantID, int electricNumber, LocalDate invoiceDate, String billPrice) {
        this.billID = billID;
        this.accountID = accountID;
        this.HomeTownID = homeTownID;
        this.RoomID = roomID;
        this.TenantID = tenantID;
        this.electricNumber = electricNumber;
        this.invoiceDate = invoiceDate;
        this.billPrice = billPrice;
    }

    public int getBillID() {
        return this.billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public String getAccountID() {
        return this.accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getHomeTownID() {
        return this.HomeTownID;
    }

    public void setHomeTownID(String homeTownID) {
        this.HomeTownID = homeTownID;
    }

    public String getRoomID() {
        return this.RoomID;
    }

    public void setRoomID(String roomID) {
        this.RoomID = roomID;
    }

    public String getTenantID() {
        return this.TenantID;
    }

    public void setTenantID(String tenantID) {
        this.TenantID = tenantID;
    }

    public int getElectricNumber() {
        return this.electricNumber;
    }

    public void setElectricNumber(int electricNumber) {
        this.electricNumber = electricNumber;
    }

    public LocalDate getInvoiceDate() {
        return this.invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getBillPrice() {
        return this.billPrice;
    }

    public void setBillPrice(String billPrice) {
        this.billPrice = billPrice;
    }
}
