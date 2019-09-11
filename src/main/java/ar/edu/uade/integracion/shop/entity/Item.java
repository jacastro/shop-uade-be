package ar.edu.uade.integracion.shop.entity;

import javax.persistence.Id;
import java.util.List;

public class Item {

    @Id
    private int id;
    private User publisher;
    private float price;
    private String name;
    private String description;
    private List<String> photos;


    public Item(int id, User publisher, float price, String name, String description, List<String> photos) {
        this.id = id;
        this.publisher = publisher;
        this.price = price;
        this.name = name;
        this.description = description;
        this.photos = photos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
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
