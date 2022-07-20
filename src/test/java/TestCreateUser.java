import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.AuthResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.After;
import models.User;
import client.TestMethods;
import io.restassured.response.Response;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;;
import java.util.ArrayList;

@RunWith(JUnitParamsRunner.class)
public class TestCreateUser {

    TestMethods testMethods = new TestMethods();
    private ArrayList<AuthResponse> testAuthUsers = new ArrayList<>();

    /*
    Удаляем созданных в ходе тестов пользователей
    */
    @After
    public void cleanTest() {
        for (AuthResponse authUser : testAuthUsers) {
            testMethods.deleteUser(authUser.getAccessToken());
        }
    }

    @Test
    @DisplayName("Создание нового уникального пользователя")
    @Description("В запросе передаём данные нового пользователя, уникальный email, уникальнное имя и пароль")
    public void checkCreateUser() {
        User newUser = new User(testMethods.genRandomAlfaNumString() + "@" + testMethods.genRandomAlfaString() + ".test", testMethods.genRandomAlfaNumString(), testMethods.genRandomAlfaString());
        Response response = testMethods.createUser(newUser);
        assertThat(response.then().extract().statusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(Boolean.valueOf(response.then().extract().path("success").toString())).isTrue();
        assertThat((response.then().extract().path("user.email").toString())).isEqualTo(newUser.getEmail().toLowerCase());
        assertThat((response.then().extract().path("user.name").toString())).isEqualTo(newUser.getName());
        assertThat((response.then().extract().path("accessToken").toString())).isNotEmpty();
        assertThat((response.then().extract().path("refreshToken").toString())).isNotEmpty();
        testAuthUsers.add(response.body().as(AuthResponse.class));
    }

    @Test
    @DisplayName("Попытка создания пользователя с уже имеющимся email")
    @Description("В запросе передаём email уже созданного пользователя, уникальнное имя и пароль")
    public void checkCreateNonUniqueUser() {
        User newUser = new User(testMethods.genRandomAlfaNumString() + "@" + testMethods.genRandomAlfaString() + ".test", testMethods.genRandomAlfaNumString(), testMethods.genRandomAlfaString());
        Response response = testMethods.createUser(newUser);
        assertThat(response.then().extract().statusCode()).isEqualTo(HttpStatus.SC_OK);
        testAuthUsers.add(response.body().as(AuthResponse.class));
        newUser.setName(testMethods.genRandomAlfaString());
        newUser.setPassword(testMethods.genRandomAlfaNumString());
        Response wrongResponse = testMethods.createUser(newUser);
        assertThat(wrongResponse.then().extract().statusCode()).isEqualTo(HttpStatus.SC_FORBIDDEN);
        assertThat(Boolean.valueOf(wrongResponse.then().extract().path("success").toString())).isFalse();
        assertThat((wrongResponse.then().extract().path("message").toString())).isEqualTo("User already exists");
    }

    @Test
    @DisplayName("Попытка создание нового пользователя без обязательного поля")
    @Description("В запросе передаём данные нового пользователя без обязательного поля")
    @Parameters({"email, name", "email, password", "name, password", "email", "password", "name", ""})
    public void checkCreateUserWithoutRequiredField(String... userParams) {
        User newUser = new User();
        for (String p : userParams) {
            switch (p) {
                case "email":
                    newUser.setEmail(p);
                    break;
                case "password":
                    newUser.setPassword(p);
                    break;
                case "name":
                    newUser.setName(p);
                    break;
            }
        }
        Response wrongResponse = testMethods.createUser(newUser);
        assertThat(wrongResponse.then().extract().statusCode()).isEqualTo(HttpStatus.SC_FORBIDDEN);
        assertThat(Boolean.valueOf(wrongResponse.then().extract().path("success").toString())).isFalse();
        assertThat((wrongResponse.then().extract().path("message").toString()))
                .isEqualTo("Email, password and name are required fields");
        testMethods.timeout(1000);
    }
}
