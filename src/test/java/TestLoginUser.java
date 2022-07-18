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

public class TestLoginUser {

    @After
    public void clearTest() {

    }

    @Test
    public void checkSuccessLogin() {

    }

}
