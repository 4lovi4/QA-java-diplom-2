package client;

import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class BaseRestClient {

    private final RestAssuredConfig config = RestAssuredConfig.newConfig()
            .sslConfig(new SSLConfig().relaxedHTTPSValidation())
            .redirect(new RedirectConfig().followRedirects(true));


    protected Response getRequest(String uri) {
        return given().config(config).get(uri);
    }

    protected Response getRequest(String uri, HashMap<String, Object> params) {
        return given().config(config).queryParams(params).get(uri);
    }

    protected Response postRequest(String uri, Object payload) {
        return given().config(config)
                .header("Content-Type", "application/json")
                .body(payload)
                .post(uri);
    }

    protected Response deleteRequest(String uri) {
        return given().config(config)
                .delete(uri);
    }

    protected  Response putRequest(String uri, Object payload) {
        return given().config(config)
                .header("Content-Type", "application/json")
                .body(payload)
                .put(uri);
    }

    protected  Response putRequest(String uri, HashMap<String, Object> params) {
        return given().config(config)
                .header("Content-Type", "application/json")
                .queryParams(params)
                .put(uri);
    }

    protected  Response putRequest(String uri) {
        return given().config(config)
                .put(uri);
    }
}
