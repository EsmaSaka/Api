package com.automation.tests.practice;
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
public class ExchangeRatesPractice {

    @BeforeAll
    public static void setup(){
        baseURI=ConfigurationReader.getProperty("exchangeRates.URI");
    }

    //GET https://api.exchangeratesapi.io/latest?base=USD HTTP/1.1
    //base it's a query parameter that will ask web service to change currency from eu to something else

    @Test
    @DisplayName("from eu to sth")
    public void test01(){
        Response response=given().accept(ContentType.JSON)
                .queryParam("base","EUR").
        when()
                .get("latest");
        System.out.println(response.prettyPrint());
    }
    //    #TASK: verify that response body, for latest currency rates, contains today's date (2020-01-23 | yyyy-MM-dd)

    @Test
    @DisplayName("todays date")
    public void test02(){
        Response response=given().accept(ContentType.JSON).when().get("latest");
    }

}
