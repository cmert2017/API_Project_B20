package day02;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class SpartanSearchTest {
    @BeforeAll
    public static void setUp(){
        //RestAssured.baseURI = "http://100.26.101.158:8000";
        baseURI = "http://100.26.101.158:8000";
        RestAssured.basePath= "/api";
    }

    @AfterAll
    public static void tearDown(){
        //resetting the value of baseURL, basePath to their original values
        RestAssured.reset();
    }



    @Test  @DisplayName("Testing /api/spartans endpoint")
    public void testgetAllSpartans(){
        Response response = given().accept(ContentType.JSON)
                .get("/spartans");

        assertThat(response.statusCode(),is(200));
        response.prettyPrint();

        assertThat(response.getContentType(),is(ContentType.JSON.toString()));

    }

    @Test  @DisplayName("Testing /api/spartans endpoint")
    public void testGetAllSpartansXML(){

        /**
         * given
         *      --- RequestSpecification
         *      used to provide additional information about the request
         *      base url  base path
         *      header , query params , path variable , payload
         *      authentication authorization
         *      logging , cookie
         * when
         *      --- This is where you actually send the request with http method
         *      -- like GET POST PUT DELETE .. with the URL
         *      -- We get Response Object after sending the request
         * then
         *      -- ValidatableResponse
         *      -- validate status code , header , payload , cookie
         *      -- responseTime , structure of the payload  , logging
         */

        //whole statement can be written like below or in slightly different format below it
       /* given().header("accept","application/xml" ).
        when().get("http://100.26.101.158:8000/api/spartans").
        then().statusCode(200);*/


        given()
                .header("accept","application/xml" ).
        when()
                .get("http://100.26.101.158:8000/api/spartans").
        then()
                .assertThat()
                .statusCode(is(200))
                .and()
                .header("content-type","application/xml")
        ;


        //instead of header() we can use accept()

        given()
                .request()
                .accept(ContentType.JSON).
        when()
               .get("http://100.26.101.158:8000/api/spartans").
        then()
                .assertThat()
                .statusCode(is(200))
                .and()
                .contentType(ContentType.XML)
        ;

    }

    @Test  @DisplayName("Testing /api/spartans/search endpoint")
    public void testSearchSpartans(){
        Response response = given().accept(ContentType.JSON)
                                    .queryParam("gender","Male")
                                    .queryParam("nameContains","ea").
        when()
            .get("/spartans/search").
        then()
                .statusCode(200)
                .body("content.gender",not(containsString("Female")))
                .extract().response();



        assertThat(response.statusCode(),is(200));
        response.peek();
        List<String> genderList = response.path("content.gender");
        System.out.println("genderList = " + genderList);
        assertThat(genderList,hasItem("Male"));
        assertThat(genderList,hasSize(3));



    }

}
