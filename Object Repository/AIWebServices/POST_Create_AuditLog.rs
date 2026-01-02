<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>POST_Create_AuditLog</name>
   <tag></tag>
   <elementGuidId>b0348b8a-5de8-4810-b0f7-373535f998a7</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <smartLocatorEnabled>false</smartLocatorEnabled>
   <useRalativeImagePath>false</useRalativeImagePath>
   <authorizationRequest>
      <authorizationInfo>
         <entry>
            <key>bearerToken</key>
            <value>${GlobalVariable.GetToken}</value>
         </entry>
      </authorizationInfo>
      <authorizationType>Bearer</authorizationType>
   </authorizationRequest>
   <autoUpdateContent>false</autoUpdateContent>
   <connectionTimeout>0</connectionTimeout>
   <followRedirects>true</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent>{
  &quot;text&quot;: &quot;[\n  {\n    \&quot;entityId\&quot;: \&quot;Automation-001\&quot;,\n    \&quot;attributes\&quot;: [\n      {\&quot;name\&quot;: \&quot;resourceId\&quot;, \&quot;value\&quot;: \&quot;acct-john-checking\&quot;},\n      {\&quot;name\&quot;: \&quot;riskLevel\&quot;, \&quot;value\&quot;: \&quot;medium\&quot;},\n      {\&quot;name\&quot;: \&quot;reasons\&quot;, \&quot;value\&quot;: \&quot;\&quot;},\n      {\&quot;name\&quot;: \&quot;decision\&quot;, \&quot;value\&quot;: \&quot;Allow\&quot;},\n      {\&quot;name\&quot;: \&quot;businessContext\&quot;, \&quot;value\&quot;: \&quot;Account Holder transferred $2500.00 from account 1001000123 to account 1001000124: Monthly savings transfer\&quot;},\n      {\&quot;name\&quot;: \&quot;ipAddress\&quot;, \&quot;value\&quot;: \&quot;sess_abc123def456\&quot;},\n      {\&quot;name\&quot;: \&quot;userAgent\&quot;, \&quot;value\&quot;: \&quot;192.168.1.100\&quot;},\n      {\&quot;name\&quot;: \&quot;principalId\&quot;, \&quot;value\&quot;: \&quot;user-john\&quot;},\n      {\&quot;name\&quot;: \&quot;sessionId\&quot;, \&quot;value\&quot;: \&quot;\&quot;},\n      {\&quot;name\&quot;: \&quot;complianceFlags\&quot;, \&quot;value\&quot;: \&quot;\&quot;},\n      {\&quot;name\&quot;: \&quot;principalRole\&quot;, \&quot;value\&quot;: \&quot;Account Holder\&quot;},\n      {\&quot;name\&quot;: \&quot;createdAt\&quot;, \&quot;value\&quot;: \&quot;2025-09-09T20:48:15.123Z\&quot;},\n      {\&quot;name\&quot;: \&quot;requestContext\&quot;, \&quot;value\&quot;: \&quot;\&quot;},\n      {\&quot;name\&quot;: \&quot;actionOutcome\&quot;,  \&quot;value\&quot;: \&quot;success\&quot;},\n      {\&quot;name\&quot;: \&quot;auditLogId\&quot;, \&quot;value\&quot;: \&quot;Auto-001\&quot;},\n      {\&quot;name\&quot;: \&quot;action\&quot;, \&quot;value\&quot;: \&quot;TransferFunds\&quot;},\n      {\&quot;name\&quot;: \&quot;principalType\&quot;, \&quot;value\&quot;: \&quot;User\&quot;},\n      {\&quot;name\&quot;: \&quot;resourceType\&quot;, \&quot;value\&quot;: \&quot;Account\&quot;}\n    ]\n  }  \n]&quot;,
  &quot;contentType&quot;: &quot;application/json&quot;,
  &quot;charset&quot;: &quot;UTF-8&quot;
}</httpBodyContent>
   <httpBodyType>text</httpBodyType>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Content-Type</name>
      <type>Main</type>
      <value>application/json</value>
      <webElementGuid>eb74e0d3-0566-43c8-bc0d-aa2d09e96610</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Origin</name>
      <type>Main</type>
      <value>${GlobalVariable.origin}</value>
      <webElementGuid>b39ec006-6627-4e11-bdcb-7d7790f38449</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Authorization</name>
      <type>Main</type>
      <value>Bearer ${GlobalVariable.GetToken}</value>
      <webElementGuid>2629d205-a0c8-4112-9f6a-af326bb7ee31</webElementGuid>
   </httpHeaderProperties>
   <katalonVersion>10.1.1</katalonVersion>
   <maxResponseSize>0</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <path></path>
   <restRequestMethod>POST</restRequestMethod>
   <restUrl>${GlobalVariable.baseUrl}/ingestion/v1/entity/bulk?entityTypeId=${GlobalVariable.EntityTypeId}</restUrl>
   <serviceType>RESTful</serviceType>
   <soapBody></soapBody>
   <soapHeader></soapHeader>
   <soapRequestMethod></soapRequestMethod>
   <soapServiceEndpoint></soapServiceEndpoint>
   <soapServiceFunction></soapServiceFunction>
   <socketTimeout>0</socketTimeout>
   <useServiceInfoFromWsdl>true</useServiceInfoFromWsdl>
   <verificationScript>import static org.assertj.core.api.Assertions.*

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webservice.verification.WSResponseManager

import groovy.json.JsonSlurper
import internal.GlobalVariable as GlobalVariable

RequestObject request = WSResponseManager.getInstance().getCurrentRequest()

ResponseObject response = WSResponseManager.getInstance().getCurrentResponse()</verificationScript>
   <wsdlAddress></wsdlAddress>
</WebServiceRequestEntity>
