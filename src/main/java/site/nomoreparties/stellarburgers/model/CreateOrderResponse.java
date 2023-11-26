package site.nomoreparties.stellarburgers.model;

public class CreateOrderResponse {
    String name;
    CreatedOrderData order;
    Boolean success;
    String message;

    public CreateOrderResponse(String name, CreatedOrderData order, Boolean success, String message) {
        this.name = name;
        this.order = order;
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
