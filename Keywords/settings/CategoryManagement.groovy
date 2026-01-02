package settings

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.testobject.ConditionType
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
import internal.GlobalVariable

public class CategoryManagement {

	@Keyword
	public void CreateCategory(String CategoryName) {
		WebUI.waitForElementClickable(findTestObject('Object Repository/Settings/btn_Settings'), 10)
		WebUI.click(findTestObject('Object Repository/Settings/btn_Settings'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Applications'), 10)
		WebUI.click(findTestObject('Object Repository/Settings/link_CategoryManagement'))
		WebUI.waitForElementClickable(findTestObject('Object Repository/Settings/btn_NewCategory'), 10)
		WebUI.click(findTestObject('Object Repository/Settings/btn_NewCategory'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Settings/input_CategoryName'), 10)
		WebUI.setText(findTestObject('Object Repository/Settings/input_CategoryName'), CategoryName)
		WebUI.setText(findTestObject('Object Repository/Settings/input_CategoryDesc'), "Category Description")
		WebUI.setText(findTestObject('Object Repository/Settings/input_CategoryOwner'), GlobalVariable.UserName)
		String OwnerObjectName = "//div[contains(text(),'"+GlobalVariable.CategoryOwner+"')]"
		TestObject OwnerElement = new TestObject().addProperty("xpath", ConditionType.EQUALS, OwnerObjectName)
		WebUI.waitForElementVisible(OwnerElement, 10)
		WebUI.click(OwnerElement)
		WebUI.click(findTestObject('Object Repository/Settings/btn_Create'))
	}


	@Keyword
	public void DeleteCategory(String CategoryName) {
		WebUI.waitForElementClickable(findTestObject('Object Repository/Settings/btn_Settings'), 10)
		WebUI.click(findTestObject('Object Repository/Settings/btn_Settings'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Applications'), 10)
		WebUI.click(findTestObject('Object Repository/Settings/link_CategoryManagement'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/input_SearchGuardrail'), 10)
		WebUI.sendKeys(findTestObject('Object Repository/Guardrails/input_SearchGuardrail'), GlobalVariable.CategoryName)
		WebUI.delay(0.5)
		WebUI.waitForElementClickable(findTestObject('Object Repository/Settings/btn_DeleteCategory'), 10)
		WebUI.click(findTestObject('Object Repository/Settings/btn_DeleteCategory'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Integrations/popUp_DeleteIntegration'), 10)
		WebUI.click(findTestObject('Object Repository/Integrations/btn_DeleteConfirm'))
		WebUI.delay(3)
	}
}
