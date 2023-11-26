package site.nomoreparties.stellarburgers.model;

import java.util.List;

public class CreateOrderRequest {
    List<String> ingredients;

    public CreateOrderRequest(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
