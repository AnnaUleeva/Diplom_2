import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.client.UserApiClient;
import site.nomoreparties.stellarburgers.helper.UserGenerator;
import site.nomoreparties.stellarburgers.helper.UserHelper;
import site.nomoreparties.stellarburgers.model.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.apache.http.HttpStatus.*;

public class LoginNonExistentUserTest {
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
    @DisplayName("Авторизация несуществующего пользователя")
    @Description("Авторизация несуществующего пользователя")
    public void loginNonExistentUserTest() {
        RegisterUserResponse registerUserResponse = registerUser();
        checkSuccessRegisterUser(registerUserResponse);
        LoginUserResponse loginUserResponse = loginUser();
        checkSuccessLoginUser(loginUserResponse);
        DeleteUserResponse deleteUserResponse = deleteUser(loginUserResponse.getAccessToken());
        checkSuccessDeleteUser(deleteUserResponse);

        Response loginNonExistentUser = loginNonExistentUser();
        checkStatusLoginUser(loginNonExistentUser);
        LoginUserResponse loginNonExistentUserResponse = getLoginNonExistentUserResponse(loginNonExistentUser);
        checkMessageLoginNonExistentUser(loginNonExistentUserResponse);
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
    public LoginUserResponse loginUser() {
        return UserHelper.login(loginUserRequest);
    }

    @Step("Проверка Success атворизации пользователя")
    public void checkSuccessLoginUser(LoginUserResponse loginUserResponse) {
        assertTrue(loginUserResponse.getSuccess());
    }

    @Step("Удаление созданного пользователя")
    public DeleteUserResponse deleteUser(String accessToken) {
        return UserHelper.delete(accessToken);
    }

    @Step("Проверка Success удаления пользователя")
    public void checkSuccessDeleteUser(DeleteUserResponse deleteUserResponse) {
        assertTrue(deleteUserResponse.getSuccess());
    }

    @Step("Авторизация несуществующего пользователя")
    public Response loginNonExistentUser() {
        return userApiClient.loginUser(loginUserRequest);
    }

    @Step("Проверка статуса ответа")
    public void checkStatusLoginUser(Response response) {
        assertEquals(SC_UNAUTHORIZED, response.statusCode());
    }

    @Step("Преобразование ответа к классу LoginUserResponse")
    public LoginUserResponse getLoginNonExistentUserResponse(Response response) {
        return response.as(LoginUserResponse.class);
    }

    @Step("Проверка сообщения ошибки")
    public void checkMessageLoginNonExistentUser(LoginUserResponse loginUserResponse) {
        assertEquals("email or password are incorrect", loginUserResponse.getMessage());
    }

}
