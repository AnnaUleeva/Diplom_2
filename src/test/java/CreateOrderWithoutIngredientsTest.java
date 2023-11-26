import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.client.OrderApiClient;
import site.nomoreparties.stellarburgers.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.junit.Assert.assertEquals;

public class CreateOrderWithoutIngredientsTest {
    public CreateOrderRequest createOrderRequest;
    public OrderApiClient orderApiClient;

    @Before
    public void setUp() {
        List<String> ingredients = new ArrayList<>();
        createOrderRequest = new CreateOrderRequest(ingredients);
        orderApiClient = new OrderApiClient();
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа без ингредиентов")
    public void createOrderWithoutIngredientsTest() {
        Response createResponse = createOrder();

        checkStatusCreateOrderWithoutIngredients(createResponse);

        CreateOrderResponse createOrderResponse = getCreateOrderResponse(createResponse);

        checkMessageCreateOrderWithoutIngredients(createOrderResponse);
    }

    @Step("Создание заказа без ингредиентов")
    public Response createOrder() {
        return orderApiClient.createOrder("", createOrderRequest);
    }

    @Step("Проверка статуса ответа")
    public void checkStatusCreateOrderWithoutIngredients(Response createResponse) {
        assertEquals(SC_BAD_REQUEST, createResponse.statusCode());
    }

    @Step("Преобразование ответа к классу CreateOrderResponse")
    public CreateOrderResponse getCreateOrderResponse(Response createResponse) {
        return createResponse.as(CreateOrderResponse.class);
    }

    @Step("Проверка сообщения ошибки")
    public void checkMessageCreateOrderWithoutIngredients(CreateOrderResponse createOrderResponse) {
        assertEquals("Ingredient ids must be provided", createOrderResponse.getMessage());
    }
}

