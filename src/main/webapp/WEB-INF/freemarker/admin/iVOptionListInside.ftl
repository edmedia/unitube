

<h2>List all Image Viewer Option</h2>

<#if pager.elements?size = 0>
<p>No records.</p>
<#else>

<form action="" name="listForm">
<#list pager.paramList as param>
<input type="hidden" name="${param.name?html}" value="${param.value?html}" />
</#list>
<table summary="">
<thead>
<tr>
<th>
<input type="checkbox" name="all"/>
#
</th>
<th>name</th>
<th><a href="${orderByLink("acturalWidthUnit")?html}">Actual Width Unit</a></th>
<th><a href="${orderByLink("minZoom")?html}">Minimum Zoom</a></th>
<th><a href="${orderByLink("maxZoom")?html}">Maximum Zoom</a></th>
<th><a href="${orderByLink("displayMeasureTool")?html}">Display Measurement tool and grid?</a></th>
<th><a href="${orderByLink("otherCanAnnotate")?html}">Other can annotate this image?</a></th>
<th><a href="${orderByLink("whichImageForIV")?html}">Which Image for ImageViewer to use?</a></th>
<th></th>
</tr>
</thead>
<tbody>
<#list pager.elements as obj>
<tr>
<td><input type="checkbox" name="id" value="${obj.id?c}"/></td>
<td><a href="iVOptionEdit.do?id=${obj.id?c}${pager.parameters?html}">${obj.meaningfulName}</a></td>
<td>
${obj.acturalWidthUnit!?string}
</td>
<td>
${obj.minZoom!?string}
</td>
<td>
${obj.maxZoom!?string}
</td>
<td>
${obj.displayMeasureTool!?string}
</td>
<td>
${obj.otherCanAnnotate!?string}
</td>
<td>
${obj.whichImageForIV!?string}
</td>
<td><a href="#" rel="iVOptionDelete.do?id=${obj.id?c}${pager.parameters?html}" class="delete">delete</a></td>
</tr>
</#list>
</tbody>
</table>
</form>

<#if pager.lastPageNumber &gt; 1>
<div id="pager">
<form method="get" action="${this_url}" name="pagerForm">
<#list pager.paramListWithoutPageNumber as param>
<input type="hidden" name="${param.name?html}" value="${param.value?html}" />
</#list>
<a href="${pager.firstLink?html}">first page</a> |
<a href="${pager.previousLink?html}">previous page</a> |
<a href="${pager.nextLink?html}">next page</a> |
<a href="${pager.lastLink?html}">last page</a>
${pager.pageNumber}/${pager.lastPageNumber}
<input type="text" name="p" size="2" maxlength="${pager.lastPageNumber?length}" value="${pager.pageNumber?c}" />
</form>
</div>
</#if>

</#if>

<div id="bottomNav">
<!--
<a href="${baseUrl}/admin.do">Back to Admin List</a>
-->
<a href="iVOptionEdit.do?aa=new${pager.parameters?html}">Create a new IVOption</a>
<#if pager.elements?size &gt; 0>
| <a href="#" onclick="return deleteRecords();">Delete selected IVOption</a>
</#if>
</div>

