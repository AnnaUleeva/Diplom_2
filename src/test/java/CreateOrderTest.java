import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.client.OrderApiClient;
import site.nomoreparties.stellarburgers.helper.UserGenerator;
import site.nomoreparties.stellarburgers.helper.UserHelper;
import site.nomoreparties.stellarburgers.model.*;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.apache.http.HttpStatus.*;

public class CreateOrderTest {
    public RegisterUserRequest registerUserRequest;
    public LoginUserRequest loginUserRequest;
    public CreateOrderRequest createOrderRequest;
    public OrderApiClient orderApiClient;

    @Before
    public void setUp() {
        registerUserRequest = UserGenerator.getRandomUser();
        loginUserRequest = new LoginUserRequest(registerUserRequest.getEmail(), registerUserRequest.getPassword());
        Burger burger = new Burger();
        Database database = new Database();
        Bun bun = database.availableBuns().get(0);
        burger.addBun(bun);
        List<Ingredient> availableSauces = database.availableSauces();
        List<Ingredient> availableFillings = database.availableFillings();
        burger.addIngredient(availableSauces.get(0));
        burger.addIngredient(availableFillings.get(0));
        burger.addIngredient(availableSauces.get(1));
        burger.addIngredient(availableFillings.get(1));
        burger.addIngredient(availableFillings.get(2));
        createOrderRequest = new CreateOrderRequest(burger.getIngredients());
        orderApiClient = new OrderApiClient();
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа с авторизованным пользователем")
    public void createOrderWithLoginUserTest() {
        RegisterUserResponse registerUserResponse = registerUser();
        checkSuccessRegisterUser(registerUserResponse);
        LoginUserResponse loginUserResponse = loginUser();
        checkSuccessLoginUser(loginUserResponse);

        Response createResponse = createOrder(loginUserResponse);
        checkStatusCreateOrder(createResponse);
        CreateOrderResponse createOrderResponse = getCreateOrderResponse(createResponse);
        checkSuccessCreateOrder(createOrderResponse);

        UserHelper.delete(loginUserResponse.getAccessToken());
    }

    @Step("Создание пользователя")
    public RegisterUserResponse registerUser() {
        return UserHelper.create(registerUserRequest);
    }

    @Step("Проверка Success регистрации пользователя")
    public void checkSuccessRegisterUser(RegisterUserResponse registerUserResponse) {
        assertTrue(registerUserResponse.getSuccess());
    }

    @Step("Авторизация пользователя")
    public LoginUserResponse loginUser() {
        return UserHelper.login(loginUserRequest);
    }

    @Step("Проверка Success авторизации пользователя")
    public void checkSuccessLoginUser(LoginUserResponse loginUserResponse) {
        assertTrue(loginUserResponse.getSuccess());
    }

    @Step("Создание заказа с токеном авторизации")
    public Response createOrder(LoginUserResponse loginUserResponse) {
        return orderApiClient.createOrder(loginUserResponse.getAccessToken(), createOrderRequest);
    }

    @Step("Проверка статуса ответа")
    public void checkStatusCreateOrder(Response creteResponse) {
        assertEquals(SC_OK, creteResponse.statusCode());
    }

    @Step("Преобразование ответа к классу CreateOrderResponse")
    public CreateOrderResponse getCreateOrderResponse(Response createResponse) {
        return createResponse.as(CreateOrderResponse.class);
    }

    @Step("Проверка Success создания заказа")
    public void checkSuccessCreateOrder(CreateOrderResponse createOrderResponse) {
        assertTrue(createOrderResponse.getSuccess());
    }
}
