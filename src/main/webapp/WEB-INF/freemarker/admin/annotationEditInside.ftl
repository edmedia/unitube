<@spring.bind "annotation.id" />

<h2><#if spring.status.value??>Edit<#else>Create a new</#if> Annotation for Image</h2>

<form action="${this_url}" name="annotationForm" method="post" enctype="multipart/form-data">
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
<@spring.bind "annotation.id" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- annotName: Annotation Name -->
<tr>
<th>Annotation Name</th>
<td>
<@spring.bind "annotation.annotName" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- description: Annotation Description -->
<tr>
<th>Annotation Description</th>
<td>
<@spring.bind "annotation.description" />
    <@displayTextArea/>
  <script type="text/javascript">
    <!--
        CKEDITOR.replace( '${spring.status.expression}', {
            customConfig : '${baseUrl}/javascript/ckeditor_config.js'
        });
    -->
  </script>
</td>
<td>

<@displayError/>
</td>
</tr>
<!-- annotFile: Annotation File -->
<tr>
<th>Annotation File</th>
<td>
<@spring.bind "annotation.annotFile" />
<input type="file" name="${spring.status.expression}" />

</td>
<td>

<@displayError/>
<@spring.bind "annotation.annotFileUserName" />
<#if spring.status.value?has_content>
<a href="<#if !uploadLocation.absolutePath>${baseUrl}/</#if>${uploadLocation.baseLink}${spring.status.value?html}">${spring.status.value!}</a>
</#if>
</td>
</tr>
<!-- annotTime: Annotation Time -->
<tr>
<th>Annotation Time</th>
<td>
<@spring.bind "annotation.annotTime" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- randomCode: Random Code -->
<tr>
<th>Random Code</th>
<td>
<@spring.bind "annotation.randomCode" />
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
<@spring.bind "annotation.mediaID" />
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
<!-- author: Author -->
<tr>
<th>Author</th>
<td>
<@spring.bind "annotation.authorID" />
<select name="authorID">
<option value="0">&nbsp;</option>
    <#list authorList as option>
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
<a href="annotationList.do?aa=list<#if pageNumber??>&amp;p=${pageNumber?c}</#if><#if pageSize??>&amp;s=${pageSize?c}</#if>">Back to Annotation list</a>
</div>

<script type="text/javascript">
<!--
$(function() {

<@spring.bind "annotation.media" />
            <#if (spring.status.value.id)??>
    $('[name=mediaID]').val("${spring.status.value.id?c}");
            </#if>


<@spring.bind "annotation.author" />
            <#if (spring.status.value.id)??>
    $('[name=authorID]').val("${spring.status.value.id?c}");
            </#if>

});
//-->
</script>
