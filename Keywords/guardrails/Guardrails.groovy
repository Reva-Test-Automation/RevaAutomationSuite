package guardrails

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import org.openqa.selenium.WebElement
import internal.GlobalVariable
import org.openqa.selenium.Keys
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.JavascriptExecutor
import com.kms.katalon.core.util.KeywordUtil

public class Guardrails {

	@Keyword
	public void CreateGuardrail() {
		WebUI.click(findTestObject('Object Repository/Guardrails/btn_Guardrails'))
		//WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/dashBoard_Guardrail'), 10)
		//WebUI.click(findTestObject('Object Repository/Guardrails/tab_Guardrails'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/btn_CreateGuardrails'), 10)
		WebUI.click(findTestObject('Object Repository/Guardrails/btn_CreateGuardrails'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/listDesignTime'), 10)
		WebUI.click(findTestObject('Object Repository/Guardrails/listDesignTime'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/slide_Create Guardrails'), 10)
		WebUI.sendKeys(findTestObject('Object Repository/Guardrails/input_GuardrailsName'), GlobalVariable.GuardrailName)
		WebUI.sendKeys(findTestObject('Object Repository/Guardrails/input_GuardrailsDescription'), GlobalVariable.GuardrailDescription)
		WebUI.sendKeys(findTestObject('Object Repository/Guardrails/input_GuardrailsType'), GlobalVariable.GuardrailsType)
		WebUI.sendKeys(findTestObject('Object Repository/Guardrails/input_GuardrailsStatus'), GlobalVariable.GuardrailsStatus)
		WebUI.sendKeys(findTestObject('Object Repository/Guardrails/input_CategoriseAs'), GlobalVariable.GuardrailsCategoriseAs)
		WebUI.setText(findTestObject('Object Repository/Guardrails/input_GuardrailsOwner'), GlobalVariable.UserName)
		String OwnerObjectName = "//div[text()='" + GlobalVariable.UserName + "']"
		TestObject OwnerElement = new TestObject().addProperty("xpath", ConditionType.EQUALS, OwnerObjectName)
		WebUI.waitForElementVisible(OwnerElement, 10)
		WebUI.click(OwnerElement)
		WebUI.click(findTestObject('Object Repository/Guardrails/btn_RemoveCategory'))
		WebUI.setText(findTestObject('Object Repository/Guardrails/input_Category'), GlobalVariable.CategoryName)
		String CategoryObjectName = "//div[text()='" + GlobalVariable.CategoryName + "']"
		TestObject CategoryElement = new TestObject().addProperty("xpath", ConditionType.EQUALS, CategoryObjectName)
		WebUI.waitForElementVisible(CategoryElement, 10)
		WebUI.click(CategoryElement)
		WebUI.click(findTestObject('Object Repository/Guardrails/btn_GuardrailNext'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/input_GuardrailCodeLines'), 10)
		String policyCode = '''\
		some i
		policy := input.entries[i]
		"ALL" in policy.actions'''.stripIndent()
		String js = """
		  var editorDiv = document.querySelector('.CodeMirror');
		  if (editorDiv && editorDiv.CodeMirror) {
			editorDiv.CodeMirror.setValue(arguments[0]);
		  }
		"""
		JavascriptExecutor jsExecutor = (JavascriptExecutor) DriverFactory.getWebDriver()
		jsExecutor.executeScript(js, policyCode)
		WebUI.click(findTestObject('Object Repository/Guardrails/btn_GuardrailNext'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/input_GuardrailErrorMsg'), 10)
		WebUI.sendKeys(findTestObject('Object Repository/Guardrails/input_GuardrailErrorMsg'), GlobalVariable.GuardrailErrorMessage)
		WebUI.click(findTestObject('Object Repository/Guardrails/btn_GuardrailCreate'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/input_SearchGuardrail'), 10)
		WebUI.sendKeys(findTestObject('Object Repository/Guardrails/input_SearchGuardrail'), GlobalVariable.GuardrailName)
		WebUI.delay(0.5)
		//WebUI.click(findTestObject('Object Repository/Library/btn_MenuSchema'))
		String xpath = "//div[contains(@class,'fixedNavbar')]/following-sibling::div/descendant::p[text()='" + GlobalVariable.GuardrailName + "']"
		TestObject guardrailObj = new TestObject("dynamicGuardrail")
		guardrailObj.addProperty("xpath", ConditionType.EQUALS, xpath)
		if (WebUI.verifyElementVisible(guardrailObj, FailureHandling.OPTIONAL)) {
			KeywordUtil.markPassed("✅ Element with Guardrail name '${GlobalVariable.GuardrailName}' is visible.")
		} else {
			KeywordUtil.markFailed("❌ Element with Guardrail name '${GlobalVariable.GuardrailName}' is NOT found.")
		}
	}

	@Keyword
	public void ValidateCategoryInGuardrailBeforeAppAssigning() {
		WebUI.click(findTestObject('Object Repository/Guardrails/btn_Guardrails'))
		//WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/dashBoard_Guardrail'), 10)
		//WebUI.click(findTestObject('Object Repository/Guardrails/tab_Guardrails'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/btn_CreateGuardrails'), 10)
		WebUI.click(findTestObject('Object Repository/Guardrails/btn_CreateGuardrails'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/listDesignTime'), 10)
		WebUI.click(findTestObject('Object Repository/Guardrails/listDesignTime'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/slide_Create Guardrails'), 10)
		WebUI.click(findTestObject('Object Repository/Guardrails/space_Category'))
		TestObject categoryList = findTestObject('Object Repository/Guardrails/list_Category')
		List<WebElement> items = WebUI.findWebElements(categoryList, 10)
		boolean found = false
		String targetCategory = GlobalVariable.CategoryName.toString().trim()
		for (WebElement item : items) {
			String itemText = item.getText().trim()
			if (itemText.equals(targetCategory)) {
				KeywordUtil.logInfo("✅ Category found in the list: " + itemText)
				found = true
				break
			}
		}
		if (found) {
			KeywordUtil.markFailed("✅ Expected category '${GlobalVariable.CategoryName}' is present in the list before assigning the application.")
		} else {
			KeywordUtil.markPassed("✅ Expected category '${GlobalVariable.CategoryName}' is NOT present in the list before assigning the application.")
		}
		WebUI.click(findTestObject('Object Repository/Applications/btn_CloseTest'))
	}

	@Keyword
	public void ValidateCategoryInGuardrailAfterAppAssigning() {
		WebUI.back()
		WebUI.delay(0.5)
		WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/btn_Guardrails'), 10)
		WebUI.click(findTestObject('Object Repository/Guardrails/btn_Guardrails'))
		//WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/dashBoard_Guardrail'), 10)
		//WebUI.click(findTestObject('Object Repository/Guardrails/tab_Guardrails'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/btn_CreateGuardrails'), 10)
		WebUI.click(findTestObject('Object Repository/Guardrails/btn_CreateGuardrails'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/listDesignTime'), 10)
		WebUI.click(findTestObject('Object Repository/Guardrails/listDesignTime'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/slide_Create Guardrails'), 10)
		WebUI.click(findTestObject('Object Repository/Guardrails/space_Category'))
		TestObject categoryList = findTestObject('Object Repository/Guardrails/list_Category')
		List<WebElement> items = WebUI.findWebElements(categoryList, 10)
		boolean found = false
		String targetCategory = GlobalVariable.CategoryName.toString().trim()
		for (WebElement item : items) {
			String itemText = item.getText().trim()
			if (itemText.equals(targetCategory)) {
				KeywordUtil.logInfo("✅ Category found in the list: " + itemText)
				found = true
				break
			}
		}
		if (found) {
			KeywordUtil.markPassed("✅ Expected category '${GlobalVariable.CategoryName}' is present in the list.")
		} else {
			KeywordUtil.markFailed("❌ Expected category '${GlobalVariable.CategoryName}' is NOT present in the list.")
		}
		WebUI.click(findTestObject('Object Repository/Applications/btn_CloseTest'))
	}

	@Keyword
	public void ValidateGuardrail() {
		TestObject titleObject = findTestObject('Object Repository/Guardrails/title_Violation')
		List<WebElement> elements = WebUiCommonHelper.findWebElements(titleObject, 10)
		boolean matchFound = false
		for (WebElement el : elements) {
			String actualText = el.getText().trim()
			if (actualText == GlobalVariable.GuardrailName) {
				KeywordUtil.markPassed("✅ Match found: '${actualText}'")
				matchFound = true
				break
			}
		}
		if (!matchFound) {
			KeywordUtil.markFailed("❌ No matching title found for '${GlobalVariable.GuardrailName}'")
		}
	}

	@Keyword
	public void DeleteGuardrail() {
		WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/input_SearchGuardrail'), 10)
		WebUI.sendKeys(findTestObject('Object Repository/Guardrails/input_SearchGuardrail'), GlobalVariable.GuardrailName)
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Guardrails/optn_GuardrailMenu'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/menu_Options'), 10)
		WebUI.click(findTestObject('Object Repository/Guardrails/btn_DeleteGuardrail'))
	}
}
