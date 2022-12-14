import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;

public class APITests {
    @Test
    public void registerUserSuccess() {

        given()
                .body("{\n"
                        + "    \"email\": \"eve.holt@reqres.in\",\n"
                        + "    \"password\": \"pistol\"\n"
                        + "}")
                .contentType("application/json").
                when().
                post("https://reqres.in/api/register")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body(containsString("id"));
    }

    @Test
    public void userList() {
        when().
                get("https://reqres.in/api/users?page=1")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body(containsString("id"));
    }

    @Test
    public void userCreate() {
        Faker faker = new Faker();
        String userJob = faker.job().position();
        String userName = faker.name().name();

        given()
                .body("{\"name\": \"" + userName + "\",\"job\": \"" + userJob + "\"}")
                .contentType("application/json").
                when().
                post("https://reqres.in/api/users")
                .then()
                .assertThat().statusCode(201)
                .assertThat().body(containsString(userJob))
                .assertThat().body(containsString(userName));
    }

    @Test
    public void userLoginSuccess() {
        given()
                .body("{\n"
                        + "    \"email\": \"eve.holt@reqres.in\",\n"
                        + "    \"password\": \"cityslicka\"\n"
                        + "}")
                .contentType("application/json").
                when().
                post("https://reqres.in/api/register")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body(containsString("token"));
    }

    @Test
    public void userLoginFail() {
        given()
                .body("{\n"
                        + "    \"email\": \"eve.holt@reqres.in\",\n"
                        + "}")
                .contentType("application/json").
                when().
                post("https://reqres.in/api/register")
                .then()
                .assertThat().statusCode(400);
    }
}
