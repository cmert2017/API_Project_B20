package day02;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

public class SelfPractice2 {

    @BeforeAll
    public static void setUp(){
        baseURI= "http://100.26.101.158:8000";
    }


    @Test
    public void test1(){
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("gender","Female");
        paramsMap.put("nameContains","a");

        Response response = given().accept(ContentType.JSON)
                .and().queryParams(paramsMap)
                .get("/api/spartans/search");

        System.out.println("response.prettyPrint() = " + response.prettyPrint());
        assertThat(response.statusCode(),is(200));
        assertThat(response.getContentType(),is("application/json"));
        assertThat(response.body().asString(),containsStringIgnoringCase("Female"));
        assertThat(response.body().asString(),containsStringIgnoringCase("a"));

    }


    @Test @Order(1)
    public void test2(){

        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 4)
                .when().get("/api/spartans/{id}");


        System.out.println("response.body().path(\"id\").toString() = " + response.body().path("id").toString());
        System.out.println("response.body().path(\"name\").toString() = " + response.body().path("name").toString());
        System.out.println("response.body().path(\"gender\").toString() = " + response.body().path("gender").toString());
        System.out.println("response.body().path(\"phone\").toString() = " + response.body().path("phone").toString());

        int id = response.body().path("id");
        String name = response.body().path("name");
        String gender = response.body().path("gender");
        int phone = response.body().path("phone");

        System.out.println("id = " + id);
        System.out.println("name = " + name);
        System.out.println("gender = " + gender);
        System.out.println("phone = " + phone);

        assertThat(id,is(4));




    }



    @Test
    public void test3(){

        Response response = get("/api/spartans");

        int firstId = response.path("id[0]");
        System.out.println("firstId = " + firstId);
        
        String first1stName = response.path("name[0]");
        System.out.println("first1stName = " + first1stName);
        
        String last1stName = response.path("name[-1]");
        System.out.println("last1stName = " + last1stName);
        
        List<String> names = response.path("name");
        System.out.println("names = " + names);
        
        List<Object> phoneNumbers = response.path("phone");
        System.out.println("phoneNumbers = " + phoneNumbers);
        System.out.println("#######################################");
        for (Object eachPhone : phoneNumbers) {
            System.out.println("eachPhone = " + eachPhone);
        }
    }

        @Test
        public void test4(){

            Response response = given().accept(ContentType.JSON)
                    .pathParam("id", 11)
                    .when().get("/api/spartans/{id}");

            //how to read value with path() method
            int id = response.path("id");
            System.out.println("id = " + id);

            //how to read value with JsonPath
            JsonPath jsonPath = response.jsonPath();
            int id1 = jsonPath.getInt("id");
            String name1= jsonPath.getString("name");
            String gender1= jsonPath.getString("gender");
            Long phoneNumber1=jsonPath.getLong("phone");

            System.out.println("phoneNumber1 = " + phoneNumber1);
            System.out.println("gender1 = " + gender1);
            System.out.println("name1 = " + name1);
            System.out.println("id1 = " + id1);

            assertThat(id1,is(11));
            assertThat(name1,is("Batch 20 Tucky"));
            assertThat(gender1,is("Male"));
            assertThat(phoneNumber1,is(1607236312L));



        }

        @Test
        public void test5(){

            given().accept(ContentType.JSON)
                    .pathParam("id",11)
                    .get("/api/spartans/{id}")
                    .then().assertThat().statusCode(200)
                    .and().assertThat().contentType("application/json")
                    .and().assertThat().body("id",equalTo(11));


        }

    

}
