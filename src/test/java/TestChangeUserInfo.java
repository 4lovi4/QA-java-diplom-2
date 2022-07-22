import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.AuthResponse;
import models.Token;
import org.apache.http.HttpStatus;
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
public class TestChangeUserInfo {
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
        testMethods.logoutUser(new Token(authUser.getRefreshToken()));
        testMethods.timeout(1000);
        testMethods.deleteUser(authUser.getAccessToken());
    }

    @Test
    @DisplayName("Изменение данных пользователя с успешной авторизацией")
    @Description("Запрос PATCH auth/user c заголовком Authorization, в котором передаётся accessToken, возвращает 200 OK, код success, актуальные email, name")
    @Parameters({"email", "password", "name"})
    public void checkChangeUserInfoWithAuth(String userParam) {
        switch (userParam) {
            case "email":
                user.setEmail(testMethods.genRandomAlfaNumString() + "@" + testMethods.genRandomAlfaString() + ".test");
            case "password":
                user.setPassword(testMethods.genRandomAlfaNumString());
            case "name":
                user.setName(testMethods.genRandomAlfaString());
        }
        Response changeUserResponse = testMethods.updateUserInfo(authUser.getAccessToken(), user);
        assertThat(changeUserResponse.then().extract().statusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(Boolean.valueOf(changeUserResponse.then().extract().path("success").toString())).isTrue();
        assertThat((changeUserResponse.then().extract().path("user.email").toString())).isEqualTo(user.getEmail().toLowerCase());
        assertThat((changeUserResponse.then().extract().path("user.name").toString())).isEqualTo(user.getName());
        testMethods.timeout(1000);
        Response loginResponse = testMethods.loginUser(user);
        assertThat(loginResponse.then().extract().statusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(Boolean.valueOf(loginResponse.then().extract().path("success").toString())).isTrue();
        assertThat((loginResponse.then().extract().path("user.email").toString())).isEqualTo(user.getEmail().toLowerCase());
        assertThat((loginResponse.then().extract().path("user.name").toString())).isEqualTo(user.getName());
        assertThat((loginResponse.then().extract().path("accessToken").toString())).isNotEmpty();
        assertThat((loginResponse.then().extract().path("refreshToken").toString())).isNotEmpty();
        authUser = loginResponse.as(AuthResponse.class);
    }

    @Test
    @DisplayName("Изменение данных пользователя с успешной авторизацией")
    @Description("Запрос PATCH auth/user без заголовка Authorization, в котором передаётся accessToken, возвращает 200 OK, код success, актуальные email, name")
    @Parameters({"email", "password", "name"})
    public void checkChangeUserInfoWithoutAuth(String userParam) {
        User newUser = user;
        switch (userParam) {
            case "email":
                newUser.setEmail(testMethods.genRandomAlfaNumString() + "@" + testMethods.genRandomAlfaString() + ".test");
            case "password":
                newUser.setPassword(testMethods.genRandomAlfaNumString());
            case "name":
                newUser.setName(testMethods.genRandomAlfaString());
        }
        Response loginResponse = testMethods.loginUser(user);
        assertThat(loginResponse.then().extract().statusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(Boolean.valueOf(loginResponse.then().extract().path("success").toString())).isTrue();
        assertThat((loginResponse.then().extract().path("user.email").toString())).isEqualTo(user.getEmail().toLowerCase());
        assertThat((loginResponse.then().extract().path("user.name").toString())).isEqualTo(user.getName());
        assertThat((loginResponse.then().extract().path("accessToken").toString())).isNotEmpty();
        assertThat((loginResponse.then().extract().path("refreshToken").toString())).isNotEmpty();
        authUser = loginResponse.as(AuthResponse.class);
        testMethods.timeout(1000);
        Response wrongResponse = testMethods.updateUserInfo(newUser);
        assertThat(wrongResponse.then().extract().statusCode()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);
        assertThat(Boolean.valueOf(wrongResponse.then().extract().path("success").toString())).isFalse();
        assertThat((wrongResponse.then().extract().path("message").toString()))
                .isEqualTo("You should be authorised");
    }
}
