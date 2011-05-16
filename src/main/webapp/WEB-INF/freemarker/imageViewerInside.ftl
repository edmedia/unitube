<div class="stage">
    <#-- only for image media file -->
    <#if obj?? && obj.mediaType == 5>

    <#include "imageViewerHelper.ftl"/>

    <div>
        <strong>${obj.title?html}<#if annotation??> - ${annotation.annotName?html}</#if></strong>
    </div>

    <#assign embedCode><applet code="ImageViewer.class" archive="${imageViewer}" width="800" height="600"><param name="params" value="${playerURL}"/><param name="java_arguments" value="-Xmx256m"></applet></#assign>

    ${embedCode}

    <#if (annotation?? && (obj.annotations?size &gt; 1)) || (obj.annotations?size &gt; 0)>
    <p><strong>Annotations:</strong>
        <#assign alreadDoneFirstAnnotation = false/>
        <#list obj.annotations as a>
        <#if !annotation?? || annotation.id != a.id>
        <#if alreadDoneFirstAnnotation>
        |
        </#if>
        <a href="${baseUrl}/imageViewer.do?a=${a.accessCode}">${a.annotName?html}</a> by <a
                href="">${a.author.firstName} ${a.author.lastName}</a>
        <#assign alreadDoneFirstAnnotation = true/>
         </#if>
        </#list>
    </p>
    </#if>

    <p>
        <a href="${baseUrl}/view?m=${obj.accessCode}">View Image</a>
        <#if isOwner?has_content || !ivOption?? || ivOption.otherCanAnnotate>
        |
        <a href="${baseUrl}/myTube/annotAuthor.do?i=${obj.id?c}">Add New Annotation</a>
        <#if annotation??>
        |
        <a href="${baseUrl}/myTube/annotAuthor.do?a=${annotation.id?c}">Change Annotation</a>
        </#if>
        </#if>
    </p>

    <form action="" name="linkForm">
        <#if viewURL?has_content>
        <p class="vid">
            <strong>URL:</strong>
            <input name="media_link" type="text"
                   value="${viewURL?html}"
                   onclick="this.focus(); this.select();"
                   size="40"
                   readonly="readonly"
                    />
        </p>
        </#if>
        <#if embedCode?has_content>
        <p class="vid">
            <strong>Embed:</strong>
            <input name="embed_code" type="text"
                   value="${embedCode?html}"
                   onclick="this.focus(); this.select();"
                   size="40"
                   readonly="readonly"
                    />
            <span title="To embed a media file, copy this code from the &quot;Embed&quot; box and paste it into your web page to embed it">?</span>
        </p>
        </#if>
    </form>

    <#else>
    <div class="info">
        <@spring.message "image.not.found"/>
    </div>
    </#if>
</div>
