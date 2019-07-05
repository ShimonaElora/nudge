package com.example.nudge.models;

public class MarketModel {

    String sellingPrice,exportCharges,profitRate,ProfitPercent,name,info;

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getExportCharges() {
        return exportCharges;
    }

    public void setExportCharges(String exportCharges) {
        this.exportCharges = exportCharges;
    }

    public String getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(String profitRate) {
        this.profitRate = profitRate;
    }

    public String getProfitPercent() {
        return ProfitPercent;
    }

    public void setProfitPercent(String profitPercent) {
        ProfitPercent = profitPercent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public MarketModel(String sellingPrice, String exportCharges, String profitRate, String profitPercent, String name, String info) {
        this.sellingPrice = sellingPrice;
        this.exportCharges = exportCharges;
        this.profitRate = profitRate;
        ProfitPercent = profitPercent;
        this.name = name;
        this.info = info;
    }
}
