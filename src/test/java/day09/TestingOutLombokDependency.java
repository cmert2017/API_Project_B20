package day09;

import io.restassured.path.json.JsonPath;
import org.codehaus.groovy.util.StringUtil;
import org.junit.jupiter.api.*;
import pojo.Country;
import testbase.HR_ORDS_TestBase;
import utility.DB_Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import pojo.Department;

public class TestingOutLombokDependency extends  HR_ORDS_TestBase{

    @Test
    public void testDepartmentPOJO(){
    Department d = new Department();
    d.setDepartment_id(100);
    System.out.println("d.getDepartment_id() = " + d.getDepartment_id());

    }


    @DisplayName("GET /departments and save List of POJO")
    @Test
    public void testDepartmentJsonArrayToListOfPojo(){

        List<Department> allDeps = get("/departments").jsonPath().getList("items",Department.class);
        allDeps.forEach(System.out::println);
        System.out.println("######################");

       /*
       //this list is unmodifiable
        allDeps.removeIf(eachDep -> eachDep.getManager_id()==0);
        allDeps.forEach(System.out::println);
*/
        //So we need to copy whole list to a new list
        List<Department> allDepsCopy = new ArrayList<>(allDeps);
        allDepsCopy.removeIf(eachDep -> eachDep.getManager_id()==0);
        allDepsCopy.forEach(System.out::println);

    }


    @DisplayName("GET /departments and filter the result with JsonPath groovy")
    @Test
    public void testFilterResultWithGroovy(){
        JsonPath jp = get("/departments").jsonPath();

        List<Department> listOfDepartments = jp.getList("items.findAll{it.manager_id > 0 }",Department.class);
        listOfDepartments.forEach(System.out::println);

        System.out.println("################");

        List<Department> listOfDepartments1 = jp.getList("items.findAll{it.manager_id != null }",Department.class);
        listOfDepartments1.forEach(System.out::println);


        //now department name

        List<String> depNames = jp.getList("items.department_name");
        depNames.forEach(System.out::println);

        List<String> depNamesFiltered = jp.getList("items.findAll{it.manager_id != null }.department_name");

        depNamesFiltered.forEach(System.out::println);


        List<Integer> allDepIds = jp.getList("items.department_id");
        System.out.println("allDepIds = " + allDepIds);
        List<Integer> allDepIdsFiltered = jp.getList("items.department_id.findAll{it > 100}");
        System.out.println("allDepIdsFiltered = " + allDepIdsFiltered);


        List<Integer> allDepIds70To100 = jp.getList("items.department_id.findAll{it > 70 && it < 100}");
        System.out.println("allDepIds70To100 = " + allDepIds70To100);

        //get the name of the department if department_id between 70 to 100
        List<String> allDepNamesWhoseIDs70To100 = jp.getList("items.findAll{it.department_id > 70 && it.department_id < 100}.department_name");
        System.out.println("allDepNamesWhoseIDs70To100 = " + allDepNamesWhoseIDs70To100);


        int maxDepIds = jp.getInt("items.department_id.max()");
        System.out.println("maxDepIds = " + maxDepIds);

        int minDepIds = jp.getInt("items.department_id.min()");
        System.out.println("minDepIds = " + minDepIds);

        int sumDepIds = jp.getInt("items.department_id.sum()");
        System.out.println("sumDepIds = " + sumDepIds);


        String dep10 = jp.getString("items.find{it.department_id = 10}.department_name");
        System.out.println("dep10 = " + dep10);

        int sumDepIds2 = jp.getInt("items.sum{it.department_id}");
        System.out.println("sumDepIds2 = " + sumDepIds2);


        //print the 5th depID
        System.out.println(jp.getInt("items.department_id[4]"));

        //print the last depID
        System.out.println(jp.getInt("items.department_id[-1]"));

        //print depIDs between index 7 till 10
        System.out.println(jp.getList("items.department_id[7..10]"));





    }
    


}
