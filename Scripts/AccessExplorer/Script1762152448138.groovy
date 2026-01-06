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


		 CustomKeywords.'commonFunctions.browserUtils.openBrowser'()
		
		 CustomKeywords.'loginPage.NavigateToLogin.NavigateUrl'(GlobalVariable.App_Url)
		 
		 CustomKeywords.'loginPage.NavigateToLogin.Login'(GlobalVariable.UserName, GlobalVariable.Password)
		 
		 CustomKeywords.'accessExplorer.AccessExplorer.ValidateAccessExplorerBySearchDoctor'()
			
		 
		 
		 
		     /*
			 * def response = WS.sendRequest(findTestObject('Object
			 * Repository/AIWebServices/POST_Create_AuditLog')) println("Response Body:\n" +
			 * response.getResponseBodyContent()) def statusCode = response.getStatusCode()
			 * println("Status Code: " + statusCode) if (statusCode == 200 || statusCode ==
			 * 201) { println("✅ Request successful") } else {
			 * println("❌ Request failed with status code: " + statusCode) } def
			 * jsonResponse = new
			 * groovy.json.JsonSlurper().parseText(response.getResponseBodyContent()) if
			 * (jsonResponse.outcome == "success" || jsonResponse.status == "success") {
			 * println("✅ API outcome success confirmed") } else {
			 * println("⚠️ API outcome not successful: " + jsonResponse) }
			 * 
			 * 
			 * 
			 * 
			 * CustomKeywords.'accessExplorer.AccessExplorer.ValidateAccessExplorerByPrompt'
			 * () def Delete = WS.sendRequest(findTestObject('Object
			 * Repository/AIWebServices/Delete'))
			 * CustomKeywords.'accessExplorer.AccessExplorer.ValidateAccessExplorerAccessMap
			 * '()
			 */
		 
		 
		
		 
		 