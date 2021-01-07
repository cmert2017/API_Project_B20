package day09;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import pojo.Country;
import testbase.HR_ORDS_TestBase;
import utility.DB_Utility;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
public class JUnit5_ParametrizedTest {

    @ParameterizedTest()
    @ValueSource(ints = {5,6,7,8,9})
    public void test1(int myNums){
        //assert 5,6,7,8,9 all less than 10

        Assertions.assertTrue(myNums < 10);

    }





    //using CSV file as source for parameterized test
    @ParameterizedTest
    @CsvFileSource(resources = "/zipcode.csv",numLinesToSkip = 1)
    public void  test2(String zip){
       // System.out.println("zip = " + zip);

        //sending request to zipcode endpoint here
        String state = get("http://api.zippopotam.us/us/{postal-code}",zip).jsonPath().getString("places.state");
        System.out.println("state = " + state);

        //sending request to zipcode endpoint here
        String place_name = get("http://api.zippopotam.us/us/{postal-code}",zip).jsonPath().getString("places[0][\"place name\"]");
        System.out.println("place_name = " + place_name);

        //alternative way
        given()
                .log().uri()
                .baseUri("http://api.zippopotam.us/")
                .pathParam("zipcode",zip).
        when()
                .get("/us/{zipcode}").
        then()
                .statusCode(200);

    }




        //country and zipcode together as parameter
    @ParameterizedTest
    @CsvFileSource(resources = "/countryzipcode.csv",numLinesToSkip = 1)
    public void  test3(String csvcountry, int csvzip) {

        given()
                .log().uri()

                .baseUri("http://api.zippopotam.us")
                .pathParam("country",csvcountry)
                .pathParam("zip",csvzip).
        when()
                .get("/{country}/{zip}").
        then()
                .statusCode(200);


    }


    @ParameterizedTest
    @CsvFileSource(resources = "/countryzipcode.csv",numLinesToSkip = 1)
    public void  test4(String csvcountry, int csvzip) {




    }


}
