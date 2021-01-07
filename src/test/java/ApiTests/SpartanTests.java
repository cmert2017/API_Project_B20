package ApiTests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.testng.Assert;
import static org.hamcrest.Matchers.*;


public class SpartanTests {
    String spartanBaseUrl = "http://3.90.84.219:8000";
    String basicAuthentication = "Basic YWRtaW46YWRtaW4=";

 /*   @Test
    public  void hamcrestTest(){
        //Assert.assertEquals(5+9,is(14));
        MatcherAssert.assertThat( 5 + 9 ,  is(14)   );

    }


*/


/**
     * When user send GET request to api/spartans end point
     * Then status code must be 200
     * And body should contains Allen
     */

    @Test
    public void viewSpartanTest1(){
      //413 payload too large
        Response response = RestAssured.given().header("Authorization",basicAuthentication)
                .when().get(spartanBaseUrl + "/api/spartans/103");

        System.out.println(response.statusCode());
        System.out.println(response.body().prettyPrint());

        Assert.assertEquals(response.statusCode(),200,"Test status code failed!!!");
        Assert.assertTrue(response.body().toString().contains("Allen"));


    }


/**
     * When user send GET request to api/spartans end point
     * Then status code must be 200
     * And body should contains Allen
     */

    @Test
    public void viewSpartanTest2(){
        Response response = RestAssured.given().header("Authorization",basicAuthentication)
                .get(spartanBaseUrl + "/api/spartans");
        //verify  status code is 200
        Assert.assertEquals(response.statusCode(),200);

        //verify body contains Allen
        Assert.assertTrue(response.body().asString().contains("Allen"));

    }



/**
     * Given accept type is Json
     * When user sends a get request to spartanAllURL
     * Then response status is 200
     * And response body should be json format
     */

    @Test
    public void spartanTest2(){

        Response response = RestAssured.given().header("Authorization",basicAuthentication)
                .given().header("Accept", ContentType.JSON)  //application/json
                .get(spartanBaseUrl + "/api/spartans/103");

            Assert.assertEquals(response.statusCode(),200,"Test status code failed!!!");
            Assert.assertEquals(response.contentType(),"application/json;charset=UTF-8");


    }






    /**
     * Given accept type is Json
     * When user sends a get request to spartanAllURL
     * Then response status is 200
     * And response body should be json format
     */
    @Test
    public void videSpartanTest3(){
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get(spartanBaseUrl+"/api/spartans");

        //verify status code is 200
        Assert.assertEquals(response.statusCode(),200);

        //verify body should be Json format
        Assert.assertEquals(response.contentType(),"application/json");

    }


}
