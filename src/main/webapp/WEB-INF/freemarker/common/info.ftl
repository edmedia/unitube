<div class="infoBox">
    <div class="infoHeader"></div>
    <div class="infoContent">
        <div class="info">

            <p>UniTube is a place for University of Otago staff and students to upload and share educational
                material.</p>
        <#if !authUser??>
            <p><a href="${baseUrl}/myTube/list.do">Log in</a> using your university username and password to join the
                UniTube community.</p>
        </#if>

            <p><a href="${baseUrl}/help.do">Read more about getting started on UniTube</a></p>

            <p><a href="${baseUrl}/copyright.do">Find out about copyright and IP rights</a></p>
        </div>
    </div>
    <!--end of infoContent-->
    <div class="infoFooter"></div>
</div>
<!-- end of infoBox -->
