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

      //String policyTitle = CustomKeywords.'onBoarding.CreateApplication.getRandomTitle'()

	      CustomKeywords.'commonFunctions.browserUtils.openBrowser'()
	
	      CustomKeywords.'loginPage.NavigateToLogin.NavigateUrl'(GlobalVariable.App_Url)
	
	  	  CustomKeywords.'loginPage.NavigateToLogin.Login'(GlobalVariable.UserName, GlobalVariable.Password)		
			
		  //CustomKeywords.'library.Library.CreateEntity'() 
		  
		  CustomKeywords.'library.Library.CreateSchema'()
		  
		  CustomKeywords.'library.Library.DefineLibAttribute'()
		  
		  CustomKeywords.'library.Library.SetUpHierarchy'()
		  
		  CustomKeywords.'library.Library.CreatePolicy'()
		  
		  CustomKeywords.'loginPage.NavigateToLogin.NavigateUrl'(GlobalVariable.App_Url)
		  
		  CustomKeywords.'onBoarding.CreateApplication.FillApplicationDetails'(GlobalVariable.ApplicationName, GlobalVariable.AppCategory, GlobalVariable.ApplicationTags, GlobalVariable.UserName,
		  GlobalVariable.AppDescription)
		  
		  //CustomKeywords.'onBoarding.CreateApplication.AddEnvironmentsWithNewPolicy'(lobalVariable.EnvironmentName, GlobalVariable.PolicyDescription, GlobalVariable.ConnectionName, policyTitle)
		  
		  CustomKeywords.'onBoarding.CreateApplication.DesignSchema'()
		  
		  CustomKeywords.'onBoarding.CreateApplication.DefineAttribute'()
		  
		  CustomKeywords.'onBoarding.CreateApplication.SetUpHierarchy'()
		  
		  CustomKeywords.'onBoarding.CreateApplication.UploadTestData'('TestData_template')
		  
		  //CustomKeywords.'applications.inSights.SettingsTab_PublishAVPGITPolicy'()
		  
		  //CustomKeywords.'onBoarding.CreateApplication.CheckForTheApplicationStatus'()
		  
		  CustomKeywords.'applications.inSights.ValidateApplication'()
		  
		  CustomKeywords.'library.Library.importPolicy'()
	      
		  CustomKeywords.'library.Library.DeleteSchemaAndPolicy'()
	  
		  CustomKeywords.'onBoarding.CreateApplication.DeleteApplication'()
		  
		  //CustomKeywords.'commonFunctions.browserUtils.CloseBrowser'()
  
  