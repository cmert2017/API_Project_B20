package day09;

import org.junit.jupiter.api.*;
import pojo.Country;
import testbase.HR_ORDS_TestBase;
import utility.DB_Utility;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class ORDS_API_DB_PracticeTest extends HR_ORDS_TestBase {

    @DisplayName("GET /countries/{country_id} Compare Result with DB")
    @Test
    public void testResponseMatchDatabaseData(){

        
        String myCountryID = "AR";
        
            Country arPOJO = given()
                                    .pathParam("country_id",myCountryID).
                              when()
                                    .get("/countries/{country_id}")
                                    .as(Country.class);
            
            //here is the shorter way of above code
        Country arPOJO1 = get("/countries/{country_id}",myCountryID).as(Country.class);


        System.out.println("arPOJO = " + arPOJO);


        DB_Utility.runQuery("SELECT * FROM COUNTRIES WHERE COUNTRY_ID ='"+myCountryID+"'" );
        Map<String, String> dBResultMap = DB_Utility.getRowMap(1);


        assertThat(arPOJO.getCountry_id(),is(dBResultMap.get("COUNTRY_ID")));
        assertThat(arPOJO.getCountry_name(),is(dBResultMap.get("COUNTRY_NAME")));

        assertThat(arPOJO.getRegion_id(),is(Integer.valueOf(dBResultMap.get("REGION_ID"))));


    }

    @DisplayName("GET /countries Capture All CountryID and Compare Result with DB")
    @Test
    public void testResponseAllCountryIDsMatchDatabaseData(){

        List<String> listOfCountyIDs = get("/countries").jsonPath().getList("items.country_id");

        System.out.println("listOfCountyIDs = " + listOfCountyIDs);
        listOfCountyIDs.forEach(System.out::println);


        DB_Utility.runQuery("SELECT * FROM COUNTRIES");
        List<String> dbListOfCounty_ID = DB_Utility.getColumnDataAsList("country_id");

        assertThat(listOfCountyIDs,is(dbListOfCounty_ID));

    }



}
