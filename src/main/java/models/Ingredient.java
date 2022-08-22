package models;

public class Ingredient {
    private String _id;
    private String name;
    private String type;
    private Long proteins;
    private Long fat;
    private Long carbohydrates;
    private Long calories;
    private Long price;
    private String image;
    private String image_mobile;
    private String image_large;
    private float __v;

    public Ingredient(String _id, String name, String type, Long proteins,
                      Long fat, Long carbohydrates, Long calories, Long price,
                      String image, String image_mobile, String image_large)
    {
        this._id = _id;
        this.name = name;
        this.type = type;
        this.proteins = proteins;
        this.fat = fat;
        this.calories = calories;
        this.carbohydrates = carbohydrates;
        this.price = price;
        this.image = image;
        this.image_mobile = image_mobile;
        this.image_large = image_large;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_mobile() {
        return image_mobile;
    }

    public void setImage_mobile(String image_mobile) {
        this.image_mobile = image_mobile;
    }

    public String getImage_large() {
        return image_large;
    }

    public void setImage_large(String image_large) {
        this.image_large = image_large;
    }

    public float get__v() {
        return __v;
    }

    public void set__v(float __v) {
        this.__v = __v;
    }
}
