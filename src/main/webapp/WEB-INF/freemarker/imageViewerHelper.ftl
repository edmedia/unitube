<#-- links for ImageViewer -->
<#assign imageViewer>${context_url}/ImageViewer/iva.jar</#assign>

<#assign mediaFileBase><#if !uploadLocation.absolutePath>${context_url}/</#if>${uploadLocation.baseLink}${obj.user.accessCode}/${obj.randomCode}</#assign>
<#assign mediaFileLink>${mediaFileBase}/${obj.realFilename}</#assign>

<#assign imageFileLink>${mediaFileLink}</#assign>
<#if (obj.width &gt; 1024) || (obj.height &gt; 1024)>
<#assign largeImageFileLink>${mediaFileBase}/image-l.jpg</#assign>
</#if>
<#if (obj.width &gt; 2048) || (obj.height &gt; 2048)>
<#assign veryLargeImageFileLink>${mediaFileBase}/image-v.jpg</#assign>
</#if>
<#if (obj.width &gt; 4096) || (obj.height &gt; 4096)>
<#assign extraLargeImageFileLink>${mediaFileBase}/image-e.jpg</#assign>
<#assign imageFileLink>${extraLargeImageFileLink}</#assign>
</#if>

<#if annotation?? && annotation.annotFileUserName??>
<#assign annotFileBase><#if !uploadLocation.absolutePath>${context_url}/</#if>${uploadLocation.baseLink}${annotation.author.accessCode}/${annotation.randomCode}</#assign>
<#assign xml>${annotFileBase}/${annotation.annotFileUserName}</#assign>
</#if>

<#assign viewURL>${context_url}/imageViewer.do?<#if annotation??>a=${annotation.accessCode}<#else>i=${obj.accessCode}</#if></#assign>
<#assign playerURL>${context_url}/iva.do?<#if annotation??>a=${annotation.accessCode}<#else>i=${obj.accessCode}</#if></#assign>

<#assign postURL>${context_url}/annotSave.do</#assign>
<#-- urls on media.otago.ac.nz -->
<#if uploadLocation.baseLink?starts_with('http://media.otago.ac.nz')>
<#assign imageViewer>http://media.otago.ac.nz/ImageViewer/iva.jar</#assign>
<#assign playerURL>http://media.otago.ac.nz/iva.do?<#if annotation??>a=${annotation.accessCode}<#else>i=${obj.accessCode}</#if></#assign>
<#assign postURL>http://media.otago.ac.nz/annotSave.do</#assign>
</#if>
