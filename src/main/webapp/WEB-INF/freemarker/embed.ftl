<#include "common/head.ftl" />
<style type="text/css" media="screen">
    html, body {
        height: 100%;
        background-image: none;
    }
    body {
        margin: 0;
        padding: 0;
        overflow: hidden;
    }
</style>
<#if obj?? && (obj.status == 2) && !obj.uploadOnly && obj.realFilename?has_content && fileExists>
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
        <#elseif (obj.mediaType == 10) || (obj.mediaType == 20)>
        <div id="avPlayer"></div>
        <#elseif obj.realFilename?ends_with(".swf")>
        <#-- flash format-->
        <style type="text/css" media="screen">
            html, body {
                background-color: black;
            }
            #flashContent {
                width: 100%;
                height: 100%;
            }
        </style>
        <div id="flashContent">
            <object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" width="100%" height="100%">
                <param name="movie" value="${mediaFileLink?html}"/>
                <!--[if !IE]>-->
                <object type="application/x-shockwave-flash" data="${mediaFileLink?html}" width="100%" height="100%">
                    <!--<![endif]-->
                    <a href="http://www.adobe.com/go/getflash">
                        <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif"
                             alt="Get Adobe Flash player"/>
                    </a>
                    <!--[if !IE]>-->
                </object>
                <!--<![endif]-->
            </object>
        </div>
    </#if>
    <#if (obj.mediaType == 10) || (obj.mediaType == 20)>
        <#include "viewAV.ftl"/>
    </#if>
    <#else>
    <div class="info">
        <#if accessDenied>
    <@spring.message "media.access.deny"/>
    <#elseif !fileExists>
        <@spring.message "media.file.not.found"/>
            <#elseif obj??>
                <#if (obj.status == 0) || (obj.status == 1)>
                <@spring.message "media.processing"/>
                    <#elseif obj.status == 9>
                    <@spring.message "media.unrecognized"/>
                    <#elseif obj.uploadOnly>
                    <@spring.message "media.uploadOnly"/>
                </#if>
            <#else>
            <@spring.message "media.record.not.found"/>
        </#if>
    </div>
</#if>
</body>
</html>
