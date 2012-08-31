<#-- Work with a Java Script person object -->
<#macro personJSON person score position >
<#escape x as jsonUtils.encodeJSONString(x)>
{
	"position": "${position}",

	"score": "${score}",
	
	"person":
	{
		"fullName": "${person.properties.userName}",
		"userName": "${person.properties.userName}",
		"firstName": "${person.properties.firstName!""}",
		"lastName": "${person.properties.lastName!""}",
	<#if person.assocs["cm:avatar"]??>
		"avatar": "${"api/node/" + person.assocs["cm:avatar"][0].nodeRef?string?replace('://','/') + "/content/thumbnails/avatar"}",
	</#if>
	<#if person.properties.jobtitle??>
		"jobtitle": "${person.properties.jobtitle}",
	</#if>
	<#if person.properties.organization??>
		"organization": "${person.properties.organization}",
	</#if>
	<#if person.properties.userStatus??>
		"userStatus": "${person.properties.userStatus}",
		"userStatusTime": { "iso8601": "${xmldate(person.properties.userStatusTime)}"},
	</#if>
		"url": "${url.serviceContext + "/api/people/" + person.properties.userName}"
	}
}
</#escape>
</#macro>