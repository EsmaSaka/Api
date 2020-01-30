package com.automation.tests.day5;
import com.automation.pojos.Job;
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

public class POJOTesting {

    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("ords.uri");
    }

    @Test
    @DisplayName("Get job info from json and convert it into POJO")
    public void test1(){
        Response response= given()
                                    .accept(ContentType.JSON).
                            when()
                                    .get("/jobs");
        JsonPath jsonPath=response.jsonPath();

        //  Job job= new Job();
        Job job=jsonPath.getObject("items[0]",Job.class);
        System.out.println(job);
        System.out.println(job.getJob_title());
    }

    @Test
    @DisplayName("from POJO to JSON")
    public void test2(){
        Job sdet= new Job("SDET","Software Development Engineer in Test",5000,2000);
         Gson gson=new Gson();
        System.out.println(sdet);
        System.out.println(gson.toJson(sdet));
    }

    @Test
    @DisplayName("Convert JSON into collection of POJO's")
    public void test3(){
        Response response= given()
                .accept(ContentType.JSON).
                        when()
                .get("/jobs");
        JsonPath jsonPath=response.jsonPath();

        //  Job job= new Job();
       List<Job> jobs=jsonPath.getList("items",Job.class);
       //List<Job>jobs = jsonPath.getList("items");
        //System.out.println(jobs.toString());  .Job.class a alternatif
       for (Job job:jobs){
           System.out.println(job.getJob_title());
           System.out.println(job);

       }

    }








}
