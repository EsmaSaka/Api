package com.automation.tests.day2;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MetaWeatherTests {

    private String baseURI="https://www.metaweather.com/api/";

    @Test
    public void test1(){
        Response response= given().
                           baseUri(baseURI+"location/search").
                queryParam("query","san").
                get();
        assertEquals(200,response.getStatusCode());
        System.out.println(response.prettyPrint());

    }
    @Test
    public void test2() {
        Response response = given()
                            .pathParam("woeid", "2487956")
                            .get(baseURI +"location/{woeid}");
        assertEquals(200, response.getStatusCode());
        System.out.println(response.prettyPrint());


    }



}
