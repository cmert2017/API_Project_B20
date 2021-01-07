package day04;

import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.*;



public class SpartanUpdatingTest {

    @BeforeAll
    public static void setUp() {
        baseURI = "http://54.234.200.137:8000";
        basePath = "/api";
    }

    @AfterAll
    public static void tearDown() {
        reset();
    }


    @DisplayName("Testing PUT /api/spartans/{id} with string body")
    @Test
    public void testUpdatingSingleSpartanWithStringBody(){
        String updateStrPayload = "{\n" +
                "    \"name\": \"spartaliKamil\",\n" +
                "    \"gender\": \"Male\",\n" +
                "    \"phone\": 2342342341\n" +
                "}";

        given()
                .log().all()
                .header("Authorization","Basic YWRtaW46YWRtaW4=")
                //.auth().basic("admin","admin")
                .pathParam("id",1)
                .contentType(ContentType.JSON)
                .body(updateStrPayload).
        when()
                .put("/spartans/{id}").
        then()
                .statusCode(is(204))
                .header("Date",is(notNullValue()))
                .body(emptyString())
        ;

    }


    @DisplayName("Testing Patch /api/spartans/{id} with string body")
    @Test
    public void testPartialUpdatingSingleSpartanWithStringBody(){

        String patchBody = "{\"name\": \"Tarzan\"}";

        given()
                .log().all()
                .auth().basic("admin","admin")
                .contentType(ContentType.JSON)
                .pathParam("id",1)
                .body(patchBody).
        when()
                .patch("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(204)
                .body(emptyString())

        ;


    }

    @DisplayName("Testing Delete /api/spartans/{id} ")
    @Test
    public void testDeletingSingleSpartanWithStringBody(){


        given()
                .log().all()
                .auth().basic("admin","admin")
                .pathParam("id",1).

        when()
                .delete("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(204)
                .body(emptyString())

        ;


    }








}

