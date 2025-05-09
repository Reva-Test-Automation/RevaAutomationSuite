package integrations
import org.openqa.selenium.Keys
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
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import com.kms.katalon.core.util.KeywordUtil

public class CreateConnections {

	@Keyword
	public void CreateAWSIntegration(String integrationType, String connectionName, String AWSAccountNo, String connectionDesc, String AWSRegion) {
		try {
			WebUI.waitForElementClickable(findTestObject('Object Repository/Integrations/btn_Integrations'), 10)
			WebUI.click(findTestObject('Object Repository/Integrations/btn_Integrations'))
			WebUI.waitForElementClickable(findTestObject('Object Repository/Integrations/btn_Integration'), 10)
			WebUI.click(findTestObject('Object Repository/Integrations/btn_Integration'))
			WebUI.waitForElementPresent(findTestObject('Object Repository/Integrations/list_Integrations'), 10)
			String IntegrationType = "//button[text()='" + integrationType + "']"
			TestObject selectIntegrationType = new TestObject().addProperty("xpath", ConditionType.EQUALS, IntegrationType)
			WebUI.click(selectIntegrationType)
			WebUI.waitForElementPresent(findTestObject('Object Repository/Integrations/popUp_Integrations'), 10)
			WebUI.setText(findTestObject('Object Repository/Integrations/input_IntegrationName'), connectionName)
			WebUI.setText(findTestObject('Object Repository/Integrations/input_AWSAccountNo'), AWSAccountNo)
			WebUI.setText(findTestObject('Object Repository/Integrations/input_integrationDesc'), connectionDesc)
			WebUI.selectOptionByLabel(findTestObject('Object Repository/Integrations/select_AWSRegion'), AWSRegion, false)
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_SchemaNext'))
			WebUI.waitForElementClickable(findTestObject('Object Repository/Integrations/btn_CFT'), 10)
			WebUI.click(findTestObject('Object Repository/Integrations/checkbox_CFTRunConfirm'))
			WebUI.click(findTestObject('Object Repository/Integrations/btn_ActivateConnection'))
		} catch (Exception e) {
			WebUI.comment("Error in fillApplicationDetails: " + e.getMessage())
		}
	}

	@Keyword
	def logInfo(String message) {
		WebUI.comment("[INFO] " + message)
	}

	@Keyword
	def selectDropdown(TestObject testObject, String value) {
		try {
			WebUI.setText(testObject, value)
			WebUI.sendKeys(testObject, Keys.chord(Keys.ENTER))
			logInfo("Selected value: " + value + " in " + testObject.getObjectId())
		} catch (Exception e) {
			WebUI.comment("Error in selectDropdown: " + e.getMessage())
		}
	}

	@Keyword
	public void DeleteIntegrations() {
		WebUI.verifyElementClickable(findTestObject('Object Repository/Integrations/btn_Integrations'), FailureHandling.STOP_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Integrations/btn_Integrations'))
		WebUI.verifyElementClickable(findTestObject('Object Repository/Integrations/btn_Integration'), FailureHandling.STOP_ON_FAILURE)
		List<WebElement> integrationsList = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Integrations/list_IntegrationsAll'), 10)
		boolean isIntegrationFound = false
		for (WebElement element : integrationsList) {
			String elementText = element.getText().trim()
			if (elementText.contains(GlobalVariable.IntegrationName)) {
				isIntegrationFound = true
				WebElement optionsButton = element.findElement(By.xpath(".//preceding-sibling::div//button[@aria-label='Options']"))
				optionsButton.click()
				break
			}
		}
		if (!isIntegrationFound) {
			KeywordUtil.markFailed("Integration with name '"+GlobalVariable.IntegrationName+"' was not found.")
			return
		}
		WebUI.verifyElementPresent(findTestObject('Object Repository/Integrations/menu_EditDelete'), 10, FailureHandling.STOP_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Integrations/btn_DeleteIntegration'))
		WebUI.verifyElementPresent(findTestObject('Object Repository/Integrations/popUp_DeleteIntegration'), 10, FailureHandling.STOP_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Integrations/btn_DeleteConfirm'))
		WebUI.delay(2)
		if (WebUI.verifyTextNotPresent(GlobalVariable.IntegrationName, false)) {
			KeywordUtil.markPassed("Integration deleted successfully.")
		} else {
			KeywordUtil.markWarning("Integration might still be present. Please double-check.")
		}
	}
}
