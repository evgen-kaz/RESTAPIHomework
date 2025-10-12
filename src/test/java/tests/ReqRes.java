package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ReqRes extends TestBase {
    TestData testData = new TestData();

    String[] userId = {"1", "2", "3", "4", "5", "6"};
    int randomIndex = ThreadLocalRandom.current().nextInt(userId.length); //получаем рандомный индекс
    String randomUserId = userId[randomIndex]; //получаем значение по этому рандомному индексу

    @Test
    @Tag("Positive")
    @DisplayName("Получение списка пользователей")
    void successfulGetListUsersTest() {
        given() //ДАНО
                .header("x-api-key", API_KEY)
                .when() //КОГДА
                .get("/users")
                .then() //ТОГДА - ЧТО ДЕЛАЕМ КОГДА ПРОВЕРЯЕМ
                .log().status()
                .log().body()
                .statusCode(200)
                .body("page", is(1))
                .body("per_page", is(6))
                .body("total", is(12))
                .body("total_pages", is(2));
    }

    @Test
    @Tag("Positive")
    @DisplayName("Получение пользователя по id")
    void successfulGetUserIdTest() {
        given() //ДАНО
                .header("x-api-key", API_KEY)
                .when() //КОГДА
                .get("/users/1")
                .then() //ТОГДА - ЧТО ДЕЛАЕМ КОГДА ПРОВЕРЯЕМ
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(1))
                .body("data.email", is("george.bluth@reqres.in"))
                .body("data.first_name", is("George"))
                .body("data.last_name", is("Bluth"));
    }

    @Test
    @Tag("Negative")
    @DisplayName("Невозможно создание нового пользователя")
    void NotSuccessfulCreateNewUserTest() {
        given() //ДАНО
                .header("x-api-key", API_KEY)
                .contentType(JSON)
                .body(testData.generateDataUserJson())
                .when() //КОГДА
                .post("/register")
                .then() //ТОГДА - ЧТО ДЕЛАЕМ КОГДА ПРОВЕРЯЕМ
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Note: Only defined users succeed registration"));
    }

    @Test
    @Tag("Positive")
    @DisplayName("Обновление пользователя методом PUT")
    void successfulUpdateUserMethodPutTest() {
        Map<String, String> emailAndFirstNameAndLastName = new HashMap<>();
        emailAndFirstNameAndLastName.put("email", testData.email);
        emailAndFirstNameAndLastName.put("first_name", testData.firstName);
        emailAndFirstNameAndLastName.put("last_name", testData.lastName);

        given() //ДАНО
                .header("x-api-key", API_KEY)
                .contentType(JSON)
                .body(emailAndFirstNameAndLastName)
                .when() //КОГДА
                .put("/users/" + randomUserId)
                .then() //ТОГДА - ЧТО ДЕЛАЕМ КОГДА ПРОВЕРЯЕМ
                .log().status()
                .log().body()
                .statusCode(200)
                .body("email", is(testData.email))
                .body("first_name", is(testData.firstName))
                .body("last_name", is(testData.lastName))
                .body("updatedAt", notNullValue());
    }

    @Test
    @Tag("Positive")
    @DisplayName("Обновление пользователя методом PATCH")
    void successfulUpdateUserMethodPatchTest() {
        Map<String, String> emailAndFirstNameAndLastName = new HashMap<>();
        emailAndFirstNameAndLastName.put("email", testData.email);
        emailAndFirstNameAndLastName.put("first_name", testData.firstName);
        emailAndFirstNameAndLastName.put("last_name", testData.lastName);

        given() //ДАНО
                .header("x-api-key", API_KEY)
                .contentType(JSON)
                .body(emailAndFirstNameAndLastName)
                .when() //КОГДА
                .patch("/users/" + randomUserId)
                .then() //ТОГДА - ЧТО ДЕЛАЕМ КОГДА ПРОВЕРЯЕМ
                .log().status()
                .log().body()
                .statusCode(200)
                .body("email", is(testData.email))
                .body("first_name", is(testData.firstName))
                .body("last_name", is(testData.lastName))
                .body("updatedAt", notNullValue());
    }

    @Test
    @Tag("Positive")
    @DisplayName("Удаление пользователя")
    void successfulDeleteUserTest() {
        given() //ДАНО
                .header("x-api-key", API_KEY)
                .when() //КОГДА
                .delete("/users/" + randomUserId)
                .then() //ТОГДА - ЧТО ДЕЛАЕМ КОГДА ПРОВЕРЯЕМ
                .log().status()
                .log().body()
                .statusCode(204);
    }
}
