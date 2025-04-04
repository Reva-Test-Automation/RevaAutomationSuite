package homePage
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject
import org.openqa.selenium.By
import commonFunctions.getScreenShotWithHighlight
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUIse
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration
import java.text.SimpleDateFormat

public class SideButtons {

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
				sideBtn.click()
				WebUI.waitForPageLoad(10)
				SideButtons = WebUI.findWebElements(findTestObject('Home_Page/side_Buttons'), 10)
				sideBtn = SideButtons.get(i)
				getScreenShotWithHighlight.takeHighlightedScreenshot(sideBtn, "Highlighted_Button", i+1)
			} catch (org.openqa.selenium.StaleElementReferenceException e) {
				WebUI.comment("⚠️ Stale element exception caught for index " + i + ". Retrying...")
				i--
			}
		}
	}
}






