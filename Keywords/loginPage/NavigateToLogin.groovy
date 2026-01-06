package loginPage

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
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class NavigateToLogin {
	@Keyword
	public void NavigateUrl(String Url) {
		WebUI.navigateToUrl(Url)
	}

	@Keyword
	public void Login(String username, String password) {
		//WebUI.waitForElementVisible(findTestObject('Object Repository/Login_Page/btn_SignInUserName'), 10)
		//WebUI.click(findTestObject('Object Repository/Login_Page/btn_SignInUserName'))
		def SignInButton = findTestObject('Object Repository/Login_Page/btn_SignInUserName')
		if (WebUI.waitForElementVisible(SignInButton, 3, FailureHandling.OPTIONAL)) {
			WebUI.click(SignInButton)
		} else {
			WebUI.comment('⏭️ Sign in button not displayed, continuing...')
		}
		WebUI.waitForElementVisible(findTestObject('Login_Page/txt_UserName'), 20)
		WebUI.setText(findTestObject('Login_Page/txt_UserName'), username)
		WebUI.setText(findTestObject('Login_Page/txt_Password'), password)
		WebUI.click(findTestObject('Login_Page/btn_LogIn'))
		WebUI.comment('User logged in successfully with username: ' + username)
		def skipButton = findTestObject('Object Repository/Login_Page/btn_Skip')
		if (WebUI.waitForElementVisible(skipButton, 3, FailureHandling.OPTIONAL)) {
			WebUI.click(skipButton)
		} else {
			WebUI.comment('⏭️ Skip button not displayed, continuing...')
		}
	}

	@Keyword
	public void LogOut() {
		WebUI.click(findTestObject('Object Repository/Home_Page/button_UserLogOut'))
		WebUI.waitForElementVisible(findTestObject('Object Repository/Home_Page/frame_LogOutPopup'), 20)
		WebUI.click(findTestObject('Object Repository/Home_Page/menu_LogOut'))
	}
}
