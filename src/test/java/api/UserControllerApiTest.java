package api;

import api.spec.Specifications;
import com.trello.controller.UserController;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(UserController.class)
public class UserControllerApiTest {

    final static String URL = "http://localhost:8081/users";

    @Test
    public void getAllUsers() {
        given().get(URL).then().log().all();
    }

    @Test
    public void createNewUser(){

    }

}
