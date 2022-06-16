package client;

import io.restassured.response.Response;
import models.IngredientsHashList;

public class TestMethods {

    private class StellarBurgerRestClient extends BaseRestClient {
        private final String BASE_URL = "https://stellarburgers.nomoreparties.site/api/";
        private final String INGREDIENTS_ENDPOINT = "ingredients";
        private final String ORDERS_ENDPOINT = "orders";
    }

    StellarBurgerRestClient client = new StellarBurgerRestClient();

    public Response getIngredients() {
        return client.getRequest(client.BASE_URL + client.INGREDIENTS_ENDPOINT);
    }

    public Response createOrder(IngredientsHashList ingredientsHashList) {
        return client.postRequest(client.BASE_URL + client.ORDERS_ENDPOINT, ingredientsHashList);
    }


}
