package testbase;

import org.junit.jupiter.api.*;
import utility.*;

import static io.restassured.RestAssured.*;

public class HR_ORDS_TestBase {

    //http://54.90.101.103:1000/ords/hr/countries/AR
    @BeforeAll
    public static void setUp(){
        // baseURI = "http://54.90.101.103:1000";
        baseURI = ConfigurationReader.getProperty("ords.baseURI");
        basePath = ConfigurationReader.getProperty("ords.basePath") ;

        //create DB  connection here

        DB_Utility.createConnection(ConfigurationReader.getProperty("hr.database.url"),
        ConfigurationReader.getProperty("hr.database.username"),
        ConfigurationReader.getProperty("hr.database.password")
        );


    }

    @AfterAll
    public static void tearDown(){
        reset();
        DB_Utility.destroy();
    }

}
