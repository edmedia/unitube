<@spring.bind "albumMedia.id" />

<h2><#if spring.status.value??>Edit<#else>Create a new</#if> AlbumMedia</h2>

<form action="${this_url}" name="albumMediaForm" method="post">
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
<@spring.bind "albumMedia.id" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- album: Album -->
<tr>
<th>Album</th>
<td>
<@spring.bind "albumMedia.albumID" />
    <#if album??>
${album.meaningfulName}
<input type="hidden" name="${spring.status.expression}" value="${album.id?c}" />
    </#if>


</td>
<td>

<@displayError/>
</td>
</tr>
<!-- media: Media -->
<tr>
<th>Media</th>
<td>
<@spring.bind "albumMedia.mediaID" />
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
<a href="albumMediaList.do?aa=list<#if pageNumber??>&amp;p=${pageNumber?c}</#if><#if pageSize??>&amp;s=${pageSize?c}</#if>&amp;albumID=${album.id?c}">Back to AlbumMedia list</a>
</div>

<script type="text/javascript">
<!--
$(function() {

<@spring.bind "albumMedia.album" />
            <#if (spring.status.value.id)??>
    $('[name=albumID]').val("${spring.status.value.id?c}");
            </#if>


<@spring.bind "albumMedia.media" />
            <#if (spring.status.value.id)??>
    $('[name=mediaID]').val("${spring.status.value.id?c}");
            </#if>

});
//-->
</script>
