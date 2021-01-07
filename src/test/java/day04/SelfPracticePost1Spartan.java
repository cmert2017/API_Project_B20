package day04;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class SelfPracticePost1Spartan {

    @BeforeAll
    public static void setup(){
        baseURI = "http://100.26.101.158";
        port = 8000;
        basePath ="/api";
        //ip:port
    }



    Spartans spartan1 = new Spartans("SpartanOmer444","Male",1234567890);

    @Test  @DisplayName("post 1spartan with JavaObject POJO")
    public void post1SpartanWithJavaObject(){

        given()
                .contentType(ContentType.JSON)
                .body(spartan1).

        when()
                .post("/spartans")
                .prettyPeek().
        then()
                .statusCode(201);

    }


    @Test  @DisplayName("post 1spartan with JavaMap")
    public void post1SpartanWithMap(){

        Map<String,Object> spartan1Map = new LinkedHashMap<>();
        spartan1Map.put("name","SpartaliOmer5555");
        spartan1Map.put("gender","Male");
        spartan1Map.put("phone",12345678911l);
        System.out.println(spartan1Map);

        given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(spartan1Map).

        when()
                .post("/spartans").
               //.prettyPeek().
        then()
                .statusCode(201)
                .log().all();

    }


        File f1 = new File("Spartan1");

    @Test  @DisplayName("post 1spartan with a file")
    public void post1SpartanWithFile(){


        given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(f1).

                when()
                .post("/spartans").
                //.prettyPeek().
                        then()
                .statusCode(201)
                .log().all();

    }







}
