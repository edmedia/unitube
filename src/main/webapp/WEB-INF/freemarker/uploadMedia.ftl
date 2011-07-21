<#-- display upload form -->
<#if user?has_content>
<h2>Upload your media file</h2>

<div>
    <h3>UniTube Security and Privacy (<a href="#">Hide this</a>)</h3>

    <p>Please ensure that you use the appropriate access option when uploading materials to UniTube.</p>

    <p><strong>Public:</strong> This means anyone can find and access your file. No login is required.</p>

    <p><strong>Hidden:</strong> Means anyone who has the link can access your file. The URL for your media is not listed on UniTube
        itself and is not discoverable by search engines. No login is required and for this reason we strongly advise
        against uploading any sensitive or private material to UniTube using the hidden option; choose the private
        option instead.</p>

    <p><strong>Private:</strong> Only people explicitly granted permission can access your file. Login is required.</p>

    <p>Also, please do ensure that you only upload material that you have created or have permission to share. See <a
            href="${baseUrl}/copyright.do">here</a> for further information.</p>

    <p>If you become aware of any material on UniTube that you feel is inappropriate for any reason or is in possible
        breach of copyright please do let us know. E-mail <a href="mailto:unitube@otago.ac.nz">unitube@otago.ac.nz</a>
    </p>

    <p>&nbsp; </p>
</div>

<form action="upload.do" name="mediaForm" method="post" enctype="multipart/form-data">
    <div>
        <input class="browse" name="uploadFile" type="file"/>
    </div>
    <div>
        <div>
            <strong>Sharing:</strong>
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
            <a id="changeOption" href="#">+ More options</a>
        </div>
    </div>
    <div id="moreOptions">
        <div id="onTwitter">
            <strong>Have a Tweet on UniTube Twitter</strong>
            <input type="radio" name="onTwitter" value="true" checked="checked"/> yes
            <input type="radio" name="onTwitter" value="false"/> No
        </div>
        <div>
            <strong>Conversion: convert file to MPEG?</strong>
            <input type="radio" name="convertTo" value="mpg"/> Yes
            <input type="radio" name="convertTo" value="" checked="checked"/> No
        </div>
        <div>
            <strong>Only for download (i.e. make your file available for download only, not for viewing online)
                ?</strong>
            <input type="radio" name="uploadOnly" value="true"/> Yes
            <input type="radio" name="uploadOnly" value="false" checked="checked"/> No
        </div>
    </div>
    <div>
        <input name="upload" type="submit" value="Upload"/>
    </div>
</form>
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
        // hide more options by default
        $('#moreOptions').hide();
        changeText();
        // hide "on twitter" for non public media file
        <#if user.uploadAccessType &gt; 0>
            $('#onTwitter').hide();
        </#if>
        $('#changeOption').click(function() {
            // toggle between "+ More options" and "- Less options"
            $('#moreOptions').toggle();
            changeText();
            return false;
        });
        $('input:radio[name=accessType]').click(function() {
            // only show "on twitter" for public media file
            if ($('input:radio[name=accessType]:checked').val() == 0)
                $('#onTwitter').show();
            else
                $('#onTwitter').hide();
        });
    }

    // change link text depending on if "more options" is visible or not
    function changeText() {
        if ($('#moreOptions').is(":visible"))
            $('#changeOption').text("- Less options");
        else
            $('#changeOption').text("+ More options");
    }

    $('h3 a').click(function() {
        $(this).parent().parent().hide();
    });

    //-->
</script>
    <#else>
    <div>Please login first before uploading media file.</div>
</#if>