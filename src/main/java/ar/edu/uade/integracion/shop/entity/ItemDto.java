package ar.edu.uade.integracion.shop.entity;

import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel(description = "Item details")
public class ItemDto {

    private Integer id;
    private UserDto seller;
    private Double price;
    private String name;
    private String description;
    private List<String> photos;
    private Warranty warranty;
    private Category category;
    private Double weight;

    public Warranty getWarranty() {
        return warranty;
    }
    
    public void setWarranty(Warranty warranty) {
        this.warranty = warranty;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDto getSeller() {
        return seller;
    }

    public void setSeller(UserDto seller) {
        this.seller = seller;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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
