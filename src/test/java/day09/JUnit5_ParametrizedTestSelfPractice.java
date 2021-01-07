package day09;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class JUnit5_ParametrizedTestSelfPractice {


    @ParameterizedTest
    @CsvFileSource(resources = "/countryzipcode.csv",numLinesToSkip = 1)
    public void testRepeating(String country,String zipcode){
        System.out.print("country = " + country);
        System.out.println("--zipcode = " + zipcode);
    }

    @ParameterizedTest(name="this is how we customized")
    @CsvFileSource(resources = "/countryzipcode.csv",numLinesToSkip = 1)
    public void testRepeating1(String country,String zipcode){
        System.out.print("country = " + country);
        System.out.println("--zipcode = " + zipcode);
    }

    @ParameterizedTest(name="this is iteration {index}")
    @CsvFileSource(resources = "/countryzipcode.csv",numLinesToSkip = 1)
    public void testRepeating2(String country,String zipcode){
        System.out.print("country = " + country);
        System.out.println("--zipcode = " + zipcode);
    }


    @ParameterizedTest(name="First column data is {0} and second column data is {1}")
    @CsvFileSource(resources = "/countryzipcode.csv",numLinesToSkip = 1)
    public void testRepeating3(String country,String zipcode){
        System.out.print("country = " + country);
        System.out.println("--zipcode = " + zipcode);
    }


}
