import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;


public class APITests {
    @Test
    public void firstTest(){
        Faker faker = new Faker();
        final private String userEmail = faker.internet.emailAddress();
        String userName = faker.name();

        given()
                .body({"email": userEmail "password": userName}).
        when().
                post("https://reqres.in/api/register")
        .then()
                .assertThat().statusCode(200)
                .assertThat().body(StringContains("id"));



    }
}
