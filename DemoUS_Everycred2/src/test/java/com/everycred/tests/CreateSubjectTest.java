package com.everycred.tests;

import org.testng.annotations.Test;
import com.everycred.base.BaseTest;
import com.everycred.pages.*;

public class CreateSubjectTest extends BaseTest {

    @Test
    public void testCreateSubject() throws Exception {

        System.out.println("===== TEST STARTED =====");

        // ======================================
        // PAGE OBJECTS
        // ======================================

        SignupPage         signup       = new SignupPage(driver);
        IssuerProfilePage  issuerProfile= new IssuerProfilePage(driver);
        LoginPage          login        = new LoginPage(driver);
        SubjectPage        subject      = new SubjectPage(driver);
        AddRecordPage      addRecord    = new AddRecordPage(driver);
        IssueCredentialPage issue       = new IssueCredentialPage(driver);
        VerifyCredentialPage verify     = new VerifyCredentialPage(driver);
        RevokeCredentialPage revoke     = new RevokeCredentialPage(driver);
        UploadSpreadsheetPage upload    = new UploadSpreadsheetPage(driver);

        // ======================================
        // STEP 1 — SIGN UP (new account)
        // ======================================

        System.out.println("--- STEP 1: Sign Up ---");
        signup.signupFlow();
        subject.delay();

        // ======================================
        // STEP 2 — CREATE ISSUER PROFILE
        // (browser is on Dashboard after Go to Dashboard)
        // ======================================

        System.out.println("--- STEP 2: Issuer Profile ---");
        issuerProfile.issuerProfileFlow();
        subject.delay();

        // ======================================
        // STEP 3 — LOGIN with the signed-up account
        // (browser is on Login page after Sign Out)
        // ======================================

        System.out.println("--- STEP 3: Login with signup credentials ---");
        System.out.println("Login email: " + SignupPage.getEmail());

        login.login(
                SignupPage.getEmail(),
                SignupPage.getPassword()
        );
        subject.delay();

        // ======================================
        // STEP 4 — NAVIGATE TO SUBJECTS (handles onboarding)
        // ======================================

        System.out.println("--- STEP 4: Navigate to Subjects ---");
        subject.navigateToSubjectsAndHandleOnboarding();
        subject.delay();

        // ======================================
        // STEP 5 — CREATE GROUP
        // ======================================

        System.out.println("--- STEP 5: Create Group ---");
        subject.createGroup();
        subject.delay();

        // ======================================
        // STEP 6 — CREATE SUBJECT
        // ======================================

        System.out.println("--- STEP 6: Create Subject ---");
        subject.createSubjectFlow();
        subject.delay();

        // ======================================
        // STEP 7 — ADD ATTRIBUTES
        // ======================================

        System.out.println("--- STEP 7: Add Subject Attributes ---");
        subject.addAttributes();
        subject.delay();

        // ======================================
        // STEP 8 — SELECT ATTRIBUTES & SUBMIT
        // ======================================

        System.out.println("--- STEP 8: Select Attributes and Submit ---");
        subject.selectMultipleCheckboxAndSubmit();
        subject.delay();

        // ======================================
        // STEP 9 — ADD RECORD
        // ======================================

        System.out.println("--- STEP 9: Add Record ---");
        addRecord.addRecordFlow();
        subject.delay();
        addRecord.fillAddRecordForm();
        subject.delay();

        // ======================================
        // STEP 10 — ISSUE CREDENTIAL
        // ======================================

        System.out.println("--- STEP 10: Issue Credential ---");
        issue.issueCredentialFlow();
        subject.delay();

        // ======================================
        // STEP 11 — VERIFY CREDENTIAL
        // ======================================

        System.out.println("--- STEP 11: Verify Credential ---");
        verify.verifyCredentialFlow();
        subject.delay();

        // ======================================
        // STEP 12 — REVOKE CREDENTIAL
        // ======================================

        System.out.println("--- STEP 12: Revoke Credential ---");
        revoke.revokeCredentialFlow();
        subject.delay();

        // ======================================
        // STEP 13 — UPLOAD SPREADSHEET
        // ======================================

        System.out.println("--- STEP 13: Upload Spreadsheet ---");
        upload.uploadSpreadsheetFlow();

        System.out.println("===== FULL FLOW COMPLETED SUCCESSFULLY =====");
    }
}
