package settings
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
import com.kms.katalon.core.testobject.ConditionType
import internal.GlobalVariable

public class usersAndGroup {

	@Keyword
	public void createUser(String userName, String firstName, String lastName, String userEmail, String attributeName, String attributeValue) {
		WebUI.click(findTestObject('Object Repository/Settings/btn_Settings'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Applications'), 10)
		WebUI.click(findTestObject('Object Repository/Settings/link_UsersAndGroups'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Settings/btn_CreateUser'), 10)
		WebUI.click(findTestObject('Object Repository/Settings/btn_CreateUser'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Settings/header_CreateUser'), 10)
		WebUI.setText(findTestObject('Object Repository/Settings/input_UserName'), userName)
		WebUI.setText(findTestObject('Object Repository/Settings/input_FirstName'), firstName)
		WebUI.setText(findTestObject('Object Repository/Settings/input_LastName'), lastName)
		WebUI.setText(findTestObject('Object Repository/Settings/input_UserEmail'), userEmail)
		WebUI.click(findTestObject('Object Repository/Settings/btn_CreateUserNext'))
		WebUI.delay(5)
		WebUI.click(findTestObject('Object Repository/Settings/btn_CreateUserNext'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Settings/input_AttributeName'), 10)
		WebUI.setText(findTestObject('Object Repository/Settings/input_AttributeName'), attributeName)
		WebUI.setText(findTestObject('Object Repository/Settings/input_AttributeValue'), attributeValue)
		//WebUI.click(findTestObject('Object Repository/Settings/btn_Create'))
	}



	@Keyword
	public void createGroups(String groupName, String groupDesc, String userName) {
		WebUI.click(findTestObject('Object Repository/Settings/btn_Settings'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Applications/menu_Applications'), 10)
		WebUI.click(findTestObject('Object Repository/Settings/link_UsersAndGroups'))
		WebUI.click(findTestObject('Object Repository/Settings/tab_Groups'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Settings/btn_CreateGroup'), 10)
		WebUI.click(findTestObject('Object Repository/Settings/btn_CreateGroup'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Settings/input_GroupName'), 10)
		WebUI.setText(findTestObject('Object Repository/Settings/input_GroupName'), groupName)
		WebUI.setText(findTestObject('Object Repository/Settings/input_GroupDesc'), groupDesc)
		WebUI.click(findTestObject('Object Repository/Settings/btn_CreateUserNext'))
		WebUI.delay(0.5)
		WebUI.waitForElementPresent(findTestObject('Object Repository/Settings/input_UserSearch'), 10)
		WebUI.setText(findTestObject('Object Repository/Settings/input_UserSearch'), userName)
		String selectUserCheckBox = "//span[text()='" + userName  + "']/parent::td/preceding-sibling::td/descendant::span"
		TestObject selectUser = new TestObject().addProperty("xpath", ConditionType.EQUALS, selectUserCheckBox)
		WebUI.waitForElementClickable(selectUser, 10)
		WebUI.click(selectUser)
		WebUI.delay(0.5)
		WebUI.click(findTestObject('Object Repository/Settings/btn_AddUser'))
	}
}
