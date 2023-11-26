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

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegistrationCreatedUserTest {
    private RegisterUserRequest registerUserRequest;
    private UserApiClient userApiClient;
    private LoginUserRequest loginUserRequest;

    @Before
    public void setUp() {
        registerUserRequest = UserGenerator.getRandomUser();
        userApiClient = new UserApiClient();
        loginUserRequest = new LoginUserRequest(registerUserRequest.getEmail(), registerUserRequest.getPassword());
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    @Description("Создание пользователя, который уже зарегистрирован")
    public void registrationCreatedUserTest() {
        Response createResponseOne = createUser();
        checkStatusCode(createResponseOne);
        RegisterUserResponse registerUserResponse = getUserResponse(createResponseOne);
        checkSuccess(registerUserResponse);

        LoginUserResponse loginUserResponse = loginUser();
        checkLoginResponse(loginUserResponse);

        Response createResponseTwo = createSecondUser();
        checkCreateSecondUserStatusCode(createResponseTwo);
        RegisterUserResponse registerUserResponseTwo = getUserResponse(createResponseTwo);
        checkMessageCreateSecondUser(registerUserResponseTwo);

        UserHelper.delete(registerUserResponse.getAccessToken());
    }

    @Step("Создание пользователя")
    public Response createUser() {
        return userApiClient.registerUser(registerUserRequest);
    }

    @Step("Проверка статуса ответа создания пользователя")
    public void checkStatusCode(Response createResponse) {
        assertEquals(SC_OK, createResponse.statusCode());
    }

    @Step("Преобразование ответа к модели RegisterUserResponse")
    public RegisterUserResponse getUserResponse(Response createResponse) {
        return createResponse.as(RegisterUserResponse.class);
    }

    @Step("Проверка success создания пользователя")
    public void checkSuccess(RegisterUserResponse registerUserResponse) {
        assertTrue(registerUserResponse.getSuccess());
    }

    @Step("Авторизация ранее созданного пользователя")
    public LoginUserResponse loginUser() {
        return UserHelper.login(loginUserRequest);
    }

    @Step("Проверка Success авторизации")
    public void checkLoginResponse(LoginUserResponse loginUserResponse) {
        assertTrue(loginUserResponse.getSuccess());
    }

    @Step("Регистрация пользователя с данными ранее созданного пользователя")
    public Response createSecondUser() {
        return userApiClient.registerUser(registerUserRequest);
    }

    @Step("Проверка статуса ответа")
    public void checkCreateSecondUserStatusCode(Response createResponseTwo) {
        assertEquals(SC_FORBIDDEN, createResponseTwo.statusCode());
    }

    @Step("Проверка сообщения об ошибке")
    public void checkMessageCreateSecondUser(RegisterUserResponse registerUserResponse) {
        assertEquals("User already exists", registerUserResponse.getMessage());
    }
}
