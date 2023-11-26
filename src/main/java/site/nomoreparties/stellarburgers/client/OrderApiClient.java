package site.nomoreparties.stellarburgers.client;

import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.model.CreateOrderRequest;

public class OrderApiClient extends BaseApiClient{
    final static String ORDER_CLIENT = "/api/orders";

    public Response createOrder(String accessToken, CreateOrderRequest createOrderRequest){
        return getPostSpec()
                .header("Authorization", accessToken)
                .body(createOrderRequest)
                .when()
                .post(ORDER_CLIENT);
    }
    public Response getOrder(String accessToken){
        return getPostSpec()
                .header("Authorization", accessToken)
                .when()
                .get(ORDER_CLIENT);
    }

}
