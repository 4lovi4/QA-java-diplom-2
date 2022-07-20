import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.AuthResponse;
import org.apache.http.HttpStatus;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import models.User;
import client.TestMethods;
import io.restassured.response.Response;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;;


@RunWith(JUnitParamsRunner.class)
public class TestLoginUser {

    TestMethods testMethods = new TestMethods();
    private AuthResponse authUser;
    private User user;

    @Before
    public void prepareTest() {
        user = new User(testMethods.genRandomAlfaNumString() + "@" +
                testMethods.genRandomAlfaString() + ".test", testMethods.genRandomAlfaNumString(), testMethods.genRandomAlfaString());
        authUser = testMethods.createUser(user).as(AuthResponse.class);
        testMethods.timeout(1000);
    }

    @After
    public void clearTest() {
        testMethods.deleteUser(authUser.getAccessToken());
    }

    @Test
    @DisplayName("Успешный логин пользователя")
    @Description("Запрос POST auth/login возвращает 200 OK, код success, токены")
    public void checkSuccessLogin() {
        Response loginResponse = testMethods.loginUser(user);
        assertThat(loginResponse.then().extract().statusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(Boolean.valueOf(loginResponse.then().extract().path("success").toString())).isTrue();
        assertThat((loginResponse.then().extract().path("user.email").toString())).isEqualTo(user.getEmail().toLowerCase());
        assertThat((loginResponse.then().extract().path("user.name").toString())).isEqualTo(user.getName());
        assertThat((loginResponse.then().extract().path("accessToken").toString())).isNotEmpty();
        assertThat((loginResponse.then().extract().path("refreshToken").toString())).isNotEmpty();
    }

    @Test
    @DisplayName("Логин пользователя с неверным email или password")
    @Description("Запрос POST auth/login возвращает 401 UNAUTHORIZED, код success: false, описание ошибки \"email or password are incorrect\"")
    @Parameters({"email", "password"})
    public void checkWrongLogin(String userParam) {
        User changeUser = user;
        switch (userParam) {
            case "email":
                changeUser.setEmail(testMethods.genRandomAlfaNumString());
                break;
            case "password":
                changeUser.setPassword(testMethods.genRandomAlfaNumString());
                break;
        }
        Response wrongResponse = testMethods.loginUser(changeUser);
        assertThat(wrongResponse.then().extract().statusCode()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);
        assertThat(Boolean.valueOf(wrongResponse.then().extract().path("success").toString())).isFalse();
        assertThat((wrongResponse.then().extract().path("message").toString()))
                .isEqualTo("email or password are incorrect");
        testMethods.timeout(1000);
    }
}
