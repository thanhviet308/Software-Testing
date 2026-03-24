package api;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.hamcrest.Matchers.lessThan;

public class ApiBaseTest {

        protected RequestSpecification requestSpec;
        protected ResponseSpecification responseSpec;

        private WireMockServer wireMockServer;

        @BeforeClass
        public void setup() {
                // reqres.in is protected by Cloudflare challenges (403) in this environment.
                // Use WireMock local stubs so performance/assertion tests are runnable.
                wireMockServer = new WireMockServer(options().dynamicPort());
                wireMockServer.start();
                configureFor("localhost", wireMockServer.port());

                // ===== Stubs for PerformanceAssertionTest =====
                stubFor(get(urlEqualTo("/api/users"))
                                .willReturn(aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-Type", "application/json")
                                                .withBody("{"
                                                                + "\"page\":1,"
                                                                + "\"per_page\":6,"
                                                                + "\"total\":12,"
                                                                + "\"total_pages\":2,"
                                                                + "\"data\":[{"
                                                                + "\"id\":1,"
                                                                + "\"email\":\"george.bluth@reqres.in\","
                                                                + "\"first_name\":\"George\","
                                                                + "\"last_name\":\"Bluth\","
                                                                + "\"avatar\":\"https://reqres.in/img/faces/1-image.jpg\""
                                                                + "}]"
                                                                + "}")));

                stubFor(get(urlEqualTo("/api/users/2"))
                                .willReturn(aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-Type", "application/json")
                                                .withBody("{"
                                                                + "\"data\":{"
                                                                + "\"id\":2,"
                                                                + "\"email\":\"janet.weaver@reqres.in\","
                                                                + "\"first_name\":\"Janet\","
                                                                + "\"last_name\":\"Weaver\","
                                                                + "\"avatar\":\"https://reqres.in/img/faces/2-image.jpg\""
                                                                + "}"
                                                                + "}")));

                stubFor(post(urlEqualTo("/api/users"))
                                .willReturn(aResponse()
                                                .withStatus(201)
                                                .withHeader("Content-Type", "application/json")
                                                .withBody("{"
                                                                + "\"name\":\"Nguyen Van A\","
                                                                + "\"job\":\"QA Engineer\","
                                                                + "\"id\":\"123\","
                                                                + "\"createdAt\":\"2026-03-24T00:00:00.000Z\""
                                                                + "}")));

                stubFor(post(urlEqualTo("/api/login"))
                                .willReturn(aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-Type", "application/json")
                                                .withBody("{\"token\":\"mock-token\"}")));

                stubFor(delete(urlEqualTo("/api/users/2"))
                                .willReturn(aResponse()
                                                .withStatus(204)));

                RestAssured.baseURI = "http://localhost:" + wireMockServer.port();

                requestSpec = new RequestSpecBuilder()
                                .setBaseUri(RestAssured.baseURI)
                                .setBasePath("/api")
                                .setContentType(ContentType.JSON)
                                .addHeader("Accept", "application/json")
                                .addFilter(new RequestLoggingFilter())
                                .addFilter(new ResponseLoggingFilter())
                                .build();

                responseSpec = new ResponseSpecBuilder()
                                .expectContentType(ContentType.JSON)
                                .expectResponseTime(lessThan(3000L))
                                .build();
        }

        @AfterClass(alwaysRun = true)
        public void teardown() {
                if (wireMockServer != null) {
                        wireMockServer.stop();
                }
        }
}