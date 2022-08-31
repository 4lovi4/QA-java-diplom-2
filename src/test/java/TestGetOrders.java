import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.*;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import client.TestMethods;
import io.restassured.response.Response;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestGetOrders {
    TestMethods testMethods = new TestMethods();
    private AuthResponse authUser;
    private User user;
    private List<Ingredient> ingredientList = testMethods.getIngredients().as(GetAllIngredientsResponse.class).getData();
    private Random random = new Random();
    private List<Order> createdOrders = new ArrayList<Order>();

    @Before
    public void prepareTest() {
        user = new User(testMethods.genRandomAlfaNumString() + "@" +
                testMethods.genRandomAlfaString() + ".test",
                testMethods.genRandomAlfaNumString(),
                testMethods.genRandomAlfaString());
        System.out.println(user.toString());
        authUser = testMethods.createUser(user).as(AuthResponse.class);
        testMethods.timeout(1000);
        int ordersAmount = random.nextInt(10);
        for (int o = 0; o < ordersAmount; o++) {
            List<String> hashList = new ArrayList<String>();
            int ingredientsAmount = random.nextInt(ingredientList.size());
            for (int i = 0; i < ingredientsAmount; i++) {
                hashList.add(ingredientList.get(random.nextInt(ingredientList.size())).get_id());
            }
            CreateOrderResponse orderResponse = testMethods.createOrder(new IngredientsHashList(hashList), authUser.getAccessToken()).as(CreateOrderResponse.class);
            testMethods.timeout(1000L);
            createdOrders.add(orderResponse.getOrder());
        }
    }

    @After
    public void clearTest() {
        testMethods.logoutUser(new Token(authUser.getRefreshToken()));
        testMethods.timeout(1000);
        testMethods.deleteUser(authUser.getAccessToken());
    }

    @Test
    @DisplayName("Получить заказы авторизованного пользователя")
    @Description("После успешной авторизации пользователя, у которого есть заказы, вызвать метод /api/orders, передав токен")
    public void getOrdersWithAuth() {
        Response getOrderResponse = testMethods.getOrdersByUser(authUser.getAccessToken());
        assertThat(getOrderResponse.statusCode()).isEqualTo(HttpStatus.SC_OK);
        OrderList ordersByUser = getOrderResponse.as(OrderList.class);
    }

    @Test
    @DisplayName("Ошибка при получении заказов пользователя без авторизации")
    @Description("Вызвать метод GET /api/orders, не передав токен")
    public void getOrdersWithoutAuth() {

    }

}
