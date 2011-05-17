<#-- all media files are under this folder -->
<#assign embedURL>${context_url}/embed.do?m=${obj.accessCode}</#assign>
<#assign fileURL>${context_url}/file.do?m=${obj.accessCode}</#assign>
<#assign JWPLAYER>${context_url}/jwplayer/player.swf</#assign>
<#assign JWPLAYER_SKIN_GLOW>${context_url}/jwplayer/glow.zip</#assign>
<#assign JWPLAYER_SKIN_SIMPLE>${context_url}/jwplayer/simple.zip</#assign>
<#assign JWPLAYER_CONFIG>${context_url}/jwPlayerConfig.do?m=${obj.accessCode}</#assign>
<#-- links to flowplayer -->
<#assign installerURL>${context_url}/FlowPlayer/expressInstall.swf</#assign>
<#assign flowPlayer>${context_url}/FlowPlayer/flowplayer-3.1.swf</#assign>
<#assign contentPlugin>${context_url}/FlowPlayer/flowplayer.content-3.1.swf</#assign>
<#assign audioPlugin>${context_url}/FlowPlayer/flowplayer.audio-3.1.swf</#assign>
<#assign viewURL>${context_url}/view?m=${obj.accessCode}</#assign>
<#assign playerURL>${context_url}/player?m=${obj.accessCode}</#assign>
<#assign originalFileLink>${fileURL}&name=${obj.uploadFileUserName}</#assign>
<#assign mediaFileLink>${fileURL}</#assign>
<#-- add realFilename to the end of url for other media file (non-uploadOnly), 
     because it only contains filename for first slide -->
<#if (obj.mediaType == 1) && !obj.uploadOnly>
<#assign mediaFileLink>${fileURL}&name=${obj.realFilename}</#assign>
</#if>
<#if obj.otherFormatFilename?has_content>
<#assign otherFormatFileLink>${fileURL}&name=${obj.otherFormatFilename}</#assign>
</#if>
<#if obj.thumbnail?has_content>
<#assign thumnailFileLink>${fileURL}&name=${obj.thumbnail}</#assign>
</#if>
<#-- for image files -->
<#if obj.mediaType == 5>
<#assign imageFileLink>${mediaFileLink}</#assign>
<#-- if image is wider or higher than 800, display medium size by default -->
<#if (obj.width &gt; 800) || (obj.height &gt; 800)>
<#assign mediaFileLink>${fileURL}&name=image-m.jpg</#assign>
</#if>
<#if (obj.width &gt; 1024) || (obj.height &gt; 1024)>
<#assign largeImageFileLink>${fileURL}&name=image-l.jpg</#assign>
<#assign imageFileLink>${largeImageFileLink}</#assign>
</#if>
<#if (obj.width &gt; 2048) || (obj.height &gt; 2048)>
<#assign veryLargeImageFileLink>${fileURL}&name=image-v.jpg</#assign>
</#if>
<#if (obj.width &gt; 4096) || (obj.height &gt; 4096)>
<#assign extraLargeImageFileLink>${fileURL}&name=image-e.jpg</#assign>
</#if>
</#if>
<#-- size -->
<#assign controllerHeight=24 />
<#-- maximum width to display media file -->
<#assign MAX_WIDTH=800 />
<#-- default width -->
<#assign width=320/>
<#if obj.width &gt; 0>
<#assign width=obj.width/>
</#if>
<#-- default height -->
<#assign height=240/>
<#if obj.height &gt; 0>
<#assign height=obj.height/>
</#if>
<#if width &gt; MAX_WIDTH>
<#assign height=(height*MAX_WIDTH/width)?round/>
<#assign width=MAX_WIDTH/>
</#if>