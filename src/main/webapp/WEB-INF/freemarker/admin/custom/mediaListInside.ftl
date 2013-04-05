

<h2>List all Media<#if user??> for ${user.meaningfulName}</#if></h2>

<#if pager.elements?size = 0>
<p>No records.</p>
<#else>

<form action="" name="listForm">
<#list pager.paramList as param>
<input type="hidden" name="${param.name?html}" value="${param.value?html}" />
</#list>
<table summary="" width="100%">
<thead>
<tr>
<th>
<input type="checkbox" name="all"/>
#
</th>
<th><a href="${orderByLink("title")?html}">Title</a></th>
<th><a href="${orderByLink("mediaType")?html}">Media Type</a></th>
<th><a href="${orderByLink("width")?html}">Width</a></th>
<th><a href="${orderByLink("height")?html}">Height</a></th>
<th>Access Type</th>
<th>Link</th>
<th></th>
</tr>
</thead>
<tbody>
<#list pager.elements as entity>
<tr>
<td><input type="checkbox" name="id" value="${entity.id?c}"/></td>
<td><a href="mediaEdit.do?id=${entity.id?c}<#if pageNumber??>&amp;p=${pager.pageNumber?c}</#if><#if pageSize??>&amp;s=${pager.pageSize?c}</#if><#if user??>&amp;userID=${user.id?c}</#if>">${entity.meaningfulName}</a></td>
<td>
    <#if entity.mediaType == 1>
    Other Meida
    <#elseif entity.mediaType == 5>
    Image
    <#elseif entity.mediaType == 10>
    Audio
    <#elseif entity.mediaType == 20>
    Video
    </#if>
</td>
<td>
${entity.width!?string}
</td>
<td>
${entity.height!?string}
</td>
<td>
<#if entity.accessType ==10>
<span title="<@spring.message "media.access.hidden"/>">hidden</span>
</#if>
<#if entity.accessType ==20>
<span title="<@spring.message "media.access.private"/>">private</span>
</#if>
</td>
<td>
<a href="${baseUrl}/view?m=${entity.accessCode}">view</a>
</td>
<td><a href="adminMediaDelete.do?id=${entity.id?c}<#if pageNumber??>&amp;p=${pager.pageNumber?c}</#if><#if pageSize??>&amp;s=${pager.pageSize?c}</#if><#if user??>&amp;userID=${user.id?c}</#if>" onclick="return deleteConfirm();">delete</a></td>
</tr>
</#list>
</tbody>
</table>
</form>

<#if pager.lastPageNumber &gt; 1>
<div id="pager">
<form method="get" action="${this_url}" onsubmit="return checkInput(this);">
<#if pageSize??>
<input type="hidden" name="s" value="${pager.pageSize?c}" />
</#if>
<#if user??>
<input type="hidden" name="id" value="${user.id?c}" />
</#if>
<a href="${this_url}?<#if pageSize??>s=${pager.pageSize?c}&amp;</#if>p=0<#if user??>&amp;userID=${user.id?c}</#if>">first page</a>
<a href="${this_url}?<#if pageSize??>s=${pager.pageSize?c}&amp;</#if>p=${pager.previousPageNumber?c}<#if user??>&amp;userID=${user.id?c}</#if>">previous page</a>
<a href="${this_url}?<#if pageSize??>s=${pager.pageSize?c}&amp;</#if>p=${pager.nextPageNumber?c}<#if user??>&amp;userID=${user.id?c}</#if>">next page</a>
<a href="${this_url}?<#if pageSize??>s=${pager.pageSize?c}&amp;</#if>p=${pager.lastPageNumber?c}<#if user??>&amp;userID=${user.id?c}</#if>">last page</a>
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
<#if user??>
<a href="${baseUrl}/myTube/admin.do">Back to user list</a>
</#if>

<#if pager.elements?size &gt; 0>
| <a href="#" onclick="return deleteRecords();">Delete selected Media</a>
</#if>
</div>

