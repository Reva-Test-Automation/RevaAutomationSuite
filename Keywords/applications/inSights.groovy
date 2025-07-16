package applications
import org.openqa.selenium.WebElement
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.JavascriptExecutor
import CustomKeywords
import commonFunctions.getScreenShotWithHighlight
import commonFunctions.browserUtils
import internal.GlobalVariable
import com.kms.katalon.core.util.KeywordUtil
import org.openqa.selenium.Keys
import loginPage.NavigateToLogin
import org.openqa.selenium.WebDriver

public class inSights {

	@Keyword
	public void ValidateApplication() {
		WebUI.click(findTestObject('Object Repository/Applications/btn_Applications'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Applications'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/link_Automation'))
		String appName = "//p[text()='Automation']/ancestor::a/following-sibling::div/descendant::p[contains(text(),'"+GlobalVariable.ApplicationName+"')]"
		TestObject appLink = new TestObject().addProperty("xpath", ConditionType.EQUALS, appName)
		WebUI.click(appLink)
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Applications'), 10)
		String envName = "//p[text()='"+GlobalVariable.EnvironmentName+"']"
		TestObject envLink = new TestObject().addProperty("xpath", ConditionType.EQUALS, envName)
		WebUI.click(envLink)
		String policyName = "//p[contains(text(),'"+GlobalVariable.PolicyDisplayName+"')]"
		TestObject policyLink = new TestObject().addProperty("xpath", ConditionType.EQUALS, policyName)
		WebUI.click(policyLink)
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Tabs'), 10)
	}

