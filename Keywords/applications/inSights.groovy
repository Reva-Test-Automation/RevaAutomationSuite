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
import commonFunctions.getScreenShotWithHighlight
import commonFunctions.browserUtils
import internal.GlobalVariable
import com.kms.katalon.core.util.KeywordUtil
import org.openqa.selenium.Keys
import loginPage.NavigateToLogin
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import CustomKeywords

public class inSights {

	@Keyword
	public void ValidateApplication() {
		WebUI.refresh()
		WebUI.click(findTestObject('Object Repository/Applications/btn_Applications'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_AppSearch'), 20)
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_AppSearch'), GlobalVariable.ApplicationName)
		WebUI.delay(3)
		WebUI.click(findTestObject('Object Repository/Applications/btn_GridView'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_NewApplication'), 20)
		String applicationObjectName = "//div[contains(@class,'fixedNavbar')]/following-sibling::div/descendant::p[text()='" + GlobalVariable.ApplicationName + "']"
		TestObject applicationElement = new TestObject().addProperty("xpath", ConditionType.EQUALS, applicationObjectName)
		WebUI.waitForElementVisible(applicationElement, 20)
		WebUI.click(applicationElement)
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Tabs'), 20)


		/*WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Applications'), 20)
		 WebUI.click(findTestObject('Object Repository/Applications/link_Automation'))
		 String appName = "//p[text()='Automation']/ancestor::a/following-sibling::div/descendant::p[contains(text(),'"+GlobalVariable.ApplicationName+"')]"
		 TestObject appLink = new TestObject().addProperty("xpath", ConditionType.EQUALS, appName)
		 WebUI.click(appLink)
		 WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Applications'), 20)
		 String envName = "//p[text()='"+GlobalVariable.EnvironmentName+"']"
		 TestObject envLink = new TestObject().addProperty("xpath", ConditionType.EQUALS, envName)
		 WebUI.click(envLink)
		 String policyName = "//p[contains(text(),'"+GlobalVariable.PolicyDisplayName+"')]"
		 TestObject policyLink = new TestObject().addProperty("xpath", ConditionType.EQUALS, policyName)
		 WebUI.click(policyLink)
		 WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Tabs'), 20)*/
	}

	@Keyword
	public void NavigateToProvidenceHospitalProd() {
		WebUI.click(findTestObject('Object Repository/Applications/btn_Applications'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Applications'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/link_EnterpriseApplications'))
		WebUI.waitForElementVisible(findTestObject('Object Repository/Applications/link_ProvidenceHospital'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/link_ProvidenceHospital'))
		WebUI.waitForElementVisible(findTestObject('Object Repository/Applications/link_ProdEnv'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/link_ProdEnv'))
		/*WebUI.waitForElementVisible(findTestObject('Object Repository/Applications/link_ProductionStore'), 20)
		 WebUI.click(findTestObject('Object Repository/Applications/link_ProductionStore'))*/
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Tabs'), 20)
	}

	@Keyword
	public void verifyInsightsTab() {
		WebUI.click(findTestObject('Object Repository/Applications/tab_Insights'))
		WebUI.delay(1)
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
			}, 20)
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
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/slide_TopActivity'), 20)
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
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/buildPolicy_Section'), 20)
		WebUI.verifyElementPresent(findTestObject('Object Repository/Applications/buildPolicy_Section'), 20)
		KeywordUtil.logInfo("✅ Policy section loaded successfully.")
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddPermitPolicy'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_Code'), 20)
		WebUI.verifyElementVisible(findTestObject('Object Repository/Applications/btn_Code'))
		KeywordUtil.logInfo("✅ Add Permit Policy button clicked and Code button appeared.")
		WebUI.click(findTestObject('Object Repository/Applications/btn_Code'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/code_TextArea'), 20)
		WebUI.verifyElementVisible(findTestObject('Object Repository/Applications/code_TextArea'))
		KeywordUtil.logInfo("✅ Code editor is now visible.")
		WebUI.click(findTestObject('Object Repository/Applications/btn_MenuDots'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_ListDots'), 20)
		WebUI.verifyElementVisible(findTestObject('Object Repository/Applications/menu_ListDots'))
		KeywordUtil.logInfo("✅ Menu list appeared after clicking dots.")
		WebUI.click(findTestObject('Object Repository/Applications/btn_DeleteContainer'))
		WebUI.verifyElementPresent(findTestObject('Object Repository/Applications/btn_Add'), 20)
		KeywordUtil.logInfo("✅ Existing container deleted successfully.")
		WebUI.click(findTestObject('Object Repository/Applications/btn_Add'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/textArea_Code'), 20)
		KeywordUtil.logInfo("✅ New code container added successfully.")
		WebUI.sendKeys(findTestObject('Object Repository/Applications/textArea_Code'), GlobalVariable.PolicyCodePermitAll)
		WebUI.click(findTestObject('Object Repository/Applications/btn_SendToApproval'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/popup_PolicySummary'), 20)
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_SendForApprovalSecond'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/btn_SendForApprovalSecond'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Tabs'), 20)
		WebUI.verifyElementVisible(findTestObject('Object Repository/Applications/menu_Tabs'))
		KeywordUtil.logInfo("✅ Policy sent for approval successfully")
	}

	@Keyword
	public void EditCreatedPolicy() {
		WebUI.click(findTestObject('Object Repository/Applications/tab_Policies'))
		WebUI.delay(0.5)
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/link_CreatedPolicy'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/link_CreatedPolicy'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_Code'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/btn_Code'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/code_TextArea'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/btn_Add'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/textArea_CodeSecond'), 20)
		WebUI.sendKeys(findTestObject('Object Repository/Applications/textArea_CodeSecond'), GlobalVariable.PolicyCodePermitStaff)
		WebUI.click(findTestObject('Object Repository/Applications/btn_PublishPD'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Tabs'), 20)
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
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/buildPolicy_Section'), 20)
		WebUI.verifyElementPresent(findTestObject('Object Repository/Applications/buildPolicy_Section'), 20)
		KeywordUtil.logInfo("✅ Policy section loaded successfully.")
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddPermitPolicy'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_Code'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/input_Principal_1'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_PrincipalList'), 20)
		TestObject PrincipaldynamicObject = new TestObject()
		PrincipaldynamicObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'suggestions-list')]/descendant::li[text()='" + principalType + "']")
		WebUI.waitForElementPresent(PrincipaldynamicObject, 20)
		WebUI.click(PrincipaldynamicObject)
		WebUI.setText(findTestObject('Object Repository/Applications/input_Principal_1'), principalInput)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddActionNode'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/input_Action_1'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/input_Action_1'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_ActionList'), 20)
		TestObject ActiondynamicObject = new TestObject()
		ActiondynamicObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'suggestions-list actions-list')]/descendant::li[text()='" + actionType + "']")
		WebUI.waitForElementPresent(ActiondynamicObject, 20)
		WebUI.click(ActiondynamicObject)
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddResource'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/container_Resource'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/input_Resource_1'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_PrincipalList'), 20)
		TestObject ResourcedynamicObject = new TestObject()
		ResourcedynamicObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'suggestions-list')]/descendant::li[text()='" + resourceType + "']")
		WebUI.waitForElementPresent(ResourcedynamicObject, 20)
		WebUI.click(ResourcedynamicObject)
		WebUI.delay(0.5)
		WebUI.setText(findTestObject('Object Repository/Applications/input_Principal_1'), resourceInput)
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddCondition'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/slide_ConditionBuilder'), 20)
		WebUI.sendKeys(findTestObject('Object Repository/Applications/textArea_ConditionGroupOne'), conditionGroupInput)
		WebUI.click(findTestObject('Object Repository/Applications/btn_ConditionBuilderSave'))
		if (WebUI.verifyElementPresent(findTestObject('Object Repository/Applications/btn_SendToApproval'), 5, FailureHandling.OPTIONAL)) {
			WebUI.click(findTestObject('Object Repository/Applications/btn_SendToApproval'))
			KeywordUtil.logInfo("Clicked 'Send For Approval' button.")
		} else if (WebUI.verifyElementPresent(findTestObject('Object Repository/Applications/btn_Publish'), 5, FailureHandling.OPTIONAL)) {
			WebUI.click(findTestObject('Object Repository/Applications/btn_Publish'))
			KeywordUtil.logInfo("Clicked 'Publish' button.")
		}
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/popup_PolicySummary'), 20)
		if (WebUI.verifyElementPresent(findTestObject('Object Repository/Applications/btn_SendForApprovalSecond'), 5, FailureHandling.OPTIONAL)) {
			WebUI.click(findTestObject('Object Repository/Applications/btn_SendForApprovalSecond'))
			KeywordUtil.logInfo("Clicked 'Send For Approval' button.")
		} else if (WebUI.verifyElementPresent(findTestObject('Object Repository/Applications/btn_PublishPD'), 5, FailureHandling.OPTIONAL)) {
			WebUI.click(findTestObject('Object Repository/Applications/btn_PublishPD'))
			KeywordUtil.logInfo("Clicked 'Publish' button.")
		}
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Tabs'), 20)
		WebUI.verifyElementVisible(findTestObject('Object Repository/Applications/menu_Tabs'))
		KeywordUtil.logInfo("✅ Policy sent for approval successfully")
		WebUI.back()
		WebUI.delay(0.5)
	}
	
	
	@Keyword
	public void DesignBankingPolicy() {
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
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/buildPolicy_Section'), 20)
		WebUI.verifyElementPresent(findTestObject('Object Repository/Applications/buildPolicy_Section'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/btn_CreateWithAI'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/header_RevaAI'), 20)
		WebUI.waitForElementClickable(findTestObject('Object Repository/Applications/textArea_AIPrompt'), 20)
		WebUI.delay(1)
		WebUI.setText(findTestObject('Object Repository/Applications/textArea_AIPrompt'), GlobalVariable.BankingAIPrompt)
		WebUI.click(findTestObject('Object Repository/Applications/btn_PromptEnter'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/text_AIResponse'), 20)
		WebUI.waitForElementClickable(findTestObject('Object Repository/Applications/btn_AIResponseYes'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AIResponseYes'))
		WebUI.click(findTestObject('Object Repository/Applications/btn_CloseAI'))
		WebUI.delay(0.5)
		
	}

	@Keyword
	public void DesignHospitalPolicy() {
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
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/buildPolicy_Section'), 20)
		WebUI.verifyElementPresent(findTestObject('Object Repository/Applications/buildPolicy_Section'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/btn_CreateWithAI'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/header_RevaAI'), 20)
		WebUI.waitForElementClickable(findTestObject('Object Repository/Applications/textArea_AIPrompt'), 20)
		WebUI.delay(1)
		WebUI.setText(findTestObject('Object Repository/Applications/textArea_AIPrompt'), GlobalVariable.DoctorAIPrompt)
		WebUI.click(findTestObject('Object Repository/Applications/btn_PromptEnter'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/text_AIResponse'), 20)
		WebUI.waitForElementClickable(findTestObject('Object Repository/Applications/btn_AIResponseYes'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AIResponseYes'))
		WebUI.click(findTestObject('Object Repository/Applications/btn_CloseAI'))
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddPolicy'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/link_PermitPolicy'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/link_PermitPolicy'))
		WebUI.click(findTestObject('Object Repository/Applications/input_Principal_1'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_PrincipalList'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/list_PrincipleALL'))
		WebUI.click(findTestObject('Object Repository/Applications/btn_ConditionBuilderDeny'))
		WebUI.click(findTestObject('Object Repository/Applications/input_ActionSelect'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_ActionList'), 20)
		TestObject ActiondynamicObject = new TestObject()
		ActiondynamicObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'suggestions-list actions-list')]/descendant::li[text()='ALL']")
		WebUI.waitForElementPresent(ActiondynamicObject, 20)
		WebUI.click(ActiondynamicObject)
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddResource'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/container_Resource'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/input_Resource_1'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_PrincipalList'), 20)
		TestObject ResourcedynamicObject = new TestObject()
		ResourcedynamicObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'suggestions-list')]/descendant::li[text()='ALL']")
		WebUI.waitForElementPresent(ResourcedynamicObject, 20)
		WebUI.click(ResourcedynamicObject)
		WebUI.delay(1)
		WebUI.click(findTestObject('Object Repository/Applications/btn_ConditionBuilderDeny'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/slide_ConditionBuilder'), 20)
		WebUI.sendKeys(findTestObject('Object Repository/Applications/textArea_ConditionGroupOne'), GlobalVariable.DenyPolicy)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddCondition1'))
		WebUI.sendKeys(findTestObject('Object Repository/Applications/textArea_ConditionGroupTwo'), GlobalVariable.DenyPolicySec)
		WebUI.click(findTestObject('Object Repository/Applications/btn_ConditionBuilderSave'))
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddLibrary'))
		WebUI.delay(1)
		TestObject checkboxLabels = findTestObject('Object Repository/Library/list_PolicyCheckBoxs')
		List<WebElement> allCheckboxLabels = WebUiCommonHelper.findWebElements(checkboxLabels, 20)
		for (WebElement label : allCheckboxLabels) {
			if (label.getAttribute("data-checked") != null) {
				WebElement pTag = label.findElement(By.xpath("following-sibling::p"))
				pTag.click()
				KeywordUtil.logInfo("☑️ Checkbox was checked — clicked <p> to uncheck.")
			} else {
				KeywordUtil.logInfo("🔲 Checkbox already unchecked.")
			}
		}
		TestObject titlePolicyObject = findTestObject('Object Repository/Library/list_PolicyLib')
		List<WebElement> elements = WebUiCommonHelper.findWebElements(titlePolicyObject, 20)
		boolean matchFound = false
		for (WebElement el : elements) {
			String actualText = el.getText().trim()
			if (actualText == GlobalVariable.LibHospitalPolicy) {
				KeywordUtil.markPassed("✅ Match found: '${actualText}'")
				el.click()
				KeywordUtil.logInfo("🖱️ Clicked on element with text: '${actualText}'")
				matchFound = true
				break
			}
		}
		if (!matchFound) {
			KeywordUtil.markFailed("❌ No matching title found for '${GlobalVariable.LibPolicyName}'")
		}
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddPolicyLibrary'))
		WebUI.delay(0.5)
		WebUI.dragAndDropToObject(findTestObject('Object Repository/Applications/arrowRight_Doctor'), findTestObject('Object Repository/Applications/arrowLeft_ActionALL'))
		WebUI.click(findTestObject('Object Repository/Applications/btn_Code'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/slide_CodePanel'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddCode'))
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Applications/title_NewAccordion'))
		WebUI.sendKeys(findTestObject('Object Repository/Applications/textArea_NewAccordion'), GlobalVariable.TextAreaBudget)
		WebUI.delay(1)
		/*WebUI.click(findTestObject('Object Repository/Applications/btn_Flag'))
		 boolean isVisible = WebUI.verifyElementVisible(findTestObject('Object Repository/Applications/alert_Recommendation'), FailureHandling.OPTIONAL)
		 if (isVisible) {
		 println "Recommendation alert is displayed for the flag click"
		 KeywordUtil.markPassed("Recommendation alert is displayed for the flag click")
		 } else {
		 println "Recommendation alert is not displayed for the flag click"
		 KeywordUtil.markFailed("Recommendation alert is not displayed for the flag click")
		 }*/
		WebUI.click(findTestObject('Object Repository/Applications/btn_FlagRecommendation'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/slide_Recommendations'), 20)
		def elementsText = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Applications/text_Violations'), 20)
		def expectedText = GlobalVariable.ViolationsPoliciesALL
		if (elementsText.contains(expectedText)) {
			WebUI.comment("✅ Pass: Found exact match with '${expectedText}'")
		} else {
			WebUI.comment("ℹ️ No exact match found. Doing nothing.")
		}
		WebUI.click(findTestObject('Object Repository/Applications/btn_CloseRecommendation'))
		WebUI.delay(1)
		WebUI.click(findTestObject('Object Repository/Applications/btn_SendToApproval'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/popup_PolicySummary'), 20)
		WebUI.delay(2)
		WebUI.click(findTestObject('Object Repository/Applications/btn_SendForApprovalSecond'))
		WebUI.delay(2)
	}

	@Keyword
	public void EditPolicy(String appName) {
		CustomKeywords.'loginPage.NavigateToLogin.NavigateUrl'(GlobalVariable.origin)
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_Applications'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/btn_Applications'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_NewApplication'), 20)
		String applicationObjectName = "//div[contains(@class,'fixedNavbar')]/following-sibling::div/descendant::p[text()='" + appName + "']"
		TestObject applicationElement = new TestObject().addProperty("xpath", ConditionType.EQUALS, applicationObjectName)
		WebUI.waitForElementVisible(applicationElement, 20)
		WebUI.click(applicationElement)
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Tabs'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/tab_Policies'))
		WebUI.delay(0.5)
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_EditPolicy'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/btn_EditPolicy'))
		WebUI.delay(2)
		boolean isPopupVisible = WebUI.verifyElementPresent(findTestObject('Object Repository/Applications/popup_EditPolicy'), 5, FailureHandling.OPTIONAL)
		if (isPopupVisible) {
			WebUI.waitForElementClickable(findTestObject('Object Repository/Applications/btn_CreateNewDraft'), 20)
			WebUI.click(findTestObject('Object Repository/Applications/btn_CreateNewDraft'))
			WebUI.waitForElementClickable(findTestObject('Object Repository/Applications/btn_Test'), 20)
			WebUI.click(findTestObject('Object Repository/Applications/btn_Test'))
		} else {
			WebUI.waitForElementClickable(findTestObject('Object Repository/Applications/btn_Test'), 20)
			WebUI.click(findTestObject('Object Repository/Applications/btn_Test'))
		}
		WebUI.delay(0.5)
		WebUI.waitForElementNotPresent(findTestObject('Object Repository/Applications/img_Processing'), 20)
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/slide_AccessMap'), 20)
		WebUI.setText(findTestObject('Object Repository/Applications/input_AccessMapAI'), GlobalVariable.AccessMapPrompt)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AIPromptSubmit'))
		def elements = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Applications/list_BeforeDeleteDeny'), 20)
		String targetText = GlobalVariable.PatientName
		for (WebElement element : elements) {
			String text = element.getText().trim()
			if (text == targetText) {
				WebUI.comment("✅ Found '${targetText}' — hovering using Actions class.")
				Actions action = new Actions(DriverFactory.getWebDriver())
				action.moveToElement(element).perform()
				WebUI.delay(0.5)
				List<WebElement> nodes = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Applications/node_FromToConnection'), 20)
				int nodeCount = nodes.size()
				WebUI.comment("🔍 Total nodes found: ${nodeCount}")
				if (nodeCount == 2) {
					KeywordUtil.markPassed("✅ Test Passed: Exactly 2 nodes found.")
				} else {
					KeywordUtil.markFailed("❌ Test Failed: Expected 2 nodes, but found ${nodeCount}.")
				}
				break
			}
		}
		WebUI.click(findTestObject('Object Repository/Applications/btn_CloseTest'))
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Applications/btn_Code'))
		WebUI.waitForElementClickable(findTestObject('Object Repository/Applications/btn_DeleteDenyPolicy'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/btn_DeleteDenyPolicy'))
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Applications/btn_CloseCode'))
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Applications/btn_Test'))
		WebUI.delay(3)
		WebUI.waitForElementNotPresent(findTestObject('Object Repository/Applications/img_Processing'), 20)
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/slide_AccessMap'), 20)
		WebUI.setText(findTestObject('Object Repository/Applications/input_AccessMapAI'), GlobalVariable.AccessMapPrompt)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AIPromptSubmit'))
		def nodeElements = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Applications/list_BeforeDeleteDeny'), 20)
		String targetPatientText = GlobalVariable.PatientName
		for (WebElement nodeelement : nodeElements) {
			String patientText = nodeelement.getText().trim()
			if (patientText == targetPatientText) {
				WebUI.comment("✅ Found '${targetPatientText}' — hovering using Actions class.")
				Actions action = new Actions(DriverFactory.getWebDriver())
				action.moveToElement(nodeelement).perform()
				WebUI.delay(0.5)
				List<WebElement> nodes = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Applications/node_FromToConnection'), 20)
				int nodeCount = nodes.size()
				WebUI.comment("🔍 Total nodes found: ${nodeCount}")
				if (nodeCount == 5) {
					KeywordUtil.markPassed("✅ Test Passed: Exactly 5 nodes found.")
				} else {
					KeywordUtil.markFailed("❌ Test Failed: Expected 5 nodes, but found ${nodeCount}.")
				}
				break
			}
		}
		WebUI.click(findTestObject('Object Repository/Applications/btn_CloseTest'))
		WebUI.waitForElementClickable(findTestObject('Object Repository/Applications/btn_SendToApproval'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/btn_SendToApproval'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Impact/table_PolicySummary'), 20)
		WebUI.click(findTestObject('Object Repository/Impact/btn_Impact'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Impact/frame_Impact'), 20)
		List<String> impactItems = GlobalVariable.ImpactList.split(',').collect { it.trim() }
		TestObject impactListObject = findTestObject('Object Repository/Impact/list_Impact')
		List<WebElement> impactElements = WebUiCommonHelper.findWebElements(impactListObject, 20)
		for (String expectedImpact : impactItems) {
			boolean found = false
			for (WebElement element : impactElements) {
				String actualText = element.getText().trim()
				if (actualText.equalsIgnoreCase(expectedImpact)) {
					println "✅ Impact matched: ${expectedImpact}"
					found = true
					break
				}
			}
			if (!found) {
				println "❌ Not Found: ${expectedImpact}"
				KeywordUtil.markWarning("Impact not found in the list: ${expectedImpact}")
			}
		}
	}

	@Keyword
	public void SettingsTab_PublishAVPPolicy() {
		WebUI.click(findTestObject('Object Repository/SettingsTab/settingsTab'))
		WebUI.waitForElementClickable(findTestObject('Object Repository/SettingsTab/btn_PublishPolicy'), 20)
		WebUI.click(findTestObject('Object Repository/SettingsTab/btn_PublishPolicy'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/SettingsTab/title_PolicyTypeSelection'), 20)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/select_PolicyConnection'))
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/select_PolicyConnection'), 'AVP')
		String connectionObjectName = "//div[text()='AVP']"
		TestObject connectionElement = new TestObject().addProperty("xpath", ConditionType.EQUALS, connectionObjectName)
		WebUI.waitForElementVisible(connectionElement, 20)
		WebUI.click(connectionElement)
		WebUI.click(findTestObject('Object Repository/SettingsTab/btn_Save'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/SettingsTab/dropDown_SelectIntegration'), 20)
		WebUI.click(findTestObject('Object Repository/SettingsTab/select_Integration'))
		WebUI.setText(findTestObject('Object Repository/SettingsTab/select_Integration'), GlobalVariable.ConnectionName)
		String integrationObjectName = "//div[text()='"+GlobalVariable.ConnectionName+"']"
		TestObject integrationElement = new TestObject().addProperty("xpath", ConditionType.EQUALS, integrationObjectName)
		WebUI.waitForElementVisible(integrationElement, 20)
		WebUI.click(integrationElement)
		String status = WebUI.getText(findTestObject('Object Repository/SettingsTab/text_IntegrationStatus'))
		if (status.equalsIgnoreCase("Active")) {
			KeywordUtil.markPassed("Integration status is Active.")
		} else {
			KeywordUtil.markFailed("Integration status is not Active. Found: " + status)
		}
		WebUI.waitForElementClickable(findTestObject('Object Repository/SettingsTab/select_AVPNewPolicy'), 20)
		WebUI.click(findTestObject('Object Repository/SettingsTab/select_AVPNewPolicy'))
		WebUI.click(findTestObject('Object Repository/SettingsTab/btn_Save'))
		WebUI.delay(10)
		if (WebUI.verifyElementVisible(findTestObject('Object Repository/SettingsTab/label_StatusOnline'), FailureHandling.OPTIONAL)) {
			KeywordUtil.markPassed("Policy store status is Online.")
		} else {
			KeywordUtil.markFailed("Policy store status is NOT Online or element not found.")
		}
	}

	@Keyword
	public void SettingsTab_PublishAVPGITPolicy() {
		WebUI.click(findTestObject('Object Repository/SettingsTab/settingsTab'))
		WebUI.waitForElementClickable(findTestObject('Object Repository/SettingsTab/btn_PublishPolicy'), 20)
		WebUI.click(findTestObject('Object Repository/SettingsTab/btn_PublishPolicy'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/SettingsTab/title_PolicyTypeSelection'), 20)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/select_PolicyConnection'))
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/select_PolicyConnection'), 'AVP')
		String connectionObjectName = "//div[text()='AVP']"
		TestObject connectionElement = new TestObject().addProperty("xpath", ConditionType.EQUALS, connectionObjectName)
		WebUI.waitForElementVisible(connectionElement, 20)
		WebUI.click(connectionElement)
		WebUI.delay(1)
		WebUI.click(findTestObject('Object Repository/SettingsTab/link_Connect'))
		WebUI.click(findTestObject('Object Repository/SettingsTab/btn_Save'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/SettingsTab/dropDown_SelectIntegration'), 20)
		WebUI.click(findTestObject('Object Repository/SettingsTab/card_AvpGit'))
		WebUI.click(findTestObject('Object Repository/SettingsTab/select_Integration'))
		WebUI.setText(findTestObject('Object Repository/SettingsTab/select_Integration'), GlobalVariable.GitConnectionName)
		String integrationObjectName = "//div[text()='"+GlobalVariable.GitConnectionName+"']"
		TestObject integrationElement = new TestObject().addProperty("xpath", ConditionType.EQUALS, integrationObjectName)
		WebUI.waitForElementVisible(integrationElement, 20)
		WebUI.click(integrationElement)
		WebUI.waitForElementPresent(findTestObject('Object Repository/SettingsTab/text_IntegrationStatus'), 20)
		String status = WebUI.getText(findTestObject('Object Repository/SettingsTab/text_IntegrationStatus'))
		if (status.equalsIgnoreCase("Active")) {
			KeywordUtil.markPassed("Integration status is Active.")
		} else {
			KeywordUtil.markFailed("Integration status is not Active. Found: " + status)
		}
		WebUI.waitForElementClickable(findTestObject('Object Repository/SettingsTab/select_AVPNewPolicy'), 20)
		WebUI.click(findTestObject('Object Repository/SettingsTab/select_AVPNewPolicy'))
		WebUI.click(findTestObject('Object Repository/SettingsTab/btn_Save'))
		if (WebUI.verifyElementVisible(findTestObject('Object Repository/SettingsTab/label_StatusOnline'), FailureHandling.OPTIONAL)) {
			KeywordUtil.markPassed("Policy store status is Online.")
		} else {
			KeywordUtil.markFailed("Policy store status is NOT Online or element not found.")
		}
	}

	@Keyword
	public void DesignConditionsPolicy() {
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddPolicy'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/link_PermitPolicy'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/link_PermitPolicy'))
		WebUI.click(findTestObject('Object Repository/Applications/input_Principal_1'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_PrincipalList'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/list_PrincipleALL'))
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddActionAll'))
		WebUI.click(findTestObject('Object Repository/Applications/input_ActionSelect'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_ActionList'), 20)
		TestObject ActiondynamicObject = new TestObject()
		ActiondynamicObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'suggestions-list actions-list')]/descendant::li[text()='ALL']")
		WebUI.waitForElementPresent(ActiondynamicObject, 20)
		WebUI.click(ActiondynamicObject)
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddResource'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/container_Resource'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/input_Resource_1'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_PrincipalList'), 20)
		TestObject ResourcedynamicObject = new TestObject()
		ResourcedynamicObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'suggestions-list')]/descendant::li[text()='ALL']")
		WebUI.waitForElementPresent(ResourcedynamicObject, 20)
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
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/buildPolicy_Section'), 20)
		WebUI.verifyElementPresent(findTestObject('Object Repository/Applications/buildPolicy_Section'), 20)
		KeywordUtil.logInfo("✅ Policy section loaded successfully.")
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddPermitPolicy'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_Code'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/input_Principal_1'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_PrincipalList'), 20)
		TestObject PrincipaldynamicObject = new TestObject()
		PrincipaldynamicObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'suggestions-list')]/descendant::li[text()='" + principalType + "']")
		WebUI.waitForElementPresent(PrincipaldynamicObject, 20)
		WebUI.click(PrincipaldynamicObject)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddActionNode'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/input_Action_1'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/input_Action_1'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_ActionList'), 20)
		TestObject ActiondynamicObject = new TestObject()
		ActiondynamicObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'suggestions-list actions-list')]/descendant::li[text()='" + actionType + "']")
		WebUI.waitForElementPresent(ActiondynamicObject, 20)
		WebUI.click(ActiondynamicObject)
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddResource'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/container_Resource'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/input_Resource_1'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_PrincipalList'), 20)
		TestObject ResourcedynamicObject = new TestObject()
		ResourcedynamicObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'suggestions-list')]/descendant::li[text()='" + resourceType + "']")
		WebUI.waitForElementPresent(ResourcedynamicObject, 20)
		WebUI.click(ResourcedynamicObject)
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Applications/btn_Violation'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/tab_Violations'), 20)
		WebUI.delay(1)
	}

	@Keyword
	public void VerifyVersionHistoryStatus(String Status) {
		WebUI.click(findTestObject('Object Repository/Applications/tab_VersionHistory'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/table_VersionHistory'), 20)
		String actualText = WebUI.getText(findTestObject('Object Repository/Applications/label_VersionHistoryStatus'))
		if (actualText == Status) {
			WebUI.comment("Status is 'Pending Approval' - Test Passed.")
		} else {
			KeywordUtil.markFailed("Expected status 'Pending Approval' but found '${actualText}'")
		}
		CustomKeywords.'commonFunctions.browserUtils.CloseBrowser'()


		WebUI.waitForElementClickable(findTestObject('Object Repository/Applications/tab_VersionHistory'), 15)
		WebUI.click(findTestObject('Object Repository/Applications/tab_VersionHistory'))
		KeywordUtil.logInfo("✅ Clicked on Version History tab")

		// Step 2: Click on "v2" link
		WebUI.waitForElementClickable(findTestObject('Page_TestAutoAppName/link_Version_v2'), 15)
		WebUI.click(findTestObject('Page_TestAutoAppName/link_Version_v2'))
		KeywordUtil.logInfo("✅ Clicked on v2 link")

		// Step 3: Click on "Compare" and select "v3"
		WebUI.waitForElementClickable(findTestObject('Page_TestAutoAppName/button_Compare'), 15)
		WebUI.click(findTestObject('Page_TestAutoAppName/button_Compare'))
		KeywordUtil.logInfo("✅ Clicked on Compare button")

		WebUI.waitForElementClickable(findTestObject('Page_TestAutoAppName/dropdown_CompareVersion'), 15)
		WebUI.click(findTestObject('Page_TestAutoAppName/dropdown_CompareVersion'))
		KeywordUtil.logInfo("✅ Opened Compare dropdown")

		WebUI.waitForElementClickable(findTestObject('Page_TestAutoAppName/option_Version_v3'), 15)
		WebUI.click(findTestObject('Page_TestAutoAppName/option_Version_v3'))
		KeywordUtil.logInfo("✅ Selected Version v3 option")

		// Step 4: Verify Doctor John and all containers are highlighted in green
		if (WebUI.waitForElementVisible(findTestObject('Page_TestAutoAppName/container_DoctorJohn'), 20, FailureHandling.OPTIONAL)) {
			String actualClass = WebUI.getAttribute(findTestObject('Page_TestAutoAppName/container_DoctorJohn'), "class")
			if (actualClass.contains("highlight-green")) {
				KeywordUtil.logInfo("✅ Highlight verification passed for Doctor John container")
			} else {
				KeywordUtil.markWarning("⚠️ Expected 'highlight-green' but found '${actualClass}'")
			}
		} else {
			KeywordUtil.markFailed("❌ Doctor John container not visible for highlight check")
		}

		// Step 5: Click on 3 dots and select "Activate"
		WebUI.waitForElementClickable(findTestObject('Page_TestAutoAppName/button_ThreeDots'), 15)
		WebUI.click(findTestObject('Page_TestAutoAppName/button_ThreeDots'))
		KeywordUtil.logInfo("✅ Clicked on 3 dots menu")

		WebUI.waitForElementClickable(findTestObject('Page_TestAutoAppName/option_Activate'), 15)
		WebUI.click(findTestObject('Page_TestAutoAppName/option_Activate'))
		KeywordUtil.logInfo("✅ Clicked on Activate option")

		// Step 6: Confirm activation success
		if (WebUI.verifyElementText(findTestObject('Page_TestAutoAppName/status_Activation'), "Active", FailureHandling.OPTIONAL)) {
			KeywordUtil.markPassed("✅ Version activated successfully")
		} else {
			KeywordUtil.markFailed("❌ Activation status not updated")
		}
	}

	@Keyword
	public void ProvideApprovals(String appUserName, String appUserPassword) {
		CustomKeywords.'commonFunctions.browserUtils.openBrowser'()
		CustomKeywords.'loginPage.NavigateToLogin.NavigateUrl'(GlobalVariable.ApprovalUrl)
		String usrName = appUserName;
		String usrPass = appUserPassword;
		WebUI.delay(3)
		CustomKeywords.'loginPage.NavigateToLogin.Login'(usrName, usrPass)
		//ValidateApplication()
		//WebUI.click(findTestObject('Object Repository/Applications/tab_Policies'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_AppSearch'), 20)
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_AppSearch'), GlobalVariable.SearchPolicy)
		WebUI.delay(3)
		String applicationStatus = "//table[contains(@class, 'chakra-table')]/descendant::a[contains(text(),'" + GlobalVariable.SearchPolicy  + "')][1]"
		TestObject testDataStatusText = new TestObject().addProperty("xpath", ConditionType.EQUALS, applicationStatus)
		WebUI.click(testDataStatusText)
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_Approve'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/btn_Approve'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/textArea_ApprovalComments'), 20)
		WebUI.setText(findTestObject('Object Repository/Applications/textArea_ApprovalComments'), "Approved")
		WebUI.click(findTestObject('Object Repository/Applications/btn_ApprovalSubmit'))


		/*WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_PublishPD'), 20)
		 WebUI.click(findTestObject('Object Repository/Applications/btn_PublishPD'))
		 WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Tabs'), 20)
		 String statusText = WebUI.getText(findTestObject('Object Repository/Applications/label_ApprovalStatus'))
		 if (statusText == 'New Policy created') {
		 WebUI.comment("✅ Approved successful - Test Passed.")
		 } else {
		 KeywordUtil.markFailed("❌ Expected status 'New Policy created' but found '${statusText}'")
		 }*/
		//CustomKeywords.'commonFunctions.browserUtils.CloseBrowser'()
	}
}
