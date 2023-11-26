package site.nomoreparties.stellarburgers.model;

public class LoginUserResponse {
    Boolean success;
    String accessToken;
    String refreshToken;
    UserData user;
    String message;

    public LoginUserResponse(Boolean success, String accessToken, String refreshToken, UserData user, String message) {
        this.success = success;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
        this.message = message;
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

