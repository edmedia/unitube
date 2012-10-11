<#--
    DO NOT REFORMAT CODE!!!
    IT WILL MESS UP EMBED CODE
-->
<div class="stage">
<#-- only for image media file -->
<#if obj?? && obj.mediaType == 5>

    <#include "imageViewerHelper.ftl"/>

    <div>
        <strong>${obj.title?html}<#if annotation??> - ${annotation.annotName?html}</#if></strong>
    </div>

    <p> &nbsp; </p>

    <#assign embedCode><applet code="ImageViewer.class" archive="${imageViewer}" width="800" height="600"><param name="params" value="${playerURL}"/><param name="java_arguments" value="-Xmx256m"></applet></#assign>

    ${embedCode}

    <p> &nbsp;</p>

    <div id="left">
        <form action="" name="linkForm1">
            <#if viewURL?has_content>
                <p>
                    <span class="title">URL:</span>
                    <input type="text"
                           value="${viewURL?html}"
                           readonly="readonly"
                           size="50"
                           class="linkURL"
                            />
                </p>
            </#if>
        </form>
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
            |
            <a href="${baseUrl}/imageViewerGuide.do">ImageViewer help</a>
        </p>
    </div>
    <div id="right">
        <form action="" name="linkForm2">
            <#if embedCode?has_content>
                <p>
                    <span class="title">Embed:</span>
                    <input type="text"
                           value="${embedCode?html}"
                           readonly="readonly"
                           size="50"
                           class="linkURL"
                            />
                    <span title="To embed a media file, copy this code from the &quot;Embed&quot; box and paste it into your web page to embed it">?</span>
                </p>
            </#if>
        </form>
        <#-- display annotations list -->
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
    </div>

<script type="text/javascript">
    <!--
    $(function() {
        $('input.linkURL').click(function() {
            $(this).focus();
            $(this).select();
        });
    });
    //-->
</script>
    <#else>
        <div class="info">
        <@spring.message "image.not.found"/>
        </div>
</#if>
</div>
