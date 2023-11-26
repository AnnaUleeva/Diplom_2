import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.client.OrderApiClient;
import site.nomoreparties.stellarburgers.model.*;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateOrderWithoutLoginUserTest {
    public CreateOrderRequest createOrderRequest;
    public OrderApiClient orderApiClient;

    @Before
    public void setUp() {
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
    @DisplayName("Создание заказа без авторизации пользователя")
    @Description("Создание заказа без авторизации пользователя")
    public void createOrderWithoutLoginUserTest() {
        Response createResponse = createOrder();
        checkStatusCreateOrderWithoutLoginUser(createResponse);
        CreateOrderResponse createOrderResponse = getCreateOrderResponse(createResponse);
        checkSuccessCreateOrderWithoutLoginUser(createOrderResponse);
    }

    @Step("Создание заказа без авторизации пользователя")
    public Response createOrder() {
        return orderApiClient.createOrder("", createOrderRequest);
    }

    @Step("Проверка статуса ответа")
    public void checkStatusCreateOrderWithoutLoginUser(Response createResponse) {
        assertEquals(SC_OK, createResponse.statusCode());
    }

    @Step("Преобразование ответа к классу CreateOrderResponse")
    public CreateOrderResponse getCreateOrderResponse(Response createResponse) {
        return createResponse.as(CreateOrderResponse.class);
    }

    @Step("Проверка Success, при создании заказа без авторизации пользователя")
    public void checkSuccessCreateOrderWithoutLoginUser(CreateOrderResponse createOrderResponse) {
        assertTrue(createOrderResponse.getSuccess());
    }
}