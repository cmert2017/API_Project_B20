package day05;
import static org.junit.jupiter.api.MethodOrderer.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;


//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@TestMethodOrder(MethodOrderer.Random.class)
@TestMethodOrder(MethodName.class)
//@TestMethodOrder(MethodOrderer.DisplayName.class)
public class TestOrderingInJunit5 {

    @Test  @Order(5) @DisplayName("1. Test A method")
    public void testA(){
        System.out.println("running test A");
    }

    @Test @Order(1) @DisplayName("5. Test A method")
    public void testE(){
        System.out.println("running test E");
    }


    @Test() @Tag("smoke")
    @Order(4) @DisplayName("4. Test A method")
    public void testB(){
        System.out.println("running test B");
    }

    @Test @Order(3) @DisplayName("3. Test A method")
    public void testC(){
        System.out.println("running test C");
    }



    @Test @Order(2) @DisplayName("2. Test A method")
    public void testD(){
        System.out.println("running test D");
    }


}
