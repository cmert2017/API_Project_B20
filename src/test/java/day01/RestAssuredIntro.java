package day01;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;


public class RestAssuredIntro {

    @DisplayName("Spartan GET api/hello Endpoint Test")
    @Test
    public void TestHello(){

        Response response = get("http://100.26.101.158:8000/api/hello");

        //get status code out of this response object

        System.out.println("response.statusCode() = " + response.statusCode());

        assertThat(response.statusCode(),is(200));
        response.prettyPrint();
        System.out.println("response.asPrettyString() = " + response.asPrettyString());


        String payload = response.prettyPrint();

        assertThat(payload,is("Hello from Sparta"));

        // get the header called ContentType from response by three ways
        System.out.println("response.getHeader(\"Content-Type\") = " + response.getHeader("Content-Type"));
        System.out.println("response.getContentType() = " + response.getContentType());
        System.out.println("response.contentType() = " + response.contentType());

        assertThat(response.contentType(),is("text/plain;charset=UTF-8"));
        assertThat(response.contentType(),startsWith(ContentType.TEXT.toString()));



        assertThat(response.contentType(),containsStringIgnoringCase("text"));

        assertThat(response.contentType(),startsWith("text"));


    }


    @DisplayName("Common Matchers for Strings")
    @Test
    public void testString(){
        String str = "Rest Assured is cool so far";

        //assert the str is  "Rest Assured is cool so far"
        assertThat(str,is("Rest Assured is cool so far"));


        //assert the str is  "Rest Assured IS COOL  so far" in case insensitive manner
        assertThat(str,equalToIgnoringCase("Rest Assured IS COOL so far"));


        // assert the str startWith "Rest"
        assertThat(str,startsWith("Rest"));

        // assert the str endWith "so far"
        assertThat(str,endsWith("so far"));


        // assert the str contains "is cool"
        assertThat(str,containsString("is cool"));


        // assert the str contains "IS COOL" case insenstive manner
        assertThat(str,containsStringIgnoringCase("is cool"));


    }

}
