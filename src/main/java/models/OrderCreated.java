package models;

import com.google.gson.annotations.SerializedName;
import io.qameta.allure.internal.shadowed.jackson.annotation.JsonProperty;

import java.util.List;

public class OrderCreated {
    private List<Ingredient> ingredients;
    @SerializedName("_id")
    private String id;
    private String status;
    private Long number;
    private Double price;
    private String createdAt;
    private String updatedAt;
    private User owner;

    public OrderCreated(List<Ingredient> ingredients, String id, String status, Long number, Double price, String createdAt, String updatedAt, User owner) {
        this.ingredients = ingredients;
        this.id = id;
        this.status = status;
        this.number = number;
        this.owner = owner;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.price = price;
        this.owner = owner;
    }

    public OrderCreated(Long number) {
        this.number = number;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Double getPrice() {
        return price;
    }

    public void setNumber(Double price) {
        this.price = price;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
