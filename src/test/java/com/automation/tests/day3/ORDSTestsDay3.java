package com.automation.tests.day3;

import com.automation.utilities.ConfigurationReader;
import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;

public class ORDSTestsDay3 {

    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("ords.uri");

    }

    @Test
    public void test1() {
        given().accept("application/json")
                .get("/employees").
        then()
                .statusCode(200)
        .and().assertThat()
                .contentType("application/json")
                .log().all(true);
    }

    // path parameter- to point on specific recource
    //query parameter - to filter results, or describe new resources
    @Test
    public void test2() {
        given().accept("application/json")
                .pathParam("id", 100).
        when()
                .get("/employees/{id}").
        then()
                .assertThat().statusCode(200).
        and()
                .assertThat().body("employee_id", is(100))
                .assertThat().body("first_name", is("Steven"),
                "department_id", is(90))
                .log().all(true);
    }

    /**
     * given path parameter is "/regions/{id}"
     * when user makes get request
     * and region id is equals to 1
     * then assert that status code is 200
     * and assert that region name is Europe
     */

    @Test
    public void test3() {
        given().accept("application/json")
                .pathParam("id", 1).
                when()
                .get("/regions/{id}").
                then()
                .assertThat().statusCode(200).
                and()
                .assertThat().body("region_name", is("Europe"))
                .time(lessThan(3L), TimeUnit.SECONDS)
                .log().all(true);
        //.log().all(true);  yerine .extract.response.prettyPrint olabilir
        // all contains header and status code
    }

    @Test
    public void test4() {
        JsonPath json = given().
                accept("application/json").
                when().
                get("/employees").
                thenReturn().jsonPath();
        //items[employee1, employee2, employee3] | items[0] = employee1.first_name = Steven
        String nameOfFirstEmployee = json.getString("items[0].first_name");
        String nameOfLastEmployee = json.getString("items[-1].first_name");
        System.out.println("First employee name: " + nameOfFirstEmployee);
        System.out.println("Last employee name : "+nameOfLastEmployee);

        // in json employees look like object that consist of param and values
        // ? means any data
// Entry represents one key value pair
        Map<String,String> firstEmployee=json.get("items[0]");
        System.out.println(firstEmployee);
        for (Map.Entry<String ,?> entry:firstEmployee.entrySet()){
            System.out.println("key: "+ entry.getKey()+" , value  :  "+ entry.getValue());
        }

        // get and print all last names
        System.out.println("===============================================");
        List<String> lastNames=json.get("items.last_name");
        for (String str: lastNames){
            System.out.println("last names :  "+ str);
        }

        }
    @Test
    public void test5(){
        JsonPath json = given().
                accept("application/json").
                when().
                get("/countries").
                thenReturn().jsonPath();
        //get and print all last names
        List<Map<String,?>> jsonList = json.get("items.country_name");
        System.out.println(jsonList);



    }

    // get collections of employees salaries
    //end verify status code
    //then sort it
    //and print
    @Test
    public void test6(){
        List<Integer> salaries=given().accept("application/json").
                when()
                .get("/employees")
                .thenReturn().jsonPath().get("items.salary");
        Collections.sort(salaries);
        Collections.reverse(salaries);
        System.out.println(salaries);
    }

    //get phone numbers
    @Test
    public void test7(){
        List<String> phoneNumbers=given().accept("application/json").
                when()
                .get("/employees")
                .thenReturn().jsonPath().get("items.phone_number");

      phoneNumbers.replaceAll(p -> p.replace(".","-"));//takes one element from collection
        //phoneNumbers.replaceAll(p -> p..toString().replace(".","-"));//takes one element from collection
        // to string turns it to string
        System.out.println(phoneNumbers);
    }
    /**
     * Given accept type as json
     * and parameter is id
     * when usr sends request to /locations
     * then user verifies that status is 200
     * and user verifies that json path info is:
     *    |location_id|postal_code|city   |state_province|
     *    |1700       |98199      |Seattle |Washington    |
     *
     * Vasyly made the below code readable above
     * and user verifies that location_id is 1700
     * and user verifies that postal code is 98199
     * and user verifies that city is Seatle
     * and user verifies that state province is washington
     */
    @Test
    public void test8(){
        given().
                accept("application/json").
                pathParam("id", 1700).
                when().get("/locations/{id}").
                then().assertThat().statusCode(200).
                assertThat().body("location_id", is(1700),
                "postal_code",is("98199"),
                "city",is("Seattle"),
                "state_province",is("Washington")).
                log().body(true);
    }




    }