	@Keyword
	public void NavigateToProvidenceHospitalProd() {
		WebUI.click(findTestObject('Object Repository/Applications/btn_Applications'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Applications'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/link_EnterpriseApplications'))
		WebUI.waitForElementVisible(findTestObject('Object Repository/Applications/link_ProvidenceHospital'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/link_ProvidenceHospital'))
		WebUI.waitForElementVisible(findTestObject('Object Repository/Applications/link_ProdEnv'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/link_ProdEnv'))
		/*WebUI.waitForElementVisible(findTestObject('Object Repository/Applications/link_ProductionStore'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/link_ProductionStore'))*/
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Tabs'), 10)
	}

	@Keyword
	public void NavigateToProvidenceHospitalDev() {
		CustomKeywords.'loginPage.NavigateToLogin.NavigateUrl'(GlobalVariable.HomePageUrl)
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_Applications'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/btn_Applications'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Applications'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/link_EnterpriseApplications'))
		WebUI.waitForElementVisible(findTestObject('Object Repository/Applications/link_ProvidenceHospital'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/link_ProvidenceHospital'))
		WebUI.waitForElementVisible(findTestObject('Object Repository/Applications/link_DevEnv'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/link_DevEnv'))
		/*WebUI.waitForElementVisible(findTestObject('Object Repository/Applications/link_DevStore'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/link_DevStore'))*/
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Tabs'), 10)
	}

	@Keyword
	public void verifyInsightsTab() {
		List<String> tabs =  GlobalVariable.InsightsTabs.split(',')
		tabs.each { tabName ->
			String xpath = "//p[text()='${tabName}']/ancestor::button"
			TestObject dynamicTab = new TestObject()
			dynamicTab.addProperty("xpath", ConditionType.EQUALS, xpath)
			WebUI.click(dynamicTab)
			WebUI.delay(1)
			def insightsXpath = "//p[contains(text(),'${GlobalVariable.ApplicationName}')]/ancestor::header/following-sibling::div/descendant::p"
			def insightstabs = WebUiCommonHelper.findWebElements(new TestObject().tap {
				addProperty("xpath", ConditionType.EQUALS, insightsXpath)
			}, 10)
			insightstabs.each {
				it.click()
				WebUI.delay(1)
			}
			WebUI.click(findTestObject('Object Repository/Applications/btn_InsightsClose'))
			WebUI.delay(0.5)
		}
		TestObject topActivityObject = findTestObject('Object Repository/Applications/topActivity_Section')
		List<WebElement> topActivityelements = WebUiCommonHelper.findWebElements(topActivityObject, 30)
		List<WebElement> topActivityvisibleElements = topActivityelements.findAll { it.isDisplayed() }
		println "Total visible elements found: " + topActivityvisibleElements.size()
		topActivityvisibleElements.eachWithIndex { el, idx ->
			println "Element ${idx + 1} text: " + el.getText()
		}
		WebUI.click(findTestObject('Object Repository/Applications/link_TopActivityViewMore'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/slide_TopActivity'), 10)
		boolean isVisible = WebUI.verifyElementVisible(findTestObject('Object Repository/Applications/header_TopActivity'), FailureHandling.OPTIONAL)
		if (isVisible) {
			println "✅ Top Activity view more is responded for the click"
			KeywordUtil.markPassed("Top Activity view more is responded for the click")
		} else {
			println "❌ Top Activity view more is not responded for the click"
			KeywordUtil.markFailed("Top Activity view more is not responded for the click")
		}
		WebUI.click(findTestObject('Object Repository/Applications/btn_TopActivityClose'))
		TestObject pendingActionsObject = findTestObject('Object Repository/Applications/pendingActions_Section')
		List<WebElement> pendingActionselements = WebUiCommonHelper.findWebElements(pendingActionsObject, 30)
		List<WebElement> pendingActionsvisibleElements = pendingActionselements.findAll { it.isDisplayed() }
		println "Total visible elements found: " + pendingActionsvisibleElements.size()
		pendingActionsvisibleElements.eachWithIndex { el, idx ->
			println "Element ${idx + 1} text: " + el.getText()
		}
	}

	@Keyword
	public void verifyPoliciesTab() {
		WebUI.click(findTestObject('Object Repository/Applications/tab_Policies'))
		WebUI.delay(0.5)
		TestObject DesignPolicyBtn = findTestObject('Object Repository/Applications/btn_DesignPolicy')
		TestObject NewVersionBtn = findTestObject('Object Repository/Applications/btn_NewVersion')
		if (WebUI.verifyElementPresent(DesignPolicyBtn, 5, FailureHandling.OPTIONAL)) {
			WebUI.click(DesignPolicyBtn)
			KeywordUtil.logInfo("Clicked 'Design Policy' button.")
		} else if (WebUI.verifyElementPresent(NewVersionBtn, 5, FailureHandling.OPTIONAL)) {
			WebUI.click(NewVersionBtn)
			KeywordUtil.logInfo("Clicked 'New Version' button.")
		} else {
			KeywordUtil.markFailed("❌ Neither 'Design Policy' nor 'New Version' button was found.")
			return
		}
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/buildPolicy_Section'), 10)
		WebUI.verifyElementPresent(findTestObject('Object Repository/Applications/buildPolicy_Section'), 5)
		KeywordUtil.logInfo("✅ Policy section loaded successfully.")
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddPermitPolicy'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_Code'), 10)
		WebUI.verifyElementVisible(findTestObject('Object Repository/Applications/btn_Code'))
		KeywordUtil.logInfo("✅ Add Permit Policy button clicked and Code button appeared.")
		WebUI.click(findTestObject('Object Repository/Applications/btn_Code'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/code_TextArea'), 10)
		WebUI.verifyElementVisible(findTestObject('Object Repository/Applications/code_TextArea'))
		KeywordUtil.logInfo("✅ Code editor is now visible.")
		WebUI.click(findTestObject('Object Repository/Applications/btn_MenuDots'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_ListDots'), 10)
		WebUI.verifyElementVisible(findTestObject('Object Repository/Applications/menu_ListDots'))
		KeywordUtil.logInfo("✅ Menu list appeared after clicking dots.")
		WebUI.click(findTestObject('Object Repository/Applications/btn_DeleteContainer'))
		WebUI.verifyElementPresent(findTestObject('Object Repository/Applications/btn_Add'), 5)
		KeywordUtil.logInfo("✅ Existing container deleted successfully.")
		WebUI.click(findTestObject('Object Repository/Applications/btn_Add'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/textArea_Code'), 10)
		KeywordUtil.logInfo("✅ New code container added successfully.")
		WebUI.sendKeys(findTestObject('Object Repository/Applications/textArea_Code'), GlobalVariable.PolicyCodePermitAll)
		WebUI.click(findTestObject('Object Repository/Applications/btn_SendToApproval'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/popup_PolicySummary'), 10)
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_SendForApprovalSecond'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/btn_SendForApprovalSecond'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Tabs'), 10)
		WebUI.verifyElementVisible(findTestObject('Object Repository/Applications/menu_Tabs'))
		KeywordUtil.logInfo("✅ Policy sent for approval successfully")
	}

	@Keyword
	public void EditCreatedPolicy() {
		WebUI.click(findTestObject('Object Repository/Applications/tab_Policies'))
		WebUI.delay(0.5)
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/link_CreatedPolicy'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/link_CreatedPolicy'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_Code'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/btn_Code'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/code_TextArea'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/btn_Add'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/textArea_CodeSecond'), 10)
		WebUI.sendKeys(findTestObject('Object Repository/Applications/textArea_CodeSecond'), GlobalVariable.PolicyCodePermitStaff)
		WebUI.click(findTestObject('Object Repository/Applications/btn_PublishPD'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Tabs'), 10)
	}

	@Keyword
	public void DesignPolicy(String principalType, String principalInput, String actionType, String resourceType, String resourceInput, String conditionGroupInput) {
		WebUI.click(findTestObject('Object Repository/Applications/tab_Policies'))
		WebUI.delay(0.5)
		TestObject DesignPolicyBtn = findTestObject('Object Repository/Applications/btn_DesignPolicy')
		TestObject NewVersionBtn = findTestObject('Object Repository/Applications/btn_NewVersion')
		if (WebUI.verifyElementPresent(DesignPolicyBtn, 5, FailureHandling.OPTIONAL)) {
			WebUI.click(DesignPolicyBtn)
			KeywordUtil.logInfo("Clicked 'Design Policy' button.")
		} else if (WebUI.verifyElementPresent(NewVersionBtn, 5, FailureHandling.OPTIONAL)) {
			WebUI.click(NewVersionBtn)
			KeywordUtil.logInfo("Clicked 'New Version' button.")
		} else {
			KeywordUtil.markFailed("❌ Neither 'Design Policy' nor 'New Version' button was found.")
			return
		}
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/buildPolicy_Section'), 10)
		WebUI.verifyElementPresent(findTestObject('Object Repository/Applications/buildPolicy_Section'), 5)
		KeywordUtil.logInfo("✅ Policy section loaded successfully.")
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddPermitPolicy'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_Code'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/input_Principal_1'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_PrincipalList'), 10)
		TestObject PrincipaldynamicObject = new TestObject()
		PrincipaldynamicObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'suggestions-list')]/descendant::li[text()='" + principalType + "']")
		WebUI.waitForElementPresent(PrincipaldynamicObject, 10)
		WebUI.click(PrincipaldynamicObject)
		WebUI.setText(findTestObject('Object Repository/Applications/input_Principal_1'), principalInput)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddActionNode'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/input_Action_1'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/input_Action_1'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_ActionList'), 10)
		TestObject ActiondynamicObject = new TestObject()
		ActiondynamicObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'suggestions-list actions-list')]/descendant::li[text()='" + actionType + "']")
		WebUI.waitForElementPresent(ActiondynamicObject, 10)
		WebUI.click(ActiondynamicObject)
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddResource'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/container_Resource'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/input_Resource_1'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_PrincipalList'), 10)
		TestObject ResourcedynamicObject = new TestObject()
		ResourcedynamicObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'suggestions-list')]/descendant::li[text()='" + resourceType + "']")
		WebUI.waitForElementPresent(ResourcedynamicObject, 10)
		WebUI.click(ResourcedynamicObject)
		WebUI.delay(0.5)
		WebUI.setText(findTestObject('Object Repository/Applications/input_Principal_1'), resourceInput)
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddCondition'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/slide_ConditionBuilder'), 10)
		WebUI.sendKeys(findTestObject('Object Repository/Applications/textArea_ConditionGroupOne'), conditionGroupInput)
		WebUI.click(findTestObject('Object Repository/Applications/btn_ConditionBuilderSave'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_SendToApproval'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/btn_SendToApproval'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/popup_PolicySummary'), 10)
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_SendForApprovalSecond'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/btn_SendForApprovalSecond'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Tabs'), 10)
		WebUI.verifyElementVisible(findTestObject('Object Repository/Applications/menu_Tabs'))
		KeywordUtil.logInfo("✅ Policy sent for approval successfully")
	}

	@Keyword
	public void DesignAIPolicy() {
		WebUI.click(findTestObject('Object Repository/Applications/tab_Policies'))
		WebUI.delay(0.5)
		TestObject DesignPolicyBtn = findTestObject('Object Repository/Applications/btn_DesignPolicy')
		TestObject NewVersionBtn = findTestObject('Object Repository/Applications/btn_NewVersion')
		if (WebUI.verifyElementPresent(DesignPolicyBtn, 5, FailureHandling.OPTIONAL)) {
			WebUI.click(DesignPolicyBtn)
			KeywordUtil.logInfo("Clicked 'Design Policy' button.")
		} else if (WebUI.verifyElementPresent(NewVersionBtn, 5, FailureHandling.OPTIONAL)) {
			WebUI.click(NewVersionBtn)
			KeywordUtil.logInfo("Clicked 'New Version' button.")
		} else {
			KeywordUtil.markFailed("❌ Neither 'Design Policy' nor 'New Version' button was found.")
			return
		}
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/buildPolicy_Section'), 10)
		WebUI.verifyElementPresent(findTestObject('Object Repository/Applications/buildPolicy_Section'), 5)
		WebUI.click(findTestObject('Object Repository/Applications/btn_CreateWithAI'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/header_RevaAI'), 10)
		WebUI.waitForElementClickable(findTestObject('Object Repository/Applications/textArea_AIPrompt'), 20)
		WebUI.delay(1)
		WebUI.setText(findTestObject('Object Repository/Applications/textArea_AIPrompt'), GlobalVariable.DoctorAIPrompt)
		WebUI.click(findTestObject('Object Repository/Applications/btn_PromptEnter'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/text_AIResponse'), 10)
		WebUI.waitForElementClickable(findTestObject('Object Repository/Applications/btn_AIResponseYes'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AIResponseYes'))
		WebUI.click(findTestObject('Object Repository/Applications/btn_CloseAI'))
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddPolicy'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/link_PermitPolicy'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/link_PermitPolicy'))
		WebUI.click(findTestObject('Object Repository/Applications/input_Principal_1'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_PrincipalList'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/list_PrincipleALL'))
		WebUI.click(findTestObject('Object Repository/Applications/btn_ConditionBuilderDeny'))
		WebUI.click(findTestObject('Object Repository/Applications/input_ActionSelect'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_ActionList'), 10)
		TestObject ActiondynamicObject = new TestObject()
		ActiondynamicObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'suggestions-list actions-list')]/descendant::li[text()='ALL']")
		WebUI.waitForElementPresent(ActiondynamicObject, 10)
		WebUI.click(ActiondynamicObject)
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddResource'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/container_Resource'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/input_Resource_1'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_PrincipalList'), 10)
		TestObject ResourcedynamicObject = new TestObject()
		ResourcedynamicObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'suggestions-list')]/descendant::li[text()='ALL']")
		WebUI.waitForElementPresent(ResourcedynamicObject, 10)
		WebUI.click(ResourcedynamicObject)
		WebUI.delay(1)
		WebUI.click(findTestObject('Object Repository/Applications/btn_ConditionBuilderDeny'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/slide_ConditionBuilder'), 10)
		WebUI.sendKeys(findTestObject('Object Repository/Applications/textArea_ConditionGroupOne'), GlobalVariable.DenyPolicy)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddCondition1'))
		WebUI.sendKeys(findTestObject('Object Repository/Applications/textArea_ConditionGroupTwo'), GlobalVariable.DenyPolicySec)
		WebUI.click(findTestObject('Object Repository/Applications/btn_ConditionBuilderSave'))
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddLibrary'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/checkBox_RestrictCloudProvisioning'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/checkBox_RestrictCloudProvisioning'))
		WebUI.click(findTestObject('Object Repository/Applications/checkBox_UserManagingReportees'))
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddPolicyLibrary'))
		WebUI.delay(0.5)
		WebUI.dragAndDropToObject(findTestObject('Object Repository/Applications/arrowRight_Doctor'), findTestObject('Object Repository/Applications/arrowLeft_ActionALL'))
		WebUI.click(findTestObject('Object Repository/Applications/btn_Code'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/slide_CodePanel'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddCode'))
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Applications/title_NewAccordion'))
		WebUI.sendKeys(findTestObject('Object Repository/Applications/textArea_NewAccordion'), GlobalVariable.TextAreaBudget)
		WebUI.delay(1)
		WebUI.click(findTestObject('Object Repository/Applications/btn_Flag'))
		boolean isVisible = WebUI.verifyElementVisible(findTestObject('Object Repository/Applications/alert_Recommendation'), FailureHandling.OPTIONAL)
		if (isVisible) {
			println "Recommendation alert is displayed for the flag click"
			KeywordUtil.markPassed("Recommendation alert is displayed for the flag click")
		} else {
			println "Recommendation alert is not displayed for the flag click"
			KeywordUtil.markFailed("Recommendation alert is not displayed for the flag click")
		}
		WebUI.click(findTestObject('Object Repository/Applications/btn_FlagRecommendation'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/slide_Recommendations'), 10)
		def elementsText = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Applications/text_Violations'), 10)
		def expectedText = GlobalVariable.ViolationsPoliciesALL
		if (elementsText.contains(expectedText)) {
			WebUI.comment("✅ Pass: Found exact match with '${expectedText}'")
		} else {
			WebUI.comment("ℹ️ No exact match found. Doing nothing.")
		}
		WebUI.click(findTestObject('Object Repository/Applications/btn_CloseRecommendation'))
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Applications/btn_SendToApproval'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/popup_PolicySummary'), 10)
	}

	@Keyword
	public void EditPolicy() {
		WebUI.click(findTestObject('Object Repository/Applications/tab_Policies'))
		WebUI.delay(0.5)
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_EditPolicy'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/btn_EditPolicy'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/popup_EditPolicy'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/radio_SelectVersion'))
		WebUI.click(findTestObject('Object Repository/Applications/btn_Continue'))
		WebUI.waitForElementClickable(findTestObject('Object Repository/Applications/btn_Test'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/btn_Test'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/slide_AccessMap'), 10)
		WebUI.setText(findTestObject('Object Repository/Applications/input_AccessMapAI'), GlobalVariable.AccessMapPrompt)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AIPromptSubmit'))
		def elements = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Applications/list_BeforeDeleteDeny'), 10)
		String targetText = GlobalVariable.PatientName
		for (WebElement element : elements) {
			String text = element.getText().trim()
			if (text == targetText) {
				WebUI.comment("✅ Found '${targetText}' — hovering using Actions class.")

				Actions action = new Actions(DriverFactory.getWebDriver())
				action.moveToElement(element).perform()
				WebDriver driver = DriverFactory.getWebDriver()
				JavascriptExecutor js = (JavascriptExecutor) driver
				js.executeScript("arguments[0].style.border='3px solid red'", element)
				getScreenShotWithHighlight.takeHighlightedScreenshotWithOutIndex(element, "VIP_Patient")
				break
			}
		}
		WebUI.click(findTestObject('Object Repository/Applications/btn_CloseTest'))
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Applications/btn_Code'))
		WebUI.waitForElementClickable(findTestObject('Object Repository/Applications/btn_DeleteDenyPolicy'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/btn_DeleteDenyPolicy'))
		WebUI.click(findTestObject('Object Repository/Applications/btn_CloseCode'))
		
	}

	@Keyword
	public void DesignConditionsPolicy() {
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddPolicy'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/link_PermitPolicy'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/link_PermitPolicy'))
		WebUI.click(findTestObject('Object Repository/Applications/input_Principal_1'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_PrincipalList'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/list_PrincipleALL'))
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddActionAll'))
		WebUI.click(findTestObject('Object Repository/Applications/input_ActionSelect'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_ActionList'), 10)
		TestObject ActiondynamicObject = new TestObject()
		ActiondynamicObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'suggestions-list actions-list')]/descendant::li[text()='ALL']")
		WebUI.waitForElementPresent(ActiondynamicObject, 10)
		WebUI.click(ActiondynamicObject)
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddResource'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/container_Resource'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/input_Resource_1'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_PrincipalList'), 10)
		TestObject ResourcedynamicObject = new TestObject()
		ResourcedynamicObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'suggestions-list')]/descendant::li[text()='ALL']")
		WebUI.waitForElementPresent(ResourcedynamicObject, 10)
		WebUI.click(ResourcedynamicObject)
		WebUI.delay(0.5)
	}

	@Keyword
	public void DesignPolicyAll(String principalType, String actionType, String resourceType) {
		WebUI.click(findTestObject('Object Repository/Applications/tab_Policies'))
		WebUI.delay(0.5)
		TestObject DesignPolicyBtn = findTestObject('Object Repository/Applications/btn_DesignPolicy')
		TestObject NewVersionBtn = findTestObject('Object Repository/Applications/btn_NewVersion')
		if (WebUI.verifyElementPresent(DesignPolicyBtn, 5, FailureHandling.OPTIONAL)) {
			WebUI.click(DesignPolicyBtn)
			KeywordUtil.logInfo("Clicked 'Design Policy' button.")
		} else if (WebUI.verifyElementPresent(NewVersionBtn, 5, FailureHandling.OPTIONAL)) {
			WebUI.click(NewVersionBtn)
			KeywordUtil.logInfo("Clicked 'New Version' button.")
		} else {
			KeywordUtil.markFailed("❌ Neither 'Design Policy' nor 'New Version' button was found.")
			return
		}
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/buildPolicy_Section'), 10)
		WebUI.verifyElementPresent(findTestObject('Object Repository/Applications/buildPolicy_Section'), 5)
		KeywordUtil.logInfo("✅ Policy section loaded successfully.")
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddPermitPolicy'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_Code'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/input_Principal_1'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_PrincipalList'), 10)
		TestObject PrincipaldynamicObject = new TestObject()
		PrincipaldynamicObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'suggestions-list')]/descendant::li[text()='" + principalType + "']")
		WebUI.waitForElementPresent(PrincipaldynamicObject, 10)
		WebUI.click(PrincipaldynamicObject)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddActionNode'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/input_Action_1'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/input_Action_1'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_ActionList'), 10)
		TestObject ActiondynamicObject = new TestObject()
		ActiondynamicObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'suggestions-list actions-list')]/descendant::li[text()='" + actionType + "']")
		WebUI.waitForElementPresent(ActiondynamicObject, 10)
		WebUI.click(ActiondynamicObject)
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddResource'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/container_Resource'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/input_Resource_1'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_PrincipalList'), 10)
		TestObject ResourcedynamicObject = new TestObject()
		ResourcedynamicObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'suggestions-list')]/descendant::li[text()='" + resourceType + "']")
		WebUI.waitForElementPresent(ResourcedynamicObject, 10)
		WebUI.click(ResourcedynamicObject)
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Applications/btn_Violation'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/tab_Warnings'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/tab_Warnings'))
		WebUI.delay(1)
		def elements = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Applications/txt_WarningCondition'), 10)
		def actualLines = elements.collect { it.getText().trim() }
		def expectedLines = GlobalVariable.WarningConditionMsg.split("\\+").collect { it.trim() }

		WebUI.comment("🔍 Actual: ${actualLines}")
		WebUI.comment("✅ Expected: ${expectedLines}")

		if (actualLines == expectedLines) {
			WebUI.comment("✅ Pass: All messages match.")
		} else {
			WebUI.comment("❌ Fail: Text mismatch in warning messages.")
			WebUI.comment("❗ Expected: ${expectedLines}")
			WebUI.comment("❗ Actual: ${actualLines}")
			assert false : "Text mismatch"
		}
	}

	@Keyword
	public void VerifyVersionHistoryStatus(String Status) {
		WebUI.click(findTestObject('Object Repository/Applications/tab_VersionHistory'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/table_VersionHistory'), 10)
		String actualText = WebUI.getText(findTestObject('Object Repository/Applications/label_VersionHistoryStatus'))
		if (actualText == Status) {
			WebUI.comment("Status is 'Pending Approval' - Test Passed.")
		} else {
			KeywordUtil.markFailed("Expected status 'Pending Approval' but found '${actualText}'")
		}
		CustomKeywords.'commonFunctions.browserUtils.CloseBrowser'()
	}

	@Keyword
	public void ProvideApprovals(String appUserName, String appUserPassword) {
		CustomKeywords.'commonFunctions.browserUtils.openBrowser'()
		CustomKeywords.'loginPage.NavigateToLogin.NavigateUrl'(GlobalVariable.ApprovalUrl)
		String usrName = appUserName;
		String usrPass = appUserPassword;
		CustomKeywords.'loginPage.NavigateToLogin.Login'(usrName, usrPass)
		//ValidateApplication()
		//WebUI.click(findTestObject('Object Repository/Applications/tab_Policies'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_AppSearch'), 10)
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_AppSearch'), GlobalVariable.PolicyDisplayName)
		WebUI.delay(3)
		String applicationStatus = "//table[contains(@class, 'chakra-table')]/descendant::a[contains(text(),'" + GlobalVariable.PolicyDisplayName  + "')][last()-1]"
		TestObject testDataStatusText = new TestObject().addProperty("xpath", ConditionType.EQUALS, applicationStatus)
		WebUI.click(testDataStatusText)
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_Approve'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/btn_Approve'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/textArea_ApprovalComments'), 10)
		WebUI.setText(findTestObject('Object Repository/Applications/textArea_ApprovalComments'), "Approved")
		WebUI.click(findTestObject('Object Repository/Applications/btn_ApprovalSubmit'))


		/*WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_PublishPD'), 10)
		 WebUI.click(findTestObject('Object Repository/Applications/btn_PublishPD'))
		 WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Tabs'), 10)
		 String statusText = WebUI.getText(findTestObject('Object Repository/Applications/label_ApprovalStatus'))
		 if (statusText == 'New Policy created') {
		 WebUI.comment("✅ Approved successful - Test Passed.")
		 } else {
		 KeywordUtil.markFailed("❌ Expected status 'New Policy created' but found '${statusText}'")
		 }*/
		//CustomKeywords.'commonFunctions.browserUtils.CloseBrowser'()
	}
}
