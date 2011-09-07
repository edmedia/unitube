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
        <audio width="100%" height="${height?c}"
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
        <style type="text/css" media="screen">
            html, body {
                height: 100%;
                background-color: black;
            }

            body {
                margin: 0;
                padding: 0;
                overflow: hidden;
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
                        //duration: ${(obj.duration/1000)?c},
                    </#if>
                    height: 60,
                    provider: 'sound'
                });
            else
                jwplayer('avPlayer').setup({
                    flashplayer: '${JWPLAYER?html}',
                    bufferlength: 5,
                    controlbar: {position: 'bottom', idlehide: false},
                    <#if obj.duration &gt; 0>
                        //duration: ${(obj.duration/1000)?c},
                    </#if>
                    provider: 'sound'
                });
                <#elseif obj.mediaType == 20>
                if (iDevice)
                    jwplayer('avPlayer').setup({
                        bufferlength: 5,
                        <#if obj.duration &gt; 0>
                            //duration: ${(obj.duration/1000)?c},
                        </#if>
                        width: $(window).width(),
                        height: $(window).height(),
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
                        width: $(window).width(),
                        height: $(window).height(),
                        provider: 'video',
                        modes: [
                            {type: 'flash', src: '${JWPLAYER}'},
                            {type: 'download'}
                        ]
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
