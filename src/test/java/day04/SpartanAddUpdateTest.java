package day04;

import org.junit.jupiter.api.*;
import io.restassured.http.ContentType;

import java.io.File;
import java.util.*;


import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static io.restassured.RestAssured.*;



public class SpartanAddUpdateTest {

    String basicAuthentication = "Basic YWRtaW46YWRtaW4=";

    @BeforeAll
    public static void setUp(){
        baseURI = "http://3.90.84.219:8000";
        basePath = "/api";
    }

    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName("Testing  Get /api/spartans adding updating one spartan ")
    @Test
    public void testAllSpartansWithBasicAuth(){

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .auth().basic("admin","admin").
                //.header("Authorization",basicAuthentication).
        when()
                .get("/spartans")
                .prettyPeek().
        then()
                .statusCode(is(200));


    }


    @DisplayName("Add 1 spartan data Raw Json String Post api/spartans")
    @Test
    public void testAddOneDate(){
        String newSpartanStr = "{\n" +
                "    \"name\": \"Maye\",\n" +
                "    \"gender\": \"Male\",\n" +
                "    \"phone\": 1607752270\n" +
                "}";

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .auth().basic("admin","admin")
                .log().ifValidationFails()
                .body(newSpartanStr).
        when()
              .post("/spartans").
        then()
                .assertThat()
                .statusCode(is(201))
               // .log().ifValidationFails()
                .log().all()
                .header("Content-Type",equalToIgnoringCase("application/json"))
                .contentType("application/json")
                .body("success",is("A Spartan is Born!"))
                .body("data.name",is("Maye"))
                .body("data.gender",is("Male"))
                .body("data.phone",is(1607752270))

        ;

    }

    @DisplayName("Test 1 Spartan with Map Object Post /api/spartans")
    @Test
    public void testAddOneDataWithMapAsBody(){

        Map<String,Object> payloadMap = new LinkedHashMap<>();
        payloadMap.put("name","SpartanG");
        payloadMap.put("gender","Male");
        payloadMap.put("phone",4445556667l);

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .auth().basic("admin","admin")
                .log().ifValidationFails()
                .body(payloadMap).
                //.expect().
        when()
                .post("/spartans").
        then()
                .assertThat()
                .statusCode(is(201))
                .body("success",containsStringIgnoringCase("born"))
                .body("data.name",is("SpartanG"))
                .body("data.gender",is("Male"))
                .body("data.phone",is(4445556667l))
                .log().ifValidationFails()
        ;


    }

    @DisplayName("Add 1 data with External json file POST /api/spartans")
    @Test
    public void testAddOneDataWithJsonFileAsBody(){

        File file = new File("singleSpartan.json");

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .auth().basic("admin","admin")
                .log().ifValidationFails()
                .body(file).
                //.expect().
                        when()
                .post("/spartans").
                then()
                .assertThat()
                .statusCode(is(201))
                .body("success",containsStringIgnoringCase("born"))
                .body("data.name",is("Tarkan"))
                .body("data.gender",is("Male"))
                .body("data.phone",is(1231231232))
                .log().ifValidationFails()
        ;



    }





}
