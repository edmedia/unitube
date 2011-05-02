<@spring.bind "user.id" />

<h2><#if spring.status.value??>Edit<#else>Create a new</#if> UniTube User</h2>

<form action="${this_url}" name="userForm" method="post">
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
<@spring.bind "user.id" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- userName: User Name -->
<tr>
<th>User Name</th>
<td>
<@spring.bind "user.userName" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- passWord: Pass Word -->
<tr>
<th>Pass Word</th>
<td>
<@spring.bind "user.passWord" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- firstName: First Name -->
<tr>
<th>First Name</th>
<td>
<@spring.bind "user.firstName" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- lastName: Last Name -->
<tr>
<th>Last Name</th>
<td>
<@spring.bind "user.lastName" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- email: Email Address -->
<tr>
<th>Email Address</th>
<td>
<@spring.bind "user.email" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- mobile: Mobile Number -->
<tr>
<th>Mobile Number</th>
<td>
<@spring.bind "user.mobile" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- isGuest: Is this user a guest? -->
<tr>
<th>Is this user a guest?</th>
<td>
<@spring.bind "user.isGuest" />
<input type="radio" name="${spring.status.expression}" value="true"<#if spring.status.value??><#if spring.status.value> checked="checked"</#if></#if> /> Yes
<input type="radio" name="${spring.status.expression}" value="false"<#if spring.status.value??><#if !spring.status.value> checked="checked"</#if></#if> /> No
</td>
<td>

<@displayError/>
</td>
</tr>
<!-- disabled: Disabled access? -->
<tr>
<th>Disabled access?</th>
<td>
<@spring.bind "user.disabled" />
<input type="radio" name="${spring.status.expression}" value="true"<#if spring.status.value??><#if spring.status.value> checked="checked"</#if></#if> /> Yes
<input type="radio" name="${spring.status.expression}" value="false"<#if spring.status.value??><#if !spring.status.value> checked="checked"</#if></#if> /> No
</td>
<td>

<@displayError/>
</td>
</tr>
<!-- medias: All Medias -->
<!-- userAlbums: All UserAlbums -->
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
<a href="userList.do?aa=list<#if pageNumber??>&amp;p=${pageNumber?c}</#if><#if pageSize??>&amp;s=${pageSize?c}</#if>">Back to User list</a>
</div>

<script type="text/javascript">
<!--
$(function() {
});
//-->
</script>
