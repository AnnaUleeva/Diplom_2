import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.client.UserApiClient;
import site.nomoreparties.stellarburgers.helper.UserGenerator;
import site.nomoreparties.stellarburgers.helper.UserHelper;
import site.nomoreparties.stellarburgers.model.LoginUserRequest;
import site.nomoreparties.stellarburgers.model.LoginUserResponse;
import site.nomoreparties.stellarburgers.model.RegisterUserRequest;
import site.nomoreparties.stellarburgers.model.RegisterUserResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.apache.http.HttpStatus.*;

public class LoginUserTest {
    RegisterUserRequest registerUserRequest;
    LoginUserRequest loginUserRequest;
    UserApiClient userApiClient;

    @Before
    public void setUp() {
        registerUserRequest = UserGenerator.getRandomUser();
        loginUserRequest = new LoginUserRequest(registerUserRequest.getEmail(), registerUserRequest.getPassword());
        userApiClient = new UserApiClient();
    }

    @Test
    @DisplayName("Авторизация пользователя")
    @Description("Авторизация пользователя")
    public void loginUserTest() {
        RegisterUserResponse registerUserResponse = registerUser();
        checkSuccessRegisterUser(registerUserResponse);
        Response loginResponse = loginUser();
        checkLoginUserStatus(loginResponse);
        LoginUserResponse loginUserResponse = getLoginUserResponse(loginResponse);
        checkSuccessLoginUser(loginUserResponse);
        UserHelper.delete(registerUserResponse.getAccessToken());
    }

    @Step("Создание пользователя")
    public RegisterUserResponse registerUser() {
        return UserHelper.create(registerUserRequest);
    }

    @Step("Проверка Success создания пользователя")
    public void checkSuccessRegisterUser(RegisterUserResponse registerUserResponse) {
        assertTrue(registerUserResponse.getSuccess());
    }

    @Step("Авторизация пользователя")
    public Response loginUser() {
        return userApiClient.loginUser(loginUserRequest);
    }

    @Step("Проверка статуса ответа авторизации пользователя")
    public void checkLoginUserStatus(Response response) {
        assertEquals(SC_OK, response.statusCode());
    }

    @Step("Преобразование ответа к классу LoginUserResponse")
    public LoginUserResponse getLoginUserResponse(Response response) {
        return response.as(LoginUserResponse.class);
    }

    @Step("Проверка Success авторизации пользователя")
    public void checkSuccessLoginUser(LoginUserResponse loginUserResponse) {
        assertTrue(loginUserResponse.getSuccess());
    }
}
