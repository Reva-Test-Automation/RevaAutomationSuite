package onBoarding
import org.openqa.selenium.Keys
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.ConditionType
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
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
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.util.KeywordUtil

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
			TestObject onboardingBtn = findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_Onboarding')
			WebUI.verifyElementClickable(onboardingBtn)
			WebUI.click(onboardingBtn)
			KeywordUtil.logInfo("Clicked Onboarding button.")
			TestObject startNewAppBtn = findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_NewApplication')
			TestObject newAppBtn = findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_NewApplication')
			if (WebUI.verifyElementPresent(startNewAppBtn, 5, FailureHandling.OPTIONAL)) {
				WebUI.click(startNewAppBtn)
				KeywordUtil.logInfo("Clicked 'Start New Application' button.")
			} else if (WebUI.verifyElementPresent(newAppBtn, 5, FailureHandling.OPTIONAL)) {
				WebUI.click(newAppBtn)
				KeywordUtil.logInfo("Clicked 'New Application' button.")
			} else {
				KeywordUtil.markFailed("Neither 'Start New Application' nor 'New Application' button was found.")
				return
			}
			TestObject nameField = findTestObject('Object Repository/Onboarding/Page_Reva.ai/application_Name')
			WebUI.waitForElementVisible(nameField, 10)
			WebUI.setText(nameField, appName)
			KeywordUtil.logInfo("✅ Application Name set: " + appName)
			selectDropdown(findTestObject('Object Repository/Onboarding/Page_Reva.ai/select_Category'), category)
			KeywordUtil.logInfo("✅ Category selected: " + category)
			selectDropdown(findTestObject('Object Repository/Onboarding/Page_Reva.ai/select_ApplicationTags'), tags)
			KeywordUtil.logInfo("✅ Tags selected: " + tags)
			selectDropdown(findTestObject('Object Repository/Onboarding/Page_Reva.ai/select_Application_Ower(s)'), owners)
			KeywordUtil.logInfo("✅ Owners selected: " + owners)
			TestObject descField = findTestObject('Object Repository/Onboarding/Page_Reva.ai/application_Description')
			WebUI.setText(descField, description)
			KeywordUtil.logInfo("✅ Description set.")
			TestObject continueBtn = findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Continue')
			WebUI.verifyElementClickable(continueBtn)
			WebUI.click(continueBtn)
			KeywordUtil.logInfo("✅ Clicked Continue button.")
		} catch (Exception e) {
			WebUI.comment("Error in fillApplicationDetails: " + e.getMessage())
		}
	}

	@Keyword
	public void AddEnvironmentsWithNewPolicy(String environmentName, String policyDescription, String connectionName, String policyName) {
		try {
			WebUI.waitForElementClickable(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Environments'), 10)
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Environments'))
			WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/create_Environment_Header'), 10)
			WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_EnvironmentName'), environmentName)
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_PolicyStore'))
			WebUI.waitForElementClickable(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_DisplayName'), 10)
			WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_DisplayName'), policyName)
			WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_PolicyDescription'), policyDescription)
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/select_PolicyConnection'))
			WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/select_PolicyConnection'), connectionName)
			String connectionObjectName = "//div[text()='" + connectionName + "']"
			TestObject connectionElement = new TestObject().addProperty("xpath", ConditionType.EQUALS, connectionObjectName)
			WebUI.waitForElementVisible(connectionElement, 10)
			WebUI.click(connectionElement)
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_CreatePolicySave'))
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_Create_Env'))
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Continue'))
			WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/popUpHeaderConfigure'), 10)
			WebUI.comment("Environment '${environmentName}' created successfully!")
		} catch (Exception e) {
			WebUI.comment("Error in fillApplicationDetails: " + e.getMessage())
		}
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
	def logInfo(String message) {
		WebUI.comment("[INFO] " + message)
	}

	@Keyword
	public void TempFunction() {
		WebUI.navigateToUrl("https://clienteks.ea.reva.ai/applicationlist")
		WebUI.waitForPageLoad(10000)
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_AppSearch'), "TestAutoAppName25")
		WebUI.waitForElementClickable(findTestObject('Object Repository/Onboarding/Page_Reva.ai/linkTestAppName'), 10)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/linkTestAppName'))
		/*WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_PolicySchema'))
		 WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/popUp_DefineSchema'), 10)
		 WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_SchemaNext'))*/
	}

	@Keyword
	public void DesignSchema(String policyName) {
		String policyConfigBtn = "//a[text()='" + policyName + "']/parent::td/following-sibling::td[last()]/descendant::button[@aria-label='Configure Schema']"
		TestObject dynamicButton = new TestObject().addProperty("xpath", ConditionType.EQUALS, policyConfigBtn)
		WebUI.click(dynamicButton)
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/popUp_DefineSchema'), 10)
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
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_SchemaNext'))
	}

	@Keyword
	public void DefineAttribute() {
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_AttributeName'), 10)
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_AttributeName'), GlobalVariable.SchemaAttName)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_SchemaAttType'))
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/select_AttType'))
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_AttValues'), GlobalVariable.SchemaAttValue)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_SchemaNext'))
	}

	@Keyword
	public void SetUpHierarchy(String policyTitle) {
		WebUI.delay(3)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_SchemaNext'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/dropPanel'), 10)
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
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_SaveAndActivate'))
		WebUI.waitForElementClickable(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_SchemaContinue'), 10)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_SchemaContinue'))
		CheckForThePolicyStatus(policyTitle)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Continue'))
	}

	@Keyword
	public void CheckForThePolicyStatus(String policyTitle) {
		String policyStatusXpath = "//a[text()='" + policyTitle + "']/parent::td/following-sibling::td[last()-1]/p"
		TestObject policyStatusText = new TestObject().addProperty("xpath", ConditionType.EQUALS, policyStatusXpath)
		String dynamicXPath = "//a[text()='" + policyTitle + "']/parent::td/following-sibling::td[last()]/descendant::button"
		TestObject dynamicButton = new TestObject().addProperty("xpath", ConditionType.EQUALS, dynamicXPath)
		WebUI.waitForElementClickable(dynamicButton, 10)
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
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Continue'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_AppSearch'), 10)
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
	public void AddEnvironmentsWithExistingPolicy(String environmentName, String policySearchInput, String policyTitle) {
		WebUI.waitForElementClickable(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Environments'), 10)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Environments'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/create_Environment_Header'), 10)
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_EnvironmentName'), environmentName)
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_PolicySearch'), policySearchInput)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/select_PolicySearchResultsFirstTitleEdit'))
		WebUI.delay(1)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_ExistingPolicyTitle'))
		WebUI.delay(0.5)
		WebUI.executeJavaScript("document.getElementById('policyStoreName').value = '';", null)
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_ExistingPolicyTitle'), policyTitle)
		WebUI.delay(0.5)
		WebElement element = WebUI.findWebElement(findTestObject('Object Repository/Onboarding/Page_Reva.ai/checkbox_ExistingPolicy'), 10)
		JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
		js.executeScript("arguments[0].click();", element)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_Create_Env'))
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Continue'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/popUpHeaderConfigure'), 10)
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
			WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/popUp_DefineSchema'), 10)
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_UploadSchema'))
			WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/textArea_UploadSchema'), 10)
			WebUI.executeJavaScript("document.getElementById('policyStoreSchema').value = '';", null)
			WebUI.delay(1)
			TestObject inputField = findTestObject('Object Repository/Onboarding/Page_Reva.ai/textArea_UploadSchema')
			if (inputField != null) {
				WebUI.waitForElementVisible(inputField, 10)
				WebUI.executeJavaScript("""arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', { bubbles: true }));""", Arrays.asList(WebUI.findWebElement(inputField), jsonCode))
			} else {
				WebUI.comment("TestObject is NULL! Check the Object Repository path.")
			}
			WebUI.delay(1)
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Preview'))
			WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/popUp_DefineSchema'), 10)
			WebUI.dragAndDropToObject(findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowRight_ExistingStaff'), findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowLeft_ExistingPatient'))
			WebUI.dragAndDropToObject(findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowRight_ExistingPatient'), findTestObject('Object Repository/Onboarding/Page_Reva.ai/arrowLeft_ExistingMedication'))
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_SchemaNext'))
			WebUI.delay(0.5)
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_SchemaNext'))
			WebUI.delay(0.5)
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_SchemaNext'))
			WebUI.delay(0.5)
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_SaveAndActivate'))
			WebUI.waitForElementClickable(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_SchemaContinue'), 10)
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_SchemaContinue'))
			CheckForThePolicyStatus(policyTitle)
			WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Continue'))
		}
	}

	@Keyword
	public void UploadTestData(String fileName,  String policyTitle) {
		String dynamicXPath = "//a[text()='" + policyTitle + "']/parent::td/following-sibling::td[last()]/descendant::button"
		TestObject dynamicButton = new TestObject().addProperty("xpath", ConditionType.EQUALS, dynamicXPath)
		if (WebUI.waitForElementClickable(dynamicButton, 10)) {
			WebUI.click(dynamicButton)
		} else {
			WebUI.comment("⚠️ Button not found or not clickable: " + dynamicXPath)
			return
		}
		TestObject fileInput = findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_FileUpload')
		String projectDir = RunConfiguration.getProjectDir()
		String filePath = projectDir + "/TestData/" + fileName + ".zip"
		File file = new File(filePath)
		if (!file.exists()) {
			WebUI.comment("❌ File not found: " + filePath)
			return
		}
		WebUI.uploadFile(fileInput, filePath)
		WebUI.comment("✅ File uploaded successfully: " + filePath)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Preview'))
		WebUI.delay(2)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/btn_Confirm'))
		CheckForTheTestDataUploadStatus(policyTitle)
	}

	@Keyword
	public void DeleteApplication() {
		WebUI.waitForElementPresent(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_AppSearch'), 10)
		WebUI.setText(findTestObject('Object Repository/Onboarding/Page_Reva.ai/input_AppSearch'), GlobalVariable.ApplicationName)
		WebUI.delay(3)
		String dynamicXPath = "//a[text()='" + GlobalVariable.ApplicationName  + "']/parent::td/following-sibling::td[last()]/button"
		TestObject deleteButton = new TestObject().addProperty("xpath", ConditionType.EQUALS, dynamicXPath)
		WebUI.click(deleteButton)
		WebUI.waitForElementClickable(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Delete'), 10)
		WebUI.click(findTestObject('Object Repository/Onboarding/Page_Reva.ai/button_Delete'))
		WebUI.delay(3)
	}
}
