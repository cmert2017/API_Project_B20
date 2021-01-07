package day01;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;


public class HamcrestMatcherTest {

    @DisplayName("Test 1 + 3 is 4")
    @Test
    public void test1(){
        assertThat(1+3,is(4));
        assertThat(1+3,equalTo(4));

        assertThat("test is failed because of wrong result",1+3,equalTo(5));

        assertThat(1+3,not(5));
        //we can nest a mathcer inside another mathcer
        assertThat(1+3,is(not(5)));
        assertThat(1+3,not(equalTo(5)));
        assertThat(1+3,is(not(equalTo(5))));

        //test 1+3 is less than 5
        assertThat(1+3,lessThan(5));


        //test 1+3 is more than 2
        assertThat(1+3,greaterThan(2));

    }

}
