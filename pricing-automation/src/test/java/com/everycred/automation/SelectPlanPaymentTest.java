package com.everycred.automation;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class SelectPlanPaymentTest {

    @Test
    public void createPlanAndOpenStripePaymentPage() {

        // API Base URL
        RestAssured.baseURI = "https://evrc-pricing.everycred.com";

        String requestBody = """
                {
                  "email": "hooper@example.com",
                  "plan_name": "starter",
                  "plan_type": "monthly",
                  "product_id": "Evrc101",
                  "user_uuid": "e9ca7aa2-ed18-489f-a92b-14d9b366f141"
                }
                """;

        // API Call
        Response response =
                given()
                        .header("Content-Type", "application/json")
                        .accept("application/json")
                        .body(requestBody)
                .when()
                        .post("/v1/select/plan")
                .then()
                        .statusCode(200)
                        .extract()
                        .response();

        // Assertions
        Assert.assertEquals(response.jsonPath().getString("status"), "success");

        String paymentUrl = response.jsonPath().getString("data");
        Assert.assertTrue(paymentUrl.contains("stripe.com"));

        System.out.println("Payment URL: " + paymentUrl);

        // Selenium
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(paymentUrl);

        Assert.assertTrue(driver.getTitle().toLowerCase().contains("stripe")
                || driver.getTitle().toLowerCase().contains("checkout"));

        driver.quit();
    }
}
