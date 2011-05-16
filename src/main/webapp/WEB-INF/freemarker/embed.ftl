<#include "common/head.ftl" />
<#include "viewHelper.ftl" />

<#if obj.mediaType == 5>
<a href="${imageFileLink}" title="${obj.title?html}">
    <img src="${mediaFileLink}" width="${width?c}" height="${height?c}" alt="${obj.title?html}"
         title="${obj.title?html}"/>
</a>
<#elseif (obj.mediaType == 1) && obj.realFilename?ends_with(".png")>
<#include "viewImages.ftl"/>
<#elseif obj.mediaType == 10>
<#assign width=480/>
<#assign height=24/>
<#-- audio format -->
<audio width="${width?c}" height="${height?c}"
       src="${mediaFileLink?html}"
       controls preload="auto" id="avPlayer">
    Your browser does not support the audio element.
</audio>
<#elseif obj.mediaType == 20>
<#-- video format-->
<video width="${width?c}" height="${height?c}"
       src="${mediaFileLink?html}"
        <#if thumnailFileLink?has_content>
       poster="${thumnailFileLink?html}"
        </#if>
       controls preload="true" id="avPlayer">
    Your browser does not support the video element.
</video>
<#elseif obj.realFilename?ends_with(".swf")>
<#-- flash format-->
<embed width="${width?c}" height="${height?c}"
       src="${mediaFileLink?html}"
       allowfullscreen="true" allowscripaccess="always" scale="showall"/>
</#if>

<#if (obj.mediaType == 10) || (obj.mediaType == 20)>
<script type="text/javascript">
    <!--
    var deviceAgent = navigator.userAgent.toLowerCase();
    var iDevice = deviceAgent.match(/(iphone|ipod|ipad)/);
    <#if obj.mediaType ==10>
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
        duration: ${(obj.duration/1000)?c},
        width: width,
        height: height,
        provider: 'sound'
    });
    <#elseif obj.mediaType == 20>
    jwplayer('avPlayer').setup({
        flashplayer: '${JWPLAYER}',
        bufferlength: 5,
        duration: ${(obj.duration/1000)?c},
        provider: 'video'
    });
    </#if>
    //-->
</script>
</#if>

</body>
</html>
