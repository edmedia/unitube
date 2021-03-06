<div class="stage">
    <#assign playerWidth = 720/>
    <#assign playerHeight = 540/>
    <#assign playlistNav = 260/>
    <#include "playlistHelper.ftl"/>

     <#assign embedCode><div id="__unitube_${album.id?c}" style="width:${(playerWidth+playlistNav)?c}px"><iframe width="${(playerWidth+playlistNav)?c}" height="${height?c}" src="${context_url}/embedPlaylist.do?a=${album.accessCode}" frameborder="0" allowfullscreen></iframe><div style="text-align:center"><a href="${context_url}/playlist.do?a=${album.accessCode}">Hosted by UniTube</a></div></div></#assign>

    <form action="" name="linkForm">
            <p>
                <span class="title">Embed:</span>
                <input type="text"
                       value="${embedCode?html}"
                       readonly="readonly"
                       size="50"
                       class="linkURL"
                        />
                <span title="To embed a media file, copy this code from the &quot;Embed&quot; box and paste it into your web page to embed it">?</span>
            </p>

        </form>
</div>

<script type="text/javascript">
    <!--
    $(function() {
        $('input.linkURL').click(function() {
            $(this).select();
        });
    });
    //-->
</script>
