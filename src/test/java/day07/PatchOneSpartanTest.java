package day07;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import pojo.Spartan;
import utility.ConfigurationReader;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class PatchOneSpartanTest {

    @BeforeAll
    public static void setUp(){
        //RestAssured.filters().add(new AllureRestAssured() ) ;
        // baseURI = ConfigurationReader.getProperty("spartan.base_url");
        baseURI = ConfigurationReader.getProperty("spartan.base_url");
        // baseURI = "http://54.234.200.137:8000";
        basePath = "/api" ;
    }
    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName("Patching 1 data with Java Object")
    @Test
    public void testPath1DataPartialUpdateWithMap(){
        Map<String,Object> patchBodyMap = new LinkedHashMap<>();
        patchBodyMap.put("name","John");
        given()
                .auth().basic("admin","admin")
                .log().all()
                .pathParam("id",12)
                .contentType(ContentType.JSON)
                .body(patchBodyMap).

        when()
                .patch("/spartans/{id}").
        then()
                .log().all()
                .statusCode(204)
        ;



    }

    @DisplayName("Patching 1 data with Java Object")
    @Test
    public void testPath1DataPartialUpdateWthPOJO(){
        //Spartan spartan1 = new Spartan("John","Male",1231231230l);
        Spartan spartan1 = new Spartan();
        spartan1.setName("John");
        spartan1.setPhone(1231231234l);

        given()
                .auth().basic("admin","admin")
                .log().all()
                .pathParam("id",12)
                .contentType(ContentType.JSON)
                .body(spartan1).

                when()
                .patch("/spartans/{id}").
                then()
                .log().all()
                .statusCode(204)
        ;



    }
}

