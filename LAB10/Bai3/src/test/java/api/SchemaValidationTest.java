package api;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class SchemaValidationTest extends ApiBaseTest {

    @Test
    public void testUserListSchema() {
        given(requestSpec)
                .when()
                .get("/users?page=2")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("page", equalTo(2))
                .body(matchesJsonSchemaInClasspath("schemas/user-list-schema.json"));
    }

    @Test
    public void testSingleUserSchema() {
        given(requestSpec)
                .when()
                .get("/users/2")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"));
    }

    @Test
    public void testCreateUserSchema() {
        String requestBody = """
                {
                  "name": "Nguyen Van A",
                  "job": "QA Engineer"
                }
                """;

        given(requestSpec)
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .spec(responseSpec)
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schemas/create-user-schema.json"));
    }
}