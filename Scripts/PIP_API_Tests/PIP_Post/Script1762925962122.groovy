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
import internal.GlobalVariable
import com.kms.katalon.core.util.KeywordUtil

			
			def postBody = '''[
			  {
			    "entityId": "Automation-001",
			    "attributes": [
			      { "name": "resourceId", "value": "acct-john-checking" },
			      { "name": "riskLevel", "value": "medium" },
			      { "name": "reasons", "value": "" },
			      { "name": "decision", "value": "Allow" },
			      { "name": "businessContext", "value": "Account Holder transferred $2500.00 from account 1001000123 to account 1001000124: Monthly savings transfer" },
			      { "name": "ipAddress", "value": "sess_abc123def456" },
			      { "name": "userAgent", "value": "192.168.1.100" },
			      { "name": "principalId", "value": "user-john" },
			      { "name": "sessionId", "value": "" },
			      { "name": "complianceFlags", "value": "" },
			      { "name": "principalRole", "value": "Account Holder" },
			      { "name": "createdAt", "value": "2025-09-09T20:48:15.123Z" },
			      { "name": "requestContext", "value": "" },
			      { "name": "actionOutcome", "value": "success" },
			      { "name": "auditLogId", "value": "Auto-001" },
			      { "name": "action", "value": "TransferFunds" },
			      { "name": "principalType", "value": "User" },
			      { "name": "resourceType", "value": "Account" }
			    ]
			  },
			  {
			    "entityId": "Automation-002",
			    "attributes": [
			      { "name": "resourceId", "value": "loan-123" },
			      { "name": "riskLevel", "value": "high" },
			      { "name": "reasons", "value": "" },
			      { "name": "decision", "value": "Allow" },
			      { "name": "businessContext", "value": "Bank Manager approved loan: approved - Amount: $15000.00, Type: personal" },
			      { "name": "ipAddress", "value": "sess_xyz789ghi012" },
			      { "name": "userAgent", "value": "10.0.0.25" },
			      { "name": "principalId", "value": "user-mike" },
			      { "name": "sessionId", "value": "" },
			      { "name": "complianceFlags", "value": "null" },
			      { "name": "principalRole", "value": "Bank Manager" },
			      { "name": "createdAt", "value": "2025-09-09T19:30:42.567Z" },
			      { "name": "requestContext", "value": "" },
			      { "name": "actionOutcome", "value": "success" },
			      { "name": "auditLogId", "value": "Auto-002" },
			      { "name": "action", "value": "ApproveLoan" },
			      { "name": "principalType", "value": "User" },
			      { "name": "resourceType", "value": "Loan" }
			    ]
			  },
			  {
			    "entityId": "Automation-003",
			    "attributes": [
			      { "name": "resourceId", "value": "txn-789" },
			      { "name": "riskLevel", "value": "low" },
			      { "name": "reasons", "value": "" },
			      { "name": "decision", "value": "Deny" },
			      { "name": "businessContext", "value": "Bank Teller attempted to view a restricted transaction record" },
			      { "name": "ipAddress", "value": "sess_mno345pqr678" },
			      { "name": "userAgent", "value": "203.0.113.45" },
			      { "name": "principalId", "value": "user-sarah" },
			      { "name": "sessionId", "value": "" },
			      { "name": "complianceFlags", "value": "suspicious_pattern" },
			      { "name": "principalRole", "value": "Bank Teller" },
			      { "name": "createdAt", "value": "2025-09-09T18:15:28.890Z" },
			      { "name": "requestContext", "value": "" },
			      { "name": "actionOutcome", "value": "failed" },
			      { "name": "auditLogId", "value": "Auto-003" },
			      { "name": "action", "value": "ViewTransaction" },
			      { "name": "principalType", "value": "User" },
			      { "name": "resourceType", "value": "Transaction" }
			    ]
			  }
			]
			'''			
			RequestObject req = new RequestObject('POST_Create_AuditLog')
			req.setRestUrl("${GlobalVariable.baseUrl}/ingestion/v1/entity/bulk?entityTypeId=${GlobalVariable.EntityTypeId}")
			req.setRestRequestMethod("POST")			
			req.setHttpHeaderProperties([
				new TestObjectProperty("Authorization", ConditionType.EQUALS, "Bearer " + GlobalVariable.GetToken),
				new TestObjectProperty("Content-Type", ConditionType.EQUALS, "application/json; charset=UTF-8")
			])			
			req.setBodyContent(new HttpTextBodyContent(postBody, "UTF-8", "application/json"))			
			def response = WS.sendRequest(req)			
			println("Response Status: " + response.getStatusCode())
			println("Response Body:\n" + response.getResponseBodyContent())			
			if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
				println("✅ POST request executed successfully!")
			} else {
				KeywordUtil.markFailed("❌ Request failed with status: " + response.getStatusCode())
			}

