package com.example.mvvmkhang.model;

public class DataCart {
    String id,dataTitle,dataLang,dataImage;

    public DataCart() {
    }

    public DataCart(String id, String dataTitle, String dataLang, String dataImage) {
        this.id = id;
        this.dataTitle = dataTitle;
        this.dataLang = dataLang;
        this.dataImage = dataImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataTitle() {
        return dataTitle;
    }

    public void setDataTitle(String dataTitle) {
        this.dataTitle = dataTitle;
    }

    public String getDataLang() {
        return dataLang;
    }

    public void setDataLang(String dataLang) {
        this.dataLang = dataLang;
    }

    public String getDataImage() {
        return dataImage;
    }

    public void setDataImage(String dataImage) {
        this.dataImage = dataImage;
    }
}
