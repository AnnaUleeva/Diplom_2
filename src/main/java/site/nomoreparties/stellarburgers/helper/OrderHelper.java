package site.nomoreparties.stellarburgers.helper;

import site.nomoreparties.stellarburgers.client.OrderApiClient;
import site.nomoreparties.stellarburgers.model.CreateOrderRequest;
import site.nomoreparties.stellarburgers.model.CreateOrderResponse;

import static org.apache.http.HttpStatus.SC_OK;

public class OrderHelper {
    static OrderApiClient orderApiClient = new OrderApiClient();
    public static CreateOrderResponse create(String accessToken, CreateOrderRequest createOrderRequest){
        return orderApiClient.createOrder(accessToken, createOrderRequest).then().statusCode(SC_OK).and().extract().as(CreateOrderResponse.class);
    }
}
