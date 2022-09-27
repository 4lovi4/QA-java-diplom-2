package client;

import io.restassured.response.Response;
import models.IngredientsHashList;
import models.Token;
import models.User;
import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class TestMethods     {

    private class StellarBurgerRestClient extends BaseRestClient {
        private final String BASE_URL = "https://stellarburgers.nomoreparties.site/api/";
        private final String INGREDIENTS_ENDPOINT = "ingredients";
        private final String ORDERS_ENDPOINT = "orders";
        private final String ORDERS_ALL_ENDPOINT = "orders/all";
        private final String USER_ENDPOINT = "auth/user";
        private final String REGISTER_ENDPOINT = "auth/register";
        private final String AUTH_ENDPOINT = "auth/login";
        private final String LOGOUT_ENDPOINT = "auth/logout";
        private final String TOKEN_ENDPOINT = "auth/token";
        private final String PASSWORD_RESET_ENDPOINT = "api/password-reset";
    }

    StellarBurgerRestClient client = new StellarBurgerRestClient();

    public String genRandomAlfaString() {
        return RandomStringUtils.randomAlphabetic(6, 16);
    }

    public String genRandomAlfaNumString() {
        return RandomStringUtils.randomAlphanumeric(4, 16);
    }

    public String genRandomAlfaNumString(int size) {
        return RandomStringUtils.randomAlphanumeric(size);
    }

    public Long randomNum() { return RandomUtils.nextLong(1L, 10L); }

    @Step("Получение информации об ингредиентах GET /api/ingredients")
    public Response getIngredients() {
        return client.getRequest(client.BASE_URL + client.INGREDIENTS_ENDPOINT);
    }

    @Step("Получение информации об ингредиентах /api/ingredients")
    public Response createOrder(IngredientsHashList ingredientsHashList) {
        return client.postRequest(client.BASE_URL + client.ORDERS_ENDPOINT, ingredientsHashList);
    }

    @Step("Получение информации об ингредиентах /api/ingredients")
    public Response createOrder(IngredientsHashList ingredientsHashList, String authToken) {
        return client.postRequest(client.BASE_URL + client.ORDERS_ENDPOINT, authToken, ingredientsHashList);
    }

    @Step("Отправить запрос создания пользователя POST /api/auth/register")
    public Response createUser(User user) {
        return client.postRequest(client.BASE_URL + client.REGISTER_ENDPOINT, user);
    }

    @Step("Отправить запрос логина пользователя POST /api/auth/login")
    public Response loginUser(User user) {
        return client.postRequest(client.BASE_URL + client.AUTH_ENDPOINT, user);
    }

    @Step("Отправить запрос логина пользователя POST /api/auth/login")
    public Response getUserInfo(String auth) {
        return client.getRequest(client.BASE_URL + client.USER_ENDPOINT, auth);
    }

    @Step("Запрос изменения информации пользователя PATCH /api/auth/user")
    public Response updateUserInfo(String auth, User user) {
        return client.patchRequest(client.BASE_URL + client.USER_ENDPOINT, auth, user);
    }

    @Step("Запрос изменения информации пользователя без авторизации PATCH /api/auth/user")
    public Response updateUserInfo(User user) {
        return client.patchRequest(client.BASE_URL + client.USER_ENDPOINT, user);
    }

    @Step("Запрос выхода из системы POST /api/auth/logout")
    public Response logoutUser(Token token) {
        return client.postRequest(client.BASE_URL + client.LOGOUT_ENDPOINT, token);
    }

    @Step("Запрос обновления токена POST /api/auth/token")
    public Response updateToken(User user) {
        return client.postRequest(client.BASE_URL + client.TOKEN_ENDPOINT, user);
    }


    @Step("Запрос удаления пользователя DELETE /api/auth/user")
    public Response deleteUser(String auth) {
        return client.deleteRequest(client.BASE_URL + client.TOKEN_ENDPOINT, auth);
    }

    @Step("Запрос получения всех заказов GET /api/orders/all")
    public Response getAllOrders() {
        return client.getRequest(client.BASE_URL + client.ORDERS_ALL_ENDPOINT);
    }

    @Step("Запрос заказов для конкретного пользователя GET /api/orders")
    public Response getOrdersByUser(String auth) {
        return client.getRequest(client.BASE_URL + client.ORDERS_ENDPOINT, auth);
    }

    @Step("Запрос заказов для конкретного пользователя GET /api/orders без авторизации")
    public Response getOrdersByUser() {
        return client.getRequest(client.BASE_URL + client.ORDERS_ENDPOINT);
    }

    @Step("Ожидание {t} мс")
    public void timeout(long t) throws RuntimeException {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
