package com.everycred.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.everycred.base.BaseTest;
import com.everycred.pages.*;

/**
 * End-to-end credential flow split into one @Test method per logical step.
 * <p>
 * Why split: the user wanted the TestNG report to show every step as its own
 * row (so "Total tests run" reflects the 13 actual steps instead of 1 mega-test).
 * <p>
 * All @Test methods share a single browser via {@link BaseTest#setUp()}
 * (now {@code @BeforeClass}) and are ordered with priorities + dependsOnMethods,
 * so any earlier step failing skips the later ones (preserving the original
 * flow guarantees).
 */
public class CreateSubjectTest extends BaseTest {

    // Page objects are initialised once after the browser opens, then reused
    // by every step so all 13 @Test methods drive the same session.
    private SignupPage          signup;
    private IssuerProfilePage   issuerProfile;
    private LoginPage           login;
    private SubjectPage         subject;
    private AddRecordPage       addRecord;
    private IssueCredentialPage issue;
    private VerifyCredentialPage verify;
    private RevokeCredentialPage revoke;
    private UploadSpreadsheetPage upload;

    @BeforeClass(alwaysRun = true, dependsOnMethods = "setUp")
    public void initPages() {
        signup        = new SignupPage(driver);
        issuerProfile = new IssuerProfilePage(driver);
        login         = new LoginPage(driver);
        subject       = new SubjectPage(driver);
        addRecord     = new AddRecordPage(driver);
        issue         = new IssueCredentialPage(driver);
        verify        = new VerifyCredentialPage(driver);
        revoke        = new RevokeCredentialPage(driver);
        upload        = new UploadSpreadsheetPage(driver);
    }

    /** Prints elapsed seconds for a step. */
    private static void logStepTime(String stepName, long startMs) {
        long elapsed = (System.currentTimeMillis() - startMs) / 1000;
        System.out.printf("⏱ STEP TIME [%s]: %d sec%n", stepName, elapsed);
    }

    // ======================================
    // STEP 1 — SIGN UP (new account)
    // ======================================
    @Test(priority = 1)
    public void step01_SignUp() throws Exception {
        long start = System.currentTimeMillis();
        System.out.println("--- STEP 1: Sign Up ---");
        signup.signupFlow();
        subject.delay();
        logStepTime("Step 1 - Sign Up", start);
    }

    // ======================================
    // STEP 2 — CREATE ISSUER PROFILE
    // ======================================
    @Test(priority = 2, dependsOnMethods = "step01_SignUp")
    public void step02_IssuerProfile() throws Exception {
        long start = System.currentTimeMillis();
        System.out.println("--- STEP 2: Issuer Profile ---");
        issuerProfile.issuerProfileFlow();
        subject.delay();
        logStepTime("Step 2 - Issuer Profile", start);
    }

    // ======================================
    // STEP 3 — LOGIN with the signed-up account
    // ======================================
    @Test(priority = 3, dependsOnMethods = "step02_IssuerProfile")
    public void step03_Login() {
        long start = System.currentTimeMillis();
        System.out.println("--- STEP 3: Login with signup credentials ---");
        System.out.println("Login email: " + SignupPage.getEmail());
        login.login(SignupPage.getEmail(), SignupPage.getPassword());
        subject.delay();
        logStepTime("Step 3 - Login", start);
    }

    // ======================================
    // STEP 4 — NAVIGATE TO SUBJECTS (handles onboarding)
    // ======================================
    @Test(priority = 4, dependsOnMethods = "step03_Login")
    public void step04_NavigateToSubjects() {
        long start = System.currentTimeMillis();
        System.out.println("--- STEP 4: Navigate to Subjects ---");
        subject.navigateToSubjectsAndHandleOnboarding();
        subject.delay();
        logStepTime("Step 4 - Navigate to Subjects", start);
    }

    // ======================================
    // STEP 5 — CREATE GROUP
    // ======================================
    @Test(priority = 5, dependsOnMethods = "step04_NavigateToSubjects")
    public void step05_CreateGroup() {
        long start = System.currentTimeMillis();
        System.out.println("--- STEP 5: Create Group ---");
        subject.createGroup();
        subject.delay();
        logStepTime("Step 5 - Create Group", start);
    }

