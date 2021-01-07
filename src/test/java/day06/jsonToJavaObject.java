package day06;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import pojo.Spartan;
import pojo.SpartanRead;
import utility.ConfigurationReader;
import utility.SpartanUtil;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
public class jsonToJavaObject {
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

    @DisplayName("Get 1 Data and Save Response Json As Java Object")
    @Test
    public void testGet1SpartanAndSaveREsponseJsonAsMap(){

        Response response =
        given()
                .log().all()
                .auth().basic("admin","admin")
                .pathParam("id",5).
        when()
                .get("/spartans/{id}").prettyPeek()
        ;

         JsonPath jp = response.jsonPath();

        Map<Object, Object> responseMap = jp.getMap("");

        System.out.println("responseMap = " + responseMap);

        //how to convert from json to object
        SpartanRead sp2 = jp.getObject("",SpartanRead.class);
        System.out.println("sp2 = " + sp2);

        //another way to get pojo
        SpartanRead sp3 = response.as(SpartanRead.class);
        System.out.println("sp3 = " + sp3);

        //another way to get from json to object


    }

    @DisplayName("Get All Data and Save Response Json As Java Object")
    @Test
    public void testGetAllSpartanAndSaveREsponseJsonAsListOfMap(){

        Response response =
                given()
                        .log().all()
                        .auth().basic("admin","admin").
                        when()
                        .get("/spartans").prettyPeek()
                ;

        JsonPath jp = response.jsonPath();

        List<SpartanRead> responseList = jp.getList("", SpartanRead.class);
        System.out.println("responseList = " + responseList);

        List<Map<String ,Object>> responseList1 = jp.getList("");
        System.out.println("responseList1 = " + responseList1);


        //responseList.forEach(System.out::println);
        responseList.forEach(x-> System.out.println(x));


    }




}
