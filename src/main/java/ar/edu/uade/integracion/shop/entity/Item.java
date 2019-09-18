package ar.edu.uade.integracion.shop.entity;

import javax.persistence.Id;
import java.util.List;

public class Item {

    @Id
    private int id;
    private User seller;
    private float price;
    private String name;
    private String description;
    private List<String> photos;
    private float weight;
    private Warranty warranty;
    private Category category;

    public Item(int id, User seller, float price, String name, String description, float weight) {
        this.id = id;
        this.seller = seller;
        this.price = price;
        this.name = name;
        this.description = description;
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}
