package day05;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrderer.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class TestOrderingInJunit5_1 {

    @Test  @Order(5)
    public void testA(){
        System.out.println("running test A");
    }

    @Test @Order(1)
    public void testE(){
        System.out.println("running test E");
    }


    @Test @Order(4)
    public void testB(){
        System.out.println("running test B");
    }

    @Test @Order(3)
    public void testC(){
        System.out.println("running test C");
    }


    @Test @Order(2)
    public void testD(){
        System.out.println("running test D");
    }

}
