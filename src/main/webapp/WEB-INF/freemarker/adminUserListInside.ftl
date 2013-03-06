<#include "/myOptions.ftl"/>

<h2>User search result</h2>

<form action="" name="searchForm">
    <input type="text" name="n" value="${n!?html}"/>
    <input type="submit" value="Search User"/>
</form>

<#if users?has_content>

<form action="" name="listForm">
<table summary="">
<thead>
<tr>
<th>
<input type="checkbox" name="all"/>
#
</th>
<th>Username</th>
<th>First Name</th>
<th>Last Name</th>
<th>Mobile Number</th>
<th>Login Times</th>
<th>Last Login Time</th>
<th>Last Login IP</th>
<th>Is this user a guest?</th>
<th>All Medias</th>
<th></th>
</tr>
</thead>
<tbody>
<#list users as obj>
<tr>
<td><input type="checkbox" name="id" value="${obj.id?c}"/></td>
<td><a href="userEdit.do?id=${obj.id?c}">${obj.meaningfulName}</a></td>
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
<td><a href="#" rel="userDelete.do?id=${obj.id?c}" class="delete">delete</a></td>
</tr>
</#list>
</tbody>
</table>
</form>

<div id="bottomNav">
    <a href="${baseUrl}/myTube/admin.do">Back to user list</a>
</div>

<#else>
No user found.
</#if>