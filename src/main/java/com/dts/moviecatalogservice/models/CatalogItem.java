package com.dts.moviecatalogservice.models;

public class CatalogItem {
    private String name;
    private String desc;
    private int raiting;

    public CatalogItem(String name, String desc, int raiting) {
        this.name = name;
        this.desc = desc;
        this.raiting = raiting;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getRaiting() {
        return raiting;
    }

    public void setRaiting(int raiting) {
        this.raiting = raiting;
    }
}
