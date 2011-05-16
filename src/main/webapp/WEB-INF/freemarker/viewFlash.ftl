<#assign embedCode><object width="${width?c}" height="${height?c}"><param name="movie" value="${mediaFileLink}" /><param name="allowfullscreen" value="true" /><param name="allowscriptaccess" value="always" /><param name="scale" value="showall" /><embed src="${mediaFileLink}" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" scale="showall" width="${width?c}" height="${height?c}"></embed></object></#assign>
<#assign embedCode><embed width="${width?c}" height="${height?c}" src="${mediaFileLink?html}" allowfullscreen="true" allowscripaccess="always" scale="showall"/></#assign>
<#assign embedCode><iframe width="${width?c}" height="${height?c}" src="${embedURL?html}" frameborder="0" allowfullscreen></iframe></#assign>
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
    swfobject.embedSWF("${mediaFileLink}", "${idDiv}", "${width?c}", "${height?c}", "7", "${installerURL}", flashvars, params);
    //-->
</script>
