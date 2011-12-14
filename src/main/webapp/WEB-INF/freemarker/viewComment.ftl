<div id="comments">
    <div>
        <img class="imageAlign" src="${baseUrl}/images/comments.png" alt=""/>
        <strong>Comments </strong>(${obj.comments?size})
        <a href="#" class="commentLink"><#if authUser??>Post a Comment<#else>Log in to post a Comment</#if></a>
    </div>

<@displayCommentForm ""/>
<#if commentList?has_content>
    <ul>
        <#list commentList as comment >
        <@displayComment comment/>
        </#list>
    </ul>
    <#if hasMoreComment?? && hasMoreComment>
        <p><a href="viewAllComment.do?m=${obj.accessCode}">View All ${obj.comments?size} Comments</a></p>
    </#if>
</#if>
</div>

<script type="text/javascript">
    <!--
    function displayComment() {
        // hide all comment forms
        $('div.commentForm').hide();
        $('a.commentLink').click(function() {
        <#if authUser??>
        <#-- if logged in already, display comment form -->
            var commentForm = $(this).parent().next();
            commentForm.show();
            // focus input area
            $('textarea', commentForm).focus();
            return false;
            <#else>
            <#-- otherwise, try to access a URL which needs login first -->
                this.href = '${baseUrl}/myTube/postComment.do?m=${obj.accessCode}';
        </#if>
        });
        // display correct character numbers remaining in comment
        $('textarea').each(function() {
            $(this).next().next().next().next().next().text((255 - $(this).val().length));
        });
        // after every keystroke, check the length, only take first 255 characters
        $('textarea').keyup(function(event) {
            $(this).next().next().next().next().next().text((255 - $(this).val().length));
            if ((event.keyCode >= 32) && (event.keyCode <= 126)) {
                if ((255 - $(this).val().length) < 0) {
                    alert("<@spring.message "comment.too.long"/>");
                    // only get first 255 characters
                    $(this).val($(this).val().substring(0, 255));
                    event.preventDefault();
                }
            }
        });
        // when pressing "Post Comment" button
        $('input[name=postCommentButton]').click(function() {
            // check if input anything yet
            if ($(this).prev().prev().val().length == 0) {
                alert("<@spring.message "comment.empty"/>");
                $(this).prev().prev().focus();
                return;
            }
            $(this).parent().ajaxSubmit({
                url: "${baseUrl}/myTube/postComment.do",
                type: "POST",
                dataType: "xml",
                success: function(xml) {
                    if ($("action", xml).attr("success") == "true") {
                        alert("<@spring.message "comment.success"/>");
                        // reload this page
                        var url = location.href;
                        if (url.indexOf("#") != -1)
                            url = url.substring(0, url.indexOf("#"));
                        url = url + "#comment_" + $("action", xml).attr("detail");
                        location.href = url;
                        location.reload(true);
                    } else
                        alert($("action", xml).attr("detail"));
                }
            });
        });
        $('input[name=discard]').click(function() {
            $(this).parent().parent().hide();
        });
    }
    //-->
</script>

