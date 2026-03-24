package integration;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

public class ApiUiIntegrationTest extends BaseUiTest {

    private String token;
    private boolean apiLoginPassed;
    private boolean isApiAlive;

    private static WireMockServer wireMockServer;

    private void configureApiBase() {
        ensureApiStubRunning();

        // Ensure localhost calls don't get routed via proxy and avoid IPv6 (::1)
        // resolution issues.
        System.setProperty("http.nonProxyHosts", "localhost|127.0.0.1");
        System.setProperty("https.nonProxyHosts", "localhost|127.0.0.1");

        RestAssured.baseURI = "http://127.0.0.1:" + wireMockServer.port();
        RestAssured.basePath = "/api";
    }

    @BeforeClass(alwaysRun = true)
    public void startApiStub() {
        ensureApiStubRunning();
    }

    private static synchronized void ensureApiStubRunning() {
        if (wireMockServer == null) {
            wireMockServer = new WireMockServer(options().dynamicPort());
        }

        if (!wireMockServer.isRunning()) {
            wireMockServer.start();
        }

        configureFor("127.0.0.1", wireMockServer.port());

        // Register stubs (idempotent for this test suite).
        stubFor(post(urlEqualTo("/api/login"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"token\":\"mock-token\"}")));

        stubFor(get(urlEqualTo("/api/users"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"data\":[{\"id\":1}]}")));
    }

    @AfterClass(alwaysRun = true)
    public void stopApiStub() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void setupApiConfig() {
        // Keep defaults configured, but do not rely on @BeforeMethod order.
        configureApiBase();
    }

    // ===== PHẦN A: API Login Precondition =====
    @BeforeMethod(onlyForGroups = { "partA" })
    public void loginViaApiPrecondition() {
        configureApiBase();
        Response response = given()
                .contentType("application/json")
                .body("""
                        {
                          "email": "eve.holt@reqres.in",
                          "password": "cityslicka"
                        }
                        """)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .body("token", not(emptyOrNullString()))
                .extract()
                .response();

        token = response.jsonPath().getString("token");
        apiLoginPassed = token != null && !token.isEmpty();

        System.out.println("[API PRECONDITION] Token = " + token);
    }

    @Test(groups = { "partA" })
    public void testUiLoginOnlyWhenApiPreconditionPass() {
        if (!apiLoginPassed) {
            throw new SkipException("API login fail -> bỏ qua UI test");
        }

        SauceDemoPage page = new SauceDemoPage(driver);

        // Bước UI: mở trang đăng nhập
        page.openLoginPage();

        // Bước UI: đăng nhập bằng form thật
        page.login("standard_user", "secret_sauce");

        // Assertion: xác minh kết quả sau đăng nhập
        Assert.assertTrue(page.getCurrentUrl().contains("inventory"),
                "URL sau đăng nhập phải chứa inventory");
        Assert.assertEquals(page.getPageTitle(), "Swag Labs",
                "Title trang phải là Swag Labs");
    }

    // ===== PHẦN B: API Health Check -> UI Flow =====
    @BeforeMethod(onlyForGroups = { "partB" })
    public void checkApiAlive() {
        configureApiBase();
        Response response = given()
                .when()
                .get("/users")
                .then()
                .extract()
                .response();

        isApiAlive = response.getStatusCode() == 200;

        System.out.println("[API CHECK] /users status = " + response.getStatusCode());
    }

    @Test(groups = { "partB" })
    public void testFullUiFlowOnlyWhenApiAlive() {
        if (!isApiAlive) {
            throw new SkipException("API không sống -> bỏ qua UI test");
        }

        SauceDemoPage page = new SauceDemoPage(driver);

        // Bước UI: mở trang đăng nhập
        page.openLoginPage();

        // Bước UI: đăng nhập
        page.login("standard_user", "secret_sauce");

        // Bước UI: thêm 2 sản phẩm
        page.addFirstTwoProducts();

        // Assertion: badge giỏ hàng phải là 2
        Assert.assertEquals(page.getCartBadgeText(), "2",
                "Cart badge phải hiển thị 2 sản phẩm");

        // Bước UI: mở giỏ hàng
        page.openCart();

        // Assertion: trong giỏ có đúng 2 sản phẩm
        Assert.assertEquals(page.getCartItemCount(), 2,
                "Trong giỏ hàng phải có đúng 2 sản phẩm");
    }
}