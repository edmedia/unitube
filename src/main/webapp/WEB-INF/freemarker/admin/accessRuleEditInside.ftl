<@spring.bind "accessRule.id" />

<h2><#if spring.status.value??>Edit<#else>Create a new</#if> Access Rule</h2>

<form action="${this_url}" name="accessRuleForm" method="post">
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
<@spring.bind "accessRule.id" />
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
<@spring.bind "accessRule.mediaID" />
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
<!-- user: User -->
<tr>
<th>User</th>
<td>
<@spring.bind "accessRule.userID" />
<select name="userID">
<option value="0">&nbsp;</option>
    <#list userList as option>
        <option value="${option.id?c}">${option.meaningfulName}</option>
    </#list>
</select>


</td>
<td>

<@displayError/>
</td>
</tr>
<!-- groupName: Group Name -->
<tr>
<th>Group Name</th>
<td>
<@spring.bind "accessRule.groupName" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- groupUsersLink: Group Users Link -->
<tr>
<th>Group Users Link</th>
<td>
<@spring.bind "accessRule.groupUsersLink" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- userInput: User Input -->
<tr>
<th>User Input</th>
<td>
<@spring.bind "accessRule.userInput" />
<@displayInputBox/>

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
<a href="accessRuleList.do?aa=list<#if pageNumber??>&amp;p=${pageNumber?c}</#if><#if pageSize??>&amp;s=${pageSize?c}</#if>">Back to AccessRule list</a>
</div>

<script type="text/javascript">
<!--
$(function() {

<@spring.bind "accessRule.media" />
            <#if (spring.status.value.id)??>
    $('[name=mediaID]').val("${spring.status.value.id?c}");
            </#if>


<@spring.bind "accessRule.user" />
            <#if (spring.status.value.id)??>
    $('[name=userID]').val("${spring.status.value.id?c}");
            </#if>

});
//-->
</script>
