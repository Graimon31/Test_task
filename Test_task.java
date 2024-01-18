import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    public void testLogin() {
        RestAssured.baseURI = "https://online.omnicomm.ru";
        String requestBody = "{ \"login\": \"rudemoru\", \"password\": \"rudemo123456\" }";

        Response response = given()
                                .header("Content-Type", "application/json")
                                .body(requestBody)
                            .when()
                                .post("/auth/login")
                            .then()
                                .statusCode(200)
                                .extract()
                                .response();

        String jwt = response.jsonPath().getString("jwt");
        assertNotNull(jwt, "JWT token is null");

        // Дополнительно можно проверить формат JWT токена
        assertTrue(jwt.matches("^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*$"), "JWT token does not match expected pattern");
    }
}
