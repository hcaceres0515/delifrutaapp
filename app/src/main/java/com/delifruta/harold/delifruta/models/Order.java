package com.delifruta.harold.delifruta.models;

import java.util.Date;
import java.util.List;

/**
 * Created by harold on 1/23/18.
 */

public class Order {

    private int id;
    private User user;
    private boolean shipping;
    private int distritoId;
    private String shippingName;
    private String shippingPhone;
    private String shippingAddress;
    private String addressReference;
    private double shippingPrice;
    private Date deliveryDate;
    private Date deliveryHour;
    private double totalOrder;
    private String comments;
    private Date createdAt;
    private int status;

    public Order() {
    }



    public Order(int id, boolean shipping, int distritoId, String shippingName, String shippingPhone, String shippingAddress, String addressReference, double shippingPrice, Date deliveryDate, double totalOrder, String comments, Date createdAt) {
        this.id = id;
        this.shipping = shipping;
        this.distritoId = distritoId;
        this.shippingName = shippingName;
        this.shippingPhone = shippingPhone;
        this.shippingAddress = shippingAddress;
        this.addressReference = addressReference;
        this.shippingPrice = shippingPrice;
        this.deliveryDate = deliveryDate;
        //this.deliveryHour = deliveryHour;
        this.totalOrder = totalOrder;
        this.comments = comments;
        this.createdAt = createdAt;
    }

    public Order(int id, double totalOrder, Date createdAt) {
        this.id = id;
        this.totalOrder = totalOrder;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isShipping() {
        return shipping;
    }

    public void setShipping(boolean shipping) {
        this.shipping = shipping;
    }

    public int getDistritoId() {
        return distritoId;
    }

    public void setDistritoId(int distritoId) {
        this.distritoId = distritoId;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getShippingPhone() {
        return shippingPhone;
    }

    public void setShippingPhone(String shippingPhone) {
        this.shippingPhone = shippingPhone;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getAddressReference() {
        return addressReference;
    }

    public void setAddressReference(String addressReference) {
        this.addressReference = addressReference;
    }

    public double getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(double shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getDeliveryHour() {
        return deliveryHour;
    }

    public void setDeliveryHour(Date deliveryHour) {
        this.deliveryHour = deliveryHour;
    }

    public double getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(double totalOrder) {
        this.totalOrder = totalOrder;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        createdAt = createdAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
