package com.ltc.btl_javafx.model;


public class Service {
    private HomeTown home;
    private String IDService;
    private String NameService;
    private String TypeService;
    private String PriceService;

    public Service(HomeTown home, String iDService, String nameService, String typeService, String priceService) {
        this.home = home;
        this.IDService = iDService;
        this.NameService = nameService;
        this.TypeService = typeService;
        this.PriceService = priceService;
    }

    public HomeTown getHome() {
        return this.home;
    }

    public void setHome(HomeTown home) {
        this.home = home;
    }

    public String getIDService() {
        return this.IDService;
    }

    public void setIDService(String iDService) {
        this.IDService = iDService;
    }

    public String getNameService() {
        return this.NameService;
    }

    public void setNameService(String nameService) {
        this.NameService = nameService;
    }

    public String getTypeService() {
        return this.TypeService;
    }

    public void setTypeService(String typeService) {
        this.TypeService = typeService;
    }

    public String getPriceService() {
        return this.PriceService;
    }

    public void setPriceService(String priceService) {
        this.PriceService = priceService;
    }
}
