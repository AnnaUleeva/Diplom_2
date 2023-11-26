package site.nomoreparties.stellarburgers.model;

import java.util.ArrayList;

public class ReceivingOrder {
    ArrayList<String> ingredients;
    String _id;
    String status;
    int number;
    String createdAt;
    String updatedAt;

    public ReceivingOrder(ArrayList<String> ingredients, String _id, String status, int number, String createdAt, String updatedAt) {
        this.ingredients = ingredients;
        this._id = _id;
        this.status = status;
        this.number = number;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
