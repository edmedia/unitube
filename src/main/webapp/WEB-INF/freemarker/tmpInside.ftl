<#list list as m>
${context_url}/view?m=${m.accessCode},<#if m.mediaType ==20>Video<#elseif m.mediaType == 10>Audio<#elseif m.mediaType == 5>Image<#else>Other</#if>,${m.uploadTime?string("yyyy/MM/dd HH:mm:ss")},${m.accessTimes}
</#list>