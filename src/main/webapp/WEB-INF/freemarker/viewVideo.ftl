<script type="text/javascript">
    <!--
    var deviceAgent = navigator.userAgent.toLowerCase();
    var iDevice = deviceAgent.match(/(iphone|ipod|ipad)/);
    if (iDevice)
        jwplayer('avPlayer').setup({
            bufferlength: 5,
        <#if obj.duration &gt; 0>
            //duration: ${(obj.duration/1000)?c},
        </#if>
            provider: 'video',
            modes: [
                {type: 'html5'},
                {type: 'download'}
            ]
        });
    else
        jwplayer('avPlayer').setup({
            bufferlength: 5,
        <#if obj.duration &gt; 0>
            //duration: ${(obj.duration/1000)?c},
        </#if>
            provider: 'video',
            modes: [
                {type: 'flash', src: '${JWPLAYER}'},
                {type: 'download'}
            ]
        });
    //-->
</script>
