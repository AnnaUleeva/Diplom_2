package site.nomoreparties.stellarburgers.client;

import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.model.LoginUserRequest;
import site.nomoreparties.stellarburgers.model.RegisterUserRequest;
import site.nomoreparties.stellarburgers.model.UpdateUserRequest;

public class UserApiClient extends BaseApiClient {
    final static String USER_CLIENT = "/api/auth";

    public Response registerUser(RegisterUserRequest registerUserRequest) {
        return getPostSpec()
                .body(registerUserRequest)
                .when()
                .post(USER_CLIENT + "/register");
    }

    public Response loginUser(LoginUserRequest loginUserRequest){
        return getPostSpec()
                .body(loginUserRequest)
                .when()
                .post( USER_CLIENT + "/login");
    }

    public Response deleteUser(String accessToken){
        return getPostSpec()
                .header("Authorization", accessToken)
                .when()
                .delete(USER_CLIENT + "/user");
    }

    public Response getUser(String accessToken){
        return getPostSpec()
                .header("Authorization", accessToken)
                .when()
                .get(USER_CLIENT +"/user");
    }
    public Response updateUser(String accessToken, UpdateUserRequest updateUserRequest){
        return getPostSpec()
                .header("Authorization", accessToken)
                .body(updateUserRequest)
                .when()
                .patch(USER_CLIENT +"/user");
    }


}
