package site.nomoreparties.stellarburgers.model;

public class RegisterUserRequest {
    String email;
    String password;
    String name;

    public RegisterUserRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
