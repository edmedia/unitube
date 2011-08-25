<#if pager?has_content>

<#list pager.elements  as avp>

<a href="avp.do?a=${avp.accessCode}">${avp.meaningfulName?html}</a>
</#list>

</#if>