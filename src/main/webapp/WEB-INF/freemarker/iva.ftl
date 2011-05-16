<#-- only for image file -->
<#if obj?? && obj.mediaType == 5>
<#include "imageViewerHelper.ftl"/>
<#if ivOption??>
    <#if ivOption.whichImageForIV??>
        <#assign imageFileLink>${fileURL}&name=${ivOption.whichImageForIV}</#assign>
    </#if>
    <#assign ivaMeasure=ivOption.displayMeasureTool />
    <#assign ivaWidth=ivOption.actualWidth />
    <#assign ivaUnits>${ivOption.acturalWidthUnit}</#assign>
    <#assign ivaMinZoom=ivOption.minZoom />
    <#assign ivaMaxZoom=ivOption.maxZoom/>
</#if>
{
    "image": "${imageFileLink}",
<#if xml??>
    "annotations": "${xml}",
</#if>
    "measure": <#if ivaMeasure??>${ivaMeasure?string}<#else>true</#if>,
    <#if ivaWidth?? && ivaWidth &gt; 0>"width": ${ivaWidth?c},</#if>
    <#if ivaUnits??>"units": "${ivaUnits}",</#if>
    <#if ivaMinZoom?? && ivaMinZoom &gt; 0>"minZoom": ${ivaMinZoom?c},</#if>
    <#if ivaMaxZoom?? && ivaMaxZoom &gt; 0>"maxZoom": ${ivaMaxZoom?c},</#if>
    "dummy": 0
}
</#if>