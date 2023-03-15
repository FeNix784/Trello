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


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@QuarkusTest
@TestHTTPEndpoint(BoardController.class)
class BoardControllerApiTest {
    private static String URL = "http://localhost:8081/boards";
    private static String userIdForCreatePutDelete = "1";
    private static String boardIdForPutDelete = "10";
    private static String boardIdForGet = "7";

    @Test
    void getBoardsByUserID(){
        Specifications.installSpec(Specifications.requestSpecification(URL), Specifications.responseSpecificationSc(200));

        String userId = "2"; // У userId = 2 пять Досок в наличии. Столяр =)

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("userId", userId);

        Response response = given().queryParams(queryParams)
                .when().get(URL)
                .then()
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        List<Integer> ids = jsonPath.get("id");
//        List<Integer> expectedIds = Arrays.asList(6, 7, 8, 9, 10);
//        Assertions.assertTrue(ids.size() == expectedIds.size() && ids.containsAll(expectedIds) && expectedIds.containsAll(ids));
    }

    @Test
    void getBoardById() {
        Specifications.installSpec(Specifications.requestSpecification(URL), Specifications.responseSpecificationSc(200));

        // boardId = "6" -- board1 , boardId = "7" -- board2,
        // boardId = "8" -- board3, boardId = "9" -- board4,
        // boardId = "10" -- board5

        String userId = "1";
        String boardId = boardIdForGet; // board2
        String expectedNameOfColumnsOnBoard = "board2";

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("userId", userId);

        Response response = given().queryParams(queryParams)
                .when().get(URL + "/" + boardId)
                .then()
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        String id = Integer.toString(jsonPath.get("id"));
        List<String> columnsTitles = jsonPath.get("columns.title");

        for(int i = 0; i < columnsTitles.size(); i++){
            Assertions.assertTrue(columnsTitles.get(i).contains(expectedNameOfColumnsOnBoard));
        }
        Assertions.assertEquals(boardId, id);
    }


    //Обычное создание доски. Все параметры передаются правильные.
    @Test
    void createBoard() {
        Specifications.installSpec(Specifications.requestSpecification(URL),Specifications.responseSpecificationSc(200));

        Map<String, String> requestBody = new HashMap<>();
        Map<String, String> queryParams = new HashMap<>();

        String titleForBoard = "boardFromRestAssured";
        String userId = userIdForCreatePutDelete;

        requestBody.put("title", titleForBoard);
        queryParams.put("userId", userId);

        Response response = given().queryParams(queryParams)
                .body(requestBody)
                .when()
                .post(URL)
                .then()
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
                .then();
    }
    //Создание доски с неправильным RequestJSON, должен вернуться 500 код.

    @Test
    void createBoardWithBadRequest() {
        Specifications.installSpec(Specifications.requestSpecification(URL),Specifications.responseSpecificationSc(500));
        Map<String, String> requestBody = new HashMap<>();
        Map<String, String> queryParams = new HashMap<>();

        String titleForBoard = "boardFromRestAssured";
        String userId = userIdForCreatePutDelete;

        requestBody.put("title", titleForBoard);
        requestBody.put("id", "4"); // Добавляем в Request поле с ID. Т.к ID формируется на сервере => BadRequest;

        queryParams.put("userId", userId);
        given().queryParams(queryParams)
                .body(requestBody)
                .when()
                .post(URL)
                .then();
    }

    @Test
    void updateBoardById() {
        Specifications.installSpec(Specifications.requestSpecification(URL),Specifications.responseSpecificationSc(200));

        Map<String, String> requestBody = new HashMap<>();
        Map<String, String> queryParams = new HashMap<>();

        String titleForRequest = "boardTestUpdate";
        String boardId = "9";
        String userId = userIdForCreatePutDelete;

        requestBody.put("title", titleForRequest);
        queryParams.put("userId", userId);

        Response response = given().queryParams(queryParams)
                .body(requestBody)
                .when()
                .put(URL + "/" + boardId)
                .then()
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        String titleFromResponse = jsonPath.get("title");
        int id = jsonPath.get("id");

        Assertions.assertEquals(boardId,Integer.toString(id));
        Assertions.assertEquals(titleForRequest,titleFromResponse);
    }

    @Test
    void deleteBoard() {
        Specifications.installSpec(Specifications.requestSpecification(URL),Specifications.responseSpecificationSc(200));

        Map<String, String> queryParams = new HashMap<>();
        String userId = userIdForCreatePutDelete;
        queryParams.put("userId", userId);
        String boardId = boardIdForPutDelete;


        given().queryParams(queryParams)
                .when()
                .delete(URL + "/" + boardId)
                .then();


        Specifications.installSpec(Specifications.requestSpecification(URL),Specifications.responseSpecificationSc(500));
        given().queryParams(queryParams)
                .when()
                .get(URL + "/" + boardId)
                .then();
    }

    @Test
    void getUsers() {
        Specifications.installSpec(Specifications.requestSpecification(URL),Specifications.responseSpecificationSc(200));
        String boardId = boardIdForGet;

        Response response = given().when()
                .get(URL + "/" + boardId + "/users")
                .then()
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        List<String> ids = jsonPath.get("id");
        List<String> name = jsonPath.get("name");

        for(int i = 0; i < name.size(); i++){
            Assertions.assertTrue(name.get(i).contains("Тестовый аккаунт"));
            Assertions.assertNotNull(ids.get(i));
        }
    }

    @Test
    void addUser() {
        Specifications.installSpec(Specifications.requestSpecification(URL),Specifications.responseSpecificationSc(200));

        String boardId = boardIdForGet;

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("userId", "5");
        queryParams.put("role", "0");

        given().when().queryParams(queryParams)
                .put(URL + "/" + boardId + "/invite")
                .then()
                .extract().response();

        Response response = given().when()
                .get(URL + "/" + boardId + "/users")
                .then()
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        List<Integer> ids = jsonPath.get("id");
        List<Integer> expectedIds = Arrays.asList(1, 2, 5);
        Assertions.assertTrue(ids.size() == expectedIds.size() && ids.containsAll(expectedIds) && expectedIds.containsAll(ids));
    }

    @Test
    void deleteUser() {
//        /boards/{boardId}/refuse?userId=…&deleteUserId=...
        Specifications.installSpec(Specifications.requestSpecification(URL),Specifications.responseSpecificationSc(200));

        String boardId = boardIdForGet;
        String userIdAdmin = userIdForCreatePutDelete;

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("deleteUserId", "5");
        queryParams.put("userId", userIdAdmin);

        given().when().queryParams(queryParams)
                .delete(URL + "/" + boardId + "/refuse")
                .then()
                .extract().response();
    }

    @Test
    void createLink() {
    }
}