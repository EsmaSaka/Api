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
public class day1Task {
    @BeforeAll
    public static void setup(){
        baseURI=ConfigurationReader.getProperty("ords.uri");
    }
    //#TASK: get employee with id 100 and verify that response returns status code 200
    //also, print body

    @Test
    @DisplayName("get emp id=100")
    public void test1(){
        given().accept(ContentType.JSON).
        when()
                .get("/employees/100").
        then()
                .assertThat().contentType(ContentType.JSON)
                .assertThat().statusCode(200)
                .log().all(true);
    }
    //    #Task: perform GET request to /regions, print body and all headers.
    @Test
    @DisplayName("perform GET request to /regions, print body and all headers.")
    public void test2(){
       given().header("accept","application/json").when().get("/regions").then().log().all(true);
    }

    @Test
    @DisplayName("2.yol perform GET request to /regions, print body and all headers.")
    public void test3(){
        Response response= given().get("/employees");
       // System.out.println(response.prettyPrint());
        Header header=response.getHeaders().get("ContentType");

        for(Header h: response.getHeaders()){
            System.out.println(h);
        }
    }








}
