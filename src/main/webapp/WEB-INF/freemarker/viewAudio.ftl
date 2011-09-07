<script type="text/javascript">
    <!--
    var deviceAgent = navigator.userAgent.toLowerCase();
    var iDevice = deviceAgent.match(/(iphone|ipod|ipad)/);
    var width = ${width?c};
    var height = ${height?c};
    if (iDevice) {
        width = 120;
        height = 60;
    }
    jwplayer('avPlayer').setup({
        flashplayer: '${JWPLAYER?html}',
        bufferlength: 5,
        controlbar: {position: 'bottom', idlehide: false},
    <#if obj.duration &gt; 0>
        //duration: ${(obj.duration/1000)?c},
    </#if>
        width: width,
        height: height,
        provider: 'sound'
    });
    //-->
</script>
