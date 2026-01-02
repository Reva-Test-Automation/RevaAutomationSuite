import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys


/* 1. Locate the **Settings icon** using the XPath: * //nav[contains(@class,'menuGrp
 * menuGrp3')]/descendant::a[last()-1]//*[local-name()='svg' and 
 * contains(@class,'chakra-icon')] - Retrieve its `class` attribute. - If the
 * class contains `ltia1u`, log and mark the test as FAILED with the message:
 * "✅ Settings button is DISABLED for App Admin". - If the class contains
 * `1gghsms`, log and mark the test as PASSED with the message:
 * "✅ Settings button is ENABLED for App Admin".
 * 
 * 2. Click the **Applications** button and wait for the App Search input field
 * to appear.
 * 
 * 3. Check the **New Application** button: - If the `disabled` attribute is
 * null, log and mark as PASSED:
 * "✅ New Application button is ENABLED for App Admin". - Otherwise, log and
 * mark as FAILED: "❌ New Application button is DISABLED for App Admin".
 * 
 * 4. Click the **Onboarding** button and wait for the Guardrail Search input to
 * appear.
 * 
 * 5. Search for "Automation_Testing" in the Guardrail search bar, then click
 * the matching application card.
 * 
 * 6. Wait for the **Draft Version** header to appear.
 * 
 * 7. Check for the **New Version** button: - If present, log and mark as
 * PASSED: "✅ 'New Version' button is present for App Admin". - Otherwise, log
 * and mark as FAILED: "❌ 'New Version' button is NOT present for App Admin".
 */
// Retrieve the class attribute of the Settings icon
String settingsClass = WebUI.getAttribute(findTestObject('Object Repository/SettingsIcon'), 'class')

// Check if the Settings button is disabled
if (settingsClass.contains('ltia1u')) {
    // Log and mark the test as FAILED if Settings button is disabled
    KeywordUtil.logInfo('? Settings button is DISABLED for App Admin')
    KeywordUtil.markFailed('? Settings button is DISABLED for App Admin')
} else if (settingsClass.contains('1gghsms')) {
    // Log and mark the test as PASSED if Settings button is enabled
    KeywordUtil.logInfo('? Settings button is ENABLED for App Admin')
    KeywordUtil.markPassed('? Settings button is ENABLED for App Admin')
}

// Click the Applications button
WebUI.click(findTestObject('Object Repository/ApplicationsButton'))

// Wait for the App Search input field to appear
WebUI.waitForElementVisible(findTestObject('Object Repository/AppSearchInput'), 30)

// Check the New Application button's disabled attribute
String newAppDisabled = WebUI.getAttribute(findTestObject('Object Repository/NewApplicationButton'), 'disabled')

// Log and mark the test based on the disabled attribute of New Application button
if (newAppDisabled == null) {
    KeywordUtil.logInfo('? New Application button is ENABLED for App Admin')
    KeywordUtil.markPassed('? New Application button is ENABLED for App Admin')
} else {
    KeywordUtil.logInfo('? New Application button is DISABLED for App Admin')
    KeywordUtil.markFailed('? New Application button is DISABLED for App Admin')
}

// Click the Onboarding button
WebUI.click(findTestObject('Object Repository/OnboardingButton'))

// Wait for the Guardrail Search input to appear
WebUI.waitForElementVisible(findTestObject('Object Repository/GuardrailSearchInput'), 30)

// Set text "Automation_Testing" in the Guardrail search bar
WebUI.setText(findTestObject('Object Repository/GuardrailSearchInput'), 'Automation_Testing')

// Click the matching application card
WebUI.click(findTestObject('Object Repository/MatchingApplicationCard'))

// Wait for the Draft Version header to appear
WebUI.waitForElementVisible(findTestObject('Object Repository/DraftVersionHeader'), 30)

// Check if the New Version button is present
if (WebUI.verifyElementPresent(findTestObject('Object Repository/NewVersionButton'), 5, FailureHandling.OPTIONAL)) {
    KeywordUtil.logInfo('? \'New Version\' button is present for App Admin')
    KeywordUtil.markPassed('? \'New Version\' button is present for App Admin')
} else {
    KeywordUtil.logInfo('? \'New Version\' button is NOT present for App Admin')
    KeywordUtil.markFailed('? \'New Version\' button is NOT present for App Admin')
}