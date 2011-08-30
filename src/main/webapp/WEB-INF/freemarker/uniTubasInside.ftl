<#include "uniTubasInfo.ftl" />

<#if pager.elements?size = 0>
<div class="stage">
<div class="info"><@spring.message "no.unitubas"/></div>
    </div>
<#else>
<table width="440" summary="">
    <tr>
        <th width="40" align="center"> RSS</th>
        <th width="200" align="center">UniTuba</th>
        <th width="200" align="center"></th>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td align="center">&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <#list pager.elements as entity>
    <#assign mediaNum=countMediaNum(entity.medias)/>
    <#assign albumNum=countAlbumNum(entity)/>
    <#if mediaNum &gt; 0>
    <tr>
        <td class="rss">
            <a href="${baseUrl}/feed.do?topic=media&amp;u=${entity.accessCode}">
                <img src="${baseUrl}/images/feed-icon.png" alt="RSS"/>
            </a>
        </td>
        <td class="alignC bold">
            <a href="${baseUrl}/media.do?u=${entity.accessCode}">
                ${entity.firstName} ${entity.lastName} (${mediaNum})
            </a>
        </td>
        <td class="alignC">
            <#if albumNum &gt; 0>
            <a href="${baseUrl}/albums.do?u=${entity.accessCode}">Albums</a> (${albumNum})
            </#if>
        </td>
    </tr>
    </#if>
    </#list>
</table>
<@displayPager pager/>
</#if>
