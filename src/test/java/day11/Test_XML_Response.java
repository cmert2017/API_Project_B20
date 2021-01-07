package day11;

import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import org.codehaus.groovy.syntax.Numbers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testbase.SpartanAdminTestBase;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Test_XML_Response extends SpartanAdminTestBase {

    //get xml response for GET /spartans

    @DisplayName("GET /spartans get xml response")
    @Test
    public void testXML(){

        XmlPath xp = given()
                                    .spec(adminReqSpec)
                                    .accept(ContentType.XML).
                         when()
                                    .get("/spartans").
                         then()
                                    .log().all()
                                    .body("list.item[-1].name",is("Faustino"))
                                    .body("list.item[-1].id",is("130"))
                                    .body("list.item[-1].id.toInteger()",is(130))
                                    //.body("list.item[-1].id",is(130))  //since in xml everything is string the number format will fail
                                    .statusCode(200)
                                    .contentType(ContentType.XML)
                                    .extract().xmlPath();
        //get the name of the first person in the response
        String name1stPerson = xp.getString("list.item[0].name");
        System.out.println("name1stPerson = " + name1stPerson);

        //get the id of the third person
        System.out.println("xp.getString(\"list.item[2].id\") = " + xp.getString("list.item[2].id"));

        //get the name of the last person
        System.out.println("xp.getString(\"list.item[-1].name\") = " + xp.getString("LiSt.item[-1].name"));


    }


    //http://ergast.com/api/f1/drivers
    @DisplayName("Test Ergast Developer API /drivers endpoint")
    @Test
    public void testDrivers(){
    String driverID =
        given()
                .baseUri("http://ergast.com")
                .basePath("/api/f1").
        when()
                .get("/drivers").
        then()
                .log().all()
                .statusCode(200)
                .body("MRData.DriverTable.Driver[0].@driverId",is("abate"))
                .body("MRData.DriverTable.Driver[0].GivenName",is("Carlo"))
                .extract()
                .xmlPath()
                .getString("MRData.DriverTable.Driver[0].@driverId")
        ;

        System.out.println("driverID = " + driverID);

        //xml attribute and element are called differently

        // Send a request to GET /drivers/:driverId endpoint using above driver id

        given()
                .baseUri("http://ergast.com")
                .basePath("/api/f1")
                .pathParam("driverId" , driverID).
        when()
                .get("/drivers/{driverId}").
        then()
                .statusCode(200)
                .body("MRData.DriverTable.Driver.GivenName",is("Carlo") ) ;


    }


    

}
