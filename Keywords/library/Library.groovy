package library
import org.openqa.selenium.WebElement
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.annotation.Keyword
import org.openqa.selenium.WebDriver
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testcase.TestCase
import org.openqa.selenium.interactions.Actions
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class Library {

	@Keyword
	public void CreateEntity() {
		WebUI.click(findTestObject('Object Repository/Library/btn_Library'))
		WebUI.waitForElementClickable(findTestObject('Object Repository/Library/btn_CreateLib'), 10)
		WebUI.click(findTestObject('Object Repository/Library/btn_CreateLib'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Library/menuList_CreateLib'), 10)
		WebUI.click(findTestObject('Object Repository/Library/list_Entity'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Library/slide_DefineSchema'), 10)
		WebUI.sendKeys(findTestObject('Object Repository/Library/input_SchemaName'), GlobalVariable.EntityName)
		WebUI.sendKeys(findTestObject('Object Repository/Library/input_SchemaDesc'), GlobalVariable.EntityDesc)
		WebUI.click(findTestObject('Object Repository/Guardrails/btn_GuardrailNext'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Library/input_EntityAttribute'), 10)
		WebUI.setText(findTestObject('Object Repository/Library/input_EntityAttribute'), GlobalVariable.EntityAttName)
		WebUI.click(findTestObject('Object Repository/Library/select_EntityAttType'))
		WebUI.click(findTestObject('Object Repository/Library/select_EntityLong'))
		WebUI.setText(findTestObject('Object Repository/Library/input_EntityValue'), GlobalVariable.EntityAttValue)
		WebUI.click(findTestObject('Object Repository/Library/btn_CreatePolicy'))	
		WebUI.waitForElementPresent(findTestObject('Object Repository/Library/input_SchemaSearch'), 10)
		WebUI.sendKeys(findTestObject('Object Repository/Library/input_SchemaSearch'), GlobalVariable.EntityName)
		WebUI.delay(0.5)
		String xpath = "//input[@placeholder='Search']/ancestor::div[@role='tabpanel']/descendant::p[text()='" + GlobalVariable.EntityName + "']"
		TestObject schemaObj = new TestObject("dynamicGuardrail")
		schemaObj.addProperty("xpath", ConditionType.EQUALS, xpath)
		if (WebUI.verifyElementVisible(schemaObj, FailureHandling.OPTIONAL)) {
			KeywordUtil.markPassed("✅ Element with Entity name '${GlobalVariable.EntityName}' is visible.")
		} else {
			KeywordUtil.markFailed("❌ Element with Entity name '${GlobalVariable.EntityName}' is not found.")
		}
	}
	
	@Keyword
	public void CreateSchema() {
		WebUI.click(findTestObject('Object Repository/Library/btn_Library'))
		WebUI.waitForElementClickable(findTestObject('Object Repository/Library/btn_CreateLib'), 10)
		WebUI.click(findTestObject('Object Repository/Library/btn_CreateLib'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Library/menuList_CreateLib'), 10)
		WebUI.click(findTestObject('Object Repository/Library/list_Schema'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Library/slide_DefineSchema'), 10)
		WebUI.sendKeys(findTestObject('Object Repository/Library/input_SchemaName'), GlobalVariable.SchemaName)
		WebUI.sendKeys(findTestObject('Object Repository/Library/input_SchemaDesc'), GlobalVariable.SchemaDesc)
		WebUI.click(findTestObject('Object Repository/Guardrails/btn_GuardrailNext'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_AddPrincipal'), 10)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_AddPrincipal'))
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/container_Principal_1'), GlobalVariable.SchemaPrincipal_1)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_AddAction'))
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/container_Action_1'), GlobalVariable.SchemaAction_1)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_AddResource'))
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/container_Resource_1'), GlobalVariable.SchemaResource_1)
		WebUI.dragAndDropToObject(findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowRight_Principal_1'), findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowLeft_Action_1'))
		WebUI.dragAndDropToObject(findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowRight_Action_1'), findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowLeft_Resource_1'))
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_AddAction'))
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/container_Action_2'), GlobalVariable.SchemaAction_3)		
		WebUI.dragAndDropToObject(findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowRight_Principal_1'), findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowLeft_Action_2'))		
		WebUI.dragAndDropToObject(findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowRight_Action_2'), findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowLeft_Resource_1'))		
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_SchemaNext'))		
		
		/*WebUI.waitForElementClickable(findTestObject('Object Repository/Library/btn_AddEntityLib'), 10)
		WebUI.click(findTestObject('Object Repository/Library/btn_AddEntityLib'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Library/slide_EntityLib'), 10)	
		TestObject titleEntityObject = findTestObject('Object Repository/Library/list_EntityLib')
		List<WebElement> elements = WebUiCommonHelper.findWebElements(titleEntityObject, 10)
		boolean matchFound = false		
		for (WebElement el : elements) {
			String actualText = el.getText().trim()
			if (actualText == GlobalVariable.EntityName) {
				KeywordUtil.markPassed("✅ Match found: '${actualText}'")
				el.click()
				KeywordUtil.logInfo("🖱️ Clicked on element with text: '${actualText}'")		
				matchFound = true
				break
			}
		}		
		if (!matchFound) {
			KeywordUtil.markFailed("❌ No matching title found for '${GlobalVariable.EntityName}'")
		}	*/	
	}

	@Keyword
	public void SetUpHierarchy() {
		WebUI.delay(3)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_SchemaNext'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/dropPanel'), 10)
		WebDriver driver = DriverFactory.getWebDriver()
		List<TestObject> sourceElements = [
			findTestObject('Object Repository/Library/dragEntityType_User'),
			findTestObject('Object Repository/Onboarding/Page_Reva.ai/dragEntityType_Application'),
			findTestObject('Object Repository/Onboarding/Page_Reva.ai/dragActions_CreateList'),
			findTestObject('Object Repository/Onboarding/Page_Reva.ai/dragActions_DeleteList')
		]
		WebElement target = WebUI.findWebElement(findTestObject('Object Repository/Onboarding/Page_Reva.ai/dropPanel'))
		int targetX = target.getLocation().getX()
		int targetY = target.getLocation().getY()
		int xOffset = -100
		int yOffset = -170
		int gapBetweenItems = 100
		Actions action = new Actions(driver)
		for (int i = 0; i < sourceElements.size(); i++) {
			WebElement source = WebUI.findWebElement(sourceElements[i])
			action.clickAndHold(source).moveToElement(target, xOffset, yOffset + (i * gapBetweenItems)).release().perform()
		}
		WebUI.dragAndDropToObject(findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowDown_User'), findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowUp_Application'))
		WebUI.dragAndDropToObject(findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowDown_CreateList'), findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowUp_DeleteList'))
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_SaveAndActivate'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Library/input_SchemaSearch'), 10)
		WebUI.sendKeys(findTestObject('Object Repository/Library/input_SchemaSearch'), GlobalVariable.SchemaName)
		WebUI.delay(0.5)
		String xpath = "//input[@placeholder='Search']/ancestor::div[@role='tabpanel']/descendant::p[text()='" + GlobalVariable.SchemaName + "']"
		TestObject schemaObj = new TestObject("dynamicGuardrail")
		schemaObj.addProperty("xpath", ConditionType.EQUALS, xpath)
		if (WebUI.verifyElementVisible(schemaObj, FailureHandling.OPTIONAL)) {
			KeywordUtil.markPassed("✅ Element with Schema name '${GlobalVariable.SchemaName}' is visible.")
		} else {
			KeywordUtil.markFailed("❌ Element with Schema name '${GlobalVariable.SchemaName}' is not found.")
		}
	}

	@Keyword
	public void CreatePolicy() {
		WebUI.refresh()
		WebUI.click(findTestObject('Object Repository/Library/btn_CreateLib'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Library/menuList_CreateLib'), 10)
		WebUI.click(findTestObject('Object Repository/Library/list_Policy'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Library/slide_DefineSchema'), 10)
		WebUI.sendKeys(findTestObject('Object Repository/Library/input_SchemaName'), GlobalVariable.LibPolicyName)
		WebUI.sendKeys(findTestObject('Object Repository/Library/input_SchemaDesc'), GlobalVariable.LibPolicyDesc)
		WebUI.click(findTestObject('Object Repository/Library/select_Schema'))
		String connectionObjectName = "//div[text()='" + GlobalVariable.SchemaName + "']"
		TestObject connectionElement = new TestObject().addProperty("xpath", ConditionType.EQUALS, connectionObjectName)
		WebUI.waitForElementVisible(connectionElement, 10)
		WebUI.click(connectionElement)
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Guardrails/btn_GuardrailNext'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_AddPolicy'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddPolicy'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/link_PermitPolicy'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/link_PermitPolicy'))
		WebUI.click(findTestObject('Object Repository/Applications/input_Principal_1'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_PrincipalList'), 10)
		TestObject PrincipaldynamicObject = new TestObject()
		PrincipaldynamicObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'suggestions-list')]/descendant::li[text()='" + GlobalVariable.PrincipalOneType + "']")
		WebUI.waitForElementPresent(PrincipaldynamicObject, 10)
		WebUI.click(PrincipaldynamicObject)
		WebUI.setText(findTestObject('Object Repository/Applications/input_Principal_1'), GlobalVariable.PrincipalPDOne)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddActionNode'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/input_Action_1'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/input_Action_1'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_ActionList'), 10)
		TestObject ActiondynamicObject = new TestObject()
		ActiondynamicObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'suggestions-list actions-list')]/descendant::li[text()='" + GlobalVariable.SchemaAction_1 + "']")
		WebUI.waitForElementPresent(ActiondynamicObject, 10)
		WebUI.click(ActiondynamicObject)
		ActiondynamicObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'suggestions-list actions-list')]/descendant::li[text()='" + GlobalVariable.SchemaAction_3 + "']")
		WebUI.waitForElementPresent(ActiondynamicObject, 10)
		WebUI.click(ActiondynamicObject)
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddResource'))		
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/container_Resource'), 10)
		WebUI.click(findTestObject('Object Repository/Applications/input_Resource_1'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/frame_PrincipalList'), 10)
		TestObject ResourcedynamicObject = new TestObject()
		ResourcedynamicObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'suggestions-list')]/descendant::li[text()='" + GlobalVariable.ResourceTypeOne + "']")
		WebUI.waitForElementPresent(ResourcedynamicObject, 10)
		WebUI.click(ResourcedynamicObject)
		WebUI.delay(0.5)
		WebUI.setText(findTestObject('Object Repository/Applications/input_Principal_1'), GlobalVariable.ResourceInput)
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Applications/btn_AddCondition'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/slide_ConditionBuilder'), 10)
		WebUI.sendKeys(findTestObject('Object Repository/Applications/textArea_ConditionGroupOne'), GlobalVariable.ConditionGroupOneInput)
		WebUI.click(findTestObject('Object Repository/Applications/btn_ConditionBuilderSave'))		
		String actualMultiValue = WebUI.getAttribute(findTestObject('Object Repository/Library/text_Actions'), 'value')
		if (actualMultiValue == GlobalVariable.ActionMultiText) {
			WebUI.comment("Actions accepted multi values - Test Passed.")
		} else {
			KeywordUtil.markFailed("Actions not accepting multi values '${actualMultiValue}'")
		}
		WebUI.waitForElementClickable(findTestObject('Object Repository/Library/btn_CreatePolicy'), 10)
		WebUI.click(findTestObject('Object Repository/Library/btn_CreatePolicy'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Library/input_SchemaSearch'), 10)
		WebUI.sendKeys(findTestObject('Object Repository/Library/input_SchemaSearch'), GlobalVariable.LibPolicyName)
		WebUI.delay(0.5)
		String xpath = "//input[@placeholder='Search']/ancestor::div[@role='tabpanel']/descendant::p[text()='" + GlobalVariable.LibPolicyName + "']"
		TestObject schemaObj = new TestObject("dynamicGuardrail")
		schemaObj.addProperty("xpath", ConditionType.EQUALS, xpath)
		if (WebUI.verifyElementVisible(schemaObj, FailureHandling.OPTIONAL)) {
			KeywordUtil.markPassed("✅ Element with Policy name '${GlobalVariable.LibPolicyName}' is visible.")
		} else {
			KeywordUtil.markFailed("❌ Element with Policy name '${GlobalVariable.LibPolicyName}' is not found.")
		}
	}
	
	@Keyword
	public void importPolicy() {
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
		WebUI.click(findTestObject('Object Repository/Library/btn_SelectFromLib'))		
		WebUI.delay(1)		
		TestObject checkboxLabels = findTestObject('Object Repository/Library/list_PolicyCheckBoxs')
		List<WebElement> allCheckboxLabels = WebUiCommonHelper.findWebElements(checkboxLabels, 10)		
		for (WebElement label : allCheckboxLabels) {			
			if (label.getAttribute("data-checked") != null) {
				label.click() 
				KeywordUtil.logInfo("☑️ Checkbox was checked — now unchecked.")
			} else {
				KeywordUtil.logInfo("🔲 Checkbox already unchecked.")
			}
		}		
		TestObject titlePolicyObject = findTestObject('Object Repository/Library/list_PolicyLib')
		List<WebElement> elements = WebUiCommonHelper.findWebElements(titlePolicyObject, 10)
		boolean matchFound = false
		for (WebElement el : elements) {
			String actualText = el.getText().trim()
			if (actualText == GlobalVariable.LibPolicyName) {
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
		WebUI.click(findTestObject('Object Repository/Library/btn_AddLib'))				
	}
	
	@Keyword
	public void DeleteSchemaAndPolicy() {
		WebUI.back()
		WebUI.delay(1)
		WebUI.click(findTestObject('Object Repository/Library/btn_Library'))		
		WebUI.waitForElementPresent(findTestObject('Object Repository/Library/input_SchemaSearch'), 10)
		WebUI.sendKeys(findTestObject('Object Repository/Library/input_SchemaSearch'), GlobalVariable.SchemaName)
		WebUI.delay(0.5)
		String xpath = "//input[@placeholder='Search']/ancestor::div[@role='tabpanel']/descendant::p[text()='" + GlobalVariable.SchemaName + "']"
		TestObject schemaObj = new TestObject("dynamicSchema")
		schemaObj.addProperty("xpath", ConditionType.EQUALS, xpath)		
		if (WebUI.verifyElementVisible(schemaObj, FailureHandling.OPTIONAL)) {	
			WebUI.click(findTestObject('Object Repository/Library/btn_MenuSchema'))
			WebUI.waitForElementPresent(findTestObject('Object Repository/Library/menu_SchemaList'), 10)
			WebUI.click(findTestObject('Object Repository/Library/btn_DeleteSchema'))			
		} else {
			KeywordUtil.markFailed("❌ Element with Schema name '${GlobalVariable.SchemaName}' is not found.")
		}			
		TestObject inputField = findTestObject('Object Repository/Library/input_SchemaSearch')
		WebUI.clearText(inputField)			
		WebUI.sendKeys(inputField, GlobalVariable.LibPolicyName)
		WebUI.delay(0.5)
		String Policyxpath = "//input[@placeholder='Search']/ancestor::div[@role='tabpanel']/descendant::p[text()='" + GlobalVariable.LibPolicyName + "']"
		TestObject policyObj = new TestObject("dynamicSchema")
		policyObj.addProperty("xpath", ConditionType.EQUALS, Policyxpath)		
		if (WebUI.verifyElementVisible(policyObj, FailureHandling.OPTIONAL)) {
			WebUI.click(findTestObject('Object Repository/Library/btn_MenuSchema'))
			WebUI.waitForElementPresent(findTestObject('Object Repository/Library/menu_SchemaList'), 10)
			WebUI.click(findTestObject('Object Repository/Library/btn_DeleteSchema'))
		} else {
			KeywordUtil.markFailed("❌ Element with Schema name '${GlobalVariable.LibPolicyName}' is not found.")
		}
	}
}
