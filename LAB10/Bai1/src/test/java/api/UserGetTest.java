package api;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserGetTest extends ApiBaseTest {

    @Test(description = "GET /api/users?page=1 - kiểm tra status, page, total_pages và số lượng data")
    public void testGetUsersPage1() {
        given()
                .spec(requestSpec)
                .queryParam("page", 1)
                .when()
                .get("/users")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("page", equalTo(1))
                .body("total_pages", greaterThan(0))
                .body("data.size()", greaterThanOrEqualTo(1));
    }

    @Test(description = "GET /api/users?page=2 - kiểm tra đầy đủ field của mỗi user")
    public void testGetUsersPage2() {
        given()
                .spec(requestSpec)
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("page", equalTo(2))
                .body("data.size()", greaterThanOrEqualTo(1))
                .body("data.id", everyItem(notNullValue()))
                .body("data.email", everyItem(not(emptyString())))
                .body("data.first_name", everyItem(not(emptyString())))
                .body("data.last_name", everyItem(not(emptyString())))
                .body("data.avatar", everyItem(not(emptyString())));
    }

    @Test(description = "GET /api/users/3 - kiểm tra id, email đúng domain reqres.in, first_name không rỗng")
    public void testGetUserById3() {
        given()
                .spec(requestSpec)
                .when()
                .get("/users/3")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("data.id", equalTo(3))
                .body("data.email", containsString("@reqres.in"))
                .body("data.first_name", not(emptyString()));
    }

    @Test(description = "GET /api/users/9999 - kiểm tra 404 và body rỗng")
    public void testGetUserNotFound() {
        given()
                .spec(requestSpec)
                .when()
                .get("/users/9999")
                .then()
                .statusCode(404)
                .body("$", anEmptyMap());
    }
}