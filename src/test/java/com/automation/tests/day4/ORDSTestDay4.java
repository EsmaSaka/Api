package com.automation.tests.day4;


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

public class ORDSTestDay4 {
    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("ords.uri");
    }
    /**
     * Warmup!
     *          Given accept type is JSON
     *         When users sends a GET request to "/employees"
     *         Then status code is 200
     *         And Content type is application/json
     *         And response time is less than 3 seconds
     */
    @Test
    @DisplayName("verify that response time is less than 3 sec")
    public void test1(){
        given()
                .accept(ContentType.JSON).
        when()
                .get("/employees").
        then()
                .assertThat().statusCode(200).
        and()
                .time(lessThan(3L),TimeUnit.SECONDS)
                .log().all(true);
    }

    /*
       Given accept type is JSON
       And parameters: q = country_id = US
       When users sends a GET request to "/countries"
       Then status code is 200
       And Content type is application/json
       And country_name from payload is "United States of America"
       {"country_id":"US"}
    */
    @Test
    @DisplayName("verify that country_name is United States of America")
    public void test2(){
        given()
                .accept("application/json")
                .queryParam("q","{\"country_id\":\"US\"}").
        when()
                .get("/countries").
        then()
                .assertThat().statusCode(200).
        and()
                .assertThat().contentType(ContentType.JSON)
                .body("items[0].country_name",is("United States of America"))

                .log().all(true);
    }

    @Test
    @DisplayName("get all links")
    public void test3(){
        Response response = given().
                accept(ContentType.JSON).
                queryParam("q", "{\"country_id\":\"US\"}").
                when().
                get("/countries");
        JsonPath jsonPath = response.jsonPath();

        List<?> links=jsonPath.getList("links.href");
        // ornek: List<?> links=jsonPath.getList("items.region_id");
        //// ornek: List<?> links=jsonPath.getList("items[0].links.href"); // linki almak icin

        for(Object link :links){
            System.out.println(link);
        }
    }
    @Test
    @DisplayName("verify that payload contains 25 coutries")
    public void  test4(){

        List<?> countries = given()
                                    .accept(ContentType.JSON).
                            when()
                                    .get("/countries")
                                    .thenReturn().jsonPath().getList("items");
             assertEquals(25,countries.size());
    }

    //same with above code
    @Test
    @DisplayName("Verify ORDS returns only 25 Countries")
    public void test5() {
        Response response = given().accept("application/json").
                when().
                get("/countries");
        JsonPath jsonPath = response.jsonPath();
        List<?> links = jsonPath.getList("items.links");
        assertEquals(25,links.size() );

    }

    /**
     * given path parameter is "/regions" and region id is 2
     * when user makes get request
     * then assert that status code is 200
     * and verify that body returns following country names
     *  |Argentina                |
     *  |Brazil                   |
     *  |Canada                   |
     *  |Mexico                   |
     *  |United States of America |
     *
     */
    @Test
    @DisplayName("verify payload contains following countries")
    public void test6(){

        List<String> expected=List.of("Argentina","Brazil","Canada","Mexico","United States of America");

        Response response=given()
                .accept(ContentType.JSON)
               .queryParam("q","{\"region_id\":\"2\"}").
                when()
                .get("/countries");

        List<String> actual= response.jsonPath().getList("items.country_name");
        assertEquals(expected,actual);

    }
    @Test
    @DisplayName("yukaridakinin asserThat ile yapilisi")
    public  void test7(){
        given()
                .accept(ContentType.JSON)
                .queryParam("q","{\"region_id\":\"2\"}").
                when()
                .get("/countries").then()
                .assertThat()
                .body("items.country_name",contains("Argentina","Brazil","Canada","Mexico","United States of America"));

    }

    @Test
    public void test8 () {
        Response response = given().accept(ContentType.JSON).
                             get("employees");
        response.then().assertThat().statusCode(200);
        JsonPath json = response.thenReturn().jsonPath();
        List<Integer> salaries = json.get("items.salary");
        for(Integer salary : salaries ){
            assertTrue(salary > 0);
        }
    }

    // collections salary ekle


    @Test
    public void test9 () {

        given().accept(ContentType.JSON).
        when().get("/employees").
        then().assertThat()
                .statusCode(200)
                .body("items.salary",everyItem(greaterThan(0)));

        //whenever you specify path as items.salary, you will get collection of salaries
        //then to check every single value
        //we can use everyItem(is()), everyItem(greaterThan())
        /**
         * Creates a matcher for {@link Iterable}s that only matches when a single pass over the
         * examined {@link Iterable} yields items that are all matched by the specified
         * <code>itemMatcher</code>.
         * For example:
         * <pre>assertThat(Arrays.asList("bar", "baz"), everyItem(startsWith("ba")))</pre>*/
    }



    /**
     * given path parameter is "/employees/{id}"
     * and path parameter is 101
     * when user makes get request
     * then assert that status code is 200
     * and verifies that phone number is 515-123-4568
     *
     */



    @Test
    @DisplayName("Verify that employee 101's phone number is 515-123-4568")
    public void test(){
        given().
                accept(ContentType.JSON).
                pathParam("id",101).
        when().
                get("/employees/{id}").
        then().
                assertThat().statusCode(200).
        and().
                assertThat().body("phone_number",is("515.123.4568"));
    }

    @Test  //vasyl
    @DisplayName("Verify that employee 101 has following phone number: 515-123-4568")
    public void test10(){
        Response response = given().
                accept(ContentType.JSON).
                when().
                get("/employees/{id}", 101);
        assertEquals(200, response.getStatusCode());
        String expected = "515-123-4568";
        String actual = response.jsonPath().get("phone_number");
        expected = expected.replace("-", ".");
        assertEquals(expected, actual);
    }


    /**
     * given path parameter is "/employees"
     * when user makes get request
     * then assert that status code is 200
     * and verify that body returns following salary information after sorting from higher to lower
     *  24000, 17000, 17000, 12008, 11000,
     *  9000, 9000, 8200, 8200, 8000,
     *  7900, 7800, 7700, 6900, 6500,
     *  6000, 5800, 4800, 4800, 4200,
     *  3100, 2900, 2800, 2600, 2500
     *
     */


    @Test
    @DisplayName("verify that body returns the salaries below in desc order")
    public  void  test12() {


        List<Integer> expectedSalaries = List.of(24000, 17000, 17000, 12008, 11000,
                9000, 9000, 8200, 8200, 8000,
                7900, 7800, 7700, 6900, 6500,
                6000, 5800, 4800, 4800, 4200,
                3100, 2900, 2800, 2600, 2500);

        List<Integer> actualSalaries = given().
                accept("application/json").
                when().
                get("/employees").
                thenReturn().jsonPath().get("items.salary");
        Collections.sort(actualSalaries);//sort from a to z, 0-9
        Collections.reverse(actualSalaries);
        assertTrue(expectedSalaries.equals(actualSalaries));



    }

    @Test
    @DisplayName("verify that body returns the salaries below in desc order")
    public  void  test13() {


        List<Integer> expectedSalaries = List.of(24000, 17000, 17000, 12008, 11000,
                9000, 9000, 8200, 8200, 8000,
                7900, 7800, 7700, 6900, 6500,
                6000, 5800, 4800, 4800, 4200,
                3100, 2900, 2800, 2600, 2500);

        Response response = given().
                accept("application/json").
                when().
                get("/employees");
        // thenReturn().jsonPath().get("items.salary");
        assertEquals(200, response.statusCode());

        List<String> actualSalaries = response.jsonPath().getList("items.salary");//, String.class);
        //       <Integer>                                                               ,Integer.class
        Collections.sort(actualSalaries, Collections.reverseOrder());
        ;
        assertEquals(expectedSalaries, actualSalaries);
        System.out.println(actualSalaries);
        assertEquals(expectedSalaries, actualSalaries);
    }

    /** ####TASK#####
     *  Given accept type as JSON
     *  And path parameter is id with value 2900
     *  When user sends get request to /locations/{id}
     *  Then user verifies that status code is 200
     *  And user verifies following json path contains following entries:
     *      |street_address         |city  |postal_code|country_id|state_province|
     *      |20 Rue des Corps-Saints|Geneva|1730       |CH        |Geneve        |
     *
     */


    /**
     *     "location_id": 2900,
     *     "street_address": "20 Rue des Corps-Saints",
     *     "postal_code": "1730",
     *     "city": "Geneva",
     *     "state_province": "Geneve",
     *     "country_id": "CH",
     */
    @Test
    @DisplayName("Verify that JSON body has following entries")
    public void test15(){
        given().
                accept(ContentType.JSON).
                pathParam("id", 2900).
                when().
                get("/locations/{id}").
                then().
                assertThat().
                statusCode(200).
                body("", hasEntry("street_address", "20 Rue des Corps-Saints")).
                body("", hasEntry("city", "Geneva")).
                body("", hasEntry("postal_code", "1730")).
                body("", hasEntry("country_id", "CH")).
                body("", hasEntry("state_province", "Geneve")).
                log().all(true);

    }

    @Test
    @DisplayName("Verify that JSON body has following entries")
    public void test9_2(){
        given().
                accept(ContentType.JSON).
                pathParam("id", 2900).
                when().
                get("/locations/{id}").
                then().
                assertThat().
                statusCode(200).
                body("street_address", is("20 Rue des Corps-Saints")).
                body("city", is("Geneva")).
                body("postal_code", is("1730")).
                body("country_id", is("CH")).
                body("state_province", is("Geneve")).
                log().all(true);
    }



}
