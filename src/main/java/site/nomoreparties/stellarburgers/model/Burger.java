package site.nomoreparties.stellarburgers.model;

import java.util.ArrayList;
import java.util.List;

public class Burger {
    Bun bun;
    List<Ingredient> ingredients = new ArrayList<>();

    public void addBun(Bun bun) {
        this.bun = bun;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public List<String> getIngredients() {
        List<String> order = new ArrayList<>();
        order.add(bun.getHash());
        for (Ingredient ingredient : ingredients) {
            order.add(ingredient.getHash());
        }
        order.add(bun.getHash());
        return order;
    }
}
