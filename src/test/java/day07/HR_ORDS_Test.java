package day07;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import pojo.Employee;
import pojo.Region;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;


public class HR_ORDS_Test {

    //Akbar's is below:
    //http://54.90.101.103:1000/ords/hr/regions/3
    //mine is below:
    //http://54.234.200.137:1000/ords/hr/regions/3
    @BeforeAll
    public static void setUp(){
        baseURI = "http://54.234.200.137:1000";
        basePath = "ords/hr" ;
    }
    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName("Testing /regions/{region_id}")
    @Test
    public void testThirdRegionIsAsia(){
        given()
                .log().all()
                .pathParam("region_id",3).
        when()
                .get("/regions/{region_id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("region_name",is("Asia"));


    }

    @DisplayName("Save GET /regions/{region_id} Response as JSON")
    @Test
    public void testSingleRegionToPOJO(){
        Response response =
        given()
                .log().all()
                .pathParam("region_id",3).
        when()
                .get("/regions/{region_id}").prettyPeek();

        JsonPath jp =  response.jsonPath();

        Region r3 = jp.getObject("",Region.class);

        System.out.println("r3 = " + r3);
        
        //without JsonPath and GetObject when there is no nested object
        Region r3_1 = response.as(Region.class);
        System.out.println("r3_1 = " + r3_1);

    }


    @DisplayName("Save GET /regions/{region_id} Response as a LIST of POJO ")
    @Test
    public void testListRegionToPOJO(){
        Response response = get("/regions").prettyPeek();

        JsonPath jp =  response.jsonPath();

        List<Region> r3 = jp.getList("items", Region.class);

        System.out.println("r3 = " + r3);
        r3.forEach(System.out::println);



    }


    @DisplayName("Save GET /employees/:employee_id Response as a POJO ")
    @Test
    public void saveGetOneEmployeeToPOJO(){
        Response response =
                            given()
                                    .log().all()
                                    .and()
                                    .pathParam("employee_id", 104).
                            when()
                                     .get("/employees/{employee_id}").prettyPeek()
                            ;
        JsonPath jsonPath = response.jsonPath();
        Employee oneEmployee = jsonPath.getObject("", Employee.class);
        System.out.println("oneEmployee = " + oneEmployee);
    }

    @DisplayName("Save GET /employees Response as a List of POJO ")
    @Test
    public void saveGetListOfEmployeeToPOJO(){
        Response response = get("/employees");
        JsonPath jsonPath = response.jsonPath();
        List<Employee> oneEmployee = jsonPath.getList("items", Employee.class);
        oneEmployee.forEach(System.out::println);
    }




}
