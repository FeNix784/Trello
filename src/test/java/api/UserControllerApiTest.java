package api;

import api.spec.Specifications;
import com.trello.controller.UserController;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(UserController.class)
public class UserControllerApiTest {

    final static String URL = "http://localhost:8081/users";

    @Test
    public void getAllUsers() {
        Specifications.installSpec(Specifications.requestSpecification(URL),Specifications.responseSpecificationSc(200));

        Response response = given()
                .when()
                .get(URL)
                .then().log().all()
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        List<Integer> ids = jsonPath.get("id");
        List<String> name = jsonPath.get("name");
        List<String> surname = jsonPath.get("surname");

        String expectedName = "Тестовый аккаунт";

        Assertions.assertNotNull(ids);
        Assertions.assertNotNull(name);
        Assertions.assertNotNull(surname);
        for(int i = 0; i < ids.size(); i++){
            Assertions.assertTrue(surname.get(i).contains(ids.get(i).toString()));
            Assertions.assertTrue(name.get(i).contains(expectedName));
        }
    }

    @Test
    public void getUserById(){
        Specifications.installSpec(Specifications.requestSpecification(URL),Specifications.responseSpecificationSc(200));

        Integer randomUserId = (int) (Math.random() * 5) + 1;

        Response response = given()
                .when()
                .get(URL + "/" + randomUserId)
                .then().log().all()
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        int id = jsonPath.get("id");
        String name = jsonPath.get("name");
        String surname = jsonPath.get("surname");

        Assertions.assertAll("Not null check",
                () -> Assertions.assertNotNull(id),
                () -> Assertions.assertNotNull(name),
                () -> Assertions.assertNotNull(surname));

        Assertions.assertEquals(randomUserId, id);
        Assertions.assertTrue(surname.contains(randomUserId.toString())); //Извращение, Мисье.
    }

    @Test
    public void putByUserId(){
        Specifications.installSpec(Specifications.requestSpecification(URL),Specifications.responseSpecificationSc(200));

    }

    @Test
    public void deleteUserById(){
        Specifications.installSpec(Specifications.requestSpecification(URL),Specifications.responseSpecificationSc(200));
        int randomUserId = (int) (Math.random() * 3) + 3;

        given().when()
                .delete(URL + "/" + randomUserId)
                .then().log().all();


        Specifications.installSpec(Specifications.requestSpecification(URL),Specifications.responseSpecificationSc(404));
        given().when()
                .get(URL + "/" + randomUserId)
                .then().log().all();
    }

}
