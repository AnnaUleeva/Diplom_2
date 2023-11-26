import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.client.UserApiClient;
import site.nomoreparties.stellarburgers.helper.UserGenerator;
import site.nomoreparties.stellarburgers.helper.UserHelper;
import site.nomoreparties.stellarburgers.model.RegisterUserRequest;
import site.nomoreparties.stellarburgers.model.RegisterUserResponse;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegisterUserTest {
    private RegisterUserRequest registerUserRequest;
    private UserApiClient userApiClient;


    @Before
    public void setUp() {
        registerUserRequest = UserGenerator.getRandomUser();
        userApiClient = new UserApiClient();
    }

    @Test
    @DisplayName("Регистрация пользователя")
    @Description("Регистрация пользователя")
    public void registerUserTest() {
        Response createResponse = createUser();
        checkStatusCode(createResponse);
        RegisterUserResponse registerUserResponse = getUserResponse(createResponse);
        checkSuccess(registerUserResponse);
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

    @Step("Проверка success регистрации пользователя")
    public void checkSuccess(RegisterUserResponse registerUserResponse) {
        assertTrue(registerUserResponse.getSuccess());
    }


}
