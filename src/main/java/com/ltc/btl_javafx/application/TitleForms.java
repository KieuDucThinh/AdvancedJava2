package com.ltc.btl_javafx.application;

public class TitleForms {
    public static String TitleVersion = "V1.0.0";
    public static String TitleLoginForm;
    public static String TitleHomePageForm;
    public static String TitleRoomForm;
    public static String TitleTenantForm;
    public static String TitleServiceForm;
    public static String TitleBillForm;
    public static String TitleStatisticsForm;

    static {
        TitleLoginForm = "Phần Mềm Quản Lý Chung Cư Mini " + TitleVersion + " - Đăng nhập";
        TitleHomePageForm = "Phần Mềm Quản Lý Chung Cư Mini " + TitleVersion + " - Trang chủ";
        TitleRoomForm = "Phần Mềm Quản Lý Chung Cư Mini " + TitleVersion + " - Quản lý tòa nhà và phòng";
        TitleTenantForm = "Phần Mềm Quản Lý Chung Cư Mini " + TitleVersion + " - Quản lý khách thuê phòng";
        TitleServiceForm = "Phần Mềm Quản Lý Chung Cư Mini " + TitleVersion + " - Quản lý dịch vụ";
        TitleBillForm = "Phần Mềm Quản Lý Chung Cư Mini " + TitleVersion + " - Tính tiền và xuất hóa đơn";
        TitleStatisticsForm = "Phần Mềm Quản Lý Chung Cư Mini " + TitleVersion + " - Thống kê doanh thu";
    }

    public TitleForms() {
    }
}
