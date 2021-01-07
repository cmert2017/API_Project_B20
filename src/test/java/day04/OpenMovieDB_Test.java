package day04;

import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.*;


public class OpenMovieDB_Test {

    private static final Logger logger = LogManager.getLogger(OpenMovieDB_Test.class);

    //http://www.omdbapi.com/?t=game&apikey=8cae31e8

    @BeforeAll
    public static void setUp(){
        baseURI = "http://www.omdbapi.com";
    }

    @AfterAll
    public static void tearDown(){
        reset();

    }

    @DisplayName("Test search movie or OpenMovieDB Test")
    @Test
    public void testMovie(){

        given()
                .queryParam("t","game")
                .queryParam("apikey","8cae31e8").
        when()
                .get() //our request URL is already complete. do not need to add anything
                .prettyPeek().
        then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("Title",is("Game of Thrones"))
                .body("Ratings[0].Source",is("Internet Movie Database"))

        ;

            logger.info("hello world");

        System.out.println("logger.getLevel() = " + logger.getLevel());
        System.out.println("logger.getLevel() = " + logger.getName());
        System.out.println("logger.getLevel() = " + logger.isDebugEnabled());
        System.out.println("logger.getLevel() = " + logger.isInfoEnabled());
        System.out.println("logger.getLevel() = " + logger.traceEntry());


    }



    @DisplayName("Getting the log of request and response")
    @Test
    public void testSendingRequestAndGetTheLog(){
        given()
                .queryParam("t","monkey")
                .queryParam("apikey","8cae31e8").
                //.log().all()
                //.log().uri().
                //.log().ifValidationFails().
        when()
                .get().
        then()
                .assertThat()
                .statusLine("HTTP/1.1 200 OK")
                //.log().all()
                .log().ifValidationFails()
                .statusCode(is(201))
                .body("Plot",containsStringIgnoringCase("folktale"))

        ;

    }

}
