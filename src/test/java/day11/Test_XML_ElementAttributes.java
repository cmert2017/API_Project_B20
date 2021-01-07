package day11;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Test_XML_ElementAttributes {
    @DisplayName("Test http://omdbapi xml response and movie info")
    @Test
    public void testMovieAttributes(){

        Response response = given()
                            .baseUri("http://www.omdbapi.com/")
                            .queryParam("apiKey", "8cae31e8")
                            .queryParam("t", "Wonder Woman 1984")
                            .queryParam("r", "xml").
                      when()
                            .get().prettyPeek().
                      then()
                            .body("root.movie.@title",is("Wonder Woman 1984"))
                            .body("root.movie.@year",is("2020"))
                            .body("root.movie.@year.toInteger()",is(2020))
                            .extract().response();


                ;

        XmlPath xp = response.xmlPath();
        System.out.println("xp.getString(\"root.movie\") = " + xp.getString("root.movie")); //it doesnt print like this

        //we want to get title atribute of movie element
        //we use .@attribute name to access the attributes
        System.out.println("xp.getString(\"root.movie.@title\") = " + xp.getString("root.movie.@title"));

        System.out.println("xp.getString(\"root.movie.@year\") = " + xp.getString("root.movie.@year"));

    }

}
