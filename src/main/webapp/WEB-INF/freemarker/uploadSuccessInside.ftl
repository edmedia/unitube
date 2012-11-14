<div class="stage">

<#if hasVirus??>
    <div class="info">
        We believe your uploaded file has virus inside, we have deleted your file.
        <#if detail??>
        ${detail}
        </#if>
    </div>
    <#else>
    <@spring.bind "media.status" />
        <#if spring.status.value == 0>
            <div class="info">
                Well done! Your media file has been uploaded successfully and is now being processed. How long this will
                take will depend on the size of the file and how busy our server is.
            </div>
        </#if>

    <@spring.bind "media.accessCode" />
        <p>You can access your media file by using this link:
            <a href="${context_url}/view?m=${spring.status.value}">${context_url}/view?m=${spring.status.value}</a>
        </p>

    <@spring.bind "media.id" />
        <p>You can change details of your media file by using this link:
            <a href="edit.do?id=${spring.status.value?c}">Edit Media</a>
        </p>

    <p><a href="${baseUrl}/myTube/list.do">Click here to upload more files.</a> </p>
</#if>
</div>
