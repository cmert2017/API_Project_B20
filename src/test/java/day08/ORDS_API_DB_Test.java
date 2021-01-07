package day08;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Region;
import testbase.HR_ORDS_TestBase;

import utility.DB_Utility;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class ORDS_API_DB_Test extends HR_ORDS_TestBase {

    @DisplayName("Testing the connection with query")
    @Test
    public void testDB_Connection() {

        DB_Utility.runQuery("Select * FROM REGIONS");
        DB_Utility.displayAllData();
    }


    @DisplayName("Testing GET /regions/{region_id} Data Match Database Data")
    @Test
    public void testRegionDataFromResponseMatchDB_Data() {

        // Response response = get("/regions/{region_id}","3");
        int myID = 3;
        Response response =
                given()
                        .pathParam("region_id", myID).
                        when()
                        .get("/regions/{region_id}").
                        then()
                        .log().body()
                        .statusCode(200)
                        .extract()
                        .response();

        Region r3 = response.as(Region.class);
        System.out.println("r3 = " + r3);

        DB_Utility.runQuery("SELECT * FROM  REGIONS where region_id= " + myID);

        Map<String, String> expectedResultMap = DB_Utility.getRowMap(1);
        System.out.println("expectedResultMap = " + expectedResultMap);


        assertThat(r3.getRegion_id() + "", is(expectedResultMap.get("REGION_ID")));
        assertThat(r3.getRegion_name(), is(expectedResultMap.get("REGION_NAME")));


    }


    @DisplayName("Testing GET /regions/{region_id} Data Match Database with Both Maps")
    @Test
    public void testRegionDataFromResponseMatchDB_Data2() {

        // Response response = get("/regions/{region_id}","3");
        int myID = 3;
        JsonPath jp =
                given()
                        .pathParam("region_id", myID).
                        when()
                        .get("/regions/{region_id}").
                        then()
                        .log().body()
                        .statusCode(200)
                        .extract()
                        .jsonPath();


        //save the response as a map object
       // Map<String, String> actualResultMap = jp.getMap("", String.class, String.class);
        //above code gives unmodifiable Map eror it doesnt let remove method work bc of string.class
        Map<String, String> actualResultMap = jp.getMap("");
        actualResultMap.remove("links");
        System.out.println("actualResultMap = " + actualResultMap);


        DB_Utility.runQuery("SELECT * FROM  REGIONS where region_id= " + myID);

        Map<String, String> expectedResultMap = DB_Utility.getRowMap(1);



        assertThat(String.valueOf(actualResultMap.get("region_id")),equalTo(expectedResultMap.get("REGION_ID")));
        assertThat(actualResultMap.get("region_name"),equalTo(expectedResultMap.get("REGION_NAME")));


        //map to map comparison second way:

        Map<String, String> actualResultMap1 = jp.getMap("",String.class,String.class);
        System.out.println("actualResultMap1 = " + actualResultMap1);


        DB_Utility.runQuery("SELECT * FROM  REGIONS where region_id= " + myID);
        Map<String, String> expectedResultMap1 = DB_Utility.getRowMap(1);



        //this fails bc of upper and lower case issue
        //java.lang.AssertionError:
        //Expected: <{REGION_ID=3, REGION_NAME=Asia}>
        //     but: was <{region_id=3, region_name=Asia}>
        //Expected :<{REGION_ID=3, REGION_NAME=Asia}>
        //Actual   :<{region_id=3, region_name=Asia}>
        assertThat(actualResultMap1,equalTo(expectedResultMap1));



    }

    @DisplayName("Testing GET /regions/{region_id} Data Match Database Data With Just value by value")
    @Test
    public void testRegionDataFromResponseMatchDB_Data3() {
        int myID = 3;
        JsonPath jp = given()
                .pathParam("region_id", myID).
                        when()
                .get("/regions/{region_id}").
                        then()
                .log().body()
                .statusCode(200)
                .extract()
                .jsonPath();


        String actualRegion_id = jp.getString("region_id");
        String actualRegion_name = jp.getString("region_name");


        DB_Utility.runQuery("SELECT REGION_ID \"region_id\",REGION_NAME  \"region_name\" FROM  REGIONS where region_id= " + myID);
       String expectedRegionID = DB_Utility.getColumnDataAtRow(1,"REGION_ID");
       String expectedRegionName = DB_Utility.getColumnDataAtRow(1,"REGION_NAME");
        System.out.println("DB_Utility.getColumnNames() = " + DB_Utility.getColumnNames());

        assertThat(actualRegion_id,is(expectedRegionID));
        assertThat(actualRegion_name,is(expectedRegionName));


    }




}