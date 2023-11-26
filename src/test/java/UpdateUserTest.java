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

public class UpdateUserTest {
    private RegisterUserRequest registerUserRequest;
    private UpdateUserRequest updateUserRequest;
    private UserApiClient userApiClient;

    @Before
    public void setUp() {
        registerUserRequest = UserGenerator.getRandomUser();
        updateUserRequest = UserGenerator.getUpdateRandomUser();
        userApiClient = new UserApiClient();

    }

    @Test
    @DisplayName("Проверка изменений данных")
    @Description("Изменение данных у авторизованного пользователя")
    public void updateUserTest() {
        RegisterUserResponse registerUserResponse = registerUser();
        checkSuccessRegisterUser(registerUserResponse);
        GetUserResponse getUserResponse = getUser(registerUserResponse);
        checkSuccessGetUser(getUserResponse);
        Response updateResponse = updateUser(registerUserResponse.getAccessToken());
        checkStatusUpdateUser(updateResponse);
        UpdateUserResponse updateUserResponse = getUpdateUserResponse(updateResponse);
        checkSuccessUpdateUser(updateUserResponse);
        UserHelper.delete(registerUserResponse.getAccessToken());
    }

    @Step("Создание пользователя")
    public RegisterUserResponse registerUser() {
        return UserHelper.create(registerUserRequest);
    }

    @Step("Проверка Success создание пользователя")
    public void checkSuccessRegisterUser(RegisterUserResponse registerUserResponse) {
        assertTrue(registerUserResponse.getSuccess());
    }

    @Step("Получение данных пользователя")
    public GetUserResponse getUser(RegisterUserResponse registerUserResponse) {
        return UserHelper.get(registerUserResponse.getAccessToken());
    }

    @Step("Проверка Success о получении данных")
    public void checkSuccessGetUser(GetUserResponse getUserResponse) {
        assertTrue(getUserResponse.getSuccess());
    }

    @Step("Изменение данных пользователя")
    public Response updateUser(String accessToken) {
        return userApiClient.updateUser(accessToken, updateUserRequest);
    }

    @Step("Проверка статуса ответа обновления данных пользователя")
    public void checkStatusUpdateUser(Response updateResponse) {
        assertEquals(SC_OK, updateResponse.statusCode());
    }

    @Step("Преобразование ответа к классу UpdateUserResponse")
    public UpdateUserResponse getUpdateUserResponse(Response updateResponse) {
        return updateResponse.as(UpdateUserResponse.class);
    }

    @Step("Проверка Success обновления данных пользователя")
    public void checkSuccessUpdateUser(UpdateUserResponse updateUserResponse) {
        assertTrue(updateUserResponse.getSuccess());
    }

}
