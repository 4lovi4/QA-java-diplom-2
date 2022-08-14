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
//        authUser.toString()
        testMethods.timeout(1000);
    }

    @After
    public void clearTest() {
        testMethods.logoutUser(new Token(authUser.getRefreshToken()));
        testMethods.timeout(1000);
        testMethods.deleteUser(authUser.getAccessToken());
    }
}
