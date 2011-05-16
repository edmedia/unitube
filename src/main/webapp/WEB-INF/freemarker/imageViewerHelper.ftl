<#-- links for ImageViewer -->
<#assign fileURL>${context_url}/file.do?m=${obj.accessCode}</#assign>

<#assign mediaFileLink>${fileURL}&name=${obj.realFilename}</#assign>

<#assign imageFileLink>${mediaFileLink}</#assign>
<#if (obj.width &gt; 1024) || (obj.height &gt; 1024)>
<#assign largeImageFileLink>${fileURL}&name=image-l.jpg</#assign>
</#if>
<#if (obj.width &gt; 2048) || (obj.height &gt; 2048)>
<#assign veryLargeImageFileLink>${fileURL}&name=image-v.jpg</#assign>
</#if>
<#if (obj.width &gt; 4096) || (obj.height &gt; 4096)>
<#assign extraLargeImageFileLink>${fileURL}&name=image-e.jpg</#assign>
<#assign imageFileLink>${extraLargeImageFileLink}</#assign>
</#if>

<#if annotation?? && annotation.annotFileUserName??>
<#assign xml>${context_url}/file.do?a=${annotation.accessCode}</#assign>
</#if>

<#assign viewURL>${context_url}/imageViewer.do?<#if annotation??>a=${annotation.accessCode}<#else>i=${obj.accessCode}</#if></#assign>
<#assign imageViewer>${context_url}/ImageViewer/iva.jar</#assign>
<#assign playerURL>${context_url}/iva.do?<#if annotation??>a=${annotation.accessCode}<#else>i=${obj.accessCode}</#if></#assign>
<#assign postURL>${context_url}/annotSave.do</#assign>
