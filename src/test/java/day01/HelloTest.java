package day01;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Day 1 Hello Test")
public class HelloTest {

    //junit5 annotations
    //@BeforeAll   @AfterAll  @BeforeEach   @AfterAll


    @BeforeAll
    public static void setUp(){
        System.out.println("BeforeAll is running");
    }


    @AfterAll
    public static void tearDown(){
        System.out.println("AfterAll is running");
    }


    @BeforeEach
    public void beforeEach(){
        System.out.println("BeforeEach is running");
    }


    @AfterEach
    public void afterEach(){
        System.out.println("AfterEach is running");
    }



    @Test @Disabled @DisplayName("Test 4 = 2+2 ")
    public void test1(){
       // Assertions.assertEquals(4,2+2);  //bc of static import we dont  need to use Assertions at the beginning
        assertEquals(4,2+2);
        System.out.println("Test1 is  running");
    }


    @DisplayName("Test 4*3 = 12")
    @Test
    public void test2(){
        //assert 4 times 3 is 12
        assertEquals(4*3,12);
        System.out.println("Test2 is  running");
    }

}
