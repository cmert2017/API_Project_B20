package day09;


import com.github.javafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;

public class JUnit5RepeatedTest {

    @RepeatedTest(10)
    public void testRepeating(){
        Faker faker = new Faker();
        System.out.println("Repeating");
        System.out.println(faker.funnyName().name());
    }




}
