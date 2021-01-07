package day05;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.*;
import utility.ConfigurationReader;

import java.util.List;

public class AssertingCollectionsInTheChain {

    @BeforeAll
    public static void setUp(){

        baseURI = ConfigurationReader.getProperty("spartan.base_url");
        basePath = "/api";

    }

    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName("Testing Get /api/spartans/search with Basic auth")
    @Test
    public void testSearchAndExtractData(){

        // search for nameContains : a , gender Female
        // verify status code is 200
        //check the size of result is some hardcoded number
        //verify all names from result contains a
        //verify all gender is Female only
        //do it in the chain

        given()
                .log().all()
                .auth().basic("admin","admin")
                .queryParam("nameContains","a")
                .queryParam("gender","Female").
        when()
                .get("/spartans/search").
        then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("numberOfElements",is(31))
                .body("content",hasSize(31))
                .body("content.name",everyItem(containsStringIgnoringCase("a")))
                .body("content.gender",everyItem(equalTo("Female")))
                ;







    }





}
