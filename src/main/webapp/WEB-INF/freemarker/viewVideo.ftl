<#assign flashvars>'playlist': [ <#if obj.thumbnail?has_content>{'url':'${thumnailFileLink}'}, </#if>{'url': '${mediaFileLink}', 'autoPlay': true, 'scaling': 'orig', 'autoBuffering': true, 'provider': 'http', 'accelerated': true } ], 'plugins': { 'controls': { 'bottom': 0, 'height': ${controllerHeight} } }</#assign>
<#assign flashvars>config=${JWPLAYER_CONFIG?url}</#assign>
<#assign embedCode><embed width="${width?c}" height="${(height+controllerHeight)?c}" src="${flowPlayer}" flashvars="config=${playerURL}" allowfullscreen="true" allowscriptaccess="always"/></#assign>
<#assign embedCode><embed width="${width?c}" height="${height?c}" src="${JWPLAYER?html}" flashvars="${flashvars}" allowfullscreen="true" allowscripaccess="always"/></#assign>
<#assign embedCode><iframe width="${width?c}" height="${height?c}" src="${embedURL?html}" frameborder="0" allowfullscreen></iframe></#assign>
<script type="text/javascript">
    <!--
    jwplayer('avPlayer').setup({
        flashplayer: '${JWPLAYER}',
        bufferlength: 5,
        <#if obj.duration &gt; 0>
        duration: ${(obj.duration/1000)?c},
        </#if>
        provider: 'video'
    });
    //-->
</script>
