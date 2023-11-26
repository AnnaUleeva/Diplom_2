import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.client.UserApiClient;
import site.nomoreparties.stellarburgers.helper.UserGenerator;
import site.nomoreparties.stellarburgers.model.*;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.assertEquals;

public class UpdateUserWithoutAuthorizationTest {
    private UpdateUserRequest updateUserRequest;
    private UserApiClient userApiClient;

    @Before
    public void setUp() {
        updateUserRequest = UserGenerator.getUpdateRandomUser();
        userApiClient = new UserApiClient();
    }

    @Test
    @DisplayName("Изменение данных у неавторизованного пользователя")
    @Description("Изменение данных у неавторизованного пользователя")
    public void updateUserWithoutAuthorizationTest() {
        Response updateResponse = updateUser();
        checkStatusUpdateUser(updateResponse);
        UpdateUserResponse updateUserResponse = getUpdateUserResponse(updateResponse);
        checkMessageUpdateUser(updateUserResponse);
    }

    @Step("Изменение данных пользователя")
    public Response updateUser() {
        return userApiClient.updateUser("", updateUserRequest);
    }

    @Step("Проверка статуса овтета обновления данных пользователя")
    public void checkStatusUpdateUser(Response updateResponse) {
        assertEquals(SC_UNAUTHORIZED, updateResponse.statusCode());
    }

    @Step("Преобразование ответа к классу UpdateUserResponse")
    public UpdateUserResponse getUpdateUserResponse(Response updateResponse) {
        return updateResponse.as(UpdateUserResponse.class);
    }

    @Step("Проверка сообщения об ошибке")
    public void checkMessageUpdateUser(UpdateUserResponse updateUserResponse) {
        assertEquals("You should be authorised", updateUserResponse.getMessage());
    }

}
