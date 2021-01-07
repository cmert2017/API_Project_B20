package day06;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import io.restassured.http.ContentType;
import pojo.Spartan;
import utility.ConfigurationReader;
import utility.SpartanUtil;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;


public class PostWithCustomObject {

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

    
    @DisplayName("Add 1 Data with POJO as body")
    @Test 
    public void testAddDataWithPojo(){

        //Spartan sp1 = new Spartan("tarkan","Male",1231231230l);
        Spartan sp1 = SpartanUtil.getRandomSpartanPOJO_Payload();

        System.out.println("sp1 = " + sp1);
        System.out.println("ConfigurationReader.getProperty(\"spartan.base_url\") = " + ConfigurationReader.getProperty("spartan.base_url"));

        given()
                .auth().basic("admin","admin")
                .log().all()
                .contentType(ContentType.JSON)
                .body(sp1).
        when()
                .post("/spartans").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(201))
        ;

        
    }






}
