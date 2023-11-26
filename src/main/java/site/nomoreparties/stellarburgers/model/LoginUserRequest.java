package site.nomoreparties.stellarburgers.model;

public class LoginUserRequest {
    String email;
    String password;

    public LoginUserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
