package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PerformanceAssertionTest extends ApiBaseTest {

    @DataProvider(name = "apiSlaData")
    public Object[][] apiSlaData() {
        return new Object[][] {
                { "GET", "/users", 200, 2000L },
                { "GET", "/users/2", 200, 1500L },
                { "POST", "/users", 201, 3000L },
                { "POST", "/login", 200, 2000L },
                { "DELETE", "/users/2", 204, 1000L }
        };
    }

    @Step("Gọi {method} {endpoint} - SLA: {maxMs}ms")
    public Response callApi(String method, String endpoint, long maxMs) {
        switch (method) {
            case "GET":
                return given(requestSpec)
                        .when()
                        .get(endpoint)
                        .then()
                        .extract()
                        .response();

            case "POST":
                if (endpoint.equals("/users")) {
                    return given(requestSpec)
                            .body("""
                                    {
                                      "name": "Nguyen Van A",
                                      "job": "QA Engineer"
                                    }
                                    """)
                            .when()
                            .post(endpoint)
                            .then()
                            .extract()
                            .response();
                } else if (endpoint.equals("/login")) {
                    return given(requestSpec)
                            .body("""
                                    {
                                      "email": "eve.holt@reqres.in",
                                      "password": "cityslicka"
                                    }
                                    """)
                            .when()
                            .post(endpoint)
                            .then()
                            .extract()
                            .response();
                }
                break;

            case "DELETE":
                return given(requestSpec)
                        .when()
                        .delete(endpoint)
                        .then()
                        .extract()
                        .response();
        }

        throw new IllegalArgumentException("Method không hỗ trợ: " + method);
    }

    @Test(dataProvider = "apiSlaData")
    public void testSlaForApis(String method, String endpoint, int expectedStatus, long maxMs) {
        Response response = callApi(method, endpoint, maxMs);

        long responseTime = response.time();

        System.out.println("[SLA] " + method + " " + endpoint + " -> "
                + responseTime + " ms");

        Assert.assertEquals(response.getStatusCode(), expectedStatus,
                "Sai status code tại " + endpoint);

        Assert.assertTrue(responseTime < maxMs,
                "Response time " + responseTime + " ms vượt SLA " + maxMs + " ms");

        if (method.equals("GET") && endpoint.equals("/users")) {
            response.then().body("data.size()", greaterThanOrEqualTo(1));
        }

        if (method.equals("GET") && endpoint.equals("/users/2")) {
            response.then().body("data.id", equalTo(2));
        }

        if (method.equals("POST") && endpoint.equals("/users")) {
            response.then().body("id", notNullValue());
        }

        if (method.equals("POST") && endpoint.equals("/login")) {
            response.then().body("token", not(emptyOrNullString()));
        }

        if (method.equals("DELETE") && endpoint.equals("/users/2")) {
            Assert.assertEquals(response.getBody().asString(), "",
                    "Body của DELETE phải rỗng");
        }
    }

    @Test
    public void testMonitoringUsersApi10Times() {
        long total = 0;
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;

        for (int i = 1; i <= 10; i++) {
            Response response = given(requestSpec)
                    .when()
                    .get("/users")
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();

            long time = response.time();
            total += time;

            if (time < min) {
                min = time;
            }

            if (time > max) {
                max = time;
            }

            System.out.println("[Monitor] Lần " + i + ": " + time + " ms");
        }

        double average = (double) total / 10;

        System.out.println("===== KẾT QUẢ MONITORING GET /users =====");
        System.out.println("Average: " + average + " ms");
        System.out.println("Min: " + min + " ms");
        System.out.println("Max: " + max + " ms");

        Assert.assertTrue(average < 3000, "Average response time vượt 3000ms");
    }
}