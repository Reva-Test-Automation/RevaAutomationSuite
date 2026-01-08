package accessExplorer
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory
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
import org.openqa.selenium.WebElement
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable
import com.kms.katalon.core.util.KeywordUtil
import org.openqa.selenium.WebDriver
public class AccessExplorer {

	@Keyword
	public void ValidateAccessExplorerByPrompt() {
		WebUI.click(findTestObject('Object Repository/AccessExplorer/btn_AccessExplorer'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/AccessExplorer/section_AccessExplorer'), 10)
		WebUI.setText(findTestObject('Object Repository/AccessExplorer/input_AccessExplorer'), GlobalVariable.AEPromptOne)
		WebUI.click(findTestObject('Object Repository/AccessExplorer/btn_PromptSend'))
		WebUI.delay(3)
		TestObject spinner = findTestObject('Object Repository/AccessExplorer/spinner_PromptLoader')
		WebUI.waitForElementPresent(spinner, 10, FailureHandling.OPTIONAL)
		boolean gone = WebUI.waitForElementNotVisible(spinner, 10, FailureHandling.OPTIONAL)
		if (!gone) {
			WebUI.waitForElementNotPresent(spinner, 20, FailureHandling.OPTIONAL)
		}
		println("✅ Spinner is gone, continuing execution...")

		String actual = WebUI.getText(findTestObject('Object Repository/AccessExplorer/text_FirstPromptResponse')).toLowerCase()

		// Normalize the text
		actual = actual.replaceAll('[^a-z0-9.$]', ' ').replaceAll('\\s+', ' ').trim()

		List<String> keyPatterns = [
			/transferfunds/,
			// action
			/(allow|allowed)/,
			// match both "allow" and "allowed"
			/(user|principal).*john/,
			// user
			/acct[- ]john[- ]checking/,
			// resource
			/secure[- ]bank/,
			// application
			/environment.*default/,
			// environment
			/risk.*medium/,
			// risk level
			/outcome.*success/,
			// success outcome
			/192\.168\.1\.100/,
			// IP address
			/sess.*abc123def456/,
			// session ID
			/(transfer|transferred).*2[ ,]?500\.00/,      
		]

		// Validation
		List<String> missing = keyPatterns.findAll { !(actual =~ it) }

		if (missing.isEmpty()) {
			KeywordUtil.logInfo("✅ Validation passed — all expected audit info found.")
			println("✅ Validation passed — all expected audit info found.")
		} else {
			KeywordUtil.markFailed("❌ Validation failed — missing expected info.\nMissing: ${missing}\nActual: ${actual}")
		}
	}

	@Keyword
	public void ValidateAccessExplorerAccessMap() {
		WebUI.setText(findTestObject('Object Repository/AccessExplorer/input_AccessExplorer'), GlobalVariable.AEPromptTwo)
		WebUI.click(findTestObject('Object Repository/AccessExplorer/btn_PromptSend'))
		WebUI.delay(3)
		TestObject spinner = findTestObject('Object Repository/AccessExplorer/spinner_PromptLoader')
		WebUI.waitForElementPresent(spinner, 10, FailureHandling.OPTIONAL)
		boolean gone = WebUI.waitForElementNotVisible(spinner, 10, FailureHandling.OPTIONAL)
		if (!gone) {
			WebUI.waitForElementNotPresent(spinner, 20, FailureHandling.OPTIONAL)
		}
		def driver = DriverFactory.getWebDriver()
		TestObject listObject = findTestObject('Object Repository/AccessExplorer/list_AccessExplorer')
		String locator = listObject.findPropertyValue('xpath')
		List<WebElement> elements = driver.findElements(By.xpath(locator))
		List<String> allTexts = elements.collect { it.getText().trim() }
		println("🔹 Found ${allTexts.size()} items in the AccessExplorer list:")
		allTexts.eachWithIndex { text, index ->
			println("${index + 1}. ${text}")
		}
	}

	@Keyword
	public void ValidateAccessExplorerBySearchDoctor() {
		WebUI.click(findTestObject('Object Repository/AccessExplorer/btn_AccessExplorer'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/AccessExplorer/section_AccessExplorer'), 10)
		WebUI.waitForElementClickable(findTestObject('Object Repository/AccessExplorer/input_AccessExplorer'), 30)
		WebUI.setText(findTestObject('Object Repository/AccessExplorer/input_AccessExplorer'), GlobalVariable.AEPromptDoc)
		WebUI.click(findTestObject('Object Repository/AccessExplorer/btn_PromptSend'))
		WebUI.delay(3)
		TestObject spinner = findTestObject('Object Repository/AccessExplorer/spinner_PromptLoader')
		WebUI.waitForElementPresent(spinner, 10, FailureHandling.OPTIONAL)
		boolean gone = WebUI.waitForElementNotVisible(spinner, 10, FailureHandling.OPTIONAL)
		if (!gone) {
			WebUI.waitForElementNotPresent(spinner, 10, FailureHandling.OPTIONAL)
		}
		println("✅ Spinner is gone, continuing execution...")
		/*String actual = WebUI.getText(findTestObject('Object Repository/AccessExplorer/text_FirstPromptResponse')).toLowerCase()
		actual = actual.replaceAll("[^a-z0-9 ]", " ").replaceAll("\\s+", " ").trim()
		actual = actual.replaceAll("\\b(at|in|the|of|there|are)\\b", " ").replaceAll("\\s+", " ").trim()
		String expected = "oncology department providence hospital"
		if (actual.contains(expected)) {
			KeywordUtil.logInfo("✅ Validation Passed — found: ${expected}")
		} else {
			KeywordUtil.markFailed("❌ Validation Failed — expected '${expected}' not found.\nNormalized actual: ${actual}")
		}*/
		String dynamicXPath = "//table[contains(@class,'chakra-table')]//tbody//a[@aria-label='" + GlobalVariable.AEViewDoctor + "'][1]/ancestor::td/following-sibling::td//button"
		TestObject ViewAction = new TestObject()
		ViewAction.addProperty("xpath", ConditionType.EQUALS, dynamicXPath)
		WebUI.click(ViewAction)
		TestObject loader = findTestObject('Object Repository/AccessExplorer/img_Loading')
		WebUI.waitForElementPresent(loader, 10, FailureHandling.OPTIONAL)
		boolean loaderGone = WebUI.waitForElementNotVisible(loader, 100, FailureHandling.OPTIONAL)
		if (!loaderGone) {
			WebUI.waitForElementNotPresent(loader, 10, FailureHandling.OPTIONAL)
		}
		String xpath = "//div[@class='react-flow__edgelabel-renderer']/following-sibling::div/descendant::div[@class='react-flow__node react-flow__node-custom']/descendant::p[normalize-space(string())!=''][last()]"
		WebDriver driver = DriverFactory.getWebDriver()
		List<WebElement> elements = driver.findElements(By.xpath(xpath))

		List<String> actualList = elements.collect { it.getAttribute("innerText").trim() }
		println "Actual:   ${actualList}"
		List<String> expectedList = GlobalVariable.AEAccessMapValues.split(',')*.trim()
		List<String> expectedSorted = expectedList.sort(false)
		List<String> actualSorted   = actualList.sort(false)
		assert expectedSorted == actualSorted : """
		❌ Mismatch in sorted comparison		
		Expected(sorted): ${expectedSorted}
		Actual(sorted):   ${actualSorted}
		"""

		println "✅ Values match after sorting"
	}
}
