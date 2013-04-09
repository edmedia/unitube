<script type="text/javascript">
    <!--
    var deviceAgent = navigator.userAgent.toLowerCase();
    var iDevice = deviceAgent.match(/(iphone|ipod|ipad)/);
    var embed = (document.URL.indexOf("embed") != -1);
    var embedPlaylist = (document.URL.indexOf("embedPlaylist") != -1);
    var width = ${width?c};
    var height = ${height?c};
    <#if obj.mediaType ==10>
    if (iDevice) {
        width = 120;
        height = 60;
    }
    if (embed) {
        width = $(window).width();
        if (iDevice) {
            width = '100%';
            height = 60;
        }
    }
    </#if>
    <#if obj.mediaType ==20>
    if (embed) {
        width = $(window).width();
        height = $(window).height();
    }
    </#if>
    <#if playerWidth?? && playerHeight?? && playlistNav??>
    if (embedPlaylist) {
        width = $(window).width() - ${playlistNav?c};
        height = $(window).height();
    }
    </#if>
    // flash first, then download
    var modes = [
        {type: 'flash', src: '${JWPLAYER}'},
        {type: 'download'}
    ];
    if (iDevice)
    // on iDevice, html5 first, then download
        modes = [
            {type: 'html5'},
            {type: 'download'}
        ];
    jwplayer('avPlayer__${obj.id?c}').setup({
        width: width,
        height: height,
        bufferlength: 5,
        events: {
            onBeforePlay: function (event) {
                log('on before play');
            }
        },
        file: "${mediaFileLink?js_string}",
    <#if obj.mediaType ==10>
        provider: 'sound',
        controlbar: {position: 'bottom', idlehide: false},
        image: '${baseUrl}/images/mp3.jpg',
    </#if>
    <#if obj.mediaType == 20>
        <#if thumnailFileLink?has_content>
            image: "${thumnailFileLink?js_string}",
        </#if>
        provider: 'video',
    </#if>
    <#if obj.duration &gt; 0>
        duration: ${(obj.duration/1000)?c},
    </#if>
        modes: modes
    });
    //-->
</script>
