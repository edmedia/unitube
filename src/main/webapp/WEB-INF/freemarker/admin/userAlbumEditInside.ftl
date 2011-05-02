<@spring.bind "userAlbum.id" />

<h2><#if spring.status.value??>Edit<#else>Create a new</#if> UserAlbum</h2>

<form action="${this_url}" name="userAlbumForm" method="post">
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
<@spring.bind "userAlbum.id" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- user: User -->
<tr>
<th>User</th>
<td>
<@spring.bind "userAlbum.userID" />
    <#if user??>
${user.meaningfulName}
<input type="hidden" name="${spring.status.expression}" value="${user.id?c}" />
    </#if>


</td>
<td>

<@displayError/>
</td>
</tr>
<!-- album: Album -->
<tr>
<th>Album</th>
<td>
<@spring.bind "userAlbum.albumID" />
<select name="albumID">
<option value="0">&nbsp;</option>
    <#list albumList as option>
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
<a href="userAlbumList.do?aa=list<#if pageNumber??>&amp;p=${pageNumber?c}</#if><#if pageSize??>&amp;s=${pageSize?c}</#if>&amp;userID=${user.id?c}">Back to UserAlbum list</a>
</div>

<script type="text/javascript">
<!--
$(function() {

<@spring.bind "userAlbum.user" />
            <#if (spring.status.value.id)??>
    $('[name=userID]').val("${spring.status.value.id?c}");
            </#if>


<@spring.bind "userAlbum.album" />
            <#if (spring.status.value.id)??>
    $('[name=albumID]').val("${spring.status.value.id?c}");
            </#if>

});
//-->
</script>
