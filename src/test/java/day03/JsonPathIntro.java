package day03;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.List;


public class JsonPathIntro {


    @BeforeAll
    public static void setUp(){
        baseURI = "http://100.26.101.158:8000";
        basePath = "/api";
    }


    @AfterAll
    public static void tearDown(){
        reset();
    }

    // send a request to get 1 spartan
// by providing path params with valid id
// save it into Response object
// NEW : create an object with type JsonPath
// by calling the method jsonPath() on response object
// extract id , name , gender , phone
// and save it into variable of correct type

    @DisplayName("Extracting data out ofSpartan Json Object")
    @Test
    public void test1SpartanPayload(){

        Response response = given()
                                    .accept(ContentType.JSON)
                                    .pathParam("id", 14).
                           when()
                                    .get("/spartans/{id}")
                                    //.prettyPeek()
                                    .peek();


        response.prettyPrint();
        response.prettyPeek();


        //JsonPath is used to navigate through the json payload
        // and extract the value according to the valid "jsonpath" provided

        JsonPath jsonPath = response.jsonPath();
        int myId = jsonPath.getInt("id");
        String myName = jsonPath.getString("name");
        String myGender = jsonPath.getString("gender");
        long myPhone = jsonPath.getLong("phone");

        System.out.println("myId = " + myId);
        System.out.println("myName = " + myName);
        System.out.println("myGender = " + myGender);
        System.out.println("myPhone = " + myPhone);
        
    }
    
    @DisplayName("Extracting data from Json array response")
    @Test
    public void getAllSpartanExtractData(){
        //short way to do the below thing is : 
        //JsonPath jp = get("/spartans").jsonPath();
        
        Response response = given()
                .accept(ContentType.JSON).
                        when()
                .get("/spartans");

        JsonPath jsonPath = response.jsonPath();
        System.out.println("jsonPath.getString(\"name\") = " + jsonPath.getString("name"));
        
        String name = jsonPath.getString("name[0]");
        System.out.println("name = " + name);

        System.out.println("jsonPath.getList(\"name\") = " + jsonPath.getList("name"));


        System.out.println("jsonPath.getString(\"gender[6]\") = " + jsonPath.getString("gender[6]"));


        //getting all the names fields from the jsonArray repsonse and storing as a list

        List<String> listOfAllNames = jsonPath.getList("name");
        System.out.println("listOfAllNames = " + listOfAllNames);

        System.out.println("##########################################");

        List<Long> listOfPhone = jsonPath.getList("phone");
        System.out.println("listOfPhone = " + listOfPhone);


    }

    //send request to this request url
    //http://100.26.101.158:8000/api/spartans/search?nameContains=de&gender=Male
    //get the name of the first guy in the result
    //get the phone of third person in the result
    //get all names
    //get all phone and save it as list
    //save the value of field called empty under pageable in the response
    //print it out
    @DisplayName("Testing /spartans/search and extracting data")
    @Test
    public void testSearch(){

        JsonPath jsonPath = given()
                                    .accept(ContentType.JSON)
                                    .queryParam("nameContains", "de")
                                    .and()
                                    .queryParam("gender", "Male").
                            when()
                                    .get("/spartans/search")
                                    .jsonPath();

        /*String name0 = jsonPath.getString("content[0].name");
        System.out.println("string = " + name0);
        long phone3 = jsonPath.getLong("content[2].phone");
        System.out.println("phone3 = " + phone3);
        List<String> allNames = jsonPath.getList("content.name");
        System.out.println("allNames = " + allNames);
        
        List<Long> allPhoneNumbers = jsonPath.getList("content.phone");
        System.out.println("allPhoneNumbers = " + allPhoneNumbers);


        boolean emptyField = jsonPath.getBoolean("pageable.sort.empty");
        System.out.println("emptyField = " + emptyField);*/

        String name5 = jsonPath.getString("content.name");
        System.out.println("name5 = " + name5);
        System.out.println(name5.charAt(2));

        List<String> allNames = jsonPath.getList("content.name");
        System.out.println("allNames = " + allNames);
        System.out.println(allNames.get(2));


    }



}
