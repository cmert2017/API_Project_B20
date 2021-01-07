package day10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testbase.SpartanAdminTestBase;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class JsonSchemaValidation extends SpartanAdminTestBase {

    @DisplayName("Testing the structure of GET /api/spartans/{id} response")
    @Test
    public void testGetSingleSpartanSchemaValidation(){

    given()
            .spec(adminReqSpec)
            .pathParam("id",34).
    when()
            .get("/spartans/{id}").
    then()
            .log().all()
            .statusCode(is(200))
            .body(matchesJsonSchemaInClasspath("singleSpartanSchema.json"));


    }


}
