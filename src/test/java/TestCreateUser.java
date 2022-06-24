import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import models.User;
import client.TestMethods;

import java.util.ArrayList;
import java.util.List;

public class TestCreateUser {

    TestMethods testMethods = new TestMethods();
    private ArrayList<User> testUsersList = new ArrayList<>();
    private ArrayList<String> testAuthTokens = new ArrayList<>();

    @After
    public void cleanTest() {
        for (String auth : testAuthTokens) {
            testMethods.deleteUser(auth);
        }
    }

    @Test
    public void checkCreateUser() {

    }
}
