<h3>There are ${loginUsers?size} online user(s) now.</h3>
        <!-- accessNum = ${accessNum} -->
<#if loginUsers?has_content>
<ul>
<#list loginUsers as user>
    <!-- ${user.onlineTime} -->
<li>
    ${user.userName} (${user.firstName} ${user.lastName}) - login ${user.loginTimes} times,
    <#if user.lastLoginTim?has_content || user.lastLoginIP?has_content>last login @ <#if user.lastLoginTime?has_content>${user.lastLoginTime?datetime}</#if> from ${user.lastLoginIP},</#if>
    online
    <#if user.onlineTime &gt; (1000*60*60)>
        ${(user.onlineTime/1000/60/60)?string("0")} hours
    <#else>
        ${(user.onlineTime/1000/60)?string("0")} minutes
    </#if>
</li>
</#list>
</ul>
</#if>

<#if notLoginUsers?has_content>
<h3>Offline users.</h3>
<ul>
<#list notLoginUsers?sort_by('lastLoginTime') as user>
<li>
    ${user.userName} (${user.firstName} ${user.lastName}) - login ${user.loginTimes} times,
    <#if user.lastLoginTim?has_content || user.lastLoginIP?has_content>last login @ <#if user.lastLoginTime?has_content>${user.lastLoginTime?datetime}</#if> from ${user.lastLoginIP},</#if>
    online
    <#if user.onlineTime &gt; (1000*60*60)>
        ${(user.onlineTime/1000/60/60)?string("0")} hours
    <#else>
        ${(user.onlineTime/1000/60)?string("0")} minutes
    </#if>
</li>
</#list>
</ul>
</#if>
