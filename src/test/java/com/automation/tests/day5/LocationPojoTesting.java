package com.automation.tests.day5;
import com.automation.pojos.Job;
import com.automation.pojos.Location;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
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

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class LocationPojoTesting {

    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("ords.uri");
    }

    /*#Task
        Create POJO for Location:
        public class Location{
        }
        Write 2 tests:
                #1 get single POJO of Location class
        #2 get collection of POJO’s.
        Same thing like we did with Job class
        * follow java naming conventions!   */
    @Test
    @DisplayName("Get job info from json and convert it into POJO")
    public void test1() {
        Response response = given()
                .accept(ContentType.JSON).
                        when()
                .get("/locations/{location_id}",2500);//specific location
        JsonPath jsonPath = response.jsonPath();
        Location job = jsonPath.getObject("", Location.class);
        System.out.println(job);
    }

    @Test
    @DisplayName("Get job info from json and convert it into POJO")
    public void test2() {
//        Response response = given()
//                .accept(ContentType.JSON).
//                        when()
//                .get("/locations");
//        JsonPath jsonPath = response.jsonPath();
//
        given().get("/locations")
                .jsonPath().getList("items", Location.class)
                .forEach(System.out::println);


    }

    @Test
    @DisplayName("Convert JSON into collection of POJO's")
    public void test5() {
        Response response = given().
                accept(ContentType.JSON).
                when().
                get("/locations");
        JsonPath jsonPath = response.jsonPath();
        List<Location> locations = jsonPath.getList("items", Location.class);
        for (Location loc : locations) {
            System.out.println(loc);
        }

    }


    @Test
    public void test06(){
        Response response = given().
                accept(ContentType.JSON).
                get("/locations");
        JsonPath jsonPath = response.jsonPath();
        Location location = jsonPath.getObject("items[0]",Location.class);
        System.out.println(location);
    }
    @Test
    public void test07(){
        Response response = given().
                accept(ContentType.JSON).
                get("/locations");
        JsonPath jsonPath = response.jsonPath();
        List<Location> locations = jsonPath.getList("items",Location.class);
        for(Location each: locations){
          //  System.out.println(each.getPostalCode());
        }
    }

}

