<#-- all media files are under this folder -->
<#assign mediaFileBase><#if !uploadLocation.absolutePath>${context_url}/</#if>${uploadLocation.baseLink}${obj.user.accessCode}/${obj.locationCode}</#assign>

<#-- links to flowplayer -->
<#assign installerURL>${context_url}/FlowPlayer/expressInstall.swf</#assign>
<#assign flowPlayer>${context_url}/FlowPlayer/flowplayer-3.1.swf</#assign>
<#assign contentPlugin>${context_url}/FlowPlayer/flowplayer.content-3.1.swf</#assign>
<#assign audioPlugin>${context_url}/FlowPlayer/flowplayer.audio-3.1.swf</#assign>
<#assign viewURL>${context_url}/view?m=${obj.accessCode}</#assign>
<#assign playerURL>${context_url}/player?m=${obj.accessCode}</#assign>

<#-- flowplayer on media.otago.ac.nz -->
<#if uploadLocation.baseLink?starts_with('http://media.otago.ac.nz')>
<#assign installerURL>http://media.otago.ac.nz/FlowPlayer/expressInstall.swf</#assign>
<#assign flowPlayer>http://media.otago.ac.nz/FlowPlayer/flowplayer-3.1.swf</#assign>
<#assign contentPlugin>http://media.otago.ac.nz/FlowPlayer/flowplayer.content-3.1.swf</#assign>
<#assign audioPlugin>http://media.otago.ac.nz/FlowPlayer/flowplayer.audio-3.1.swf</#assign>
<#assign playerURL>http://media.otago.ac.nz/player?m=${obj.accessCode}</#assign>
</#if>

<#assign originalFileLink>${mediaFileBase}/${obj.uploadFileUserName}</#assign>
<#assign originalFileLinkRelative>/${obj.user.accessCode}/${obj.locationCode}/${obj.uploadFileUserName}</#assign>
<#if obj.realFilename?has_content>
<#assign mediaFileLink>${mediaFileBase}/${obj.realFilename}</#assign>
<#assign mediaFileLinkRelative>/${obj.user.accessCode}/${obj.locationCode}/${obj.realFilename}</#assign>
</#if>
<#if obj.otherFormatFilename?has_content>
<#assign otherFormatFileLink>${mediaFileBase}/${obj.otherFormatFilename}</#assign>
<#assign otherFormatFileLinkRelative>/${obj.user.accessCode}/${obj.locationCode}/${obj.otherFormatFilename}</#assign>
</#if>
<#if obj.thumbnail?has_content>
<#assign thumnailFileLink>${mediaFileBase}/${obj.thumbnail}</#assign>
</#if>

<#-- for image files -->
<#if obj.mediaType == 5>
<#assign imageFileLink>${mediaFileLink}</#assign>
<#-- if image is wider or higher than 800, display medium size by default -->
<#if (obj.width &gt; 800) || (obj.height &gt; 800)>
<#assign mediaFileLink>${mediaFileBase}/image-m.jpg</#assign>
<#assign mediaFileLinkRelative>/${obj.user.accessCode}/${obj.locationCode}/image-m.jpg</#assign>
</#if>
<#if (obj.width &gt; 1024) || (obj.height &gt; 1024)>
<#assign largeImageFileLink>${mediaFileBase}/image-l.jpg</#assign>
<#assign imageFileLink>${largeImageFileLink}</#assign>
<#assign largeImageFileLinkRelative>/${obj.user.accessCode}/${obj.locationCode}/image-l.jpg</#assign>
</#if>
<#if (obj.width &gt; 2048) || (obj.height &gt; 2048)>
<#assign veryLargeImageFileLink>${mediaFileBase}/image-v.jpg</#assign>
<#assign veryLargeImageFileLinkRelative>/${obj.user.accessCode}/${obj.locationCode}/image-v.jpg</#assign>
</#if>
<#if (obj.width &gt; 4096) || (obj.height &gt; 4096)>
<#assign extraLargeImageFileLink>${mediaFileBase}/image-e.jpg</#assign>
<#assign extraLargeImageFileLinkRelative>/${obj.user.accessCode}/${obj.locationCode}/image-e.jpg</#assign>
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

