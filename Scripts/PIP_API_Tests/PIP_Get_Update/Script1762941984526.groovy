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



					if (!GlobalVariable.GetToken || GlobalVariable.GetToken.trim().isEmpty()) {
					    KeywordUtil.markFailed("❌ GlobalVariable.GetToken is empty. Generate token before running validation.")
					    return
					}
					def expectedData = [
					    "Automation-001": [
					        riskLevel: "high",
					        decision: "Deny",
					        businessContext: "Transfer flagged by risk engine",
					        ipAddress: "sess_xyz999abc777",
					        auditLogId: "Auto-001"
					    ],
					    "Automation-002": [
					        riskLevel: "low",
					        decision: "Allow",
					        businessContext: "Bank Manager approved loan: approved - Amount: \$15000.00, Type: personal",
					        ipAddress: "sess_xyz789ghi012",
					        auditLogId: "Auto-002"
					    ],
					    "Automation-003": [
					        riskLevel: "medium",
					        decision: "Deny",
					        businessContext: "Bank Teller attempted to view a restricted transaction record",
					        ipAddress: "sess_mno345pqr678",
					        auditLogId: "Auto-003"
					    ]
					]					
						expectedData.each { entityId, expectedAttrs ->
					    println "\n🔍 Validating entity: ${entityId}"					   
					    String getUrl = "${GlobalVariable.baseUrl}/ingestion/v1/entity/${entityId}?entityTypeId=${GlobalVariable.EntityTypeId}"
					    println "➡ GET URL: ${getUrl}"
					    RequestObject getReq = new RequestObject("GET_Entity_${entityId}")
					    getReq.setRestUrl(getUrl)
					    getReq.setRestRequestMethod("GET")
					    getReq.setHttpHeaderProperties([
					        new TestObjectProperty("Authorization", ConditionType.EQUALS, "Bearer " + GlobalVariable.GetToken.trim()),
					        new TestObjectProperty("Accept", ConditionType.EQUALS, "application/json")
					    ])
					    def getResponse = WS.sendRequest(getReq)
					    println "Response Status: ${getResponse.getStatusCode()}"
					    println "Response Body: ${getResponse.getResponseBodyContent()}"
					    if (getResponse.getStatusCode() == 401) {
					        KeywordUtil.markFailed("❌ 401 Unauthorized for ${entityId}. Token invalid or expired.")
					        return
					    }
					    if (getResponse.getStatusCode() != 200) {
					        KeywordUtil.markFailed("❌ Failed to fetch ${entityId} (status: ${getResponse.getStatusCode()})")
					        return
					    }
					    def bodyText = getResponse.getResponseBodyContent()
					    def json = new JsonSlurper().parseText(bodyText)   
					    def attrList = null
					    if (json instanceof Map) {
					        if (json.containsKey('attributes')) {
					            attrList = json.attributes
					        } else if (json.containsKey('entityAttributes')) {
					            attrList = json.entityAttributes
					        } else if (json.containsKey('entity') && json.entity instanceof Map) {            
					            attrList = json.entity.attributes ?: json.entity.entityAttributes
					        }
					    }
					    if (!attrList || !(attrList instanceof List) || attrList.size() == 0) {
					        KeywordUtil.markFailed("❌ GET response did not contain attributes for ${entityId}. Response body: ${bodyText}")
					        return
					    }   
					    def actualMap = [:]
					    attrList.each { a ->					        
					        if (a?.name != null) actualMap[a.name] = a.value
					        else if (a?.key != null) actualMap[a.key] = a.val
					    }   
					    int failCount = 0
					    expectedAttrs.each { key, expectedValue ->
					        def actualValue = actualMap[key]
					        if (actualValue == expectedValue) {
					            println "✅ ${key} = ${actualValue}"
					        } else {
					            println "❌ ${key} mismatch → Expected: '${expectedValue}' | Actual: '${actualValue}'"
					            failCount++
					        }
					    }
					    if (failCount == 0) {
					        KeywordUtil.markPassed("🎯 All attributes matched for ${entityId}")
					    } else {
					        KeywordUtil.markFailed("❌ ${failCount} attribute(s) mismatched for ${entityId}")
					    }
					}


