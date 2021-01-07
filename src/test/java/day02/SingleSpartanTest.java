package day02;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class SingleSpartanTest {

    @BeforeAll
    public static void setUp(){
        baseURI = "http://100.26.101.158";
        port = 8000;
        basePath = "/api";
    }

    @AfterAll
    public static void tearDown(){
        reset();
    }


    @DisplayName("Testing GET /spartan/{id} endpoint")
    @Test
    public void testingOneSpartan(){

        given()
                .accept(ContentType.JSON).

        when()
                .get("/spartans/100").
        then()
                .statusCode(is(200))
                .header("content-type","application/json")
        ;






        given()
                .accept(ContentType.JSON)
                .pathParam("id",33)
                .log().all().
        when()
                .get("/spartans/{id}").
        then()
                .statusCode(is(200))
                .header("content-type","application/json")
        ;


        //this is the easiest one, same result
        given()
                .accept(ContentType.JSON).
                //.pathParam("id",11).
        when()
                .get("/spartans/{id}",100).
        then()
                .statusCode(is(200))
                .header("content-type","application/json")
        ;

    }



    @DisplayName("Testing GET /spartan/{id} endpoint Payload")
    @Test
    public void testingOneSpartanPayload() {

        given()
                .accept(ContentType.JSON)
               // .log().all()
                .log().everything().
        when()
                .get("/spartans/{id}",100).
        then()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                .body("id",is(100))
                .body("name",is("SpartanX"))
                .body("gender",is("Female"))
                .body("phone",is(9999999999L));

    }

}
