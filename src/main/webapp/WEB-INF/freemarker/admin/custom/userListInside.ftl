

<h2>List all UniTube User</h2>

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
<th><a href="${orderByLink("userName")?html}">Username</a></th>
<th><a href="${orderByLink("firstName")?html}">First Name</a></th>
<th><a href="${orderByLink("lastName")?html}">Last Name</a></th>
<th><a href="${orderByLink("mobile")?html}">Mobile Number</a></th>
<th><a href="${orderByLink("loginTimes")?html}">Login Times</a></th>
<th><a href="${orderByLink("lastLoginTime")?html}">Last Login Time</a></th>
<th><a href="${orderByLink("lastLoginIP")?html}">Last Login IP</a></th>
<th><a href="${orderByLink("isGuest")?html}">Is this user a guest?</a></th>
<th>All Medias</th>
<th></th>
</tr>
</thead>
<tbody>
<#list pager.elements as obj>
<tr>
<td><input type="checkbox" name="id" value="${obj.id?c}"/></td>
<td><a href="userEdit.do?id=${obj.id?c}${pager.parameters?html}">${obj.meaningfulName}</a></td>
<td>
${obj.firstName!?string}
</td>
<td>
${obj.lastName!?string}
</td>
<td>
${obj.mobile!?string}
</td>
<td>
${obj.loginTimes!?string}
</td>
<td>
${obj.lastLoginTime!?string}
</td>
<td>
${obj.lastLoginIP!?string}
</td>
<td>
${obj.isGuest!?string}
</td>
<td>
<a href="mediaList.do?userID=${obj.id?c}">all medias (${obj.medias?size})</a>
</td>
<td><a href="#" rel="userDelete.do?id=${obj.id?c}${pager.parameters?html}" class="delete">delete</a></td>
</tr>
</#list>
</tbody>
</table>
</form>

<#if pager.lastPageNumber &gt; 1>
<div id="pager">
<form method="get" action="${this_url}" onsubmit="return checkInput(this);">
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
<a href="userEdit.do?aa=new${pager.parameters?html}">Create a new User</a>
<#if pager.elements?size &gt; 0>
| <a href="#" onclick="return deleteRecords();">Delete selected User</a>
</#if>
</div>

