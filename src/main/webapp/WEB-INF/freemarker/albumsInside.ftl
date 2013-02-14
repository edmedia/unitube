<#if user?has_content>
    <#if pager.elements?size = 0>
    <div class="stage">
        <div class="info"><@spring.message "no.album"/></div>
    </div>
    <#else>
    <table width="100%" summary="">
        <tr>
            <th width="40" align="center"> RSS</th>
            <th width="240" align="center">Title</th>
            <th width="120" align="center">Added by</th>
            <th>Description</th>
        </tr>

        <#list pager.elements as entity>
            <#if entity.mediaNum &gt; 0>
                <tr valign="top">
                    <td class="rss"><a href="${baseUrl}/feed.do?topic=album&amp;a=${entity.accessCode}"><img
                            src="${baseUrl}/images/feed-icon.png" alt="RSS"/></a></td>
                    <td class=" bold"><a href="${baseUrl}/album?a=${entity.accessCode}">${entity.albumName}
                        (${entity.mediaNum})</a>
                    </td>
                    <td>
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
<#else>
${content}
</#if>
