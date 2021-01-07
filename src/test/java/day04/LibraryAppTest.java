package day04;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeAll;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class LibraryAppTest {

    private static String myToken;
    @BeforeAll
    public static void setUp(){
        baseURI = "http://library1.cybertekschool.com";
        basePath = "/rest/v1" ;

        //alternative way to reach MyToken before each test
        //we are going to create Library_utility class getToken("username","password")
        myToken =
                given()
                        .log().all()
                        .accept(ContentType.URLENC)
                        .formParam("email","librarian69@library")
                        .formParam("password","KNPXrm3S").
                        when()
                        .post("/login").
                        then()
                        .log().all()
                        .assertThat()
                        .statusCode(200)
                        .body("token",is(not(emptyString()))).
                        extract()
                        .body().jsonPath().getString("token")
                ;


    }
    @AfterAll
    public static void tearDown(){
        reset();
    }


    @DisplayName("Testing /login end point ")
    @Test
    public void testLibraryLogin(){

    myToken =
            given()
                    .log().all()
                    .accept(ContentType.URLENC)
                    .formParam("email","librarian69@library")
                    .formParam("password","KNPXrm3S").
            when()
                    .post("/login").
            then()
                    .log().all()
                    .assertThat()
                    .statusCode(200)
                    .body("token",is(not(emptyString()))).
            extract()
                        .body().jsonPath().getString("token")
            ;


        System.out.println("myToken = " + myToken);


    }


    @DisplayName("Testing /dashboard_stats end point ")
    @Test
    public void testDashboardStats(){


                given()
                        .log().all()
                        .header("x-library-token",myToken).
                when()
                        .get("/dashboard_stats").prettyPeek().
                then()
                        .log().all()
                        .assertThat()
                        .statusCode(200)


                ;





    }








}
