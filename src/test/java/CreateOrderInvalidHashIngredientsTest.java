import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.client.OrderApiClient;
import site.nomoreparties.stellarburgers.model.*;

import java.util.List;

import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.junit.Assert.assertEquals;

public class CreateOrderInvalidHashIngredientsTest {
    public CreateOrderRequest createOrderRequest;
    public OrderApiClient orderApiClient;

    @Before
    public void setUp() {
        Burger burger = new Burger();
        Database database = new Database();
        Ingredient invalidIngredient = new Ingredient(IngredientType.FILLING, "Пирожок", "InvalidHash");
        Bun bun = database.availableBuns().get(0);
        burger.addBun(bun);
        List<Ingredient> availableSauces = database.availableSauces();
        burger.addIngredient(availableSauces.get(0));
        burger.addIngredient(invalidIngredient);

        createOrderRequest = new CreateOrderRequest(burger.getIngredients());
        orderApiClient = new OrderApiClient();
    }

    @Test
    @DisplayName("Создание заказа с невалидным хэшом игредиента")
    @Description("Создание заказа с невалидным хэшом игредиента")
    public void createOrderInvalidHashIngredientsTest() {
        Response createResponse = createOrder();
        checkStatusCreateOrderInvalidHashIngredients(createResponse);
    }

    @Step("Создание заказа с невалидным хэшом")
    public Response createOrder() {
        return orderApiClient.createOrder("", createOrderRequest);
    }

    @Step("Проверка статуса ответа создания заказа")
    public void checkStatusCreateOrderInvalidHashIngredients(Response createResponse) {
        assertEquals(SC_INTERNAL_SERVER_ERROR, createResponse.statusCode());
    }
}
