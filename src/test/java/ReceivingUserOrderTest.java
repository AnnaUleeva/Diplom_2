import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.client.OrderApiClient;
import site.nomoreparties.stellarburgers.helper.OrderHelper;
import site.nomoreparties.stellarburgers.helper.UserGenerator;
import site.nomoreparties.stellarburgers.helper.UserHelper;
import site.nomoreparties.stellarburgers.model.*;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReceivingUserOrderTest {
    public RegisterUserRequest registerUserRequest;
    public LoginUserRequest loginUserRequest;
    public OrderApiClient orderApiClient;
    public CreateOrderRequest createOrderRequest;

    @Before
    public void setUp() {
        registerUserRequest = UserGenerator.getRandomUser();
        loginUserRequest = new LoginUserRequest(registerUserRequest.getEmail(), registerUserRequest.getPassword());
        orderApiClient = new OrderApiClient();
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
    }

    @Test
    @DisplayName("Получение заказа авторизованного пользователя")
    @Description("Получение заказа авторизованного пользователя")
    public void receivingUserOrderTest() {
        RegisterUserResponse registerUserResponse = registerUser();
        checkSuccessRegisterUser(registerUserResponse);
        LoginUserResponse loginUserResponse = loginUser();
        checkSuccessLoginUser(loginUserResponse);

        CreateOrderResponse createResponse = createOrder(loginUserResponse);
        checkStatusCreateOrder(createResponse);

        Response getResponse = getOrder(loginUserResponse);
        checkStatusOrder(getResponse);
        ReceivingUserOrdersResponse getOrderResponse = getOrderResponse(getResponse);
        checkSuccessGetOrderResponse(getOrderResponse);
    }

    @Step("Регистрация пользователя")
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

    @Step("Создание заказа")
    public CreateOrderResponse createOrder(LoginUserResponse loginUserResponse) {
        return OrderHelper.create(loginUserResponse.getAccessToken(), createOrderRequest);
    }

    @Step("Проверка Success создания заказа")
    public void checkStatusCreateOrder(CreateOrderResponse createOrderResponse) {
        assertTrue(createOrderResponse.getSuccess());
    }

    @Step("Получение заказа")
    public Response getOrder(LoginUserResponse loginUserResponse) {
        return orderApiClient.getOrder(loginUserResponse.getAccessToken());
    }

    @Step("Проверка статуса ответа получения заказа")
    public void checkStatusOrder(Response getResponse) {
        assertEquals(SC_OK, getResponse.statusCode());
    }

    @Step("Преобразование ответа к модели ReceivingUserOrderResponse")
    public ReceivingUserOrdersResponse getOrderResponse(Response getResponse) {
        return getResponse.as(ReceivingUserOrdersResponse.class);
    }

    @Step("Проверка Success получения заказа")
    public void checkSuccessGetOrderResponse(ReceivingUserOrdersResponse getOrderResponse) {
        assertTrue(getOrderResponse.getSuccess());
    }
}
