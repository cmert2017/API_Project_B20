package day02;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;


public class SelfPractice1 {



    @Test  @DisplayName("PathParameter")
    public void test1() {
         Response response = given().accept(ContentType.JSON)
        .and().pathParam("id", 500)
        .get("http://100.26.101.158:8000/api/spartans/{id}");

         assertThat(response.statusCode(),is(404));
         assertThat(response.getContentType(),is("application/json"));
         assertThat(response.body().asPrettyString(),containsStringIgnoringCase("Not Found"));
    }


    @Test @DisplayName("Query Parameter")
    public void test2(){

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("gender", "Female")
                .queryParam("nameContains", "e")
                .get("http://100.26.101.158:8000/api/spartans/search");

        assertThat(response.statusCode(),is(200));
        assertThat(response.getContentType(),is("application/json"));
        assertThat(response.body().asString(),containsStringIgnoringCase("Female"));
        assertThat(response.body().asString(),containsStringIgnoringCase("Janette"));

    }






}
