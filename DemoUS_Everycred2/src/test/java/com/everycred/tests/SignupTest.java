package com.everycred.tests;

import org.testng.annotations.Test;
import com.everycred.base.BaseTest;
import com.everycred.pages.SignupPage;

public class SignupTest extends BaseTest {

    @Test
    public void testSignup() throws Exception {

        System.out.println("===== SIGNUP TEST STARTED =====");

        // BaseTest already opens the login page URL from config.properties.
        // SignupPage starts from there and completes the full sign-up + email verification flow.
        SignupPage signup = new SignupPage(driver);
        signup.signupFlow();

        System.out.println("===== SIGNUP TEST COMPLETED =====");
    }
}
