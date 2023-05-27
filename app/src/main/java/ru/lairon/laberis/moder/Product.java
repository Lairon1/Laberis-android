package ru.lairon.laberis.moder;

import android.graphics.Bitmap;

import java.util.Map;


public class Product {

    private Long id;
    private String title;
    private String description;
    private double price;
    private ProductType type;
    private Map<String, String> specifications;
    private Bitmap image;

    public Product(Long id, String title, String description, double price, ProductType type, Map<String, String> specifications, Bitmap image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.type = type;
        this.specifications = specifications;
        this.image = image;
    }

    public Product(Long id, String title, String description, double price, ProductType type, Map<String, String> specifications) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.type = type;
        this.specifications = specifications;
        this.image = image;
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public Map<String, String> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(Map<String, String> specifications) {
        this.specifications = specifications;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
