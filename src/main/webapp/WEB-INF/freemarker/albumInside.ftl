<div class="stage">
    <p>&nbsp;</p>
<#if obj??>
    <div>
        <a href="${baseUrl}/feed.do?topic=album&amp;a=${obj.accessCode}"
           title="RSS feed for Album "${obj.albumName?html}""><img
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
        <#if isOwner>
            <span class="title">For album owner: you can re-order media files inside an album, by dragging a media file and dropping it to right place. </span>
        </#if>
    </div>
    <#if obj.albumMedias?has_content>
        <form action="">

            <table summary="">
                <tbody data-id="${obj.id?c}">
                    <#list obj.albumMedias as albumMedia>
                        <#assign entity=albumMedia.media />
                    <#-- display hidden media in hidden album -->
                    <#-- hide hidden media in public album -->
                        <#if ((obj.accessType == 10) && (entity.accessType == 10)) || (entity.accessType == 0)>
                            <#assign linkTitle>${entity.title!?html}</#assign>
                        <tr valign="top" data-id="${albumMedia.id?c}" class="albumMediaList">
                            <td align="center">
                            <@viewLinkWithThumbnail entity/>
                            </td>
                            <td>
                                <a href="${baseUrl}/view?m=${entity.accessCode}"
                                   title="${linkTitle}"><@getShortTitle entity.title/></a>
                                <#if entity.description?has_content>
                                    <div><@displayBrief entity.description maxDescriptionLength/></div>
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
                </tbody>
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
            <#if authUser??>
                $('tbody').sortable({
                    cursor:'move',
                    placeholder: {
                        element : function(i) {
                            log(i.html());
                            return $('<tr class="placeHolder"/>').append(i.html());
                        },
                        update : function(container, p) {
                            return;
                        }
                    },
                    stop: function(event, ui) {
                        var albumId = $(event.target).data('id');
                        log('album id = ' + albumId);
                        var album = new Array();
                        $.each($(event.target).children('tr'), function(i, e) {
                            var id = $(e).data('id');
                            var albumMedia = {id: id, orderNumber: i};
                            album.push(albumMedia);
                            log('id = ' + id + ' orderNumber = ' + i);
                        });
                        var url = '${baseUrl}/myTube/albumMediaReOrder.do';
                        $.post(url,
                                {albumId: albumId, data: JSON.stringify(album)},
                                function(xml) {
                                    if ($("action", xml).attr("success") == "false") {
                                        log("Re-order media files inside album failed.");
                                        alert("Re-order media files inside album failed.")
                                    }
                                });
                    }
                }).disableSelection();
            </#if>
        });
        //-->
    </script>
    <#else>
        Can't find this album.
</#if>
</div>