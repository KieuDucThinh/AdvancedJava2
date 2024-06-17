package com.ltc.btl_javafx.DAO;

import java.io.InputStream;
import javafx.collections.ObservableList;

public interface DAOInterface<T> {
    void insertData(T var1, String var2, String var3);

    void deleteData(T var1, String var2);

    void updateData(T var1, String var2, String var3);

    ObservableList<T> selectALL();

    ObservableList<T> selectByCondition(String var1, String var2, String var3);

    ObservableList<T> searching(String var1, String var2, String var3);

    int selectCount(String var1);

    String selectID(String var1, String var2);

    void deleteAllData(String var1);

    T selectObject(String var1, String var2);

    String sumPrice(String var1, String var2);

    void insertDataToTempTable(T var1);

    void deleteDataFromTempTable(T var1);

    InputStream getReport(String var1);

    String selectTurnover(int var1, int var2);
}
