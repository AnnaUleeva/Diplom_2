import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.client.OrderApiClient;
import site.nomoreparties.stellarburgers.model.ReceivingUserOrdersResponse;


import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.assertEquals;

public class ReceivingUserOrderWithoutAuthorizationTest {
    public OrderApiClient orderApiClient;

    @Before
    public void setUp() {
        orderApiClient = new OrderApiClient();
    }

    @Test
    @DisplayName("Получение заказа неавторизованного пользователя")
    @Description("Получение заказа неавторизованного пользователя")
    public void receivingUserOrderWithoutAuthorizationTest() {
        Response getResponse = getOrder();
        checkStatusOrder(getResponse);
        ReceivingUserOrdersResponse getOrderResponse = getOrderResponse(getResponse);
        checkMessageGetOrderResponse(getOrderResponse);
    }

    @Step("Получение заказа неавторизованного пользователя")
    public Response getOrder() {
        return orderApiClient.getOrder("");
    }

    @Step("Проверка статуса ответа получения заказа")
    public void checkStatusOrder(Response getResponse) {
        assertEquals(SC_UNAUTHORIZED, getResponse.statusCode());
    }

    @Step("Преобразование ответа к модели ReceivingUserOrderResponse")
    public ReceivingUserOrdersResponse getOrderResponse(Response getResponse) {
        return getResponse.as(ReceivingUserOrdersResponse.class);
    }

    @Step("Проверка сообщения ошибки")
    public void checkMessageGetOrderResponse(ReceivingUserOrdersResponse getOrderResponse) {
        assertEquals("You should be authorised", getOrderResponse.getMessage());
    }
}
