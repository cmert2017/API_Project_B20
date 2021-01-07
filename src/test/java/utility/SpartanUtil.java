package utility;

import com.github.javafaker.Faker;
import pojo.Spartan;

import java.util.*;

public class SpartanUtil {
    static Faker faker =  new Faker();

    public static Map<String,Object> getRandomSpartanRequestPayload() {

        Map<String, Object> payLoadMap = new LinkedHashMap<>();
        payLoadMap.put("name",faker.name().firstName());
        payLoadMap.put("gender", faker.demographic().sex());
      //  payLoadMap.put("phone",faker.phoneNumber().subscriberNumber(12));
        payLoadMap.put("phone",faker.number().numberBetween(10000000000l,100000000000l));

        return payLoadMap;
    }


    public static Spartan getRandomSpartanPOJO_Payload(){
        Spartan randomSpartan = new Spartan();
        randomSpartan.setGender(faker.demographic().sex());
        randomSpartan.setName(faker.name().firstName());
        randomSpartan.setPhone(faker.number().numberBetween(10000000000l,100000000000l));
        return randomSpartan;
    }



}
