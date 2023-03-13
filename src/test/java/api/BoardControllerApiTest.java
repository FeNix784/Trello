package api;

import api.spec.Specifications;
import com.trello.controller.BoardController;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@QuarkusTest
@Testcontainers
@TestHTTPEndpoint(BoardController.class)
class BoardControllerApiTest {
    private static String URL = "http://localhost:8081/boards";


    //Обычное создание доски. Все параметры передаются правильные.
    @Test
    void createBoard() {
        Specifications.installSpec(Specifications.requestSpecification(URL),Specifications.responseSpecificationSc(200));
        Map<String, String> requestBody = new HashMap<>();
        Map<String, String> queryParams = new HashMap<>();

        String titleForBoard = "newBoard";
        String userId = "2";

        requestBody.put("title", titleForBoard);
        queryParams.put("userId", userId);

        Response response = given().queryParams(queryParams)
                .body(requestBody)
                .when()
                .post(URL)
                .then().log().all()
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        String titleFromResponse = jsonPath.get("title");
        int id = jsonPath.get("id");

        Assertions.assertEquals(titleForBoard, titleFromResponse); //Проверка, что сервер возвращает такой же Title, который мы и отдаем.
        Assertions.assertNotNull(id);  //Проверка, что сервер возвращает id не Null.
    }

    //Создание доски с невалидным юзером.
    @Test
    void createBoardWithBadUser() {
        Specifications.installSpec(Specifications.requestSpecification(URL),Specifications.responseSpecificationSc(400));
        Map<String, String> requestBody = new HashMap<>();
        Map<String, String> queryParams = new HashMap<>();

        String titleForBoard = "newBoard";
        String userId = "0"; //нулевого ID никогда не будет => Крайний базовый случай.

        requestBody.put("title", titleForBoard);
        queryParams.put("userId", userId);

        given().queryParams(queryParams)
                .body(requestBody)
                .when()
                .post(URL)
                .then().log().all();
    }

    //Создание доски с неправильным RequestJSON, должен вернуться 500 код.
    @Test
    void createBoardWithBadRequest() {
        Specifications.installSpec(Specifications.requestSpecification(URL),Specifications.responseSpecificationSc(500));
        Map<String, String> requestBody = new HashMap<>();
        Map<String, String> queryParams = new HashMap<>();

        String titleForBoard = "newBoard";
        String userId = "2";

        requestBody.put("title", titleForBoard);
        requestBody.put("id", "4"); // Добавляем в Request поле с ID. Т.к ID формируется на сервере => BadRequest;

        queryParams.put("userId", userId);
        given().queryParams(queryParams)
                .body(requestBody)
                .when()
                .post(URL)
                .then().log().all();
    }

    @Test
    void getBoardsByUserID(){
        Specifications.installSpec(Specifications.requestSpecification(URL), Specifications.responseSpecificationSc(200));

        Map<String, String> queryParams = new HashMap<>();
        String userId = "1";
        queryParams.put("userId", userId);

        Map<String, String> requestBody = new HashMap<>();
        String titleForBoard = "getBoardsByUserID";
        requestBody.put("title", titleForBoard);

        List<String> response = given().queryParams(queryParams)
                .when().get(URL)
                .then().log().all()
                .extract().jsonPath().getList("id");

        response.get(0);
//        String id = Integer.toString(jsonPath.get(".id"));
//        String boardId = "3";
//        Assertions.assertEquals(boardId, id);
    }

    @Test
    void getBoardById() {
        Specifications.installSpec(Specifications.requestSpecification(URL), Specifications.responseSpecificationSc(200));

        Map<String, String> queryParams = new HashMap<>();
        String userId = "1";
        String boardId = "3";
        queryParams.put("userId", userId);

        Map<String, String> requestBody = new HashMap<>();
        String titleForBoard = "getBoardsByUserID";
        requestBody.put("title", titleForBoard);

        Response response = given().queryParams(queryParams)
                .when().get(URL + "/" + boardId)
                .then().log().all()
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        String id = Integer.toString(jsonPath.get("id"));
        Assertions.assertEquals(boardId, id);
    }

    @Test
    void createLink() {
    }

    @Test
    void getUsers() {
    }

    @Test
    void updateBoard() {
    }

    @Test
    void addUser() {
    }

    @Test
    void deleteBoard() {
    }

    @Test
    void deleteUser() {
    }
}