package day06;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import utility.SpartanUtil;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import java.util.Map;

public class SelfPractice_JsonSchemaValidation {
    private static Map<String, Object> payloadMap;
    private static int newID;
    @BeforeAll
    public static void setUp() {
        //baseURI = ConfigurationReader.getProperty("spartan.base_url");
        baseURI = "http://54.90.101.103:8000";
        System.out.println(baseURI);
        basePath = "/api";
        payloadMap = SpartanUtil.getRandomSpartanRequestPayload();
    }

    @AfterAll
    public static void tearDown() {
        RestAssured.reset();
    }


    @Test
    public void test1(){

        given()
                .log().all()
                .auth().basic("admin","admin")
                .accept(ContentType.JSON)
                .pathParam("id",4).
        when()
                .get("/spartans/{id}").
        then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body(matchesJsonSchemaInClasspath("singleSpartanSchema.json"))
        ;



    }

}
