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
//        Вызов таймаута перед каждым тестом, чтобы избежать 429 Too Many Requests
        testMethods.timeout(1000);
    }

    @AfterClass
    public static void clearTest() {
        testMethods.logoutUser(new Token(authUser.getRefreshToken()));
        testMethods.timeout(1000);
        testMethods.deleteUser(authUser.getAccessToken());
    }

    @Test
    @DisplayName("Создание заказа от авторизованного пользователя")
    @Description("Пользователь после авторизации получает список ингредиентов, выбираются случайные и вызывается метод POST /api/orders." +
            "\nВ ответе будет информация о пользователе, статус заказа, список ингредиентов, цена, название и номер.")
    public void createOrderAuth() {
        List<String> ingredientsHashList = new ArrayList<String>();
        Random random = new Random();
        Double burgerPrice = 0.0;
        for (int i = 0; i < random.nextInt(currentIngredients.size()); i++) {
            Ingredient ingredient = currentIngredients.get(random.nextInt(currentIngredients.size()));
            ingredientsHashList.add(ingredient.get_id());
            burgerPrice += ingredient.getPrice();
        }
        Response orderResponse = testMethods.createOrder(new IngredientsHashList(ingredientsHashList), authUser.getAccessToken());
        assertThat(orderResponse.then().extract().statusCode()).isEqualTo(HttpStatus.SC_OK);
        CreateOrderResponse orderCreated = orderResponse.as(CreateOrderResponse.class);
        assertThat(orderCreated.getSuccess()).isTrue();
        assertThat(orderCreated.getName()).isNotEmpty();
        assertThat(orderCreated.getOrder().getIngredients().size()).isEqualTo(ingredientsHashList.size());
        assertThat(orderCreated.getOrder().getNumber()).isNotNull();
        assertThat(orderCreated.getOrder().getPrice()).isEqualTo(burgerPrice);
        assertThat(orderCreated.getOrder().getOwner().getName()).isEqualTo(authUser.getUser().getName());
        assertThat(orderCreated.getOrder().getOwner().getEmail()).isEqualTo(authUser.getUser().getEmail());
    }

    @Test
    @DisplayName("Создание заказа от неавторизованного пользователя")
    @Description("Получить список ингредиентов, выбрать случайные и вызваеть метод POST /api/orders, не передав токен." +
            "\nВ ответе приходит имя бургера, статус и номер заказа.")
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
        assertThat(orderCreated.getOrder().getNumber()).isNotNull();
    }

    @Test
    @DisplayName("Создание заказа c ингредиентами")
    @Description("Пользователь после авторизации получает список ингредиентов вызывает, выбирает случайные в количестве ingredientsAmount и передаёт их в метод POST /api/orders.")
    @Parameters({"1", "2", "3"})
    public void createOrderWithIngredients(String ingredientsAmount) {
        List<String> ingredientsHashList = new ArrayList<String>();
        Random random = new Random();
        Double burgerPrice = 0.0;
        for (int i = 0; i < Integer.parseInt(ingredientsAmount); i++) {
            Ingredient ingredient = currentIngredients.get(random.nextInt(currentIngredients.size()));
            ingredientsHashList.add(ingredient.get_id());
            burgerPrice += ingredient.getPrice();
        }
        Response orderResponse = testMethods.createOrder(new IngredientsHashList(ingredientsHashList), authUser.getAccessToken());
        assertThat(orderResponse.then().extract().statusCode()).isEqualTo(HttpStatus.SC_OK);
        CreateOrderResponse orderCreated = orderResponse.as(CreateOrderResponse.class);
        assertThat(orderCreated.getSuccess()).isTrue();
        assertThat(orderCreated.getName()).isNotEmpty();
        assertThat(orderCreated.getOrder().getIngredients().size()).isEqualTo(Integer.parseInt(ingredientsAmount));
        assertThat(orderCreated.getOrder().getNumber()).isNotNull();
        assertThat(orderCreated.getOrder().getPrice()).isEqualTo(burgerPrice);
        assertThat(orderCreated.getOrder().getOwner().getName()).isEqualTo(authUser.getUser().getName());
        assertThat(orderCreated.getOrder().getOwner().getEmail()).isEqualTo(authUser.getUser().getEmail());
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Пользователь после авторизации вызывает метод POST /api/orders, не передавая в теле запроса ингредиентов в списке")
    public void createOrderWithoutIngredients() {
        List<String> ingredientsHashList = new ArrayList<String>();
        Response orderResponse = testMethods.createOrder(new IngredientsHashList(ingredientsHashList), authUser.getAccessToken());
        assertThat(orderResponse.then().extract().statusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
        assertThat(Boolean.parseBoolean(orderResponse.then().extract().path("success").toString())).isFalse();
        assertThat(orderResponse.then().extract().path("message").toString()).isEqualTo("Ingredient ids must be provided");
    }

    @Test
    @DisplayName("Создание заказа с неправильными хэшами ингредиентов")
    @Description("Пользователь после успешной авторизации генерирует случайные хэши ингредиентов, вызывается метод POST /api/orders, в котором передаётся список с неправильными хэшами инредиентов")
    public void createOrderWrongIngredients() {
        List<String> ingredientsHashList = new ArrayList<String>();
        Random random = new Random();
        for (int i = 0; i < random.nextInt(currentIngredients.size()); i++) {
            String wrongHashId = testMethods.genRandomAlfaNumString(24);
            ingredientsHashList.add(wrongHashId);
        }
        Response errorResponse = testMethods.createOrder(new IngredientsHashList(ingredientsHashList), authUser.getAccessToken());
        assertThat(errorResponse.then().extract().statusCode()).isEqualTo(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Создание заказа, где есть правильные и неправильными хэши ингредиентов")
    @Description("Пользователь после успешной авторизации получает список ингредиентов и генерирует случайные хэши ингредиентов, вызывается метод POST /api/orders, в котором передаётся список с неправильными хэшами инредиентов")
    public void createOrderPartlyWrongIngredients() {
        List<String> ingredientsHashList = new ArrayList<String>();
        Random random = new Random();
        int ingredientsAmount = random.nextInt(currentIngredients.size());
        for (int i = 0; i < ingredientsAmount; i++) {
            String wrongHashId = testMethods.genRandomAlfaNumString(24);
            Ingredient ingredient = currentIngredients.get(random.nextInt(currentIngredients.size()));
            if(i % 2 == 0 ) ingredientsHashList.add(wrongHashId);
            else ingredientsHashList.add(ingredient.get_id());
        }
        Response errorResponse = testMethods.createOrder(new IngredientsHashList(ingredientsHashList), authUser.getAccessToken());
        assertThat(errorResponse.then().extract().statusCode()).isEqualTo(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
}