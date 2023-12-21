package com.model;

public class Order extends Product {
    private int orderId;
    private int uid;
    private int quantity;
    private String date;

    public Order() {
    }

    public Order(int orderid, int uid, int quantity, String date) {
        this.orderId = orderid;
        this.uid = uid;
        this.quantity = quantity;
        this.date = date;
    }

    public Order(int uid, int quantity, String date) {
        this.uid = uid;
        this.quantity = quantity;
        this.date = date;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderid) {
        this.orderId = orderid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderid=" + orderId +
                ", uid=" + uid +
                ", quantity=" + quantity +
                ", date='" + date + '\'' +
                '}';
    }
}
