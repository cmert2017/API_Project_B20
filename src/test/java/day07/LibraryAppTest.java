package day07;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.BookCategory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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



    @DisplayName("Save the result of Get Dashboard Stat as Map Object")
    @Test
    public void testGetDashBoardStatAsMap(){

        JsonPath jsonPath =given()
                                .header("x-library-token",myToken).
                           when()
                                .get("/dashboard_stats").jsonPath();

        //Get the response as a map and print it out

//        Map<String,Object> responseJsonAsMap = new LinkedHashMap<>();
//        responseJsonAsMap.putAll(jsonPath.getMap(""));
        //or like below 
        Map<String,Object> responseJsonAsMap = jsonPath.getMap("");

        System.out.println("responseJsonAsMap = " + responseJsonAsMap);
        
        


    }



    @DisplayName("4. Save /get_book_categories response as POJO")
    @Test
    public void testGetBookCategoriesAsPOJO(){

        JsonPath jp = given()
                                 .log().all()
                                 .header("x-library-token",myToken).
                      when()
                                 .get("/get_book_categories").prettyPeek().jsonPath()
        ;

        List<BookCategory> listOfBooksCategories = jp.getList("",BookCategory.class);
        listOfBooksCategories.forEach(System.out::println);

    }


    @DisplayName("5. Save 5th /get_book_categories response as POJO")
    @Test
    public void testOneGetBookCategoryAsPOJO(){

        JsonPath jp = given()
                .log().all()
                .header("x-library-token",myToken).
                        when()
                .get("/get_book_categories").prettyPeek().jsonPath()
                ;

        BookCategory fifthBookCategory = jp.getObject("[4]",BookCategory.class);
        System.out.println("fifthBookCategory = " + fifthBookCategory);


    }


}
