<@spring.bind "media.id" />

<h2><#if spring.status.value??>Edit<#else>Create a new</#if> Media</h2>

<form action="${this_url}" name="mediaForm" method="post" enctype="multipart/form-data">
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
<@spring.bind "media.id" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- title: Title -->
<tr>
<th>Title</th>
<td>
<@spring.bind "media.title" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- description: Description -->
<tr>
<th>Description</th>
<td>
<@spring.bind "media.description" />
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
<!-- uploadFile: Upload File -->
<tr>
<th>Upload File</th>
<td>
<@spring.bind "media.uploadFile" />
<input type="file" name="${spring.status.expression}" />

</td>
<td>

<@displayError/>
<@spring.bind "media.uploadFileUserName" />
<#if spring.status.value?has_content>
<a href="<#if !uploadLocation.absolutePath>${baseUrl}/</#if>${uploadLocation.baseLink}${spring.status.value?html}">${spring.status.value!}</a>
</#if>
</td>
</tr>
<!-- realFilename: Real File Name -->
<tr>
<th>Real File Name</th>
<td>
<@spring.bind "media.realFilename" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- thumbnail: Thumbnail -->
<tr>
<th>Thumbnail</th>
<td>
<@spring.bind "media.thumbnail" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- mediaType: Media Type(1: Other, 5: Image, 10: Audio, 20: Video) -->
<tr>
<th>Media Type(1: Other, 5: Image, 10: Audio, 20: Video)</th>
<td>
<@spring.bind "media.mediaType" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- convertTo: convert to a different format -->
<tr>
<th>convert to a different format</th>
<td>
<@spring.bind "media.convertTo" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- width: Width -->
<tr>
<th>Width</th>
<td>
<@spring.bind "media.width" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- height: Height -->
<tr>
<th>Height</th>
<td>
<@spring.bind "media.height" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- snapshotPosition: Snapshot Position -->
<tr>
<th>Snapshot Position</th>
<td>
<@spring.bind "media.snapshotPosition" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- tags: Tags -->
<tr>
<th>Tags</th>
<td>
<@spring.bind "media.tags" />
<@displayInputBox/>

</td>
<td>
tags for Tag Cloud
<@displayError/>
</td>
</tr>
<!-- status: Status(0:waiting, 1:processing, 2:finished, 9:unrecognized) -->
<tr>
<th>Status(0:waiting, 1:processing, 2:finished, 9:unrecognized)</th>
<td>
<@spring.bind "media.status" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- isOnOtherServer: Put this media on other server? -->
<tr>
<th>Put this media on other server?</th>
<td>
<@spring.bind "media.isOnOtherServer" />
<input type="radio" name="${spring.status.expression}" value="true"<#if spring.status.value??><#if spring.status.value> checked="checked"</#if></#if> /> Yes
<input type="radio" name="${spring.status.expression}" value="false"<#if spring.status.value??><#if !spring.status.value> checked="checked"</#if></#if> /> No
</td>
<td>

<@displayError/>
</td>
</tr>
<!-- accessType: access type(0: public, 10: hidden, 20: private) -->
<tr>
<th>access type(0: public, 10: hidden, 20: private)</th>
<td>
<@spring.bind "media.accessType" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- viaEmail: Upload via Email? -->
<tr>
<th>Upload via Email?</th>
<td>
<@spring.bind "media.viaEmail" />
<input type="radio" name="${spring.status.expression}" value="true"<#if spring.status.value??><#if spring.status.value> checked="checked"</#if></#if> /> Yes
<input type="radio" name="${spring.status.expression}" value="false"<#if spring.status.value??><#if !spring.status.value> checked="checked"</#if></#if> /> No
</td>
<td>

<@displayError/>
</td>
</tr>
<!-- viaMMS: Upload via MMS? -->
<tr>
<th>Upload via MMS?</th>
<td>
<@spring.bind "media.viaMMS" />
<input type="radio" name="${spring.status.expression}" value="true"<#if spring.status.value??><#if spring.status.value> checked="checked"</#if></#if> /> Yes
<input type="radio" name="${spring.status.expression}" value="false"<#if spring.status.value??><#if !spring.status.value> checked="checked"</#if></#if> /> No
</td>
<td>

<@displayError/>
</td>
</tr>
<!-- uploadOnly: Upload Only? -->
<tr>
<th>Upload Only?</th>
<td>
<@spring.bind "media.uploadOnly" />
<input type="radio" name="${spring.status.expression}" value="true"<#if spring.status.value??><#if spring.status.value> checked="checked"</#if></#if> /> Yes
<input type="radio" name="${spring.status.expression}" value="false"<#if spring.status.value??><#if !spring.status.value> checked="checked"</#if></#if> /> No
</td>
<td>
When it's true, this file will keep as original format.
<@displayError/>
</td>
</tr>
<!-- randomCode: Random Code -->
<tr>
<th>Random Code</th>
<td>
<@spring.bind "media.randomCode" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- locationCode: Location Code(where to put media file) -->
<tr>
<th>Location Code(where to put media file)</th>
<td>
<@spring.bind "media.locationCode" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- accessTimes: Access Times -->
<tr>
<th>Access Times</th>
<td>
<@spring.bind "media.accessTimes" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- processTimes: Process Times -->
<tr>
<th>Process Times</th>
<td>
<@spring.bind "media.processTimes" />
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
<@spring.bind "media.userID" />
    <#if user??>
${user.meaningfulName}
<input type="hidden" name="${spring.status.expression}" value="${user.id?c}" />
    </#if>


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
<a href="mediaList.do?aa=list<#if pageNumber??>&amp;p=${pageNumber?c}</#if><#if pageSize??>&amp;s=${pageSize?c}</#if>&amp;userID=${user.id?c}">Back to Media list</a>
</div>

<script type="text/javascript">
<!--
$(function() {

<@spring.bind "media.user" />
            <#if (spring.status.value.id)??>
    $('[name=userID]').val("${spring.status.value.id?c}");
            </#if>


<@spring.bind "media.albumMedias" />
            <#list spring.status.value as obj>
                <#if (obj.id)??>
    $('[name=albumMediasID]').val("${obj.id?c}");
                </#if>
            </#list>


<@spring.bind "media.comments" />
            <#list spring.status.value as obj>
                <#if (obj.id)??>
    $('[name=commentsID]').val("${obj.id?c}");
                </#if>
            </#list>


<@spring.bind "media.annotations" />
            <#list spring.status.value as obj>
                <#if (obj.id)??>
    $('[name=annotationsID]').val("${obj.id?c}");
                </#if>
            </#list>


<@spring.bind "media.accessRules" />
            <#list spring.status.value as obj>
                <#if (obj.id)??>
    $('[name=accessRulesID]').val("${obj.id?c}");
                </#if>
            </#list>

});
//-->
</script>
