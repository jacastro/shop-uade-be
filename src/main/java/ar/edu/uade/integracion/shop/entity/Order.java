package ar.edu.uade.integracion.shop.entity;

import java.util.Date;

public class Order {

    private Item item;
    private Integer quantity;
    private float total;
    private Integer shippingId;
    private Address address;
    private User buyer;
    private Date date;

    public Order(Item item, Integer quantity, Address address, User buyer) {
        this.item = item;
        this.quantity = quantity;
        this.address = address;
        this.buyer = buyer;
        this.total = this.item.getPrice() * this.quantity;
        this.date  = new Date();
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Integer getShippingId() {
        return shippingId;
    }

    public void setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
