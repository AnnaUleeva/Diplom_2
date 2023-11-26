import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.client.UserApiClient;
import site.nomoreparties.stellarburgers.helper.UserGenerator;
import site.nomoreparties.stellarburgers.model.RegisterUserRequest;
import site.nomoreparties.stellarburgers.model.RegisterUserResponse;

import static org.junit.Assert.assertEquals;
import static org.apache.http.HttpStatus.*;

public class RegisterUserWithoutRequiredFieldTest {
    RegisterUserRequest registerUserRequest;
    UserApiClient userApiClient;


    @Before
    public void setUp() {
        RegisterUserRequest randomUser = UserGenerator.getRandomUser();
        registerUserRequest = new RegisterUserRequest(randomUser.getEmail(), randomUser.getPassword(), "");
        userApiClient = new UserApiClient();
    }

    @Test
    @DisplayName("Регистрация пользователя без обязательного поля")
    @Description("Регистрация пользователя без обязательного поля")
    public void registerUserWithoutRequiredFieldTest() {
        Response registerResponse = registerUser();
        checkRegisterUserStatus(registerResponse);
        RegisterUserResponse registerUserResponse = getRegisterUserResponse(registerResponse);
        checkMessageRegisterUser(registerUserResponse);
    }

    @Step("Создание пользователя без обязательного поля")
    public Response registerUser() {
        return userApiClient.registerUser(registerUserRequest);
    }

    @Step("Проверка статуса ответа")
    public void checkRegisterUserStatus(Response response) {
        assertEquals(SC_FORBIDDEN, response.statusCode());
    }

    @Step("Преобразование ответа к классу ReqisterUserResponse")
    public RegisterUserResponse getRegisterUserResponse(Response response) {
        return response.as(RegisterUserResponse.class);
    }

    @Step("Проверка текста сообщения ошибки")
    public void checkMessageRegisterUser(RegisterUserResponse registerUserResponse) {
        assertEquals("Email, password and name are required fields", registerUserResponse.getMessage());
    }
}
