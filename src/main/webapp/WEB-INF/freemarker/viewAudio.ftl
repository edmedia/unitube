<#-- audio -->
<#assign width=480/>
<#assign height=0/>
<#assign flashvars>'playlist': [ { 'url': '${mediaFileLink}', 'autoPlay': false, 'autoBuffering': true} ], 'plugins': { 'controls': { 'bottom': 0, 'height': ${controllerHeight},<#-- for flowplayer 3.2, we need add 'autoHide': false, here--> 'fullscreen': false }, 'audio': { 'url': '${audioPlugin}' } }</#assign>
<#assign embedCode><object width="${width?c}" height="${(controllerHeight+28)?c}"><param name="movie" value="${flowPlayer}" /><param name="flashvars" value="config=${playerURL}" /><param name="allowscriptaccess" value="always" /><embed src="${flowPlayer}" type="application/x-shockwave-flash" allowscriptaccess="always" width="${width?c}" height="${(controllerHeight+28)?c}" flashvars="config=${playerURL}"></embed></object></#assign>
<script type="text/javascript">
    <!--
    var deviceAgent = navigator.userAgent.toLowerCase();
	var iDevice = deviceAgent.match(/(iphone|ipod|ipad)/);
    // using flash for non-iDevice
	if(!iDevice) {
        var flashvars = {
            config: "{ ${flashvars} }"
        };
        var params = {
            allowscriptaccess: "always"
        };
        var requiredFlashVersion = "7.0.0";
        swfobject.embedSWF("${flowPlayer}", "${idDiv}", "${width?c}", "${(height+controllerHeight)?c}", requiredFlashVersion, "${installerURL}", flashvars, params);
    }
    //-->
</script>
