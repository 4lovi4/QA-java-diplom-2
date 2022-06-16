package models;

import java.util.List;

public class IngredientsHashList {
    private List<String> ingredients;

    public IngredientsHashList(List<String> ingredientsHashList) {
        this.ingredients = ingredientsHashList;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
