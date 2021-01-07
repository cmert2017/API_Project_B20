package ApiTests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class SelfPractice0 {
/*

    @DisplayName("BaseURI success")
    @BeforeEach
    public void beforeEachTest() {
        String baseURI = "http://100.26.101.158:8000";
    }
*/


    @DisplayName("Spartan 69 as Map")
    @Test
    @Order(1)
    public void spartan69() {
        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 69)
                .when().get("http://100.26.101.158:8000/api/spartans/{id}");

        Map<String, Object> spartan69 = response.body().as(Map.class);
        System.out.println(spartan69);
        System.out.println("spartan69.get(\"name\") = " + spartan69.get("name"));
        System.out.println("spartan69.get(\"phone\") = " + spartan69.get("phone"));
    }


    @DisplayName("All Spartans as List of Map")
    @Test
    @Order(1)
    public void allSpartans() {
        Response response = given().accept(ContentType.JSON)
                .when().get("/api/spartans");
        List<Map<String, Object>> listOfSpartans = response.body().as(List.class);
        int count = 1;
        for (Map<String, Object> eachSpartan : listOfSpartans) {
            System.out.println("Spartan " + count + ": " + eachSpartan);
            count++;
        }


    }
}
