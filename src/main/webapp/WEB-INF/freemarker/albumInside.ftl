<div class="stage">
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
            <#--
            <a href="playlist.do?a=${obj.accessCode}"><img src="${baseUrl}/images/play-icon.png" alt="Play this album"/> Play this album</a>  -->
        </form>
        <#if isOwner>
            <span class="title">For album owner: you can re-order media files inside an album, by dragging a media file and dropping it to right place. </span>
        </#if>
    </div>
    <#if obj.albumMedias?has_content>
        <form action="">

            <table summary="" style="margin-top: 20px" width="100%">
                <tbody data-id="${obj.id?c}">
                    <#list list as albumMedia>
                        <#assign entity=albumMedia.media />
                        <#assign linkTitle>${entity.title!?html}</#assign>
                    <tr valign="top" data-id="${albumMedia.id?c}" class="albumMediaList">
                        <td width="10">${albumMedia_index+1}</td>
                        <td align="center" width="70">
                        <@viewLinkWithThumbnail entity/>
                        </td>
                        <td>
                            <a href="${baseUrl}/view?m=${entity.accessCode}"
                               title="${linkTitle}">${linkTitle}</a>
                            <#if entity.description?has_content>
                                <div><@displayBrief entity.description maxDescriptionLength/></div>
                            </#if>

                            <#if isOwner>
                                <p>
                                    <span class="title">URL:</span>
                                    <input type="text"
                                           value="${context_url}/view?m=${entity.accessCode}"
                                           readonly="readonly"
                                           size="40"
                                           class="linkURL"
                                            />
                                    <span class="title">Embed:</span>
                                    <input type="text"
                                           value="${getEmbedCode(entity)?html}"
                                           readonly="readonly"
                                           size="40"
                                           class="linkURL"
                                            />
                                </p>
                            </#if>
                        </td>
                    </tr>
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
            <#if isOwner>
                $('tbody').sortable({
                    cursor:'move',
                    placeholder: {
                        element : function(i) {
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
                            log('album media id = ' + id + ' orderNumber = ' + i);
                        });
                        var url = '${baseUrl}/myTube/albumMediaReOrder.do';
                        $.post(url,
                                {albumId: albumId, data: JSON.stringify(album)},
                                function(xml) {
                                    if ($("action", xml).attr("success") == "false") {
                                        log("Re-order media files inside album failed.");
                                        alert("Re-order media files inside album failed.")
                                    } else {
                                        $.each($(event.target).children('tr'), function(i, e) {
                                            // set first td to right number
                                            $(this).children('td').first().text((i + 1));
                                        });
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