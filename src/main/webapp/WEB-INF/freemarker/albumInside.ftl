<div class="stage">
    <p>&nbsp;</p>
    <#if obj??>
    <div>
        <a href="${baseUrl}/feed.do?topic=album&amp;a=${obj.accessCode}"><img
                src="${baseUrl}/images/feed-icon.png" alt=""/></a>
        <strong>${obj.albumName?html}</strong>
    </div>
    <#if obj.description?has_content>
    <div>${obj.description}</div>
    </#if>
    <form action="">
        <p class="vid">
            <strong>URL:</strong>
            <input name="album_link" type="text"
                   value="${context_url}/album?a=${obj.accessCode}"
                   onclick="this.focus(); this.select();"
                   readonly="readonly"
                    />
        </p>
    </form>
    <#if obj.albumMedias?has_content>
    <table summary="">
        <#list obj.albumMedias?sort_by(['media', 'uploadTime'])?reverse as albumMedia>
        <#assign entity=albumMedia.media />
        <#-- display private media in private album -->
        <#-- hide private media in public album -->
        <#if ((obj.accessType == 10) && (entity.accessType == 10)) || (entity.accessType == 0)>
        <#assign linkTitle>${entity.title!?html}</#assign>
        <tr valign="top">
            <td align="center">
                <@viewLinkWithThumbnail entity />
            </td>
            <td>
                <a href="${baseUrl}/view?m=${entity.accessCode}" title="${linkTitle}">${entity.title}</a>
                <#if entity.description?has_content>
                <div><@getDesc entity.description/></div>
                </#if>
            </td>
        </tr>
        </#if>
        </#list>
    </table>
    </#if>
    <#else>
    Can't find this album.
    </#if>
</div>