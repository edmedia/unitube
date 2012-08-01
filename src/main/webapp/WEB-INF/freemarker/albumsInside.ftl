<#if pager.elements?size = 0>
<div class="stage">
    <div class="info"><@spring.message "no.album"/></div>
</div>
    <#else>

<table width="780" summary="">
    <tr>
        <th width="40" align="center"> RSS</th>
        <th width="240" align="center">Title</th>
        <th width="200" align="center">Added by</th>
        <th width="300">Description</th>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td align="center">&nbsp;</td>
        <td align="center">&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <#list pager.elements as entity>
    <#if entity.mediaNum &gt; 0>
    <tr>
        <td class="rss"><a href="${baseUrl}/feed.do?topic=album&amp;a=${entity.accessCode}"><img
                src="${baseUrl}/images/feed-icon.png" alt="RSS"/></a></td>
        <td class="alignC bold"><a href="${baseUrl}/album?a=${entity.accessCode}">${entity.albumName} (${entity.mediaNum})</a>
        </td>
        <td class="alignC">
            <#if entity.owner??>
            <a href="${baseUrl}/media.do?u=${entity.owner.accessCode}">${entity.owner.firstName} ${entity.owner.lastName}</a>
            </#if>
        </td>
        <td>${entity.description!}</td>
    </tr>
    </#if>
    </#list>
</table>
<@displayPager pager/>
</#if>
