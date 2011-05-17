<#-- audio -->
<#assign flashvars>'playlist': [ { 'url': '${mediaFileLink}', 'autoPlay': false, 'autoBuffering': true} ], 'plugins': { 'controls': { 'bottom': 0, 'height': ${controllerHeight},<#-- for flowplayer 3.2, we need add 'autoHide': false, here--> 'fullscreen': false }, 'audio': { 'url': '${audioPlugin}' } }</#assign>
<#assign flashvars>config=${JWPLAYER_CONFIG?url}</#assign>
<#assign embedCode><object width="${width?c}" height="${(controllerHeight+28)?c}"><param name="movie" value="${flowPlayer}" /><param name="flashvars" value="config=${playerURL}" /><param name="allowscriptaccess" value="always" /><embed src="${flowPlayer}" type="application/x-shockwave-flash" allowscriptaccess="always" width="${width?c}" height="${(controllerHeight+28)?c}" flashvars="config=${playerURL}"></embed></object></#assign>
<#assign embedCode><embed width="${width?c}" height="${height?c}" src="${JWPLAYER?html}" flashvars="${flashvars}"/></#assign>
<#assign embedCode><iframe width="${width?c}" height="${height?c}" src="${embedURL?html}" frameborder="0" allowfullscreen></iframe></#assign>
<script type="text/javascript">
    <!--
    var deviceAgent = navigator.userAgent.toLowerCase();
    var iDevice = deviceAgent.match(/(iphone|ipod|ipad)/);
    var width = ${width?c};
    var height = ${height?c};
    if(iDevice) {
        width = 120;
        height = 60;
    }
    jwplayer('avPlayer').setup({
        flashplayer: '${JWPLAYER?html}',
        bufferlength: 5,
        controlbar: {position: 'bottom', idlehide: false},
        <#if obj.duration &gt; 0>
        duration: ${(obj.duration/1000)?c},
        </#if>
        width: width,
        height: height,
        provider: 'sound'
    });
    //-->
</script>
