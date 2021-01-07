package testbase;

import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.*;
import pojo.Spartan;
import utility.ConfigurationReader;
import utility.SpartanUtil;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

public class SpartanAdminTestBase {

    public static RequestSpecification adminReqSpec;

    @BeforeAll
    public static void setUp(){
        baseURI= ConfigurationReader.getProperty("spartan.base_url");
        basePath="/api";
        adminReqSpec = given()
                            .log().all()
                            .auth()
                            .basic(ConfigurationReader.getProperty("spartan.admin.username"),
                                   ConfigurationReader.getProperty("spartan.admin.password"));

    }

    @AfterAll
    public static void cleanUp(){
        reset();
    }


}
