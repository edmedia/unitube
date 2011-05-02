<@spring.bind "comment.id" />

<h2><#if spring.status.value??>Edit<#else>Create a new</#if> User Comment</h2>

<form action="${this_url}" name="commentForm" method="post">
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
<@spring.bind "comment.id" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- msg: Message -->
<tr>
<th>Message</th>
<td>
<@spring.bind "comment.msg" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- msgTime: Message Time -->
<tr>
<th>Message Time</th>
<td>
<@spring.bind "comment.msgTime" />
<@displayInputBox/>

</td>
<td>

<@displayError/>
</td>
</tr>
<!-- credits: Comment Credits -->
<tr>
<th>Comment Credits</th>
<td>
<@spring.bind "comment.credits" />
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
<@spring.bind "comment.mediaID" />
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
<@spring.bind "comment.authorID" />
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
<!-- comment: Parent Comment -->
<tr>
<th>Parent Comment</th>
<td>
<@spring.bind "comment.commentID" />
<select name="commentID">
<option value="0">&nbsp;</option>
    <#list commentList as option>
        <option value="${option.id?c}">${option.meaningfulName}</option>
    </#list>
</select>


</td>
<td>

<@displayError/>
</td>
</tr>
<!-- childComments: Child Comments -->
<tr>
<th>Child Comments</th>
<td>
<@spring.bind "comment.childCommentsID" />
<select name="childCommentsID" multiple="multiple"<#if childCommentsList?size &gt; 4> size="4"</#if>>
    <#list childCommentsList as option>
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
<a href="commentList.do?aa=list<#if pageNumber??>&amp;p=${pageNumber?c}</#if><#if pageSize??>&amp;s=${pageSize?c}</#if>">Back to Comment list</a>
</div>

<script type="text/javascript">
<!--
$(function() {

<@spring.bind "comment.media" />
            <#if (spring.status.value.id)??>
    $('[name=mediaID]').val("${spring.status.value.id?c}");
            </#if>


<@spring.bind "comment.author" />
            <#if (spring.status.value.id)??>
    $('[name=authorID]').val("${spring.status.value.id?c}");
            </#if>


<@spring.bind "comment.comment" />
            <#if (spring.status.value.id)??>
    $('[name=commentID]').val("${spring.status.value.id?c}");
            </#if>


<@spring.bind "comment.childComments" />
            <#list spring.status.value as obj>
                <#if (obj.id)??>
    $('[name=childCommentsID]').val("${obj.id?c}");
                </#if>
            </#list>

});
//-->
</script>
