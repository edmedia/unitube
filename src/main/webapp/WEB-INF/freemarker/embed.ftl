<#include "common/head.ftl" />
<#if obj??>
<#include "viewHelper.ftl" />
<#-- image -->
<#if obj.mediaType == 5>
<a href="${imageFileLink}" title="${obj.title?html}">
    <img src="${mediaFileLink}" width="${width?c}" height="${height?c}" alt="${obj.title?html}"
         title="${obj.title?html}"/>
</a>
<#elseif (obj.mediaType == 1) && obj.realFilename?ends_with(".png")>
<#-- images -->
<#include "viewImages.ftl"/>
<#elseif obj.mediaType == 10>
<#-- audio format -->
<#assign width=480/>
<#assign height=24/>
<audio width="100%" height="100%"
       src="${mediaFileLink?html}"
       controls preload="auto" id="avPlayer">
    Your browser does not support the audio element.
</audio>
<#elseif obj.mediaType == 20>
<#-- video format-->
<video width="100%" height="100%"
       src="${mediaFileLink?html}"
        <#if thumnailFileLink?has_content>
       poster="${thumnailFileLink?html}"
        </#if>
       controls preload="true" id="avPlayer">
    Your browser does not support the video element.
</video>
<#elseif obj.realFilename?ends_with(".swf")>
<#-- flash format-->
<embed width="100%" height="100%"
       src="${mediaFileLink?html}"
       allowfullscreen="true" allowscripaccess="always" scale="showall"/>
</#if>
<#if (obj.mediaType == 10) || (obj.mediaType == 20)>
<script type="text/javascript">
    <!--
    var deviceAgent = navigator.userAgent.toLowerCase();
    var iDevice = deviceAgent.match(/(iphone|ipod|ipad)/);
    <#if obj.mediaType ==10>
    if (iDevice)
        jwplayer('avPlayer').setup({
            flashplayer: '${JWPLAYER?html}',
            bufferlength: 5,
            controlbar: {position: 'bottom', idlehide: false},
            <#if obj.duration &gt; 0>
            duration: ${(obj.duration/1000)?c},
            </#if>
            width: $(window).width(),
            height: $(window).height(),
            provider: 'sound'
        });
    else
        jwplayer('avPlayer').setup({
            flashplayer: '${JWPLAYER?html}',
            bufferlength: 5,
            controlbar: {position: 'bottom', idlehide: false},
            <#if obj.duration &gt; 0>
            duration: ${(obj.duration/1000)?c},
            </#if>
            provider: 'sound'
        });
    <#elseif obj.mediaType == 20>
    jwplayer('avPlayer').setup({
        flashplayer: '${JWPLAYER}',
        bufferlength: 5,
        <#if obj.duration &gt; 0>
        duration: ${(obj.duration/1000)?c},
        </#if>
        width: $(window).width(),
        height: $(window).height(),
        provider: 'video'
    });
    </#if>
    //-->
</script>
</#if>
<#else>
<div class="info">
    <#if accessDenied>
    <@spring.message "media.access.deny"/>
    <#else>
    <@spring.message "media.not.found"/>
    </#if>
</div>
</#if>
</body>
</html>
