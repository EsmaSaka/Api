package com.automation.tests.day6;
import com.automation.utilities.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class SpartanTests {
    @BeforeAll
    public  static void setup(){
        baseURI = ConfigurationReader.getProperty("spartan.uri");
    }

    /**
     * given accept content type as JSON
     * when user sends GET request to /spartans
     * then user verifies that status code is 200
     * and user verifies that content type is JSON
     */

    @Test
    @DisplayName(" verify content Type=json")
    public void test1(){

        given().accept(ContentType.JSON).
                when()
                .get("/spartans")
                .prettyPeek().
                then()
                .assertThat().statusCode(200)
                .assertThat().contentType(ContentType.JSON);

    }
    /**
     * given accept content type as XML
     * when user sends GET request to /spartans
     * then user verifies that status code is 200
     * and user verifies that content type is XML
     */
    @Test
    @DisplayName(" verify content Type=xml")
    public void test2(){

        given().accept(ContentType.XML).
         when()
                .get("/spartans")
                .prettyPeek().
         then()
                .assertThat().statusCode(200)
                .assertThat().contentType(ContentType.XML);

    }

    @Test
    @DisplayName("save the payoad in a collection")
    public void test3(){
        Response response=given().accept(ContentType.JSON).get("/spartans");
        response.then().assertThat().statusCode(200).contentType(ContentType.JSON);
        response.prettyPrint();
        System.out.println("=====================================================================");
        List<Map<String,?>> values=response.jsonPath().get();
        for(Map<String ,?>value:values){
            System.out.println(value);
        }

    }



}
