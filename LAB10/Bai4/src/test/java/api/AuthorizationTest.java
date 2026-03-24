package api;

import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthorizationTest extends ApiBaseTest {

    @Test
    public void testLoginSuccess() {
        String token = given(requestSpec)
                .body("""
                        {
                          "email": "eve.holt@reqres.in",
                          "password": "cityslicka"
                        }
                        """)
                .when()
                .post("/login")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("token", notNullValue())
                .body("token", not(emptyOrNullString()))
                .extract()
                .jsonPath()
                .getString("token");

        Assert.assertNotNull(token);
        Assert.assertFalse(token.isEmpty(), "Token không được rỗng");
    }

    @Test
    public void testLoginMissingPassword() {
        given(requestSpec)
                .body("""
                        {
                          "email": "eve.holt@reqres.in"
                        }
                        """)
                .when()
                .post("/login")
                .then()
                .spec(responseSpec)
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }

    @Test
    public void testLoginMissingEmail() {
        given(requestSpec)
                .body("""
                        {
                          "password": "cityslicka"
                        }
                        """)
                .when()
                .post("/login")
                .then()
                .spec(responseSpec)
                .statusCode(400)
                .body("error", equalTo("Missing email or username"));
    }

    @Test
    public void testRegisterSuccess() {
        given(requestSpec)
                .body("""
                        {
                          "email": "eve.holt@reqres.in",
                          "password": "pistol"
                        }
                        """)
                .when()
                .post("/register")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("id", notNullValue())
                .body("token", notNullValue())
                .body("token", not(emptyOrNullString()));
    }

    @Test
    public void testRegisterMissingPassword() {
        given(requestSpec)
                .body("""
                        {
                          "email": "sydney@fife"
                        }
                        """)
                .when()
                .post("/register")
                .then()
                .spec(responseSpec)
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }
}