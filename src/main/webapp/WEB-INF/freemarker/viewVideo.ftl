<#assign flashvars>'playlist': [ <#if obj.thumbnail?has_content>{'url':'${thumnailFileLink}'}, </#if>{'url': '${mediaFileLink}', 'autoPlay': false, 'scaling': 'orig', 'autoBuffering': true, 'accelerated': true } ], 'plugins': { 'controls': { 'bottom': 0, 'height': ${controllerHeight} } }</#assign>
<#assign embedCode><object width="${width?c}" height="${(height+controllerHeight)?c}"><param name="movie" value="${flowPlayer}" /><param name="flashvars" value="config=${playerURL}" /><param name="allowfullscreen" value="true" /><param name="allowscriptaccess" value="always" /><embed src="${flowPlayer}" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" width="${width?c}" height="${(height+controllerHeight)?c}" flashvars="config=${playerURL}"></embed></object></#assign>
<script type="text/javascript">
    <!--
    var deviceAgent = navigator.userAgent.toLowerCase();
	var iPhone = deviceAgent.match(/(iphone|ipod|ipad)/);
	if(!iPhone) {
        var flashvars = {
            config: "{ ${flashvars} }"
        };
        var params = {
            allowfullscreen: "true",
            allowscriptaccess: "always"
        };
        // require 9.0.115 or above to play H.264 video
        var requiredFlashVersion = "9.0.115";
        swfobject.embedSWF("${flowPlayer}", "${idDiv}", "${width?c}", "${(height+controllerHeight)?c}", requiredFlashVersion, "${installerURL}", flashvars, params);
    }
    //-->
</script>
