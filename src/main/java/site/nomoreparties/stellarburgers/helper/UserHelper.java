package site.nomoreparties.stellarburgers.helper;

import site.nomoreparties.stellarburgers.client.UserApiClient;
import site.nomoreparties.stellarburgers.model.*;

import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;

public class UserHelper {
    static UserApiClient userApiClient = new UserApiClient();

    public static RegisterUserResponse create(RegisterUserRequest registerUserRequest){
        return userApiClient.registerUser(registerUserRequest).then().statusCode(SC_OK).and().extract().as(RegisterUserResponse.class);
    }
    public static LoginUserResponse login(LoginUserRequest loginUserRequest){
        return userApiClient.loginUser(loginUserRequest).then().statusCode(SC_OK).and().extract().as(LoginUserResponse.class);
    }

    public static DeleteUserResponse delete(String accessToken) {
        return userApiClient.deleteUser(accessToken).then().statusCode(SC_ACCEPTED).and().extract().as(DeleteUserResponse.class);

    }
    public static GetUserResponse get(String accessToken){
        return userApiClient.getUser(accessToken).then().statusCode(SC_OK).and().extract().as(GetUserResponse.class);
    }
}
