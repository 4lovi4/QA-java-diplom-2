import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.*;
import org.apache.http.HttpStatus;
import org.junit.*;
import client.TestMethods;
import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RunWith(JUnitParamsRunner.class)
public class TestCreateOrder {
    static TestMethods testMethods = new TestMethods();
    private static AuthResponse authUser;
    private static User user;
    private static List<Ingredient> currentIngredients;

    @BeforeClass
    public static void prepareUserAndIngredients() {
        GetAllIngredientsResponse getIngredientsListResponse = testMethods.getIngredients().as(GetAllIngredientsResponse.class);
        currentIngredients = getIngredientsListResponse.getData();
        user = new User(testMethods.genRandomAlfaNumString() + "@" + testMethods.genRandomAlfaString() + ".test", testMethods.genRandomAlfaNumString(), testMethods.genRandomAlfaString());
        authUser = testMethods.createUser(user).as(AuthResponse.class);
    }

    @Before
    public void prepareTest() {

    }

    @AfterClass
    public static void clearTest() {
        testMethods.logoutUser(new Token(authUser.getRefreshToken()));
        testMethods.timeout(1000);
        testMethods.deleteUser(authUser.getAccessToken());
    }

    @Test
    @DisplayName("Создание заказа от авторизованного пользователя")
    @Description("Пользователь после авторизации получает список ингредиентов, выбираются случайные и вызывается метод POST /api/orders")
    public void createOrderAuth() {
        List<String> ingredientsHashList = new ArrayList<String>();
        Random random = new Random();
        for (int i = 0; i < random.nextInt(currentIngredients.size()); i++) {
            ingredientsHashList.add(currentIngredients.get(random.nextInt(currentIngredients.size())).get_id());
        }
        Response orderResponse = testMethods.createOrder(new IngredientsHashList(ingredientsHashList), authUser.getAccessToken());
        assertThat(orderResponse.then().extract().statusCode()).isEqualTo(HttpStatus.SC_OK);
        CreateOrderResponse orderCreated = orderResponse.as(CreateOrderResponse.class);
        assertThat(orderCreated.getSuccess()).isTrue();
        assertThat(orderCreated.getName()).isNotEmpty();
        assertThat(orderCreated.getOrder()).isNotNull();
    }

    @Test
    @DisplayName("Создание заказа от неавторизованного пользователя")
    @Description("Получить список ингредиентов, выбрать случайные и вызваеть метод POST /api/orders, не передав токен")
    public void createOrderWithoutAuth() {
        List<String> ingredientsHashList = new ArrayList<String>();
        Random random = new Random();
        for (int i = 0; i < random.nextInt(currentIngredients.size()); i++) {
            ingredientsHashList.add(currentIngredients.get(random.nextInt(currentIngredients.size())).get_id());
        }
        Response orderResponse = testMethods.createOrder(new IngredientsHashList(ingredientsHashList));
        assertThat(orderResponse.then().extract().statusCode()).isEqualTo(HttpStatus.SC_OK);
        CreateOrderResponse orderCreated = orderResponse.as(CreateOrderResponse.class);
        assertThat(orderCreated.getSuccess()).isTrue();
        assertThat(orderCreated.getName()).isNotEmpty();
        assertThat(orderCreated.getOrder()).isNotNull();
    }

    @Test
    @DisplayName("Создание заказа c ингредиентами")
    @Description("Пользователь после авторизации получает список ингредиентов вызывает, выбирает случайные в количестве ingredientsAmount и передаёт их в метод POST /api/orders")
    @Parameters({"1", "2", "3"})
    public void createOrderWithIngredients(String ingredientsAmount) {

    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Пользователь после авторизации вызывает метод POST /api/orders, не передавая в теле запроса списка ингредиентов")
    public void createOrderWithoutIngredients() {

    }

    @Test
    @DisplayName("Создание заказа с неправильными хэшами ингредиентов")
    @Description("Пользователь после успешной авторизации генерирует случайные хэши ингредиентов, вызывается метод POST /api/orders, в котором передаётся список с неправильными хэшами инредиентов")
    public void createOrderWrongIngredients() {

    }

    @Test
    @DisplayName("Создание заказа, где есть правильные и неправильными хэши ингредиентов")
    @Description("Пользователь после успешной авторизации получает список ингредиентов и генерирует случайные хэши ингредиентов, вызывается метод POST /api/orders, в котором передаётся список с неправильными хэшами инредиентов")
    public void createOrderPartlyWrongIngredients() {

    }

}
