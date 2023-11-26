package site.nomoreparties.stellarburgers.model;

public class GetUserResponse {
    Boolean success;
    UserData user;
    String message;

    public GetUserResponse(Boolean success, UserData user, String message) {
        this.success = success;
        this.user = user;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
