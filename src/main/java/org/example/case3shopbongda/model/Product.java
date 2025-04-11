package org.example.case3shopbongda.model;

public class Product {
    private String id;
    private String name;
    private double price;
    private String origin;
    private String imageUrl;
    private String category;

    public Product() {
    }

    public Product(String id, String name, double price, String origin, String imageUrl, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.origin = origin;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
