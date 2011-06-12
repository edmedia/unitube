<div class="stage">
    <p>&nbsp;</p>
<#if obj??>
    <div>
        <a href="${baseUrl}/feed.do?topic=album&amp;a=${obj.accessCode}" title="RSS feed for Album "${obj.albumName?html}""><img
                src="${baseUrl}/images/feed-icon.png" alt="RSS icon"/></a>
        <strong>${obj.albumName?html}</strong>
    </div>
    <#if obj.description?has_content>
        <div>${obj.description}</div>
    </#if>
    <div>
        <form action="">
            <span class="title">URL:</span>
            <input name="album_link" type="text"
                   value="${context_url}/album?a=${obj.accessCode}"
                   readonly="readonly"
                   size="60"
                   class="linkURL"
                    />
        </form>
    </div>
    <#if obj.albumMedias?has_content>
        <form action="">

            <table summary="">
                <#list obj.albumMedias?sort_by(['media', 'uploadTime'])?reverse as albumMedia>
                    <#assign entity=albumMedia.media />
                <#-- display hidden media in hidden album -->
                <#-- hide hidden media in public album -->
                    <#if ((obj.accessType == 10) && (entity.accessType == 10)) || (entity.accessType == 0)>
                        <#assign linkTitle>${entity.title!?html}</#assign>
                        <tr valign="top">
                            <td align="center">
                            <@viewLinkWithThumbnail entity/>
                            </td>
                            <td>
                                <a href="${baseUrl}/view?m=${entity.accessCode}"
                                   title="${linkTitle}"><@getShortTitle entity.title/></a>
                                <#if entity.description?has_content>
                                    <div><@getDesc entity.description/></div>
                                </#if>
                            </td>
                            <td>
                                <span class="title">URL:</span>
                                <input type="text"
                                       value="${context_url}/view?m=${entity.accessCode}"
                                       readonly="readonly"
                                       size="40"
                                       class="linkURL"
                                        />
                            </td>
                            <td>
                                <span class="title">Embed:</span>
                                <input type="text"
                                       value="${getEmbedCode(entity)?html}"
                                       readonly="readonly"
                                       size="40"
                                       class="linkURL"
                                        />
                            </td>
                        </tr>
                    </#if>
                </#list>
            </table>
        </form>
    </#if>

    <script type="text/javascript">
        <!--
        $(function() {
            $('input.linkURL').click(function() {
                $(this).focus();
                $(this).select();
            });

        });
        //-->
    </script>
    <#else>
        Can't find this album.
</#if>
</div>