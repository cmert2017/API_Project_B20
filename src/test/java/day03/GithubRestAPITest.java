package day03;


import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;


public class GithubRestAPITest {


    //create  a test for testing Github rest APi users/user endpoint

    @DisplayName("Test Github users/{username} ")
    @Test
    public void testGithub(){

        given()
                .accept(ContentType.JSON)
                .pathParam("username","cmert2017").
                // .pathParam("balbla","cmert2017").

        when()
                .get("https://api.github.com/users/{username}").
                //.get("https://api.github.com/users/{blabla}"). // it will work

        then()
                .assertThat()
                .statusCode(is(200))
                .contentType("application/json")
                .header("server","GitHub.com")
                .body("login",is("cmert2017"));
        ;




    }



}
