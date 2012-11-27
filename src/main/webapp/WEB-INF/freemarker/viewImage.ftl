<a href="${imageFileLink?html}" title="${obj.title?html}" class="shadowbox">
    <img src="${mediaFileLink?html}" width="${width?c}" height="${height?c}" alt="${obj.title?html}"
         title="${obj.title?html}"/>
</a>

<script type="text/javascript">
    <!--
    var embed = (document.URL.indexOf("embed") != -1);
    var embedPlaylist = (document.URL.indexOf("embedPlaylist") != -1);
    var width = ${width?c};
    var height = ${height?c};
    if (embed) {
        width = $(window).width();
        height = $(window).height();
    }
    <#if playerWidth?? && playerHeight?? && playlistNav??>
    if (embedPlaylist) {
        width = $(window).width() - ${playlistNav?c};
        height = $(window).height();
    }
    </#if>
    // keep image ratio
    var tmp = (${obj.width?c} * height / ${obj.height?c});
    if (tmp > width)
        height = (${obj.height?c}* width / ${obj.width?c});
    else
        width = tmp;
    log('width = ' + width + ' height = ' + height + ' embed = '  + embed + ' embedPlaylist = ' + embedPlaylist);
    $('a.shadowbox img').css('width', width + 'px').css('height', height + 'px');

    Shadowbox.init({
        skipSetup: true
    });

    Shadowbox.setup("a.shadowbox", {
        overlayOpacity: 0.85,
        player: 'img',
        counterType: 'skip'
    });
    //-->
</script>
