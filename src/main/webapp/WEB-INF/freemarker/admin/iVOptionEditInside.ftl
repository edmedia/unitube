<@spring.bind "iVOption.id" />

<h2><#if spring.status.value??>Edit<#else>Create a new</#if> Image Viewer Option</h2>

<form action="${this_url}" name="iVOptionForm" method="post">
<#if pageNumber??>
<input type="hidden" name="p" value="${pageNumber?c}"/>
</#if>
<#if pageSize??>
<input type="hidden" name="s" value="${pageSize?c}"/>
</#if>
<table summary="">

<!-- id: ID -->
<tr class="hidden">
<th>ID</th>
<td>
<@spring.bind "iVOption.id" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- actualWidth: Actual Width -->
<tr>
<th>Actual Width</th>
<td>
<@spring.bind "iVOption.actualWidth" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- acturalWidthUnit: Actual Width Unit -->
<tr>
<th>Actual Width Unit</th>
<td>
<@spring.bind "iVOption.acturalWidthUnit" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- minZoom: Minimum Zoom -->
<tr>
<th>Minimum Zoom</th>
<td>
<@spring.bind "iVOption.minZoom" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- maxZoom: Maximum Zoom -->
<tr>
<th>Maximum Zoom</th>
<td>
<@spring.bind "iVOption.maxZoom" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- displayMeasureTool: Display Measurement tool and grid? -->
<tr>
<th>Display Measurement tool and grid?</th>
<td>
<@spring.bind "iVOption.displayMeasureTool" />
<input type="radio" name="${spring.status.expression}" value="true"<#if spring.status.value??><#if spring.status.value> checked="checked"</#if></#if> /> Yes
<input type="radio" name="${spring.status.expression}" value="false"<#if spring.status.value??><#if !spring.status.value> checked="checked"</#if></#if> /> No
</td>
<td>

<@displayError/>
</td>
</tr>
<!-- otherCanAnnotate: Other can annotate this image? -->
<tr>
<th>Other can annotate this image?</th>
<td>
<@spring.bind "iVOption.otherCanAnnotate" />
<input type="radio" name="${spring.status.expression}" value="true"<#if spring.status.value??><#if spring.status.value> checked="checked"</#if></#if> /> Yes
<input type="radio" name="${spring.status.expression}" value="false"<#if spring.status.value??><#if !spring.status.value> checked="checked"</#if></#if> /> No
</td>
<td>

<@displayError/>
</td>
</tr>
<!-- whichImageForIV: Which Image for ImageViewer to use? -->
<tr>
<th>Which Image for ImageViewer to use?</th>
<td>
<@spring.bind "iVOption.whichImageForIV" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- media: Media -->
<tr>
<th>Media</th>
<td>
<@spring.bind "iVOption.mediaID" />
<select name="mediaID">
<option value="0">&nbsp;</option>
    <#list mediaList as option>
        <option value="${option.id?c}">${option.meaningfulName}</option>
    </#list>
</select>


</td>
<td>

<@displayError/>
</td>
</tr>
<tr>
<td colspan="3">
<input name="submit" type="submit" value="Save" />
<input name="reset" type="reset" value="Reset" />
</td>
</tr>

</table>
</form>

<div id="bottomNav">
<#--
<a href="${baseUrl}/admin.do">Back to Admin List</a>
-->
<a href="iVOptionList.do?aa=list<#if pageNumber??>&amp;p=${pageNumber?c}</#if><#if pageSize??>&amp;s=${pageSize?c}</#if>">Back to IVOption list</a>
</div>

<script type="text/javascript">
<!--
$(function() {

<@spring.bind "iVOption.media" />
            <#if (spring.status.value.id)??>
    $('[name=mediaID]').val("${spring.status.value.id?c}");
            </#if>

});
//-->
</script>
