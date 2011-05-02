<@spring.bind "album.id" />

<h2><#if spring.status.value??>Edit<#else>Create a new</#if> Album</h2>

<form action="${this_url}" name="albumForm" method="post">
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
<@spring.bind "album.id" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- albumName: Album Name -->
<tr>
<th>Album Name</th>
<td>
<@spring.bind "album.albumName" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- description: Album Description -->
<tr>
<th>Album Description</th>
<td>
<@spring.bind "album.description" />
<script type="text/javascript">
<!--
  var fck_${spring.status.expression} = new FCKeditor('${spring.status.expression}');
  fck_${spring.status.expression}.BasePath = '${baseUrl}/fckeditor/';
  fck_${spring.status.expression}.Value = '<#if spring.status.value??><#if spring.status.value?is_number>${spring.status.value?c}<#else>${spring.status.value?js_string}</#if></#if>';
  fck_${spring.status.expression}.Config['CustomConfigurationsPath'] = '${baseUrl}/fckeditor/custom/config.js';
  fck_${spring.status.expression}.Width = '430px';
  fck_${spring.status.expression}.Height = '200px';
  fck_${spring.status.expression}.ToolbarSet = 'CUSTOM';
  fck_${spring.status.expression}.Create();
//-->
</script>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- randomCode: Random Code -->
<tr>
<th>Random Code</th>
<td>
<@spring.bind "album.randomCode" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- accessType: access type(0: public, 10: hidden, 20: private) -->
<tr>
<th>access type(0: public, 10: hidden, 20: private)</th>
<td>
<@spring.bind "album.accessType" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- owner: Owner of this album -->
<tr>
<th>Owner of this album</th>
<td>
<@spring.bind "album.ownerID" />
<select name="ownerID">
<option value="0">&nbsp;</option>
    <#list ownerList as option>
        <option value="${option.id?c}">${option.meaningfulName}</option>
    </#list>
</select>


</td>
<td>

<@displayError/>
</td>
</tr>
<!-- albumMedias: All AlbumMedias -->
<!-- userAlbums: All UserAlbums -->
<tr>
<th>All UserAlbums</th>
<td>
<@spring.bind "album.userAlbumsID" />
<select name="userAlbumsID" multiple="multiple"<#if userAlbumsList?size &gt; 4> size="4"</#if>>
    <#list userAlbumsList as option>
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
<a href="albumList.do?aa=list<#if pageNumber??>&amp;p=${pageNumber?c}</#if><#if pageSize??>&amp;s=${pageSize?c}</#if>">Back to Album list</a>
</div>

<script type="text/javascript">
<!--
$(function() {

<@spring.bind "album.owner" />
            <#if (spring.status.value.id)??>
    $('[name=ownerID]').val("${spring.status.value.id?c}");
            </#if>


<@spring.bind "album.userAlbums" />
            <#list spring.status.value as obj>
                <#if (obj.id)??>
    $('[name=userAlbumsID]').val("${obj.id?c}");
                </#if>
            </#list>

});
//-->
</script>
