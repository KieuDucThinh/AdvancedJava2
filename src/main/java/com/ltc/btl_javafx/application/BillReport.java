package com.ltc.btl_javafx.application;


import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class BillReport {
    public static JasperReport jreport;
    public static JasperViewer jviewer;
    public static JasperPrint jprint;

    public BillReport() {
    }

    public static void createReport(Connection connect, Map<String, Object> map, InputStream by) {
        try {
            jreport = (JasperReport)JRLoader.loadObject(by);
            jprint = JasperFillManager.fillReport(jreport, map, connect);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static void showReport() {
        jviewer = new JasperViewer(jprint);
        jviewer.setVisible(true);
    }
}

