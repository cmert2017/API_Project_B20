package day10;

import com.github.javafaker.Faker;
import io.restassured.filter.log.LogDetail;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pojo.Spartan;
import utility.ConfigurationReader;
import utility.SpartanUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class SpartanWithReusableSpecForAdminRoleTest {
    static RequestSpecification givenSpec;
    static ResponseSpecification thenSpec;
    static ResponseSpecification postResponseSpec;
    static RequestSpecification postReqSpec;
    static Spartan randomSpartanPayload ;


    @BeforeAll
    public static void setUp(){
        baseURI= ConfigurationReader.getProperty("spartan.base_url");
        basePath="/api";
        givenSpec = given()
                .log().all()
                .auth().basic("admin","admin");

        thenSpec = expect().logDetail(LogDetail.ALL)
                .statusCode(is(200))
                .contentType(ContentType.JSON);

        randomSpartanPayload = SpartanUtil.getRandomSpartanPOJO_Payload();

        postReqSpec =   given().spec(givenSpec)
                .contentType(ContentType.JSON)
                .body(randomSpartanPayload);

        postResponseSpec = expect().logDetail(LogDetail.ALL)
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("success",is("A Spartan is Born!"))
                .body("data.id",is(not(equalTo(null))))
                .body("data.id",isA(int.class))
                .body("data.id",not(notANumber()))
                .body("data.id",notNullValue())
                .body("data.name",is(randomSpartanPayload.getName()))
                .body("data.gender",is(randomSpartanPayload.getGender()))
                .body("data.phone",is(randomSpartanPayload.getPhone()));

    }

    @AfterAll
    public static void cleanUp(){
        reset();
    }

    @DisplayName("GET /api/spartans/{id} Endpoint Test")
    @Test
    public void testOneSpartan(){

        given()
                .spec(givenSpec)
                .pathParam("id",33).
        when()
                .get("/spartans/{id}").
        then()
                .spec(thenSpec);

        //alternative way of writing above thing
        givenSpec
                .pathParam("id",33).
        when()
                .get("/spartans/{id}").
        then()
                .spec(thenSpec);

    }


    @DisplayName("POST /api/spartans Endpoint Test")
    @Test
    public void testPostOneSpartan(){

    given()
            .spec(postReqSpec).
    when()
            .post("/spartans").
    then()
            .spec(postResponseSpec);

    }


    @DisplayName("GET /api/spartans check response time <1 second")
    @Test
    public void testResponseTime(){
        given()

                .spec(givenSpec).
                when()
                .get("/spartans").
                then()
                .spec(thenSpec)
                //.time(is(lessThan(1L)), TimeUnit.SECONDS);
                .time(is(lessThan(1700L)));



    }


     /*
     verify the errors field has value of json array with 3 items
     verify default messages for those errors :
     "Gender should be either Male or Female"
     "name should be at least 2 character and max 15 character"
     "Phone number should be at least 10 digit and UNIQUE!!"
     verify the message field contains "Error count: 3"
     */
    @DisplayName("Test POST /api/spartans Endpoint negative scenario ")
    @Test
    public void testBadRequest400responseBody(){

        Spartan badPayload = new Spartan("A","Mal",345l);
        String nameErrorMessage = "name should be at least 2 character and max 15 character";
        String phoneErrorMessage = "Phone number should be at least 10 digit and UNIQUE!!";
        String genderErrorMessage = "Gender should be either Male or Female";
        given()
                .spec(postReqSpec)
                .body(badPayload).
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode(400)
                .body("errors",hasSize(3))
//                .body("errors[0].defaultMessage",is("Gender should be either Male or Female"))
//                .body("errors[1].defaultMessage",is("Phone number should be at least 10 digit and UNIQUE!!"))
//                .body("errors[2].defaultMessage",is("name should be at least 2 character and max 15 character"))
                .body("errors.defaultMessage",containsInAnyOrder(nameErrorMessage,genderErrorMessage,phoneErrorMessage))
                .body("message",containsString("Error count: 3"));
    }



    @DisplayName("GET /api/spartans Endpoint Test")
    @Test
    public void testAllSpartan(){

        given()

                .spec(givenSpec).
        when()
                .get("/spartans").
        then()
                .spec(thenSpec);

    }



    //self Practice
    // challenge yourself to parameterize this test
    // with csv file source with different error count
    // csv should have columns including name, gender, phone
    // add any additional column needed to make it easy.
    @DisplayName("Test POST /api/spartans Endpoint negative scenario: {0}--{1}--{2}")
    @ParameterizedTest
    @CsvFileSource(resources = "/singlePostSpartan.csv",numLinesToSkip = 1)
    public void testBadRequest400responseBody_2(String name, String gender, long phone){
        Spartan badPayload = new Spartan(name,gender,phone);
        String nameErrorMessage = "name should be at least 2 character and max 15 character";
        String phoneErrorMessage = "Phone number should be at least 10 digit and UNIQUE!!";
        String genderErrorMessage = "Gender should be either Male or Female";
        String allErrorMessages = nameErrorMessage + phoneErrorMessage + genderErrorMessage;
        ValidatableResponse rs = given()
                .spec(postReqSpec)
                .body(badPayload).

                        when()
                              .post("/spartans").
                        then()
                              .log().all()
                              .statusCode(400);

        JsonPath jp = rs.extract().jsonPath();
        String errorCount = jp.getString("errors.size()");
        System.out.println("errorCount = " + errorCount);


        List<String> defaultErrorMessages = jp.getList("errors.defaultMessage");
        defaultErrorMessages.forEach(System.out::println);

        System.out.println("errorMessage: "+jp.getString("message"));

    }
       /* List<String> errorFields = jp.getList("errors.field");
        System.out.println("errorFields = " + errorFields);
        for (String errorField : errorFields) {
            if(errorField.equals("name")){

            }
        }*/
               /* String errorMessage = jp.getString("message");
                System.out.println("errorMessage = " + errorMessage);

                rs.body("errors.defaultMessage.toString()",is(errorMessage));*/
               /* //anyOf() or allOf() or anything() or everyItem()
                 rs.body("errors.defaultMessage.toString()",anyOf(containsString(nameErrorMessage),containsString(genderErrorMessage),containsString(phoneErrorMessage)));
*/




    //Department Of Transportation Public API for Practice
    @DisplayName("Test GET /api/vehicles/GetAllManufacturers?format=xml&page=2 Endpoint and Catch the Mfr_ID and Test /vehicles/GetManufacturerDetails/Mfr_ID Endoint")
    @Test
    public void testAllManufacturers(){
        Faker faker = new Faker();
        int randomPageNumber = faker.number().numberBetween(1,196);

        XmlPath xp =
                           given()
                                   .log().uri()
                                   .baseUri("https://vpic.nhtsa.dot.gov")
                                   .basePath("/api")
                                   .queryParam("format","xml")
                                   .queryParam("page",randomPageNumber).
                           when()
                                   .get("/vehicles/GetAllManufacturers").prettyPeek().
                           then()
                                   .log().all()
                                   .statusCode(200)
                                   .extract()
                                   .xmlPath()
                           ;

        int numberOfManufacturersOnThisPage = xp.getInt("Response.Count");
        System.out.println("numberOfManufacturersOnThisPage = " + numberOfManufacturersOnThisPage);
        int randomManufacturerOnThisPage = faker.number().numberBetween(1, numberOfManufacturersOnThisPage);
        System.out.println("randomManufacturerOnThisPage = " + randomManufacturerOnThisPage);
        int randomManufacturersMfr_ID = xp.getInt("Response.Results.Manufacturers[" + randomManufacturerOnThisPage + "].Mfr_ID");
        System.out.println("randomManufacturersMfr_ID = " + randomManufacturersMfr_ID);


        //Using the captured Mfr_ID Get Manufacturer Details by /vehicles/GetManufacturerDetails/Mfr_ID
    XmlPath xp1 =
        given()
                .log().uri()
                .baseUri("https://vpic.nhtsa.dot.gov")
                .basePath("/api")
                .pathParam("Mfr_ID",randomManufacturersMfr_ID).
        when()
                .get("/vehicles/GetManufacturerDetails/{Mfr_ID}").
        then()
                .log().all()
                .statusCode(200)
                .extract()
                .xmlPath()
        ;

        String Mfr_Name = xp1.getString("Response.Results.ManufacturerDetails.Mfr_Name");
        System.out.println("Mfr_Name = " + Mfr_Name);

    }







}
