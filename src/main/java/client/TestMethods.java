package client;

import io.restassured.response.Response;
import models.IngredientsHashList;
import models.User;

public class TestMethods {

    private class StellarBurgerRestClient extends BaseRestClient {
        private final String BASE_URL = "https://stellarburgers.nomoreparties.site/api/";
        private final String INGREDIENTS_ENDPOINT = "ingredients";
        private final String ORDERS_ENDPOINT = "orders";
        private final String USER_ENDPOINT = "auth/user";
        private final String REGISTER_ENDPOINT = "auth/register";
        private final String AUTH_ENDPOINT = "auth/login";
        private final String TOKEN_ENDPOINT = "auth/token";
    }

    StellarBurgerRestClient client = new StellarBurgerRestClient();

    public Response getIngredients() {
        return client.getRequest(client.BASE_URL + client.INGREDIENTS_ENDPOINT);
    }

    public Response createOrder(IngredientsHashList ingredientsHashList) {
        return client.postRequest(client.BASE_URL + client.ORDERS_ENDPOINT, ingredientsHashList);
    }

    public Response createUser(User user) {
        return client.postRequest(client.BASE_URL + client.REGISTER_ENDPOINT, user);
    }

    public Response loginUser(User user) {
        return client.postRequest(client.BASE_URL + client.AUTH_ENDPOINT, user);
    }

    public Response getUserInfo(String token) {
        return client.getRequest(client.BASE_URL + client.USER_ENDPOINT, token);
    }
}
