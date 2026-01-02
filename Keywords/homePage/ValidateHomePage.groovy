package homePage

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.util.KeywordUtil
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
import org.openqa.selenium.WebElement
import internal.GlobalVariable
import com.kms.katalon.core.testobject.ConditionType

public class ValidateHomePage {

	@Keyword
	public void ValidateUserDetails() {
		String expectedUser = GlobalVariable.UserName
		String actualText = WebUI.getText(findTestObject('Object Repository/Home_Page/UserWelcomeText'))
		if (actualText.toLowerCase().contains(expectedUser.toLowerCase())) {
			println("✅ User text is correctly displayed: " + actualText)
			KeywordUtil.markPassed("User verification successful")
		} else {
			println("❌ User text is not matching. Found: " + actualText)
			KeywordUtil.markFailed("User verification failed")
		}
		TestObject chartPaths = new TestObject("chartPaths")
		chartPaths.addProperty("xpath", ConditionType.EQUALS, "//*[local-name()='g' and contains(@class,'apexcharts-series')]/*[local-name()='path' and @val]")
		List<WebElement> paths = WebUI.findWebElements(chartPaths, 10)
		println("✅ Total paths found: " + paths.size())
		for (int i = 0; i < paths.size(); i++) {
			String valAttr = paths.get(i).getAttribute("val")
			println("👉 Path " + i + " has val = " + valAttr)
		}
	}

	@Keyword
	public void ValidateSideButtons() {
		List<WebElement> SideButtons = WebUI.findWebElements(findTestObject('Home_Page/side_Buttons'), 10)
		for (int i = 0; i < SideButtons.size(); i++) {
			try {
				SideButtons = WebUI.findWebElements(findTestObject('Home_Page/side_Buttons'), 10)
				if (i >= SideButtons.size()) {
					WebUI.comment("Button at index " + i + " is no longer available. Skipping.")
					continue
				}
				WebElement sideBtn = SideButtons.get(i)
				String btnText = sideBtn.getText()
				println("👉 Clicking Side Button " + i + " : " + btnText)
				WebUI.comment("👉 Clicking Side Button " + i + " : " + btnText)
				sideBtn.click()
				WebUI.waitForPageLoad(10)
				SideButtons = WebUI.findWebElements(findTestObject('Home_Page/side_Buttons'), 10)
				sideBtn = SideButtons.get(i)
			} catch (org.openqa.selenium.StaleElementReferenceException e) {
				WebUI.comment("⚠️ Stale element exception caught for index " + i + ". Retrying...")
				i--
			}
		}
	}
}
