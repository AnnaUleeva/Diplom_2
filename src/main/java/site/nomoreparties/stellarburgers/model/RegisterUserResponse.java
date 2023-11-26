package site.nomoreparties.stellarburgers.model;

public class RegisterUserResponse {
    Boolean success;
    String message;
    UserData user;
    String accessToken;
    String refreshToken;

    public RegisterUserResponse(Boolean success, String message, UserData user, String accessToken, String refreshToken) {
        this.success = success;
        this.message = message;
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
