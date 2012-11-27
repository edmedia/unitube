<script type="text/javascript">
    <!--
    var flashvars = {
    };
    var params = {
        allowfullscreen: "true",
        allowscriptaccess: "always",
        scale: "showall"
    };
    var requiredFlashVersion = "7.0.0";
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
    swfobject.embedSWF("${mediaFileLink}", "${idDiv}", width, height, "7", "${installerURL}", flashvars, params);
    //-->
</script>
