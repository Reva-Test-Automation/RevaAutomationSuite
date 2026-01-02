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
import com.kms.katalon.core.testobject.*
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import groovy.json.JsonSlurper
import internal.GlobalVariable

					def updatedBody = '''[
					  {
					    "entityId": "Automation-001",
					    "attributes": [					     
					      {"name": "riskLevel", "value": "high"},					     
					      {"name": "decision", "value": "Deny"},
					      {"name": "businessContext", "value": "Transfer flagged by risk engine"},
					      {"name": "ipAddress", "value": "sess_xyz999abc777"},
					      {"name": "auditLogId", "value": "Auto-001"}						     
					    ]
					  },					
					  {
					    "entityId": "Automation-002",
					    "attributes": [
					      
					      { "name": "riskLevel", "value": "low" },					      
					      { "name": "decision", "value": "Allow" },
					      { "name": "businessContext", "value": "Bank Manager approved loan: approved - Amount: $15000.00, Type: personal" },
					      { "name": "ipAddress", "value": "sess_xyz789ghi012" },					     	
					      { "name": "auditLogId", "value": "Auto-002" }					      
					    ]
					  },
					  {
					    "entityId": "Automation-003",
					    "attributes": [					     
					      { "name": "riskLevel", "value": "medium" },					      
					      { "name": "decision", "value": "Deny" },
					      { "name": "businessContext", "value": "Bank Teller attempted to view a restricted transaction record" },
					      { "name": "ipAddress", "value": "sess_mno345pqr678" },
					      { "name": "auditLogId", "value": "Auto-003" }					      
					    ]
					  }
					]'''		
					RequestObject updateReq = new RequestObject('POST_Update_AuditLog')
					updateReq.setRestUrl("${GlobalVariable.baseUrl}/ingestion/v1/entity/bulk?entityTypeId=${GlobalVariable.EntityTypeId}")
					updateReq.setRestRequestMethod("POST")					
					updateReq.setHttpHeaderProperties([
						new TestObjectProperty("Authorization", ConditionType.EQUALS, "Bearer " + GlobalVariable.GetToken),
						new TestObjectProperty("Content-Type", ConditionType.EQUALS, "application/json; charset=UTF-8")
					])					
					updateReq.setBodyContent(new HttpTextBodyContent(updatedBody, "UTF-8", "application/json"))					
					def updateResponse = WS.sendRequest(updateReq)					
					println("Response Status: " + updateResponse.getStatusCode())
					println("Response Body:\n" + updateResponse.getResponseBodyContent())					
					if (updateResponse.getStatusCode() == 200 || updateResponse.getStatusCode() == 201) {
						println("✅ Update request executed successfully!")
					} else {
						KeywordUtil.markFailed("❌ Update request failed with status: " + updateResponse.getStatusCode())
					}








