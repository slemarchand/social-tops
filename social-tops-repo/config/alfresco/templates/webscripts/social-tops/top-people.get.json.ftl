<#-- List persons Implementation-->
<#import "person.lib.ftl" as personLib />

<#assign positions = nodes?keys?sort />
<#assign people = nodes />
[
<#list positions as position>	 	   
	<@personLib.personJSON person=people[position] score=scores[position] position=position />
	<#if position_has_next>,</#if>
</#list>
]
