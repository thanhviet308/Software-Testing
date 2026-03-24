package api;

import api.pojo.CreateUserRequest;
import api.pojo.UserResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserCrudTest extends ApiBaseTest {

    private String createdUserId;

    // ===== POST =====
    @Test(priority = 1)
    public void testCreateUser() {

        CreateUserRequest body = new CreateUserRequest("Nguyen Van A", "QA Engineer");

        UserResponse response = given(requestSpec)
                .body(body)
                .when()
                .post("/users")
                .then()
                .spec(responseSpec)
                .statusCode(201)
                .body("name", equalTo("Nguyen Van A"))
                .body("job", equalTo("QA Engineer"))
                .body("id", notNullValue())
                .body("createdAt", notNullValue())
                .extract()
                .as(UserResponse.class);

        createdUserId = response.getId();
        Assert.assertNotNull(createdUserId);
    }

    // ===== PUT =====
    @Test(priority = 2)
    public void testUpdateUser() {

        CreateUserRequest body = new CreateUserRequest("Nguyen Van A", "Senior QA");

        given(requestSpec)
                .body(body)
                .when()
                .put("/users/2")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("job", equalTo("Senior QA"))
                .body("updatedAt", notNullValue());
    }

    // ===== PATCH =====
    @Test(priority = 3)
    public void testPatchUser() {

        given(requestSpec)
                .body("{\"job\":\"Lead QA\"}")
                .when()
                .patch("/users/2")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("job", equalTo("Lead QA"))
                .body("updatedAt", notNullValue());
    }

    // ===== DELETE =====
    @Test(priority = 4)
    public void testDeleteUser() {

        given(requestSpec)
                .when()
                .delete("/users/2")
                .then()
                .statusCode(204);
    }

    // ===== POST → GET =====
    @Test(priority = 5)
    public void testPostThenGet() {

        CreateUserRequest body = new CreateUserRequest("Tran Thi B", "Tester");

        Response postRes = given(requestSpec)
                .body(body)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract()
                .response();

        String id = postRes.jsonPath().getString("id");
        Assert.assertNotNull(id);

        // reqres.in là mock → chỉ check gọi được
        given(requestSpec)
                .when()
                .get("/users/" + id)
                .then()
                .statusCode(anyOf(is(200), is(404)));
    }
}