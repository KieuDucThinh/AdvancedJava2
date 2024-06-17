/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ltc.btl_javafx.DAO;

import com.ltc.btl_javafx.model.Member;
import javafx.collections.ObservableList;

/**
 *
 * @author TUF
 */
public interface MemberFunction {
    public boolean addMember(Member item);
    public boolean editMember(Member item);
    public boolean delMember(Member item);
    
    public ObservableList<Member> selectAllMember();
    public ObservableList<Member> selectMemberByMaK(String cond);
    public ObservableList<Member> selectMemberByCondition(String cond);
    
    public boolean deleteAllData(String cond);
}
