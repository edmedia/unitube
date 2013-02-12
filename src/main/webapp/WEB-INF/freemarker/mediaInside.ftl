<#if user?has_content || mediaType?has_content>
    <@displayMediaList2 pager.elements/>
    <#if pager.lastPageNumber &gt; 1>
        <@displayPager pager/>
    </#if>
<#else>
    ${content}
</#if>



