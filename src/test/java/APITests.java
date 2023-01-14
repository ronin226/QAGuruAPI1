import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static specs.Spec.loginRequestSpec;
import models.lombokModel;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;

public class APITests {
    @Test
    public void registerUserSuccess() {
        lombokModel body = new lombokModel();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("pistol");

        given()
                .spec(loginRequestSpec)
                .body(body).
                when().
                post("api/register")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body(containsString("id"));
    }

    @Test
    public void userList() {
        given()
                .spec(loginRequestSpec).
                when().
                get("api/users?page=1")
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
                .spec(loginRequestSpec)
                .body("{\"name\": \"" + userName + "\",\"job\": \"" + userJob + "\"}").
                when().
                post("/api/users")
                .then()
                .assertThat().statusCode(201)
                .assertThat().body(containsString(userJob))
                .assertThat().body(containsString(userName));
    }

    @Test
    public void userLoginSuccess() {
        lombokModel body = new lombokModel();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("cityslicka");
        given()
                .spec(loginRequestSpec)
                .body(body).
                when().
                post("/api/register")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body(containsString("token"));
    }

    @Test
    public void userLoginFail() {
        lombokModel body = new lombokModel();
        body.setEmail("eve.holt@reqres.in");
        given()
                .spec(loginRequestSpec)
                .body(body).
                when().
                post("/api/register")
                .then()
                .assertThat().statusCode(400);
    }

    @Test
    void checkExistingUserEmailGroovy() {
        given(loginRequestSpec)
                .when()
                .get("/api/users")
                .then()
                .statusCode(200)
                .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()",
                        hasItem("eve.holt@reqres.in"));
    }
}
