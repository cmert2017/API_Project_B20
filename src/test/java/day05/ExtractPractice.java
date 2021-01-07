package day05;

import io.restassured.path.json.JsonPath;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import utility.ConfigurationReader;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

import java.util.List;

public class ExtractPractice {

    /*

    extract() method of REstAssured enable you to extract data
    after validation in then section of the method chaining
     */


    @BeforeAll
    public static void setUp(){


       // baseURI = "http://54.234.200.137:8000";
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
        // extract jsonPath object after validation
        // use that jsonPath object to get the list of all results
        // and get the numberOfElements field value
        // compare those 2

        JsonPath jp =
        given()
                .log().all()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .auth().basic("admin","admin")
                .queryParam("nameContains","a")
                .queryParam("gender","Female").
        when()
                .get("/spartans/search").
        then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .extract().
                jsonPath();


        //get the list of all names in String
        List<Object> jpList = jp.getList("content.name");
        System.out.println("jpList = " + jpList);


        int numberOfElements = jp.getInt("numberOfElements");
        System.out.println("numberOfElements = " + numberOfElements);


        assertThat(jpList.size(),is(numberOfElements));


        // hamcrest matcher collection support for asserting the size

        assertThat(jpList,hasSize(numberOfElements));



    }





}
