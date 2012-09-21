<#--
<div class="infoBox">
    <div class="info">
        <h2>Latest Tweets</h2>

        <div id="twitter">
            <ul id="twitter_update_list">
            </ul>
        </div>
        <p><a href="http://twitter.com/${twitterUsername}">More ...</a></p>
    </div>
</div> -->
<!-- end of infoBox -->

<div class="mediaArea">

<a class="twitter-timeline" href="https://twitter.com/diyun" data-widget-id="248563120212156416">Tweets by @diyun</a>
<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
  <#--
    <form id="feedback" method="post" action="">
        <p>Be a part of UniTube's Twitter community by posting a comment in our feed, or by checking the 'Tweet' option
            each time you upload a new file. If you have your own Twitter account, make sure you <a
                    href="http://twitter.com/${twitterUsername}">follow us</a> to keep up with what's new!</p>

        <!-- <p>Latest uploads are also notified on UniTube <i>Latest Toots</i>. </p> -->

        <#--
        <textarea id="status" name="status" rows="10" cols="60"></textarea>
        <br/>
        <input id="feedbackButton" type="button" value="Tweet!" align="right"/>
    </form>
    -->

</div>
<#--
<script type="text/javascript" src="http://twitter.com/javascripts/blogger.js"></script>
<script type="text/javascript"
        src="http://twitter.com/statuses/user_timeline/${twitterUsername}.json?callback=twitterCallback2&amp;count=10"></script>

<script type="text/javascript">
    <!--
    $(function() {
        $('#feedbackButton').click(function() {
            if ($('textarea[name=status]').val() == "") {
                alert("<@spring.message "feedback.required"/>");
                $('textarea[name=status]').focus();
                return;
            }
            if ($('textarea[name=status]').val().length > 140) {
                alert("<@spring.message "feedback.too.long"/>");
                $('textarea[name=status]').focus();
                return;
            }
            $('#feedback').ajaxSubmit({
                url: "sendFeedback.do",
                type: "POST",
                dataType: "xml",
                success: function(xml) {
                    if ($("action", xml).attr("success") == "true") {
                        alert("<@spring.message "feedback.success"/>");
                        // reload this page
                        location.reload(true);
                    } else
                        alert($("action", xml).attr("detail"));
                }
            });
        });
    });
    //-->      <#--
</script>          -->
