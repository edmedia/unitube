

<h2>List all AlbumMedia<#if album??> for ${album.meaningfulName}</#if></h2>

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
<th></th>
</tr>
</thead>
<tbody>
<#list pager.elements as obj>
<tr>
<td><input type="checkbox" name="id" value="${obj.id?c}"/></td>
<td><a href="albumMediaEdit.do?id=${obj.id?c}${pager.parameters?html}">${obj.meaningfulName}</a></td>
<td><a href="#" rel="albumMediaDelete.do?id=${obj.id?c}${pager.parameters?html}" class="delete">delete</a></td>
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
<#if album??>
<a href="albumList.do">Back to album list</a> |
</#if>
<a href="albumMediaEdit.do?aa=new${pager.parameters?html}">Create a new AlbumMedia</a>
<#if pager.elements?size &gt; 0>
| <a href="#" onclick="return deleteRecords();">Delete selected AlbumMedia</a>
</#if>
</div>

