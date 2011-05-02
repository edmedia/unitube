<div class="infoBox">
    <div class="infoHeader"></div>
    <div class="infoContent">
        <div class="info">
            <h2>Latest Uploads/Feedback</h2>

            <div id="twitter">
                <ul id="twitter_update_list">
                </ul>
            </div>
            <p><a href="http://twitter.com/${twitterUsername}">More ...</a></p>
        </div>
    </div>
    <!--end of infoContent-->
    <div class="infoFooter"></div>
</div>
<!-- end of infoBox -->

<div class="mediaArea">

    <h2>Have your Toot on UniTube</h2>


    <form id="feedback" method="post" action="">
        <p>Give us your comments and they will be posted straight away to the UniTube Latest Toots. Comments are also
            displayed on the <a href="http://twitter.com/${twitterUsername}">UniTube Twitter Site</a> so that you
            can easily keep up with what is new on UniTube using either the Web or your mobile phone.</p>

        <!-- <p>Latest uploads are also notified on UniTube <i>Latest Toots</i>. </p> -->

        <p>Tell us what you think of UniTube now!</p>

        <textarea id="status" name="status" rows="10" cols="60"></textarea>
        <br/>
        <input id="feedbackButton" type="button" value="Toot!" align="right"/>
    </form>

</div>
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
                    } else {
                        alert("<@spring.message "ajax.request.fail"/>");
                    }
                }
            });
        });
    });
    //-->
</script>
