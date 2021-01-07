package day05;

import io.restassured.path.json.JsonPath;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

import java.util.*;

public class HamcrestCollectionSupport {

    @Test
    public void testList(){
        List<Integer> numList= Arrays.asList(1,2,3,4,5,6,7,8,9,10);

        //use hamcrest matcher to test the size of this list
        assertThat(numList,hasSize(numList.size()));


        //assert that this list contains number 9
        assertThat(numList,hasItem(9));
        assertThat(numList,hasItems(9,2));
       // assertThat(numList,hasItems(9,12));

        //how we would do in JAVA
        System.out.println("Collections.binarySearch(numList,9) gives the index of the item which is = " + Collections.binarySearch(numList, 9));
        System.out.println("numList.contains(9) = " + numList.contains(9));

        //assert all the item more than 3
        assertThat(numList,everyItem(is(greaterThan(3))));


    }


    @Test
    public void testList2(){
        List<String> allNames = Arrays.asList("Rary","Olivia","Gulbadan","Zara","Tarkan","HeroCan");


        //asserthat all names has size 8
        assertThat(allNames,hasSize(6));
        assertThat(allNames,hasItems("Tarkan","Olivia"));

        //asserthat in all the names there is a certain letter
        assertThat(allNames,everyItem(containsStringIgnoringCase("a")));


        //how to do and or in hamcrest syntax
        //allof-> and     anyof--> or
        assertThat("Murat Tekil",allOf(startsWith("M"),endsWith("l")));
        assertThat("Murat Tekil",anyOf(startsWith("z"),endsWith("3"),hasLength(11)));





    }



}
