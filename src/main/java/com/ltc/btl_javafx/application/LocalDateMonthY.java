/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltc.btl_javafx.application;

import java.time.LocalDate;

/**
 *
 * @author TUF
 */
public class LocalDateMonthY {

    private LocalDate currentDate;

    public LocalDateMonthY() {
        currentDate = LocalDate.now();
    }

    //Lay thang hien tai
    public int getMonthNow() {
        return currentDate.getMonthValue();
    }

    // Lấy năm hiện tại
    public int getYearNow() {
        return currentDate.getYear();
    }

    public int getDayNow() {
        return currentDate.getDayOfMonth();
    }

    //Lay thang truoc
    public int previousMonth(int minus) {
        return currentDate.minusMonths(minus).getMonthValue();
    }

    //Lay nam truoc
    public int previousYear(int minus) {
        return currentDate.minusYears(minus).getYear();
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    
}
