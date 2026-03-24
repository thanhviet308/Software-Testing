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
                // reqres.in is protected by Cloudflare challenges (HTML/403) in this
                // environment.
                // Use WireMock so tests remain runnable and deterministic.
                wireMockServer = new WireMockServer(options().dynamicPort());
                wireMockServer.start();
                configureFor("localhost", wireMockServer.port());

                // ===== /api/login stubs =====
                stubFor(post(urlEqualTo("/api/login"))
                                .withRequestBody(equalToJson(
                                                "{\"email\":\"eve.holt@reqres.in\",\"password\":\"cityslicka\"}", true,
                                                false))
                                .willReturn(aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-Type", "application/json")
                                                .withBody("{\"token\":\"mock-token-123\"}")));

                stubFor(post(urlEqualTo("/api/login"))
                                .withRequestBody(equalToJson("{\"email\":\"eve.holt@reqres.in\"}", true, false))
                                .willReturn(aResponse()
                                                .withStatus(400)
                                                .withHeader("Content-Type", "application/json")
                                                .withBody("{\"error\":\"Missing password\"}")));

                stubFor(post(urlEqualTo("/api/login"))
                                .withRequestBody(equalToJson("{\"password\":\"cityslicka\"}", true, false))
                                .willReturn(aResponse()
                                                .withStatus(400)
                                                .withHeader("Content-Type", "application/json")
                                                .withBody("{\"error\":\"Missing email or username\"}")));

                stubFor(post(urlEqualTo("/api/login"))
                                .withRequestBody(equalToJson(
                                                "{\"email\":\"notexist@reqres.in\",\"password\":\"wrongpass\"}", true,
                                                false))
                                .willReturn(aResponse()
                                                .withStatus(400)
                                                .withHeader("Content-Type", "application/json")
                                                .withBody("{\"error\":\"user not found\"}")));

                stubFor(post(urlEqualTo("/api/login"))
                                .withRequestBody(equalToJson("{\"email\":\"invalid-email\",\"password\":\"pass123\"}",
                                                true, false))
                                .willReturn(aResponse()
                                                .withStatus(400)
                                                .withHeader("Content-Type", "application/json")
                                                .withBody("{\"error\":\"user not found\"}")));

                // ===== /api/register stubs =====
                stubFor(post(urlEqualTo("/api/register"))
                                .withRequestBody(equalToJson(
                                                "{\"email\":\"eve.holt@reqres.in\",\"password\":\"pistol\"}", true,
                                                false))
                                .willReturn(aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-Type", "application/json")
                                                .withBody("{\"id\":4,\"token\":\"register-token-456\"}")));

                stubFor(post(urlEqualTo("/api/register"))
                                .withRequestBody(equalToJson("{\"email\":\"sydney@fife\"}", true, false))
                                .willReturn(aResponse()
                                                .withStatus(400)
                                                .withHeader("Content-Type", "application/json")
                                                .withBody("{\"error\":\"Missing password\"}")));

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