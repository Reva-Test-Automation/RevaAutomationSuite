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




        // On Boarding > Create Application > New Policy

        // Navigating to the base URL
		CustomKeywords.'loginPage.NavigateToLogin.NavigateUrl'()		
		// User logging into the Reva
		CustomKeywords.'loginPage.UserLogin.Login'(GlobalVariable.UserName, GlobalVariable.Password)		
		// Validating logged in User
		CustomKeywords.'homePage.VerifyLoggedInUser.ValidateUserDetails'()		
		// Creating Application > Basic Details
		CustomKeywords.'onBoarding.CreateApplication.FillApplicationDetails'(GlobalVariable.ApplicationName, GlobalVariable.AppCategory, GlobalVariable.ApplicationTags, GlobalVariable.UserName, GlobalVariable.AppDescription)		
        // Creating Application > Create Environment & Create Policy 
		CustomKeywords.'onBoarding.CreateApplication.AddEnvironmentsWithNewPolicy'(GlobalVariable.EnvironmentName, GlobalVariable.PolicyDisplayName, GlobalVariable.PolicyDescription, GlobalVariable.ConnectionName)
		// Create Policy > Design Schema
        CustomKeywords.'onBoarding.CreateApplication.DesignSchema'()
		// Create Policy > Define Attribute
		CustomKeywords.'onBoarding.CreateApplication.DefineAttribute'()
		// Create Policy > Set Up Hierarchy
		CustomKeywords.'onBoarding.CreateApplication.SetUpHierarchy'()
		// Creating Application > Adding Environment & Policy > Uploading Test Data
		CustomKeywords.'onBoarding.CreateApplication.UploadTestData'('RevaEntityData')
		// Creating Application > Adding Environment & Policy > Checking Application status
		CustomKeywords.'onBoarding.CreateApplication.CheckForTheApplicationStatus'()
		// Creating Application > Adding Environment & Policy > Deleting Application
		CustomKeywords.'onBoarding.CreateApplication.DeleteApplication'()



