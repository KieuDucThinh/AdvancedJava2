/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ltc.btl_javafx.DAO;

import com.ltc.btl_javafx.model.Tenant;
import java.util.ArrayList;
import javafx.collections.ObservableList;

/**
 *
 * @author TUF
 */
public interface TenantFunction {
    public boolean addTenant(Tenant item);
    public boolean editTenant(Tenant item);
	
    public ObservableList<Tenant> selectAllTenant();
    public ObservableList<Tenant> selectTenantByMaT(String maT);
    public ObservableList<Tenant> selectHomeTownOfTenant();
    public ObservableList<Tenant> selectTenantByMaP(String maP);
    public ObservableList<Tenant> selectTenantByMaP2(String maP);
    
    public ObservableList<Tenant> selectTenantByCondition(String cond);
    public boolean deleteData(String MaK, String MaP);
    
    public int selectCount(int year, int month);
    public int selectCount2(int year, int month);;
    
    public ObservableList<Tenant> selectAllTenant2();
}