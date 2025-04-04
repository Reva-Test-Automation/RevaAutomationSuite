package commonFunctions
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
import com.kms.katalon.core.configuration.RunConfiguration
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.driver.DriverFactory
import java.text.SimpleDateFormat
import java.io.File
import java.util.Date
import internal.GlobalVariable

public class getScreenShotWithHighlight {

	private static String screenshotFolder = null

	private static String getOrCreateScreenshotFolder() {
		if (screenshotFolder == null) {
			String projectDir = RunConfiguration.getProjectDir()
			String dateTime = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date())
			screenshotFolder = projectDir + "/Screenshots/" + dateTime

			File folder = new File(screenshotFolder)
			if (!folder.exists()) {
				folder.mkdirs()
			}
		}
		return screenshotFolder
	}

	@Keyword
	public static String takeHighlightedScreenshot(WebElement element, String fileName, int index) {
		try {
			String folderPath = getOrCreateScreenshotFolder()
			WebUI.executeJavaScript("arguments[0].style.border='3px solid red'", Arrays.asList(element))
			String screenshotPath = folderPath + "/" + fileName + "_" + index + ".png"
			WebUI.takeScreenshot(screenshotPath)
			WebUI.comment("📸 Screenshot saved at: " + screenshotPath)
			WebUI.executeJavaScript("arguments[0].style.border=''", Arrays.asList(element))
			return screenshotPath
		} catch (Exception e) {
			WebUI.comment("❌ Failed to take highlighted screenshot: " + e.getMessage())
			return null
		}
	}
}
