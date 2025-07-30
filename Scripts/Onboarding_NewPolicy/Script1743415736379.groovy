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

       
		CustomKeywords.'commonFunctions.browserUtils.openBrowser'()
       
		CustomKeywords.'loginPage.NavigateToLogin.NavigateUrl'(GlobalVariable.App_Url)		
		
		CustomKeywords.'loginPage.NavigateToLogin.Login'(GlobalVariable.UserName, GlobalVariable.Password)		
		
		// CustomKeywords.'homePage.VerifyLoggedInUser.ValidateUserDetails'()		
		
		CustomKeywords.'onBoarding.CreateApplication.FillApplicationDetails'(GlobalVariable.ApplicationName, GlobalVariable.AppCategory, GlobalVariable.ApplicationTags, GlobalVariable.AppOwner, GlobalVariable.AppDescription)		
       
		CustomKeywords.'onBoarding.CreateApplication.AddEnvironmentsWithNewPolicy'(GlobalVariable.EnvironmentName, GlobalVariable.PolicyDescription, GlobalVariable.ConnectionName, PolicyTitle)
		
        CustomKeywords.'onBoarding.CreateApplication.DesignSchema'(PolicyTitle)
		
		CustomKeywords.'onBoarding.CreateApplication.DefineAttribute'()
		
		CustomKeywords.'onBoarding.CreateApplication.SetUpHierarchy'(PolicyTitle)
		
		CustomKeywords.'onBoarding.CreateApplication.UploadTestData'('TestData_template', PolicyTitle)
		
		CustomKeywords.'onBoarding.CreateApplication.CheckForTheApplicationStatus'()
		
		//CustomKeywords.'onBoarding.CreateApplication.DeleteApplication'()
		
		//CustomKeywords.'commonFunctions.browserUtils.CloseBrowser'()


