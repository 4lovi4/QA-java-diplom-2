import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.AuthResponse;
import models.IngredientsHashList;
import models.Token;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.After;
import models.User;
import client.TestMethods;
import io.restassured.response.Response;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class TestCreateOrder {
    TestMethods testMethods = new TestMethods();
    private AuthResponse authUser;
    private User user;
    private IngredientsHashList currentIngredients;

    @BeforeClass
    public void prepareIngredients() {
        currentIngredients = testMethods.getIngredients().as(IngredientsHashList.class);
    }

    @Before
    public void prepareTest() {
        user = new User(testMethods.genRandomAlfaNumString() + "@" +
                testMethods.genRandomAlfaString() + ".test", testMethods.genRandomAlfaNumString(), testMethods.genRandomAlfaString());
        System.out.println(user.toString());
        authUser = testMethods.createUser(user).as(AuthResponse.class);
        testMethods.timeout(1000);
    }

    @After
    public void clearTest() {
        testMethods.logoutUser(new Token(authUser.getRefreshToken()));
        testMethods.timeout(1000);
        testMethods.deleteUser(authUser.getAccessToken());
    }

    @Test
    @DisplayName("Создание заказа от авторизованного пользователя")
    @Description("Пользователь после авторизации получает список ингредиентов, выбираются случайные и вызывается метод POST /api/orders")
    public void createOrderAuth() {

    }

    @Test
    @DisplayName("Создание заказа от неавторизованного пользователя")
    @Description("Получить список ингредиентов, выбрать случайные и вызваеть метод POST /api/orders, не передав токен")
    public void createOrderWithoutAuth() {

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
