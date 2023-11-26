package site.nomoreparties.stellarburgers.model;

public class Ingredient {
    IngredientType type;
    String name;
    String hash;

    public Ingredient(IngredientType type, String name, String hash) {
        this.type = type;
        this.name = name;
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }
}