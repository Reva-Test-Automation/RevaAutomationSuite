package onBoarding
import org.openqa.selenium.Keys
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.testobject.ConditionType
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.util.KeywordUtil
import CustomKeywords
import java.util.function.Consumer
import org.openqa.selenium.logging.LogType

public class CreateApplication {

	private static String PolicyTitle = null

	@Keyword
	public static String getRandomTitle() {
		if (PolicyTitle == null) {
			int randomNum = 100 + new Random().nextInt(900)
			PolicyTitle = GlobalVariable.PolicyDisplayName + randomNum
		}
		return PolicyTitle
	}

	@Keyword
	public void FillApplicationDetails(String appName, String category, String tags, String owners, String description) {
		try {
			WebUI.delay(3)
			WebUI.waitForPageLoad(10000)
			TestObject onboardingBtn = findTestObject('Object Repository/Applications/btn_OnBoarding')
			WebUI.verifyElementClickable(onboardingBtn)
			WebUI.click(onboardingBtn)
			KeywordUtil.logInfo("Clicked Onboarding button.")
			WebUI.click(findTestObject('Object Repository/Applications/btn_GridView'))
			TestObject startNewAppBtn = findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_NewApplication')
			TestObject newAppBtn = findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_NewApplication')
			if (WebUI.verifyElementPresent(startNewAppBtn, 5, FailureHandling.OPTIONAL)) {
				WebUI.click(startNewAppBtn)
				WebUI.waitForElementVisible(findTestObject('Object Repository/Applications/list_Applications'), 20)
				WebUI.verifyElementClickable(findTestObject('Object Repository/Applications/btn_Application'))
				WebUI.click(findTestObject('Object Repository/Applications/btn_Application'))
				KeywordUtil.logInfo("Clicked 'Start New Application' button.")
			} else if (WebUI.verifyElementPresent(newAppBtn, 5, FailureHandling.OPTIONAL)) {
				WebUI.click(newAppBtn)
				WebUI.verifyElementClickable(findTestObject('Object Repository/Applications/btn_Application'))
				WebUI.click(findTestObject('Object Repository/Applications/btn_Application'))
				KeywordUtil.logInfo("Clicked 'New Application' button.")
			} else {
				KeywordUtil.markFailed("Neither 'Start New Application' nor 'New Application' button was found.")
				return
			}
			TestObject nameField = findTestObject('Object Repository/Onboarding/Page_Reva.ai/application_Name')
			WebUI.waitForElementVisible(nameField, 20)
			WebUI.setText(nameField, appName)
			KeywordUtil.logInfo("✅ Application Name set: " + appName)
			TestObject descField = findTestObject('Object Repository/Onboarding/Page_Reva.ai/application_Description')
			WebUI.setText(descField, description)
			KeywordUtil.logInfo("✅ Description set.")
			selectDropdown(findTestObject('Object Repository/Onboarding/Page_Reva.ai/select_Category'), category)
			KeywordUtil.logInfo("✅ Category selected: " + category)
			selectDropdown(findTestObject('Object Repository/Onboarding/Page_Reva.ai/select_Application_Ower(s)'), owners)
			KeywordUtil.logInfo("✅ Owners selected: " + owners)
			selectDropdown(findTestObject('Object Repository/Onboarding/Page_Reva.ai/select_ApplicationTags'), tags)
			KeywordUtil.logInfo("✅ Tags selected: " + tags)
			TestObject continueBtn = findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_Create_Env')
			WebUI.verifyElementClickable(continueBtn)
			WebUI.click(continueBtn)
			KeywordUtil.logInfo("✅ Clicked Create button.")
			WebUI.waitForElementClickable(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_OnboardSchema'), 20)
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_OnboardSchema'))
		} catch (Exception e) {
			WebUI.comment("Error in fillApplicationDetails: " + e.getMessage())
		}
	}

	@Keyword
	public void AddEnvironmentsWithNewPolicy(String environmentName, String policyDescription, String connectionName, String policyName) {
		try {
			WebUI.waitForElementClickable(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Environments'), 20)
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Environments'))
			WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/create_Environment_Header'), 20)
			WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_EnvironmentName'), environmentName)
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_PolicyStore'))
			WebUI.waitForElementClickable(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_DisplayName'), 20)
			WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_DisplayName'), policyName)
			WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_PolicyDescription'), policyDescription)
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/select_PolicyConnection'))
			WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/select_PolicyConnection'), connectionName)
			String connectionObjectName = "//div[text()='" + connectionName + "']"
			TestObject connectionElement = new TestObject().addProperty("xpath", ConditionType.EQUALS, connectionObjectName)
			WebUI.waitForElementVisible(connectionElement, 20)
			WebUI.click(connectionElement)
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_CreatePolicySave'))
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_Create_Env'))
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Continue'))
			WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/popUpHeaderConfigure'), 20)
			WebUI.comment("Environment '${environmentName}' created successfully!")
		} catch (Exception e) {
			WebUI.comment("Error in fillApplicationDetails: " + e.getMessage())
		}
	}
	
	
	/*@Keyword
	public void captureNetworkLogs() {
		def driver = DriverFactory.getWebDriver()
		DevTools devTools = driver.getDevTools()
		devTools.createSession()
		
		// Requests
		devTools.addListener(
			Network.requestWillBeSent(),
			{ e ->
				KeywordUtil.logInfo(
					"REQ  ${e.getRequest().getMethod()} ${e.getRequest().getUrl()}"
				)
			} as Consumer
		)
		
		// Responses
		devTools.addListener(
			Network.responseReceived(),
			{ e ->
				KeywordUtil.logInfo(
					"RES  ${e.getResponse().getStatus()} ${e.getResponse().getUrl()}"
				)
			} as Consumer
		)
	}*/
			
		
		/*def driver = DriverFactory.getWebDriver()
		
		DevTools devTools = driver.getDevTools()
		devTools.createSession()
		
		devTools.addListener(
				Network.responseReceived(),
				{ event ->
					Response response = event.getResponse()
					println "URL    : ${response.getUrl()}"
					println "Status : ${response.getStatus()}"
				} as Consumer
		)
		
		devTools.addListener(
			Network.requestWillBeSent(),
			{ e ->
				println "REQ  ${e.getRequest().getMethod()} ${e.getRequest().getUrl()}"
			} as Consumer
		)
		
		devTools.addListener(
			Network.responseReceived(),
			{ e ->
				println "RES  ${e.getResponse().getStatus()} ${e.getResponse().getUrl()}"
			} as Consumer
		)*/
	

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
	def logInfo(String message) {
		WebUI.comment("[INFO] " + message)
	}

	@Keyword
	public void TempFunction() {
		WebUI.navigateToUrl("https://clienteks.ea.reva.ai/applicationlist")
		WebUI.waitForPageLoad(10000)
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_AppSearch'), "TestAutoAppName25")
		WebUI.waitForElementClickable(findTestObject('Object Repository/Onboarding/Page_Reva.ai/linkTestAppName'), 20)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/linkTestAppName'))
		/*WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_PolicySchema'))
		 WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/popUp_DefineSchema'), 20)
		 WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_SchemaNext'))*/
	}

	@Keyword
	public void DesignSchema() {
		/*String policyConfigBtn = "//a[text()='" + policyName + "']/parent::td/following-sibling::td[last()]/descendant::button[@aria-label='Configure Schema']"
		 TestObject dynamicButton = new TestObject().addProperty("xpath", ConditionType.EQUALS, policyConfigBtn)
		 WebUI.click(dynamicButton)*/
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_DesignSchema'), 20)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_DesignSchema'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_AddPrincipal'), 20)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_AddPrincipal'))
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/container_Principal_1'), GlobalVariable.SchemaPrincipal_1)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_AddAction'))
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/container_Action_1'), GlobalVariable.SchemaAction_1)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_AddResource'))
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/container_Resource_1'), GlobalVariable.SchemaResource_1)
		WebUI.dragAndDropToObject(findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowRight_Principal_1'), findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowLeft_Action_1'))
		WebUI.dragAndDropToObject(findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowRight_Action_1'), findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowLeft_Resource_1'))
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_AddAction'))
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/container_Action_2'), GlobalVariable.SchemaAction_2)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_AddAction'))
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/container_Action_3'), GlobalVariable.SchemaAction_3)
		WebUI.dragAndDropToObject(findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowRight_Principal_1'), findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowLeft_Action_2'))
		WebUI.dragAndDropToObject(findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowRight_Principal_1'), findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowLeft_Action_3'))
		WebUI.dragAndDropToObject(findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowRight_Action_2'), findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowLeft_Resource_1'))
		WebUI.dragAndDropToObject(findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowRight_Action_3'), findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowLeft_Resource_1'))
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Continue'))
	}

	@Keyword
	public void DefineAttribute() {
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_AttributeName'), 20)
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_AttributeName'), GlobalVariable.SchemaAttName)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_SchemaAttType'))
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/select_AttType'))
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_AttValues'), GlobalVariable.SchemaAttValue)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Continue'))
	}

	@Keyword
	public void SetUpHierarchy() {
		WebUI.delay(3)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Continue'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/dropPanel'), 20)
		WebDriver driver = DriverFactory.getWebDriver()
		List<TestObject> sourceElements = [
			findTestObject('Object Repository/Onboarding/Page_Reva.ai/dragEntityType_User'),
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
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_Update'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/node_AISuggPolicies'), 20)
		//WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/checkBox_SelectAIPolicy'))
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_Add'))
		/*WebUI.waitForElementClickable(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_SchemaContinue'), 20)
		 WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_SchemaContinue'))
		 CheckForThePolicyStatus(policyTitle)
		 WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Continue'))*/
	}

	@Keyword
	public void CheckForThePolicyStatus(String policyTitle) {
		String policyStatusXpath = "//a[text()='" + policyTitle + "']/parent::td/following-sibling::td[last()-1]/p"
		TestObject policyStatusText = new TestObject().addProperty("xpath", ConditionType.EQUALS, policyStatusXpath)
		String dynamicXPath = "//a[text()='" + policyTitle + "']/parent::td/following-sibling::td[last()]/descendant::button"
		TestObject dynamicButton = new TestObject().addProperty("xpath", ConditionType.EQUALS, dynamicXPath)
		WebUI.waitForElementClickable(dynamicButton, 20)
		WebUI.click(dynamicButton)
		int refreshInterval = 2
		int attempts = 30 / refreshInterval
		for (int i = 0; i < attempts; i++) {
			if (WebUI.waitForElementVisible(policyStatusText, 5)) {
				String currentStatus = WebUI.getText(policyStatusText).trim()
				WebUI.comment("Attempt ${i + 1}: Current Status - ${currentStatus}")
				if (currentStatus.equalsIgnoreCase("Active")) {
					WebUI.comment("✅ Status changed to Active! Proceeding...")
					return
				}
			} else {
				WebUI.comment("⚠️ Status element not visible yet.")
			}
			WebUI.delay(refreshInterval)
			WebUI.click(dynamicButton)
		}
		WebUI.comment("❌ Status did not change to Active within the expected time.")
		throw new StepFailedException("Status did not change to Active within the expected time.")
	}

	@Keyword
	public void CheckForTheTestDataUploadStatus(String policyTitle) {
		String testDataStatus = "//a[text()='" + policyTitle  + "']/parent::td/following-sibling::td[last()-1]/p"
		TestObject testDataStatusText = new TestObject().addProperty("xpath", ConditionType.EQUALS, testDataStatus)
		String dynamicXPath = "//a[text()='" + policyTitle  + "']/parent::td/following-sibling::td[last()]/descendant::button"
		TestObject refreshButton = new TestObject().addProperty("xpath", ConditionType.EQUALS, dynamicXPath)
		WebUI.click(refreshButton)
		int refreshInterval = 2
		int attempts = 30 / refreshInterval
		for (int i = 0; i < attempts; i++) {
			String currentStatus = WebUI.getText(testDataStatusText).trim()
			WebUI.comment("Attempt ${i + 1}: Current Status - " + currentStatus)
			if (currentStatus.equalsIgnoreCase("Success")) {
				WebUI.comment("✅ Status changed to Success! ")
				break
			}
			WebUI.click(refreshButton)
			WebUI.delay(refreshInterval)
		}
		WebUI.delay(3)
	}

	@Keyword
	public void CheckForTheApplicationStatus() {
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_AppSearch'), 20)
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_AppSearch'), GlobalVariable.ApplicationName)
		WebUI.delay(3)
		String applicationStatus = "//a[text()='" + GlobalVariable.ApplicationName  + "']/parent::td/following-sibling::td[last()-2]/p"
		TestObject testDataStatusText = new TestObject().addProperty("xpath", ConditionType.EQUALS, applicationStatus)
		int refreshInterval = 2
		int attempts = 30 / refreshInterval
		for (int i = 0; i < attempts; i++) {
			String currentStatus = WebUI.getText(testDataStatusText).trim()
			WebUI.comment("Attempt ${i + 1}: Current Status - " + currentStatus)
			if (currentStatus.equalsIgnoreCase("Completed")) {
				WebUI.comment("✅ Application Status changed to Completed...!")
				break
			}
			WebUI.delay(refreshInterval)
		}
	}

	@Keyword
	public void AddEnvironmentsWithExistingPolicy(String environmentName) {
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_OnBoarding'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/btn_OnBoarding'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/input_SearchGuardrail'), 20)
		WebUI.sendKeys(findTestObject('Object Repository/Guardrails/input_SearchGuardrail'), GlobalVariable.ApplicationName)
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Guardrails/optn_GuardrailMenu'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_Settings'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/btn_Settings'))
		WebUI.waitForElementClickable(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Environments'), 20)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Environments'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/create_Environment_Header'), 20)
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_EnvironmentName'), environmentName)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_Create_Env'))
		/*WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_PolicySearch'), policySearchInput)
		 TestObject titleObject = findTestObject('Object Repository/Onboarding/Page_Reva.ai/title_PolicyStore')
		 TestObject checkboxObject = findTestObject('Object Repository/Onboarding/Page_Reva.ai/checkbox_ExistingPolicy')
		 String actualText = WebUI.getText(titleObject).trim()
		 if (actualText == GlobalVariable.PolicySearchInput) {
		 WebUI.click(checkboxObject)
		 KeywordUtil.markPassed("✅ Clicked on checkbox because text matched: '${actualText}'")
		 } else {
		 KeywordUtil.markWarning("⚠️ Text did not match. Found: '${actualText}', Expected: '${GlobalVariable.PolicySearchInput}'")
		 }		
		 WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Continue'))
		 WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/popUpHeaderConfigure'), 20)*/
	}

	@Keyword
	public void UploadHospitalSchemaJson(String jsonFile) {
		WebUI.waitForElementClickable(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_UploadJSON'), 20)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_UploadJSON'))
		TestObject fileInput = findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_FileUpload')
		String projectDir = RunConfiguration.getProjectDir()
		String filePath = projectDir + "/TestData/" + jsonFile + ".json"
		File file = new File(filePath)
		if (!file.exists()) {
			WebUI.comment("❌ JSON file not found: " + filePath)
			return
		}
		WebUI.uploadFile(fileInput, filePath)
		WebUI.comment("✅ JSON file uploaded successfully: " + filePath)
		WebUI.waitForElementClickable(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_Confirm'), 20)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_Confirm'))
		WebUI.waitForElementClickable(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Continue'), 20)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Continue'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/select_AttDropdown'), 20)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Continue'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/title_Attributes'), 20)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Continue'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_Update'), 20)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_Update'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/node_AISuggPolicies'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/btn_Skip'))
	}


	@Keyword
	public void UploadSchemaJson(String jsonCode, String policyTitle) {
		String policyStatusXpath = "//a[text()='" + policyTitle + "']/parent::td/following-sibling::td[last()-1]/p"
		TestObject policyStatusText = new TestObject().addProperty("xpath", ConditionType.EQUALS, policyStatusXpath)
		String status = WebUI.getText(policyStatusText).trim()
		if (status == "Active") {
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Continue'))
		} else {
			String policyEditXPath = "//a[text()='" + policyTitle + "']/parent::td/following-sibling::td[last()]/descendant::button[@aria-label='Configure Schema']"
			TestObject policyEditButton = new TestObject().addProperty("xpath", ConditionType.EQUALS, policyEditXPath)
			WebUI.click(policyEditButton)
			WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/popUp_DefineSchema'), 20)
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_UploadSchema'))
			WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/textArea_UploadSchema'), 20)
			WebUI.executeJavaScript("document.getElementById('policyStoreSchema').value = '';", null)
			WebUI.delay(1)
			TestObject inputField = findTestObject('Object Repository/Onboarding/Page_Reva.ai/textArea_UploadSchema')
			if (inputField != null) {
				WebUI.waitForElementVisible(inputField, 20)
				WebUI.executeJavaScript("""arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', { bubbles: true }));""", Arrays.asList(WebUI.findWebElement(inputField), jsonCode))
			} else {
				WebUI.comment("TestObject is NULL! Check the Object Repository path.")
			}
			WebUI.delay(1)
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Preview'))
			WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/popUp_DefineSchema'), 20)
			WebUI.dragAndDropToObject(findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowRight_ExistingStaff'), findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowLeft_ExistingPatient'))
			WebUI.dragAndDropToObject(findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowRight_ExistingPatient'), findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowLeft_ExistingMedication'))
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_SchemaNext'))
			WebUI.delay(0.5)
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_SchemaNext'))
			WebUI.delay(0.5)
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_SchemaNext'))
			WebUI.delay(0.5)
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_SaveAndActivate'))
			WebUI.waitForElementClickable(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_SchemaContinue'), 20)
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_SchemaContinue'))
			CheckForThePolicyStatus(policyTitle)
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Continue'))
		}
	}

	@Keyword
	public void UploadTestData(String fileName) {
		WebUI.waitForElementClickable(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_UploadTestData'), 20)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_UploadTestData'))
		if (WebUI.verifyElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_TestData'), 5, FailureHandling.OPTIONAL)) {
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_TestData'))
			KeywordUtil.logInfo("Clicked 'Upload Test data' button.")
		} else if (WebUI.verifyElementPresent(findTestObject('Object Repository/Applications/card_TestData'), 5, FailureHandling.OPTIONAL)) {
			WebUI.click(findTestObject('Object Repository/Applications/card_TestData'))
			KeywordUtil.logInfo("Clicked 'Upload Test data' card.")
		}
		WebUI.delay(2)
		String projectDir = RunConfiguration.getProjectDir()
		String filePath = projectDir + "/TestData/TestData_template.zip"
		def driver = DriverFactory.getWebDriver()
		WebElement fileInput = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_FileUpload'),10)		
		((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block'; arguments[0].style.visibility='visible';",	fileInput)		
		WebUI.delay(1)
		fileInput.sendKeys(filePath)
		/*String filePath = RunConfiguration.getProjectDir() +
        "/TestData/TestData_template.zip"
		assert new File(filePath).exists() : "File NOT found: " + filePath
		CustomKeywords.'com.katalon.testcloud.FileExecutor.uploadFileToWeb'(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_FileUpload'), filePath)*/
		WebUI.comment("✅ File uploaded successfully: " + filePath)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_Upload'))
		WebUI.delay(2)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_Confirm'))
		WebUI.delay(2)
	}

	@Keyword
	public void DeleteApplication() {
		//WebUI.back()
		WebUI.delay(0.5)
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/btn_OnBoarding'), 20)
		WebUI.click(findTestObject('Object Repository/Applications/btn_OnBoarding'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/input_SearchGuardrail'), 20)
		WebUI.sendKeys(findTestObject('Object Repository/Guardrails/input_SearchGuardrail'), GlobalVariable.ApplicationName)
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Guardrails/optn_GuardrailMenu'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Guardrails/btn_DeleteGuardrail'), 20)
		WebUI.click(findTestObject('Object Repository/Guardrails/btn_DeleteGuardrail'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Integrations/popUp_DeleteIntegration'), 20)
		WebUI.click(findTestObject('Object Repository/Integrations/btn_DeleteConfirm'))
		WebUI.delay(3)
	}
}
