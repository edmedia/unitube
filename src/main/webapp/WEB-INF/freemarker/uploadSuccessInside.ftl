<div class="stage">
    <p>&nbsp;</p>
<@spring.bind "media.status" />
<#if spring.status.value == 0>
    <div class="info">
        Well done! Your media file has been uploaded successfully and is now being processed. How long this will take
        will depend on the size of the file and how busy our server is.
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
</div>