    // ======================================
    // STEP 6 — CREATE SUBJECT
    // ======================================
    @Test(priority = 6, dependsOnMethods = "step05_CreateGroup")
    public void step06_CreateSubject() {
        long start = System.currentTimeMillis();
        System.out.println("--- STEP 6: Create Subject ---");
        subject.createSubjectFlow();
        subject.delay();
        logStepTime("Step 6 - Create Subject", start);
    }

    // ======================================
    // STEP 7 — ADD ATTRIBUTES
    // ======================================
    @Test(priority = 7, dependsOnMethods = "step06_CreateSubject")
    public void step07_AddAttributes() {
        long start = System.currentTimeMillis();
        System.out.println("--- STEP 7: Add Subject Attributes ---");
        subject.addAttributes();
        subject.delay();
        logStepTime("Step 7 - Add Attributes", start);
    }

    // ======================================
    // STEP 8 — SELECT ATTRIBUTES & SUBMIT
    // ======================================
    @Test(priority = 8, dependsOnMethods = "step07_AddAttributes")
    public void step08_SelectAttributesAndSubmit() {
        long start = System.currentTimeMillis();
        System.out.println("--- STEP 8: Select Attributes and Submit ---");
        subject.selectMultipleCheckboxAndSubmit();
        subject.delay();
        logStepTime("Step 8 - Select Attributes & Submit", start);
    }

    // ======================================
    // STEP 9 — ADD RECORD
    // ======================================
    @Test(priority = 9, dependsOnMethods = "step08_SelectAttributesAndSubmit")
    public void step09_AddRecord() throws Exception {
        long start = System.currentTimeMillis();
        System.out.println("--- STEP 9: Add Record ---");
        addRecord.addRecordFlow();
        subject.delay();
        addRecord.fillAddRecordForm();
        subject.delay();
        logStepTime("Step 9 - Add Record", start);
    }

    // ======================================
    // STEP 10 — ISSUE CREDENTIAL
    // ======================================
    @Test(priority = 10, dependsOnMethods = "step09_AddRecord")
    public void step10_IssueCredential() throws Exception {
        long start = System.currentTimeMillis();
        System.out.println("--- STEP 10: Issue Credential ---");
        issue.issueCredentialFlow();
        subject.delay();
        logStepTime("Step 10 - Issue Credential", start);
    }

    // ======================================
    // STEP 11 — VERIFY CREDENTIAL
    // ======================================
    @Test(priority = 11, dependsOnMethods = "step10_IssueCredential")
    public void step11_VerifyCredential() throws Exception {
        long start = System.currentTimeMillis();
        System.out.println("--- STEP 11: Verify Credential ---");
        verify.verifyCredentialFlow();
        subject.delay();
        logStepTime("Step 11 - Verify Credential", start);
    }

    // ======================================
    // STEP 12 — REVOKE CREDENTIAL
    // ======================================
    @Test(priority = 12, dependsOnMethods = "step11_VerifyCredential")
    public void step12_RevokeCredential() throws Exception {
        long start = System.currentTimeMillis();
        System.out.println("--- STEP 12: Revoke Credential ---");
        revoke.revokeCredentialFlow();
        subject.delay();
        logStepTime("Step 12 - Revoke Credential", start);
    }

    // ======================================
    // STEP 13 — UPLOAD SPREADSHEET
    // ======================================
    @Test(priority = 13, dependsOnMethods = "step12_RevokeCredential")
    public void step13_UploadSpreadsheet() throws Exception {
        long start = System.currentTimeMillis();
        System.out.println("--- STEP 13: Upload Spreadsheet ---");
        upload.uploadSpreadsheetFlow();
        logStepTime("Step 13 - Upload Spreadsheet", start);
        System.out.println("===== FULL FLOW COMPLETED SUCCESSFULLY =====");
    }
}
