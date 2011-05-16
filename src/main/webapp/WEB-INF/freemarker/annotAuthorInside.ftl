<div class="stage">
    <#-- only for image media file -->
    <#if obj?? && obj.mediaType == 5>

    <#include "imageViewerHelper.ftl"/>

    <div>
        <strong>${obj.title?html}</strong>
    </div>

    <applet code="ImageViewer.class" archive="${imageViewer}" width="800" height="600">
        <param name="params" value="${playerURL}"/>
        <param name="author" value="yes"/>
        <param name="postUrl" value="${postURL}"/>
        <param name="imageId" value="${obj.accessCode}"/>
        <param name="userId" value="${user.accessCode}"/>
        <param name="java_arguments" value="-Xmx256m">
    </applet>

    <p>
        <a href="${baseUrl}/view?m=${obj.accessCode}">View image</a>
        |
        <a href="${baseUrl}/imageViewer.do?i=${obj.accessCode}">View image in ImageViewer</a>
        <#if annotation??>
        |
        <a href="${baseUrl}/imageViewer.do?a=${annotation.accessCode}">View image in ImageViewer with Annotation</a>
        </#if>
    </p>

    </#if>
</div>
