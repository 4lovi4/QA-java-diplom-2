package models;
import com.google.gson.annotations.SerializedName;


public class Ingredient {
    @SerializedName("_id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private String type;
    @SerializedName("proteins")
    private Long proteins;
    @SerializedName("fat")
    private Long fat;
    @SerializedName("carbohydrates")
    private Long carbohydrates;
    @SerializedName("calories")
    private Long calories;
    @SerializedName("price")
    private Double price;
    @SerializedName("image")
    private String image;
    @SerializedName("image_mobile")
    private String imageMobile;
    @SerializedName("image_large")
    private String imageLarge;
    @SerializedName("__v")
    private float v;

    public Ingredient(String id, String name, String type, Long proteins,
                      Long fat, Long carbohydrates, Long calories, Double price,
                      String image, String imageMobile, String imageLarge)
    {
        this.id = id;
        this.name = name;
        this.type = type;
        this.proteins = proteins;
        this.fat = fat;
        this.calories = calories;
        this.carbohydrates = carbohydrates;
        this.price = price;
        this.image = image;
        this.imageMobile = imageMobile;
        this.imageLarge = imageLarge;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getProteins() {
        return proteins;
    }

    public void setProteins(Long proteins) {
        this.proteins = proteins;
    }

    public Long getFat() {
        return fat;
    }

    public void setFat(Long fat) {
        this.fat = fat;
    }

    public Long getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(Long carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public Long getCalories() {
        return calories;
    }

    public void setCalories(Long calories) {
        this.calories = calories;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageMobile() {
        return imageMobile;
    }

    public void setImageMobile(String imageMobile) {
        this.imageMobile = imageMobile;
    }

    public String getImageLarge() {
        return imageLarge;
    }

    public void setImageLarge(String imageLarge) {
        this.imageLarge = imageLarge;
    }

    public float getV() {
        return v;
    }

    public void setV(float v) {
        this.v = v;
    }
}
