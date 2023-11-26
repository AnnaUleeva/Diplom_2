package site.nomoreparties.stellarburgers.model;

import java.util.ArrayList;

public class ReceivingUserOrdersResponse {
    Boolean success;
    ArrayList<ReceivingOrder> orders;
    int total;
    int totalToday;
    String message;

    public ReceivingUserOrdersResponse(Boolean success, ArrayList<ReceivingOrder> orders, int total, int totalToday, String message) {
        this.success = success;
        this.orders = orders;
        this.total = total;
        this.totalToday = totalToday;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
