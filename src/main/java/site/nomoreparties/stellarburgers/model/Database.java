package site.nomoreparties.stellarburgers.model;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private final List<Bun> buns = new ArrayList<>();
    private final List<Ingredient> sauces = new ArrayList<>();
    private final List<Ingredient> fillings = new ArrayList<>();

    public Database() {
        // булочки
        buns.add(new Bun("Флюоресцентная булка R2-D3", "61c0c5a71d1f82001bdaaa6d"));
        buns.add(new Bun("Краторная булка N-200i", "61c0c5a71d1f82001bdaaa6c"));

        // соусы
        sauces.add(new Ingredient(IngredientType.SAUCE, "Соус Spicy-X", "61c0c5a71d1f82001bdaaa72"));
        sauces.add(new Ingredient(IngredientType.SAUCE, "Соус фирменный Space Sauce", "61c0c5a71d1f82001bdaaa73"));

        // начинки
        fillings.add(new Ingredient(IngredientType.FILLING, "Мясо бессмертных моллюсков Protostomia", "61c0c5a71d1f82001bdaaa6f"));
        fillings.add(new Ingredient(IngredientType.FILLING, "Говяжий метеорит (отбивная)", "61c0c5a71d1f82001bdaaa70"));
        fillings.add(new Ingredient(IngredientType.FILLING, "Биокотлета из марсианской Магнолии", "61c0c5a71d1f82001bdaaa71"));
        fillings.add(new Ingredient(IngredientType.FILLING, "Филе Люминесцентного тетраодонтимформа", "61c0c5a71d1f82001bdaaa6e"));
    }

    public List<Bun> availableBuns() {
        return buns;
    }

    public List<Ingredient> availableSauces() {
        return sauces;
    }

    public List<Ingredient> availableFillings() {
        return fillings;
    }
}
