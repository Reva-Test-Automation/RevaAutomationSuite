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
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.ConditionType
import groovy.json.JsonSlurper
import com.kms.katalon.core.util.KeywordUtil					
import groovy.json.JsonSlurper
import com.kms.katalon.core.testobject.*
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.model.FailureHandling
import internal.GlobalVariable

				
				Map<String, Map<String, String>> expectedEntities = [				
					"Automation-001": [
						"resourceId"      : "acct-john-checking",
						"riskLevel"       : "medium",
						"reasons"         : "",
						"decision"        : "Allow",
						"businessContext" : 'Account Holder transferred $2500.00 from account 1001000123 to account 1001000124: Monthly savings transfer',
						"ipAddress"       : "sess_abc123def456",
						"userAgent"       : "192.168.1.100",
						"principalId"     : "user-john",
						"sessionId"       : "",
						"complianceFlags" : "",
						"principalRole"   : "Account Holder",
						"createdAt"       : "2025-09-09T20:48:15.123Z",
						"requestContext"  : "",
						"actionOutcome"   : "success",
						"auditLogId"      : "Auto-001",
						"action"          : "TransferFunds",
						"principalType"   : "User",
						"resourceType"    : "Account"
					],
				
					"Automation-002": [
						"resourceId"      : "loan-123",
						"riskLevel"       : "high",
						"reasons"         : "",
						"decision"        : "Allow",
						"businessContext" : 'Bank Manager approved loan: approved - Amount: $15000.00, Type: personal',
						"ipAddress"       : "sess_xyz789ghi012",
						"userAgent"       : "10.0.0.25",
						"principalId"     : "user-mike",
						"sessionId"       : "",
						"complianceFlags" : "null",
						"principalRole"   : "Bank Manager",
						"createdAt"       : "2025-09-09T19:30:42.567Z",
						"requestContext"  : "",
						"actionOutcome"   : "success",
						"auditLogId"      : "Auto-002",
						"action"          : "ApproveLoan",
						"principalType"   : "User",
						"resourceType"    : "Loan"
					],
				
					"Automation-003": [
						"resourceId"      : "txn-789",
						"riskLevel"       : "low",
						"reasons"         : "",
						"decision"        : "Deny",
						"businessContext" : "Bank Teller attempted to view a restricted transaction record",
						"ipAddress"       : "sess_mno345pqr678",
						"userAgent"       : "203.0.113.45",
						"principalId"     : "user-sarah",
						"sessionId"       : "",
						"complianceFlags" : "suspicious_pattern",
						"principalRole"   : "Bank Teller",
						"createdAt"       : "2025-09-09T18:15:28.890Z",
						"requestContext"  : "",
						"actionOutcome"   : "failed",
						"auditLogId"      : "Auto-003",
						"action"          : "ViewTransaction",
						"principalType"   : "User",
						"resourceType"    : "Transaction"
					]
				]				
				expectedEntities.each { entityId, expectedMap ->				
					println("\n====================== 🔍 Validating Entity: ${entityId} ======================")						
					String getUrl = "${GlobalVariable.baseUrl}/ingestion/v1/entity/${entityId}?entityTypeId=${GlobalVariable.EntityTypeId}"
					println("➡ GET URL: " + getUrl)				
					RequestObject getReq = new RequestObject("GET_AuditLog_${entityId}")
					getReq.setRestUrl(getUrl)
					getReq.setRestRequestMethod("GET")
					getReq.setHttpHeaderProperties([
						new TestObjectProperty("Authorization", ConditionType.EQUALS, "Bearer " + GlobalVariable.GetToken),
						new TestObjectProperty("Content-Type", ConditionType.EQUALS, "application/json")
					])				
					def getResponse = WS.sendRequest(getReq)
					println("Response Status: " + getResponse.getStatusCode())
					println("Response Body:\n" + getResponse.getResponseBodyContent())				
					if (getResponse.getStatusCode() == 200) {				
						def json = new JsonSlurper().parseText(getResponse.getResponseBodyContent())
						def attributes = json?.get("entityAttributes")				
						if (attributes != null && attributes.size() > 0) {				
							println("\n🔹 Attributes found for entityId '${json.get("name")}':")
							attributes.each { attr -> println("   ${attr.name} = ${attr.value}") }				
							int failCount = 0
							expectedMap.each { key, expectedValue ->
								def actualValue = attributes.find { it.name == key }?.value
								if (actualValue == expectedValue) {
									println("✅ ${key} matched: ${actualValue}")
								} else {
									println("❌ ${key} mismatch → Expected: '${expectedValue}' | Actual: '${actualValue}'")
									failCount++
								}
							}				
							if (failCount == 0) {
								KeywordUtil.markPassed("🎯 All attribute values matched successfully for entityId: ${entityId}")
							} else {
								KeywordUtil.markFailed("❌ ${failCount} attribute(s) mismatched in GET validation for entityId: ${entityId}")
							}				
						} else {
							KeywordUtil.markFailed("❌ No entityAttributes found in GET response for entityId: ${entityId}")
						}				
					} else {
						KeywordUtil.markFailed("❌ GET request failed with status code: ${getResponse.getStatusCode()} for entityId: ${entityId}")
					}
				}


