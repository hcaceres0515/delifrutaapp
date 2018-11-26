package com.delifruta.harold.delifruta.models;

/**
 * Created by harold on 4/14/18.
 */

public class OrderDetail {

    private int id;
    private int prodCod;
    private String prodNom;
    private int cantPro;
    private double pricePro;
    private String imgUrl;
    private int status;

    public OrderDetail() {
    }

    public OrderDetail(int id, int prodCod) {
        this.id = id;
        this.prodCod = prodCod;
    }

    public OrderDetail(int prodCod, int id, int cantPro, double pricePro, int status) {
        this.prodCod = prodCod;
        this.id = id;
        this.cantPro = cantPro;
        this.pricePro = pricePro;
        this.status = status;
    }

    public OrderDetail(int id, int prodCod, String prodNom, int cantPro, double pricePro, String imgUrl) {
        this.id = id;
        this.prodCod = prodCod;
        this.prodNom = prodNom;
        this.cantPro = cantPro;
        this.pricePro = pricePro;
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProdCod() {
        return prodCod;
    }

    public void setProdCod(int prodCod) {
        prodCod = prodCod;
    }

    public String getProdNom() {
        return prodNom;
    }

    public void setProdNom(String prodNom) {
        this.prodNom = prodNom;
    }

    public int getCantPro() {
        return cantPro;
    }

    public void setCantPro(int cantPro) {
        this.cantPro = cantPro;
    }

    public double getPricePro() {
        return pricePro;
    }

    public void setPricePro(float pricePro) {
        this.pricePro = pricePro;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
