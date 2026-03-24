package api;

import io.restassured.response.ValidatableResponse;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

public class LoginDataDrivenTest extends ApiBaseTest {

    @DataProvider(name = "loginScenarios")
    public Object[][] loginScenarios() {
        return new Object[][] {
                { "eve.holt@reqres.in", "cityslicka", 200, null },
                { "eve.holt@reqres.in", "", 400, "Missing password" },
                { "", "cityslicka", 400, "Missing email or username" },
                { "notexist@reqres.in", "wrongpass", 400, "user not found" },
                { "invalid-email", "pass123", 400, "user not found" }
        };
    }

    @Test(dataProvider = "loginScenarios")
    public void testLoginScenarios(String email, String password,
            int expectedStatus, String expectedError) {

        Map<String, String> body = new HashMap<>();

        if (!email.isEmpty()) {
            body.put("email", email);
        }

        if (!password.isEmpty()) {
            body.put("password", password);
        }

        ValidatableResponse response = given(requestSpec)
                .body(body)
                .when()
                .post("/login")
                .then()
                .spec(responseSpec)
                .statusCode(expectedStatus);

        if (expectedError != null) {
            response.body("error", containsString(expectedError));
        } else {
            response.body("token", not(emptyOrNullString()));
        }
    }
}