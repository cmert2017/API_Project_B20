package day12;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class ComparingTwoPojos {

    @Test
    public void testingTwoPojos() throws JsonProcessingException {

      ObjectMapper mapper = new ObjectMapper();

      String str1 = "{\n" +
              "  \"name\": \"Tarkan\",\n" +
              "  \"gender\": \"Male\",\n" +
              "  \"phone\": 1231231232\n" +
              "}";
      String str2 = "{\n" +
              "  \"name\": \"Tarkan\",\n" +
              "  \"gender\": \"Male\",\n" +
              "  \"phone\": 1231231232\n" +
              "}";

      assertEquals(mapper.readTree(str1),mapper.readTree(str2));


    }
}
