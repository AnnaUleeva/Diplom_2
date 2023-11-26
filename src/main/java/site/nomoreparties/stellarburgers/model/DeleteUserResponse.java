package site.nomoreparties.stellarburgers.model;

public class DeleteUserResponse {
    Boolean success;
    String message;


    public DeleteUserResponse(Boolean success, String message) {
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
