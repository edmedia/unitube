<#-- display upload form -->
<#if user?has_content>
<h2>Upload your media file</h2>

<div>
    <p>Please ensure that you use the appropriate access option when uploading to UniTube.
        <a href="#" class="showMore">+ Show more</a>
        <a href="#" class="showLess">- Show less</a>
    </p>

    <div id="accessTypeDetail">
        <p><strong>Public:</strong> Anyone can find and access your file. No login is required.</p>

        <p><strong>Hidden:</strong> Anyone who has the link can access your file. The file URL is not
            listed on UniTube and is not discoverable by search engines. No login is required, and for this reason
            we strongly advise against uploading any sensitive or private material to UniTube using the hidden option;
            choose the private option instead.</p>

        <p><strong>Private:</strong> Only people explicitly granted permission can access your file. Login is required.
        </p>

        <p>Please also ensure that you only upload material that you have created or have permission to share. See our
            <a href="${baseUrl}/copyright.do">copyright and IP page</a> for more information.</p>

        <p>If you become aware of any material on UniTube that you feel is inappropriate for any reason or may
            breach copyright, please let us know. E-mail <a href="mailto:unitube@otago.ac.nz">unitube@otago.ac.nz</a>
        </p>

        <p>&nbsp; </p>
    </div>
</div>

<form action="upload.do" name="mediaForm" method="post" enctype="multipart/form-data">
    <div>
        <input class="browse" name="uploadFile" type="file"/>
    </div>
    <div>
        <div>
            <input type="radio" name="accessType" value="0"
                   title="<@spring.message "media.access.public"/>"
                   <#if user.uploadAccessType == 0>checked="checked"</#if>/>
            <span title="<@spring.message "media.access.public"/>">Public</span>
            <input type="radio" name="accessType" value="10"
                   title="<@spring.message "media.access.hidden"/>"
                   <#if user.uploadAccessType == 10>checked="checked"</#if>/>
            <span title="<@spring.message "media.access.hidden"/>">Hidden</span>
            <input type="radio" name="accessType" value="20"
                   title="<@spring.message "media.access.private"/>"
                   <#if user.uploadAccessType == 20>checked="checked"</#if>/>
            <span title="<@spring.message "media.access.private"/>">Private</span>
            <a href="#" class="moreOption">+ More options</a>
            <a href="#" class="lessOption">- Less options</a>
        </div>
    </div>
    <div id="moreOptions">
        <div id="onTwitter">
            <span title="Post my activity in UniTube Twitter feed.">Tweet?</span>
            <input type="radio" name="onTwitter" value="true" checked="checked"/> Yes
            <input type="radio" name="onTwitter" value="false"/> No
        </div>
        <div>
            <span title="Converting a video file to MPEG will allow you to embed it into PowerPoint.">Convert to MPEG?</span>
            <input type="radio" name="convertTo" value="mpg"/> Yes
            <input type="radio" name="convertTo" value="" checked="checked"/> No
        </div>
        <div>
            <span title="This will make your file available for download only, not for viewing online">Only for download?</span>
            <input type="radio" name="uploadOnly" value="true"/> Yes
            <input type="radio" name="uploadOnly" value="false" checked="checked"/> No
        </div>
    </div>
    <div>
        <input name="upload" type="submit" value="Upload"/>
		<br />
    </div>
</form>
<br />
<p>Did you know you can also upload files by emailing them to UniTube? <a href="${baseUrl}/help.do">Find out more</a></p>
<br/>
<hr/>
<br/>
    <#if user.isGuest>
    <p>You are a guest user, all your media files will be REMOVED after 24 hours. Please contact us if you want to be a
        permanent user.</p>
    <p>&nbsp; </p>
    </#if>
<script type="text/javascript">
    <!--
    function initUploadForm() {
        // hide access type detail by default
        $('#accessTypeDetail').hide();
        $('a.showMore').show();
        $('a.showLess').hide();
        $('a.showMore').click(function() {
            $('#accessTypeDetail').show();
            showMoreOrLess();
            return false;
        });
        $('a.showLess').click(function() {
            $('#accessTypeDetail').hide();
            showMoreOrLess();
            return false;
        });

        // hide more options by default
        $('#moreOptions').show();
        $('a.moreOption').hide();
        $('a.lessOption').show();
        $('a.moreOption').click(function() {
            $('#moreOptions').show();
            showMoreOrLessOption();
            return false;
        });
        $('a.lessOption').click(function() {
            $('#moreOptions').hide();
            showMoreOrLessOption();
            return false;
        });

        // hide "on twitter" for non public media file
        <#if user.uploadAccessType &gt; 0>
            $('#onTwitter').hide();
        </#if>

        $('input:radio[name=accessType]').click(function() {
            // only show "on twitter" for public media file
            if ($('input:radio[name=accessType]:checked').val() == 0)
                $('#onTwitter').show();
            else
                $('#onTwitter').hide();
        });
    }

    function showMoreOrLess() {
        if ($('#accessTypeDetail').is(':visible')) {
            $('a.showMore').hide();
            $('a.showLess').show();
        } else {
            $('a.showMore').show();
            $('a.showLess').hide();
        }
    }

    function showMoreOrLessOption() {
        if ($('#moreOptions').is(':visible')) {
            $('a.moreOption').hide();
            $('a.lessOption').show();
        } else {
            $('a.moreOption').show();
            $('a.lessOption').hide();
        }
    }
    //-->
</script>
    <#else>
    <div>Please login first before uploading media file.</div>
</#if>
