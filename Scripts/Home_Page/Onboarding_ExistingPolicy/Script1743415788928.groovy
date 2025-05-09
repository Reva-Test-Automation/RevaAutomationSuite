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

        String PolicyTitle = CustomKeywords.'onBoarding.CreateApplication.getRandomTitle'()

        // On Boarding > Create Application > Existing Policy
		CustomKeywords.'commonFunctions.browserUtils.openBrowser'()
		// Navigating to the base URL
		CustomKeywords.'loginPage.NavigateToLogin.NavigateUrl'()
		// User logging into the Reva
		CustomKeywords.'loginPage.NavigateToLogin.Login'(GlobalVariable.UserName, GlobalVariable.Password)
		// Validating logged in User
		//CustomKeywords.'homePage.VerifyLoggedInUser.ValidateUserDetails'()
		// Creating Application > Basic Details
		CustomKeywords.'onBoarding.CreateApplication.FillApplicationDetails'(GlobalVariable.ApplicationName, GlobalVariable.AppCategory, GlobalVariable.ApplicationTags, GlobalVariable.AppOwner, GlobalVariable.AppDescription)
		//CustomKeywords.'onBoarding.CreateApplication.TempFunction'()
		// Creating Application > Adding Environment & Policy
		CustomKeywords.'onBoarding.CreateApplication.AddEnvironmentsWithExistingPolicy'(GlobalVariable.EnvironmentName, GlobalVariable.PolicySearchInput, PolicyTitle)
		// Creating Application > Adding Environment & Policy > Uploading Schema
		CustomKeywords.'onBoarding.CreateApplication.UploadSchemaJson'(GlobalVariable.SchemaJson, PolicyTitle)
		// Creating Application > Adding Environment & Policy > Uploading Test Data
		//CustomKeywords.'onBoarding.CreateApplication.UploadTestData'('RevaEntityData', PolicyTitle)
		// Creating Application > Adding Environment & Policy > Checking Application status
		CustomKeywords.'onBoarding.CreateApplication.CheckForTheApplicationStatus'()
		// Creating Application > Adding Environment & Policy > Deleting Application
		//CustomKeywords.'onBoarding.CreateApplication.DeleteApplication'()
		
		CustomKeywords.'commonFunctions.browserUtils.CloseBrowser'()
		
		
		
		