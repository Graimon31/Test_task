import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationTest {

    @Test
    public void shouldReturnJwtTokenWhenLogin() {
        // Установка базового URI
        RestAssured.baseURI = "https://online.omnicomm.ru";

        // Тело запроса
        String loginPayload = "{ \"login\": \"rudemoru\", \"password\": \"rudemo123456\" }";

        // Выполнение POST запроса для аутентификации
        Response response = given()
                .header("Content-Type", "application/json")
                .body(loginPayload)
                .post("/auth/login")
                .thenReturn();

        // Проверка статуса ответа
        assertEquals(200, response.getStatusCode(), "Status code should be 200");

        // Извлечение JWT токена из ответа
        String jwtToken = response.jsonPath().getString("jwt");
        assertNotNull(jwtToken, "JWT token should not be null");

        // Проверка формата JWT токена
        assertTrue(jwtToken.matches("^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*$"), "JWT token does not match expected pattern");
    }
}
