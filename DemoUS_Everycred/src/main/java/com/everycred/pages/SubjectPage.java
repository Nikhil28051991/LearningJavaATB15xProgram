package com.everycred.pages;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SubjectPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private WebDriverWait shortWait;

    private boolean onboardingHandled = false;
    private boolean subjectsNavigationHandled = false;
    private String createdGroupName = "Employee Department";

    private static boolean GLOBAL_SUBJECTS_LOADED = false;

    public SubjectPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    // ======================================
    // DELAY METHOD
    // ======================================

    public void delay() {
        delay(120);
    }

    public void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // ======================================
    // UI STABILITY FIX
    // ======================================

    public void waitForUIToSettle() {
        try {
            Thread.sleep(300);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        } catch (Exception ignored) {}
    }

    // ======================================
    // FAST CLICK CREATE NEW (🔥 MAIN FIX)
    // ======================================

    private void fastClickCreateNewButton() {

        By locator = By.xpath("//button[contains(.,'Create New')]");
        Exception lastError = null;
        WebDriverWait quickWait = new WebDriverWait(driver, Duration.ofSeconds(1));

        // Keep retries short so we do not block 25 seconds before Create Subject.
        for (int attempt = 1; attempt <= 8; attempt++) {
            waitForBlockingOverlayToClear();
            try {
                WebElement btn = quickWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                jsClick(btn);
                return;
            } catch (Exception e) {
                lastError = e;
                delay(100);
            }
        }

        try {
            WebElement btn = shortWait.until(ExpectedConditions.elementToBeClickable(locator));
            jsClick(btn);
            return;
        } catch (Exception e) {
            lastError = e;
        }

        throw new RuntimeException("Unable to click Create New quickly", lastError);
    }

    // ======================================
    // COLLAPSE PANEL
    // ======================================

    public void clickCollapseArrow() {
        try {
            By collapseArrow = By.xpath("//button[@type='button']//i[contains(@class,'pi-chevron-down')]");
            // 4s wait — new accounts render the "Get Started" panel slower on first load;
            // old accounts (no panel) still bail quickly when the element is absent.
            WebElement icon = new WebDriverWait(driver, Duration.ofSeconds(4))
                    .until(ExpectedConditions.presenceOfElementLocated(collapseArrow));
            WebElement button = icon.findElement(By.xpath("./ancestor::button"));
            jsClick(button);
            delay(200);
            System.out.println("Get Started panel collapsed");
        } catch (Exception e) {
            System.out.println("Collapse not found");
        }
    }

    // ======================================
    // NAVIGATE SUBJECTS
    // ======================================

    public void navigateToSubjects() {

        if (GLOBAL_SUBJECTS_LOADED) {
            return;
        }

        WebElement subjectsTab = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[normalize-space()='Subjects']")));

        jsClick(subjectsTab);

        wait.until(ExpectedConditions.urlContains("/subjects"));

        waitForUIToSettle();

        clickCollapseArrow();

        GLOBAL_SUBJECTS_LOADED = true;
    }

    // ======================================
    // NAVIGATION + ONBOARDING
    // ======================================

    public void navigateToSubjectsAndHandleOnboarding() {

        if (subjectsNavigationHandled) {
            return;
        }

        if (!onboardingHandled) {
            closeOnboardingIfPresent();
            onboardingHandled = true;
        }

        navigateToSubjects();
        delay(150);

        subjectsNavigationHandled = true;
    }

    // ======================================
    // CREATE GROUP
    // ======================================

    public void createGroup() {
        // Step 1: onboarding -> Subjects -> Group tab
        openSubjectsThenGroupForCreation();
        clickGroupSubTab();
        waitForGroupTabReady();

        // Step 2: generate a unique name using a short timestamp — no page scanning needed
        long ts = System.currentTimeMillis() % 100000;  // 5-digit suffix, unique per run
        String candidateName = "Employee Department " + ts;
        System.out.println("Attempting Group Name (timestamp-based): " + candidateName);

        // Step 4: open Create New → Create Group popup
        openCreateGroupDialogWithRetry();

        for (int attempt = 1; attempt <= 30; attempt++) {
            if (!isCreateGroupDialogOpen()) {
                openCreateGroupDialogWithRetry();
            }

            System.out.println("Trying group name: " + candidateName + " (attempt " + attempt + ")");
            clearAndTypeGroupName(candidateName);
            clickCreateGroupSubmitButton();

            // Wait up to ~2.5s watching for either: popup closes OR error toast appears
            boolean succeeded = false;
            for (int tick = 0; tick < 15; tick++) {
                delay(150);
                if (isDuplicateGroupErrorToastVisible()) {
                    // "Group name already exists!" toast — increment and retype in same open popup
                    candidateName = getNextGroupName(candidateName);
                    System.out.println("Duplicate toast detected, trying next: " + candidateName);
                    // Dismiss toast if possible, then retype
                    delay(500);
                    break;
                }
                if (!isCreateGroupDialogOpen()) {
                    // Popup closed — group was created
                    succeeded = true;
                    break;
                }
            }

            if (succeeded) {
                delay(200);
                if (isGroupCreateSuccessToastVisible() || !isCreateGroupDialogOpen()) {
                    createdGroupName = candidateName;
                    System.out.println("Group Created Successfully: " + createdGroupName);
                    ensureSubjectsPageAndTab("Subject");
                    return;
                }
                // Popup still open — try next name
                candidateName = getNextGroupName(candidateName);
                System.out.println("Popup still open after submit, trying: " + candidateName);
                openCreateGroupDialogWithRetry();
            }
            // else: toast was detected, loop continues with new candidateName in same open popup
        }

        throw new RuntimeException(
                "Unable to confirm group creation from popup submit for candidate: " + candidateName);
    }

    private void openSubjectsThenGroupForCreation() {
        // Follow strict sequence: close onboarding → Subjects tab → Group sub-tab.
        // ensureSubjectsPageAndTab already clicks the Group tab, so no duplicate calls.
        closeOnboardingIfPresent();
        clickSubjectsTabForCreateFlow();
        ensureSubjectsPageAndTab("Group");
    }

    private void clickSubjectsTabForCreateFlow() {
        By subjectsTab = By.xpath("//a[normalize-space()='Subjects']");
        WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(subjectsTab));
        jsClick(tab);
        wait.until(ExpectedConditions.urlContains("/subjects"));
        waitForUIToSettle();
        clickCollapseArrow();
    }

    private void openCreateGroupDialogWithRetry() {
        Exception lastError = null;
        for (int attempt = 1; attempt <= 4; attempt++) {
            try {
                clickCreateNewButton();
                delay(80);
                clickCreateGroupOption();
                shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("groupName")));
                return;
            } catch (Exception e) {
                lastError = e;
                delay(100);
            }
        }
        throw new RuntimeException("Unable to open Create Group popup", lastError);
    }

    private boolean isGroupCreateSuccessToastVisible() {
        try {
            List<WebElement> successToasts = driver.findElements(By.xpath(
                    "//*[contains(@class,'p-toast-message-success') and " +
                            "(contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'group') or " +
                            "contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'success'))]"));
            return !successToasts.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isCreateGroupDialogOpen() {
        try {
            List<WebElement> groupNameInputs = driver.findElements(By.id("groupName"));
            return !groupNameInputs.isEmpty() && groupNameInputs.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    private void clickCreateGroupSubmitButton() {
        By createGroupBtn = By.xpath(
                "//div[contains(@class,'p-dialog')]//button[.//span[normalize-space()='Create Group'] or normalize-space()='Create Group']");
        Exception lastError = null;

        for (int attempt = 1; attempt <= 5; attempt++) {
            waitForBlockingOverlayToClear();
            try {
                // Blur the input first so any dropdown/autocomplete is dismissed
                try {
                    ((JavascriptExecutor) driver).executeScript("document.activeElement.blur();");
                } catch (Exception ignored) {}
                delay(50);

                WebElement button = wait.until(ExpectedConditions.elementToBeClickable(createGroupBtn));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", button);
                // Use native click first, fall back to JS click
                try {
                    button.click();
                } catch (Exception e) {
                    jsClick(button);
                }
                System.out.println("Clicked Create Group submit button (attempt " + attempt + ")");
                return;
            } catch (Exception e) {
                lastError = e;
                delay(200);
            }
        }

        throw new RuntimeException("Unable to click Create Group button in popup", lastError);
    }

    private void clickCreateGroupOption() {
        By primaryCreateGroup = By.xpath("//span[normalize-space()='Create Group']");
        By fallbackCreateGroup = By.xpath("//*[self::a or self::button][contains(normalize-space(),'Create Group')]");

        Exception lastError = null;
        WebDriverWait quickWait = new WebDriverWait(driver, Duration.ofSeconds(1));
        for (int attempt = 1; attempt <= 2; attempt++) {
            try {
                WebElement createGroupOption = quickWait.until(
                        ExpectedConditions.visibilityOfElementLocated(primaryCreateGroup));
                jsClick(createGroupOption);
                shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("groupName")));
                return;
            } catch (Exception e) {
                lastError = e;
            }

            try {
                WebElement fallback = quickWait.until(ExpectedConditions.visibilityOfElementLocated(fallbackCreateGroup));
                jsClick(fallback);
                shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("groupName")));
                return;
            } catch (Exception e) {
                lastError = e;
                clickCreateNewButton();
                delay(120);
            }
        }

        throw new RuntimeException("Unable to open Create Group popup", lastError);
    }

    private int getNextGroupSequenceFromPage(String baseGroupName) {
        // Use a single JS execution to scan the DOM — much faster than Selenium element iteration
        try {
            String script =
                "var cells = document.querySelectorAll('tbody tr td');" +
                "var max = -1;" +
                "var base = arguments[0];" +
                "for (var i = 0; i < cells.length; i++) {" +
                "  var raw = (cells[i].textContent || '').replace(/[^a-zA-Z0-9 ]/g, '').trim().replace(/\\s+/g, ' ');" +
                "  if (raw.indexOf(base) === 0) {" +
                "    var rest = raw.substring(base.length).trim();" +
                "    var num = rest === '' ? 0 : parseInt(rest, 10);" +
                "    if (!isNaN(num) && num > max) max = num;" +
                "  }" +
                "}" +
                "return max;";
            Object result = ((JavascriptExecutor) driver).executeScript(script, baseGroupName);
            int highest = (result == null) ? -1 : ((Long) result).intValue();
            System.out.println("Highest group sequence found on page: " + highest);
            return highest + 1;
        } catch (Exception e) {
            return 1;
        }
    }

    private void clearAndTypeGroupName(String candidateName) {
        By groupNameInput = By.id("groupName");
        WebDriverWait inputWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        for (int attempt = 1; attempt <= 5; attempt++) {
            try {
                WebElement input = inputWait.until(ExpectedConditions.visibilityOfElementLocated(groupNameInput));
                // Use JS to fully clear React-controlled input, then send keys
                ((JavascriptExecutor) driver).executeScript(
                        "var el = arguments[0]; var nativeInput = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set; nativeInput.call(el, ''); el.dispatchEvent(new Event('input', { bubbles: true }));",
                        input);
                delay(80);
                input.sendKeys(candidateName);
                delay(100);
                return;
            } catch (Exception e) {
                delay(150);
            }
        }
        throw new RuntimeException("Unable to type group name: " + candidateName);
    }

    private boolean isDuplicateGroupErrorToastVisible() {
        try {
            List<WebElement> toasts = driver.findElements(By.xpath(
                    "//*[contains(@class,'p-toast-message-error') or contains(@class,'p-toast-message-warn')]" +
                    "[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'already') or " +
                    " contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'exist')]"));
            return !toasts.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    private void typeGroupNameWithRetry(String candidateName) {
        Exception lastError = null;
        By groupNameInput = By.id("groupName");
        WebDriverWait inputWait = new WebDriverWait(driver, Duration.ofSeconds(3));

        for (int attempt = 1; attempt <= 5; attempt++) {
            try {
                if (!isCreateGroupDialogOpen()) {
                    reopenCreateGroupDialog();
                }
                WebElement input = inputWait.until(ExpectedConditions.visibilityOfElementLocated(groupNameInput));
                input.clear();
                input.sendKeys(candidateName);
                return;
            } catch (StaleElementReferenceException e) {
                lastError = e;
                delay(120);
            } catch (Exception e) {
                lastError = e;
                try {
                    if (!isCreateGroupDialogOpen()) {
                        reopenCreateGroupDialog();
                    }
                } catch (Exception ignored) {
                    // Continue retrying within this method.
                }
                delay(80);
            }
        }

        throw new RuntimeException("Unable to type Group Name in create popup", lastError);
    }

    private int getTotalGroupsFromPage() {
        try {
            List<WebElement> totalLabels = driver.findElements(By.xpath(
                    "//*[contains(normalize-space(),'Total Groups')]"));
            if (!totalLabels.isEmpty()) {
                String text = totalLabels.get(0).getText();
                Matcher matcher = Pattern.compile("(\\d+)").matcher(text);
                if (matcher.find()) {
                    return Integer.parseInt(matcher.group(1));
                }
            }

            // Fallback: count rows in group list table when summary label is missing/empty.
            List<WebElement> rows = driver.findElements(By.xpath(
                    "//tbody/tr[.//td and not(contains(@class,'p-datatable-emptymessage'))]"));
            return rows.size();
        } catch (Exception e) {
            return 0;
        }
    }

    private String buildGroupNameFromCount(int groupCount) {
        if (groupCount <= 0) {
            return "Employee Department";
        }
        return "Employee Department " + groupCount;
    }

    private String getNextGroupName(String currentGroupName) {
        if ("Employee Department".equals(currentGroupName)) {
            return "Employee Department 1";
        }
        Pattern p = Pattern.compile("^Employee Department\\s+(\\d+)$");
        Matcher m = p.matcher(currentGroupName);
        if (m.matches()) {
            return "Employee Department " + (Integer.parseInt(m.group(1)) + 1);
        }
        return "Employee Department 1";
    }

    private String getNextAvailableGroupNameFromTable() {
        String baseName = "Employee Department";
        String candidate = baseName;
        int suffix = 0;
        while (isGroupNamePresentInTable(candidate)) {
            suffix++;
            candidate = baseName + " " + suffix;
        }
        return candidate;
    }

    private boolean waitForCreateGroupPopupToClose() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.invisibilityOfElementLocated(By.id("groupName")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void waitForGroupTabReady() {
        try {
            shortWait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(normalize-space(),'Total Groups')]")),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr"))
            ));
        } catch (Exception ignored) {
            // Continue; downstream fallback count parsing handles missing summary labels.
        }
    }

    private boolean isGroupNamePresentInTable(String groupName) {
        try {
            List<WebElement> names = driver.findElements(By.xpath(
                    "//tbody/tr/td[1]//*[normalize-space()='" + groupName + "'] | " +
                    "//tbody/tr/td[1][normalize-space()='" + groupName + "']"));
            return !names.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    private void clickGroupSubTab() {
        By groupTab = By.xpath("//button[normalize-space()='Group'] | //span[normalize-space()='Group']/ancestor::button");
        try {
            WebElement tab = shortWait.until(ExpectedConditions.elementToBeClickable(groupTab));
            jsClick(tab);
            waitForUIToSettle();
        } catch (Exception ignored) {
            // If already in group context, continue.
        }
    }

    private int extractGroupSequence(String text, String baseGroupName) {
        if (text.equals(baseGroupName)) {
            return 0;
        }
        // Use find() to tolerate trailing icon text; extract number after base name
        Pattern pattern = Pattern.compile(Pattern.quote(baseGroupName) + "\\s+(\\d+)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return -1;
    }

    // ======================================
    // CREATE SUBJECT (🔥 FIX APPLIED HERE)
    // ======================================

    public void createSubjectFlow() {
        navigateToSubjectsAndHandleOnboarding();
        ensureSubjectsPageAndTab("Subject");

        System.out.println("Opening Create Subject");
        int nextSubjectSeq = getNextSubjectSequenceFromPage("Employee ID Card");
        System.out.println("Highest subject sequence on page: " + (nextSubjectSeq - 1) + " → next: " + nextSubjectSeq);
        String subjectTitle = buildSubjectNameFromSequence(nextSubjectSeq);
        String subjectDescription = subjectTitle + " 2026";
        System.out.println("Attempting Subject Name: " + subjectTitle);

        waitForUIToSettle();

        System.out.println("Clicking Create New...");
        fastClickCreateNewButton();

        delay(50);

        System.out.println("Clicking Create Subject option...");
        clickCreateSubjectOption();

        waitForUIToSettle();

        WebElement title = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("credentialTitle")));

        title.sendKeys(subjectTitle);

        WebElement desc = driver.findElement(By.id("description"));
        desc.sendKeys(subjectDescription);

        selectFromDropdown(By.id("group"), createdGroupName);

        selectFromDropdown(
                By.xpath("//span[@aria-label='Select a credential design'] | //div[@id='credentialDesign']"),
                "Dark Template");

        selectFromDropdown(By.id("verifierDesign"), "Theme 1");

        System.out.println("Subject Basic Details Completed");
    }

    /**
     * Scans the subjects table for existing "Employee ID Card N" names and returns
     * the next available sequence number (highest existing + 1).
     * Sequence 0 → "Employee ID Card" (no number), sequence 1 → "Employee ID Card 1", etc.
     */
    private int getNextSubjectSequenceFromPage(String baseSubjectName) {
        try {
            // Target cells in the table that contain the base subject name
            List<WebElement> cells = driver.findElements(By.xpath(
                    "//tbody//tr//td[contains(normalize-space(),'" + baseSubjectName + "')]"));

            int highestSeq = -1; // -1 means nothing found yet
            for (WebElement cell : cells) {
                String rawText = cell.getText();
                // Use only the FIRST line — title is on line 1, description (e.g. "Employee ID Card 2026") is below
                String firstLine = rawText.contains("\n") ? rawText.split("\n")[0].trim() : rawText.trim();
                // Strip non-alphanumeric chars (icons, special chars) and normalise whitespace
                String cleaned = firstLine.replaceAll("[^a-zA-Z0-9 ]", "").replaceAll("\\s+", " ").trim();
                int seq = extractSubjectSequence(cleaned, baseSubjectName);
                if (seq > highestSeq) {
                    highestSeq = seq;
                }
            }

            // highestSeq == -1 → no match, start from 0 ("Employee ID Card")
            // highestSeq ==  0 → base name without number exists, next is 1
            // highestSeq ==  N → "Employee ID Card N" exists, next is N+1
            return highestSeq + 1;
        } catch (Exception e) {
            return 0; // safe fallback → "Employee ID Card"
        }
    }

    /**
     * Extracts the trailing sequence number from a subject cell text.
     * "Employee ID Card"   → 0
     * "Employee ID Card 3" → 3
     * Returns -1 if the base name is not found in the text.
     */
    private int extractSubjectSequence(String cleanedText, String baseSubjectName) {
        if (!cleanedText.contains(baseSubjectName)) {
            return -1;
        }
        java.util.regex.Matcher m = java.util.regex.Pattern
                .compile(java.util.regex.Pattern.quote(baseSubjectName) + "\\s+(\\d+)")
                .matcher(cleanedText);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        // Base name present but no trailing number → sequence 0
        return 0;
    }

    /** sequence 0 → "Employee ID Card", sequence N → "Employee ID Card N" */
    private String buildSubjectNameFromSequence(int seq) {
        if (seq <= 0) {
            return "Employee ID Card";
        }
        return "Employee ID Card " + seq;
    }

    private void clickCreateSubjectOption() {
        By primaryCreateSubject = By.xpath("//span[normalize-space()='Create Subject']");
        By fallbackCreateSubject = By.xpath("//*[self::a or self::button][contains(normalize-space(),'Create Subject')]");
        Exception lastError = null;
        WebDriverWait quickWait = new WebDriverWait(driver, Duration.ofSeconds(1));

        // Fast retries so user can see continuous progress (no long stall).
        for (int attempt = 1; attempt <= 10; attempt++) {
            try {
                WebElement createSubjectBtn = quickWait.until(
                        ExpectedConditions.visibilityOfElementLocated(primaryCreateSubject));
                jsClick(createSubjectBtn);
                return;
            } catch (Exception e) {
                lastError = e;
            }

            try {
                WebElement fallback = quickWait.until(
                        ExpectedConditions.visibilityOfElementLocated(fallbackCreateSubject));
                jsClick(fallback);
                return;
            } catch (Exception e) {
                lastError = e;
            }

            try {
                // Keep the menu open if it closed between retries.
                fastClickCreateNewButton();
            } catch (Exception e) {
                lastError = e;
            } finally {
                delay(120);
            }
        }

        throw new RuntimeException("Unable to click Create Subject option", lastError);
    }


    // ======================================
    // ADD ATTRIBUTES (SLOWED FOR VISIBILITY)
    // ======================================

    public void addAttributes() {
        // Close any blocking onboarding/welcome dialog before interacting with the form.
        // This is a no-op on old accounts where no such dialog appears.
        closeOnboardingIfPresent();
        ensureCreateSubjectFormOpen();

        System.out.println("Adding Attributes");

        addAttribute("Employees ID", "fieldType_0", "EPID-401-04837");
        addAttribute("Designation", "fieldType_1", "Designation");
        addAttribute("Personal Email ID", "fieldType_2", "Email");
        addAttribute("Personal Contact Number", "fieldType_3", "Contact");
        addAttribute("Joining Date", "fieldType_4", "Joining Date");
        addAttribute("Expiry Date", "fieldType_5", "Expiry Date");
        addAttribute("Company URL", "fieldType_6", "URL");
        addAttribute("Address", "fieldType_1", "Address");

        System.out.println("All Attributes Added Successfully");
    }

    // ======================================
    // ADD ATTRIBUTE (SAFE + SLOW VISIBILITY)
    // ======================================

    public void addAttribute(String title, String fieldTypeId, String descriptionText) {

        By addNewLocator = By.xpath("//span[normalize-space()='Add New']");
        // Allow up to 10s for "Add New" — after a previous popup cancel the button may re-render briefly
        WebDriverWait addNewWait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Scroll to bottom first so Angular renders the attributes section even if it's below the fold.
            // Without this, "Add New" can time out on accounts where the page is long.
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            delay(200);

            WebElement addNewBtn = addNewWait.until(
                    ExpectedConditions.elementToBeClickable(addNewLocator));

            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            delay(120);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", addNewBtn);
            delay(120);
            jsClick(addNewBtn);

            delay(200);

            WebElement titleField = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("fieldTitle")));

            titleField.clear();
            titleField.sendKeys(title);

            delay(150);

            WebElement fieldType = wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("fieldType")));

            jsClick(fieldType);

            delay(150);

            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//li[@id='" + fieldTypeId + "']"))).click();

            WebElement description = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("fieldDescription")));

            description.clear();
            description.sendKeys(descriptionText);

            delay(150);

            jsClick(wait.until(ExpectedConditions.presenceOfElementLocated(By.id("isRequired"))));

            delay(150);

            jsClick(wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//span[normalize-space()='Save']"))));

            // Poll every 200ms for up to 4s: react as soon as popup closes (saved) OR
            // duplicate toast appears — this keeps cancel within ~1s of the toast showing.
            boolean popupClosed = false;
            boolean duplicateFound = false;
            for (int tick = 0; tick < 20 && !popupClosed && !duplicateFound; tick++) {
                delay(200);
                if (isAttributeDuplicateValidationVisible()) {
                    duplicateFound = true;
                } else if (driver.findElements(By.id("fieldTitle")).isEmpty()) {
                    popupClosed = true;
                }
            }

            if (duplicateFound) {
                closeAttributePopupIfOpen();
                System.out.println("Attribute already exists, skipped: " + title);
                return;
            }
            if (!popupClosed) {
                closeAttributePopupIfOpen();
                System.out.println("Attribute popup still open after Save, cancelled: " + title);
                return;
            }

        } catch (Exception e) {
            // Only close popup if it's actually open (avoid accidentally clicking wrong Cancel)
            closeAttributePopupIfOpen();
            System.out.println("Attribute failed: " + title + " (" + e.getClass().getSimpleName() + ")");
        }
    }

    private void closeAttributePopupIfOpen() {
        // Only click Cancel if the attribute popup (fieldTitle) is actually visible
        try {
            if (driver.findElements(By.id("fieldTitle")).isEmpty()) {
                return; // Popup not open — do nothing
            }
            // Use a 1-second wait so we don't stall 3-4 seconds for the Cancel button
            By cancelInPopup = By.xpath(
                    "//div[contains(@class,'p-dialog') and .//input[@id='fieldTitle']]" +
                    "//button[.//span[normalize-space()='Cancel'] or normalize-space()='Cancel']");
            WebDriverWait quickCancel = new WebDriverWait(driver, Duration.ofSeconds(1));
            WebElement cancel = quickCancel.until(ExpectedConditions.elementToBeClickable(cancelInPopup));
            jsClick(cancel);
            new WebDriverWait(driver, Duration.ofSeconds(2))
                    .until(ExpectedConditions.invisibilityOfElementLocated(By.id("fieldTitle")));
        } catch (Exception ignored) {}
    }

    private boolean isAttributeDuplicateValidationVisible() {
        try {
            List<WebElement> duplicateHints = driver.findElements(By.xpath(
                    "//*[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'already') and " +
                            "contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'exist')]"));
            return !duplicateHints.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    private boolean waitForAttributePopupToCloseQuickly() {
        try {
            // Allow up to 8s for the server to process Save and close the popup
            new WebDriverWait(driver, Duration.ofSeconds(8))
                    .until(ExpectedConditions.invisibilityOfElementLocated(By.id("fieldTitle")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ======================================
    // CHECKBOX SELECTION
    // ======================================

    public void selectMultipleCheckboxAndSubmit() {
        // Close any blocking onboarding/welcome dialog before touching the attribute table.
        closeOnboardingIfPresent();
        ensureCreateSubjectFormOpen();

        String[] attributes = {
                "Employees ID",
                "Designation",
                "Personal Email ID",
                "Personal Contact Number",
                "Joining Date",
                "Expiry Date",
                "Company URL",
                "Address"
        };

        int selectedCount = 0;
        for (String attr : attributes) {
            if (selectAttributeCheckbox(attr)) {
                selectedCount++;
            }
        }

        System.out.println("Attributes selected before submit: " + selectedCount);
        clickSubmitButton();
        closeWorkflowPopupUsingLaterIfPresent();

        System.out.println("Submit clicked");
    }

    private boolean selectAttributeCheckbox(String attr) {
        System.out.println("Selecting attribute: " + attr);

        By rowLocator = By.xpath(
                "//tbody/tr[.//td[normalize-space()='" + attr + "'] or .//span[normalize-space()='" + attr + "']]");
        By checkboxLocator = By.xpath(
                ".//input[@type='checkbox'] | .//div[contains(@class,'p-checkbox-box')]");

        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                // Fresh fetch of row on every attempt
                WebElement row = wait.until(ExpectedConditions.visibilityOfElementLocated(rowLocator));
                WebElement checkbox = row.findElement(checkboxLocator);

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center'});", checkbox);
                delay(120);

                // Re-fetch after scroll — Angular may have re-rendered the table row
                row = wait.until(ExpectedConditions.visibilityOfElementLocated(rowLocator));
                checkbox = row.findElement(checkboxLocator);

                try {
                    checkbox.click();
                } catch (Exception e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
                }

                delay(120);
                return true;

            } catch (StaleElementReferenceException e) {
                System.out.println("Stale element on attempt " + attempt + " for: " + attr + ", retrying...");
                try { Thread.sleep(150); } catch (InterruptedException ignored) {}
            } catch (Exception e) {
                System.out.println("Attribute row not found, skipping: " + attr);
                return false;
            }
        }
        System.out.println("Failed to select attribute after retries: " + attr);
        return false;
    }

    // ======================================
    // CLICK UTIL
    // ======================================

    public void click(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        jsClick(element);
    }

    // ======================================
    // JS CLICK
    // ======================================

    public void jsClick(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }

    private void closeDialogIfVisible() {
        try {
            WebElement close = shortWait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class,'p-dialog-header-close')]")));
            jsClick(close);
            waitForUIToSettle();
        } catch (Exception ignored) {
            // no dialog open
        }
    }

    private void closeOnboardingIfPresent() {
        By onboardingClose = By.xpath("//button[contains(@class,'p-dialog-header-close')]");
        WebDriverWait quickWait = new WebDriverWait(driver, Duration.ofSeconds(1));
        try {
            for (int attempt = 1; attempt <= 3; attempt++) {
                if (driver.findElements(onboardingClose).isEmpty()) {
                    System.out.println("Onboarding not present");
                    return;
                }
                try {
                    WebElement closeBtn = quickWait.until(ExpectedConditions.elementToBeClickable(onboardingClose));
                    jsClick(closeBtn);
                    System.out.println("Onboarding closed");
                    return;
                } catch (Exception ignored) {
                    delay(120);
                }
            }
        } catch (Exception e) {
            System.out.println("Onboarding check skipped");
        }
    }

    private boolean isDuplicateGroupValidationVisible() {
        try {
            List<WebElement> duplicateHints = driver.findElements(By.xpath(
                    "//*[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'already') and " +
                            "contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'exist')]"));
            if (!duplicateHints.isEmpty()) {
                return true;
            }

            List<WebElement> errorToasts = driver.findElements(By.xpath(
                    "//*[contains(@class,'p-toast-message-error') and contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'exist')]"));
            return !errorToasts.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    private void clickCancelInCreateGroupPopup() {
        try {
            WebElement cancelBtn = shortWait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[normalize-space()='Cancel']")));
            jsClick(cancelBtn);
            waitForUIToSettle();
        } catch (Exception e) {
            closeDialogIfVisible();
        }
    }

    private void reopenCreateGroupDialog() {
        delay(120);
        clickCreateNewButton();
        delay(120);
        WebElement createGroupOption = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//span[normalize-space()='Create Group']")));
        jsClick(createGroupOption);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("groupName")));
    }

    private void selectFromDropdown(By triggerLocator, String optionText) {
        WebElement trigger = wait.until(ExpectedConditions.presenceOfElementLocated(triggerLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", trigger);
        delay(120);

        try {
            wait.until(ExpectedConditions.visibilityOf(trigger));
            jsClick(trigger);
        } catch (Exception e) {
            WebElement clickableTrigger = wait.until(ExpectedConditions.elementToBeClickable(triggerLocator));
            jsClick(clickableTrigger);
        }
        delay(180);

        By optionLocator = By.xpath(
                "//li[normalize-space()='" + optionText + "'] | " +
                "//*[@role='option' and normalize-space()='" + optionText + "'] | " +
                "//span[normalize-space()='" + optionText + "']");

        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(optionLocator));
        jsClick(option);
        waitForUIToSettle();
    }

    private void clickCreateNewButton() {

        By createNewLocator = By.xpath("//button[contains(.,'Create New')]");
        Exception lastError = null;
        WebDriverWait quickWait = new WebDriverWait(driver, Duration.ofSeconds(1));

        // Quick retries only, avoids 25s wait freeze.
        for (int attempt = 1; attempt <= 10; attempt++) {
            waitForBlockingOverlayToClear();
            try {
                WebElement createNewBtn = quickWait.until(
                        ExpectedConditions.visibilityOfElementLocated(createNewLocator));
                jsClick(createNewBtn);
                return;
            } catch (Exception e) {
                lastError = e;
                delay(100);
            }
        }

        throw new RuntimeException("Unable to click Create New button quickly", lastError);
    }

    private void waitForBlockingOverlayToClear() {
        // Use a 300ms max — the dialog's own backdrop triggers a 3s timeout with shortWait
        By overlayLocator = By.xpath("//div[contains(@class,'p-overlay-mask') and contains(@style,'pointer-events: auto')]");
        try {
            new WebDriverWait(driver, Duration.ofMillis(300))
                    .until(ExpectedConditions.invisibilityOfElementLocated(overlayLocator));
        } catch (Exception ignored) {}
    }

    private void clickSubmitButton() {
        By submitByAriaLabel = By.xpath("//button[@aria-label='Submit']");
        By submitFallback = By.xpath("//button[normalize-space()='Submit'] | //span[normalize-space()='Submit']/ancestor::button");

        Exception lastError = null;
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                WebElement submitBtn = wait.until(ExpectedConditions.presenceOfElementLocated(submitByAriaLabel));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", submitBtn);
                waitForUIToSettle();
                jsClick(submitBtn);
                return;
            } catch (Exception e) {
                lastError = e;
            }

            try {
                WebElement fallback = wait.until(ExpectedConditions.presenceOfElementLocated(submitFallback));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", fallback);
                waitForUIToSettle();
                jsClick(fallback);
                return;
            } catch (Exception e) {
                lastError = e;
                delay(250);
            }
        }

        throw new RuntimeException("Unable to click Submit button", lastError);
    }

    private void closeWorkflowPopupUsingLaterIfPresent() {
        By workflowTitle = By.xpath(
                "//*[contains(normalize-space(),'Subject created successfully')] | " +
                "//div[contains(@class,'p-dialog') and .//*[contains(normalize-space(),'WORKFLOW SETUP')]]");
        By laterButton = By.xpath(
                "//div[contains(@class,'p-dialog')]//button[.//span[normalize-space()='Later'] or normalize-space()='Later'] | " +
                "//div[contains(@class,'p-dialog')]//span[normalize-space()='Later']/ancestor::button");
        WebDriverWait quickWait = new WebDriverWait(driver, Duration.ofSeconds(2));

        try {
            // New accounts get this popup a few seconds after Submit; poll for up to 8s (32×250ms)
            // so it is never missed on slow connections. Old accounts exit the loop immediately.
            for (int attempt = 1; attempt <= 32; attempt++) {
                if (driver.findElements(workflowTitle).isEmpty()) {
                    // Popup can appear shortly after submit on new users.
                    if (attempt == 12) {
                        return;
                    }
                    delay(250);
                    continue;
                }
                try {
                    WebElement later = quickWait.until(ExpectedConditions.elementToBeClickable(laterButton));
                    jsClick(later);
                    try {
                        shortWait.until(ExpectedConditions.invisibilityOfElementLocated(workflowTitle));
                    } catch (Exception ignored) {}
                    System.out.println("Workflow popup closed using Later");
                    return;
                } catch (Exception e) {
                    // Force-click fallback for sticky dialog states.
                    try {
                        WebElement laterFallback = driver.findElement(By.xpath(
                                "(//button[.//span[normalize-space()='Later'] or normalize-space()='Later'] | " +
                                "//span[normalize-space()='Later']/ancestor::button)[last()]"));
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", laterFallback);
                        try {
                            shortWait.until(ExpectedConditions.invisibilityOfElementLocated(workflowTitle));
                        } catch (Exception ignored) {}
                        System.out.println("Workflow popup force-closed using Later");
                        return;
                    } catch (Exception ignored) {}
                    // Retry quickly; popup footer sometimes stabilizes a bit later.
                    delay(250);
                }
            }
        } catch (Exception e) {
            System.out.println("Workflow popup not present or not clickable, continuing");
        }
    }

    private void ensureSubjectsPageAndTab(String tabName) {
        String currentUrl = safeGetCurrentUrl();
        if (currentUrl.isEmpty()) {
            throw new IllegalStateException("Browser window is no longer available");
        }
        if (!currentUrl.contains("/subjects")) {
            navigateToSubjectsAndHandleOnboarding();
        }

        try {
            By tabLocator = By.xpath(
                    "//button[normalize-space()='" + tabName + "'] | //span[normalize-space()='" + tabName + "']");
            WebElement tab = shortWait.until(ExpectedConditions.visibilityOfElementLocated(tabLocator));
            jsClick(tab);
            waitForUIToSettle();
        } catch (Exception ignored) {
            // If tab control is not visible in current state, continue with existing flow.
        }
    }

    private String safeGetCurrentUrl() {
        try {
            return driver.getCurrentUrl();
        } catch (NoSuchWindowException e) {
            // Try to switch to any remaining window handle
            try {
                Set<String> handles = driver.getWindowHandles();
                if (handles != null && !handles.isEmpty()) {
                    driver.switchTo().window(handles.iterator().next());
                    return driver.getCurrentUrl();
                }
            } catch (Exception ignored) {}
            // No windows at all — return empty string so callers can handle gracefully
            return "";
        }
    }

    private void ensureCreateSubjectFormOpen() {
        By subjectTitleInput = By.id("credentialTitle");
        By addAttributeButton = By.xpath("//span[normalize-space()='Add New']");
        By submitButton = By.xpath("//button[.//span[normalize-space()='Submit'] or normalize-space()='Submit']");

        try {
            waitForBlockingOverlayToClear();
        } catch (Exception ignored) {}

        // Fast-path checks (avoid unnecessary waits/re-navigation).
        try {
            if (!driver.findElements(subjectTitleInput).isEmpty()
                    || !driver.findElements(addAttributeButton).isEmpty()
                    || !driver.findElements(submitButton).isEmpty()) {
                return;
            }
        } catch (Exception ignored) {}

        try {
            WebDriverWait localWait = new WebDriverWait(driver, Duration.ofSeconds(6));
            localWait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(subjectTitleInput),
                    ExpectedConditions.presenceOfElementLocated(addAttributeButton),
                    ExpectedConditions.presenceOfElementLocated(submitButton)
            ));
            return;
        } catch (Exception ignored) {
            // Not on create subject screen anymore; reopen it.
        }

        createSubjectFlow();
    }
}