package site.nomoreparties.stellarburgers.model;

public class UpdateUserRequest {
    String email;
    String name;

    public UpdateUserRequest(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
