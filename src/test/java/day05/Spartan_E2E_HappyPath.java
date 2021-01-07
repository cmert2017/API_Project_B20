package day05;

import io.restassured.RestAssured.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;
import utility.ConfigurationReader;
import utility.SpartanUtil;

import java.util.Map;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Spartan CRUD Testing")
public class Spartan_E2E_HappyPath {

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
        reset();
    }


    @DisplayName("1. Testing POST /api/spartans Endpoint  ")
    @Test
    public void testAddData() {

        newID =
                given()
                        .log().all()
                        .auth().basic("admin", "admin")
                        .contentType(ContentType.JSON)
                        .body(payloadMap).
                        when()
                        .post("/spartans").
                        then()
                        .log().ifValidationFails()
                        .assertThat()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .body("data.name", is(payloadMap.get("name")))
                        .body("data.gender", is(payloadMap.get("gender")))
                        .body("data.phone", is(payloadMap.get("phone")))
                        .extract()
                        .jsonPath()
                        .getInt("data.id");
        ;

        System.out.println("newID = " + newID);


    }

    @DisplayName("2. Testing GET /api/spartans/{id} Endpoint")
    @Test
    public void testGetData() {


        given()
                .log().all()
                .accept(ContentType.JSON)
                .auth().basic("admin", "admin")
                .pathParam("id", newID).
                when()
                .get("/spartans/{id}").
                then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", is(newID))
                .body("name", is(payloadMap.get("name")))
                .body("gender", is(payloadMap.get("gender")))
                .body("phone", is(payloadMap.get("phone")))

        ;


    }

    @DisplayName("3. Testing PUT /api/spartans/{id} Endpoint")
    @Test
    public void testPUTData() {

        payloadMap = SpartanUtil.getRandomSpartanRequestPayload();

        given()
                .log().all()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .auth().basic("admin", "admin")
                .pathParam("id", newID)
                .body(payloadMap).
                when()
                .put("/spartans/{id}").
                then()
                .log().all()
                .assertThat()
                .statusCode(is(204))
                .body(emptyString())

        ;

        //by GET request lets make verification of PUT

        given()
                .log().all()
                .accept(ContentType.JSON)
                .auth().basic("admin", "admin")
                .pathParam("id", newID).
        when()
                .get("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", is(newID))
                .body("name", is(payloadMap.get("name")))
                .body("gender", is(payloadMap.get("gender")))
                .body("phone", is(payloadMap.get("phone")))

        ;


    }


    @DisplayName("4. Testing DELETE /api/spartans/{id} Endpoint")
    @Test
    public void testDeleteData() {


        given()
                .log().all()
                .accept(ContentType.JSON)
                .auth().basic("admin", "admin")
                .pathParam("id",newID).
        when()
                .delete("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(204)
                .body(emptyString())
        ;


        //lets check the result of delete by get endpoint

        given()
                .log().all()
                .accept(ContentType.JSON)
                .auth().basic("admin", "admin")
                .pathParam("id", newID).
        when()
                .get("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(404))
        ;


    }
}