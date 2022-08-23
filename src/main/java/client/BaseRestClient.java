package client;

import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
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

    protected Response getRequest(String uri, String token) {
        Header authHeader = new Header("Authorization", token);
        return given().header(authHeader).config(config).get(uri);
    }

    protected Response getRequest(String uri, HashMap<String, Object> params) {
        return given().config(config).queryParams(params).get(uri);
    }

    protected Response postRequest(String uri, Object payload) {
        Header contentHeader = new Header("Content-Type", "application/json");
        return given().config(config)
                .header(contentHeader)
                .body(payload)
                .when()
                .post(uri);
    }

    protected Response postRequest(String uri, String token, Object payload) {
        Header authHeader = new Header("Authorization", token);
        Header contentHeader = new Header("Content-Type", "application/json");
        return given().config(config)
                .header(authHeader)
                .header(contentHeader)
                .body(payload)
                .when()
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

    protected Response patchRequest(String uri, String token, Object payload) {
        Header authHeader = new Header("Authorization", token);
        Response response = given().config(config).contentType(ContentType.JSON).header(authHeader).body(payload).patch(uri);
        return response;
    }

    protected Response patchRequest(String uri, Object payload) {
        return given().config(config).body(payload).patch(uri);
    }

    protected Response deleteRequest(String uri, String authToken) {
        Header authHeader = new Header("Authorization", authToken);
        return given().config(config)
                .header(authHeader)
                .delete(uri);
    }
}
