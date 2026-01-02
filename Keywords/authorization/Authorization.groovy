package authorization

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.testobject.ConditionType
import internal.GlobalVariable
import com.kms.katalon.core.util.KeywordUtil

public class Authorization {
	@Keyword
	public void VerifyViewerAuthorization() {
		TestObject settingsIcon = new TestObject("settingsIcon")
		settingsIcon.addProperty("xpath", ConditionType.EQUALS,
				"//nav[contains(@class,'menuGrp menuGrp3')]/descendant::a[last()-1]//*[local-name()='svg' and contains(@class,'chakra-icon')]")
		String svgClass = WebUI.getAttribute(settingsIcon, 'class')
		if (svgClass.contains("ltia1u")) {
			println("✅ Settings button is DISABLED for Viewer")
			KeywordUtil.markPassed("✅ Settings button is DISABLED for Viewer")
		} else if (svgClass.contains("1gghsms")) {
			println("✅ Settings button is ENABLED for Viewer")
			KeywordUtil.markFailed("✅ Settings button is ENABLED for Viewer")
		} else {
			println("⚠️ Unknown state, class = " + svgClass)
			KeywordUtil.markFailed("⚠️ Unknown state, class = " + svgClass)
		}
		WebUI.click(findTestObject('Object Repository/Applications/btn_Applications'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_AppSearch'), 10)
		TestObject newAppBtn = findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_NewApplication')
		String disabledAttr = WebUI.getAttribute(newAppBtn, 'disabled')
		if (disabledAttr != null) {
			println("✅ New Application button is DISABLED for Viewer")
			KeywordUtil.markPassed("✅ New Application button is DISABLED for Viewer")
		} else {
			println("✅ New Application button is ENABLED for Viewer")
			KeywordUtil.markFailed("✅ New Application button is ENABLED for Viewer")
		}
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_OnBoarding'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/btn_OnBoarding'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/input_SearchGuardrail'), 10)
		WebUI.sendKeys(findTestObject('Object Repository/Guardrails/input_SearchGuardrail'), "Automation_Testing")
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Authorization/Card_AppName'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Authorization/header_DraftVersion'), 10)
		TestObject newVersionBtn = findTestObject('Object Repository/Applications/btn_NewVersion')
		boolean isPresent = WebUI.verifyElementPresent(newVersionBtn, 5, FailureHandling.OPTIONAL)
		if (isPresent) {
			println("❌ 'New Version' button is present for Viewer")
			KeywordUtil.markFailed("❌ 'New Version' button is present for Viewer")
		} else {
			println("✅ 'New Version' button is NOT present for Viewer")
			KeywordUtil.markPassed("✅ 'New Version' button is NOT present for Viewer")
		}
	}

	@Keyword
	public void VerifyCollaboratorAuthorization() {
		TestObject settingsIcon = new TestObject("settingsIcon")
		settingsIcon.addProperty("xpath", ConditionType.EQUALS,
				"//nav[contains(@class,'menuGrp menuGrp3')]/descendant::a[last()-1]//*[local-name()='svg' and contains(@class,'chakra-icon')]")
		String svgClass = WebUI.getAttribute(settingsIcon, 'class')
		if (svgClass.contains("ltia1u")) {
			println("✅ Settings button is DISABLED for Collaborator")
			KeywordUtil.markPassed("✅ Settings button is DISABLED for Collaborator")
		} else if (svgClass.contains("1gghsms")) {
			println("✅ Settings button is ENABLED for Collaborator")
			KeywordUtil.markFailed("✅ Settings button is ENABLED for Collaborator")
		}
		WebUI.click(findTestObject('Object Repository/Applications/btn_Applications'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_AppSearch'), 10)
		TestObject newAppBtn = findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_NewApplication')
		String disabledAttr = WebUI.getAttribute(newAppBtn, 'disabled')
		if (disabledAttr != null) {
			println("✅ New Application button is DISABLED for Collaborator")
			KeywordUtil.markPassed("✅ New Application button is DISABLED for Collaborator")
		} else {
			println("✅ New Application button is ENABLED for Collaborator")
			KeywordUtil.markFailed("✅ New Application button is ENABLED for Viewer")
		}
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_OnBoarding'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/btn_OnBoarding'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/input_SearchGuardrail'), 10)
		WebUI.sendKeys(findTestObject('Object Repository/Guardrails/input_SearchGuardrail'), "Automation_Testing")
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Authorization/Card_AppName'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Authorization/header_DraftVersion'), 10)
		TestObject newVersionBtn = findTestObject('Object Repository/Applications/btn_NewVersion')
		boolean isPresent = WebUI.verifyElementPresent(newVersionBtn, 5, FailureHandling.OPTIONAL)
		if (isPresent) {
			println("✅ 'New Version' button is present for Collaborator")
			KeywordUtil.markPassed("✅ 'New Version' button is present for Collaborator")
		} else {
			println("❌ 'New Version' button is NOT present for Collaborator")
			KeywordUtil.markFailed("❌ 'New Version' button is NOT present for Collaborator")
		}
	}

	@Keyword
	public void VerifyAppAdminAuthorization() {
		TestObject settingsIcon = new TestObject("settingsIcon")
		settingsIcon.addProperty("xpath", ConditionType.EQUALS,
				"//nav[contains(@class,'menuGrp menuGrp3')]/descendant::a[last()-1]//*[local-name()='svg' and contains(@class,'chakra-icon')]")
		String svgClass = WebUI.getAttribute(settingsIcon, 'class')
		if (svgClass.contains("ltia1u")) {
			println("✅ Settings button is DISABLED for App Admin")
			KeywordUtil.markFailed("✅ Settings button is DISABLED for App Admin")
		} else if (svgClass.contains("1gghsms")) {
			println("✅ Settings button is ENABLED for App Admin")
			KeywordUtil.markPassed("✅ Settings button is ENABLED for App Admin")
		}
		WebUI.click(findTestObject('Object Repository/Applications/btn_Applications'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_AppSearch'), 10)
		TestObject newAppBtn = findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_NewApplication')
		String disabledAttr = WebUI.getAttribute(newAppBtn, 'disabled')
		if (disabledAttr == null) {
			println("✅ New Application button is ENABLED for App Admin")
			KeywordUtil.markPassed("✅ New Application button is ENABLED for App Admin")
		} else {
			println("❌ New Application button is DISABLED for App Admin")
			KeywordUtil.markFailed("❌ New Application button is DISABLED for App Admin")
		}
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_OnBoarding'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/btn_OnBoarding'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/input_SearchGuardrail'), 10)
		WebUI.sendKeys(findTestObject('Object Repository/Guardrails/input_SearchGuardrail'), "Automation_Testing")
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Authorization/Card_AppName'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Authorization/header_DraftVersion'), 10)
		TestObject newVersionBtn = findTestObject('Object Repository/Applications/btn_NewVersion')
		boolean isPresent = WebUI.verifyElementPresent(newVersionBtn, 5, FailureHandling.OPTIONAL)
		if (isPresent) {
			println("✅ 'New Version' button is present for App Admin")
			KeywordUtil.markPassed("✅ 'New Version' button is present for App Admin")
		} else {
			println("❌ 'New Version' button is NOT present for App Admin")
			KeywordUtil.markFailed("❌ 'New Version' button is NOT present for App Admin")
		}
	}
}
