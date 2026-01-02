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
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.util.KeywordUtil


				def entityIds = ["Automation-001", "Automation-002", "Automation-003"]
				def baseUrl = GlobalVariable.baseUrl
				def entityTypeId = GlobalVariable.EntityTypeId
				def token = GlobalVariable.GetToken
				println("🚀 Starting DELETE process for entities: ${entityIds.join(', ')}\n")
				entityIds.each { entityId ->
					try {						
						println("Deleting Entity: ${entityId}")		
						String deleteUrl = "${baseUrl}/ingestion/v1/entity/${entityId}?entityTypeId=${entityTypeId}"
						RequestObject deleteReq = new RequestObject("DELETE_${entityId}")
						deleteReq.setRestUrl(deleteUrl)
						deleteReq.setRestRequestMethod("DELETE")
						deleteReq.setHttpHeaderProperties([
							new TestObjectProperty("Authorization", ConditionType.EQUALS, "Bearer " + token),
							new TestObjectProperty("Content-Type", ConditionType.EQUALS, "application/json")
						])
						def deleteResponse = WS.sendRequest(deleteReq)
						println("Response Status: " + deleteResponse.getStatusCode())
						println("Response Body:\n" + deleteResponse.getResponseBodyContent())
						if (deleteResponse.getStatusCode() == 200 || deleteResponse.getStatusCode() == 204) {
							println("✅ Successfully deleted entity: ${entityId}\n")
						} else if (deleteResponse.getStatusCode() == 404) {
							println("⚠️ Entity not found: ${entityId} (might be already deleted)")
						} else if (deleteResponse.getStatusCode() == 401) {
							KeywordUtil.markFailed("❌ Unauthorized (401) while deleting ${entityId} — check your Bearer token.")
						} else {
							KeywordUtil.markFailed("❌ Delete failed for ${entityId} — Status: ${deleteResponse.getStatusCode()}")
						}				
					} catch (Exception e) {
						KeywordUtil.markFailed("❌ Exception while deleting ${entityId}: " + e.message)
					}
				}


