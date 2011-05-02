<#-- if no media, album or user matched, display no result information -->
<#if !mediaList?has_content && !albumList?has_content && !userList?has_content>
<div class="info">
    No record matched for "${searchWords?html}".
</div>
<#else>

<#if mediaList?has_content>
<div class="mediaDisplay2">
    <div class="mediaBox">
        <div class="mediaTitle">
            <div class="titleText">Matched Media</div>
        </div>
    </div>
    <#list mediaList as entity>
    <@displayMediaInList entity />
    </#list>
</div>
</#if>

<div class="clear"></div>

<#if albumList?has_content>
<div class="mediaDisplay2">
    <div class="mediaBox">
        <div class="mediaTitle">
            <div class="titleText">Matched Albums</div>
        </div>
    </div>
    <#list albumList as album>
    <div class="mediaBox">
        <div class="mediaLine"></div>
        <div class="mediacontent">
            <a href="${baseUrl}/album?a=${album.accessCode}">
                <img class="mediaImage" src="${baseUrl}/images/albums.png" alt=""/>
            </a>

            <div class="mediadetails">
                <a href="${baseUrl}/album?a=${album.accessCode}">${album.albumName}</a>
            </div>
        </div>
    </div>
    </#list>
</div>
</#if>

<div class="clear"></div>

<#if userList?has_content>
<div class="mediaDisplay2">
    <div class="mediaBox">
        <div class="mediaTitle">
            <div class="titleText">Matched UniTubas</div>
        </div>
    </div>
    <#list userList as user>
    <div class="mediaBox">
        <div class="mediaLine"></div>
        <div class="mediacontent">
            <a href="${baseUrl}/media.do?u=${user.accessCode}">
                <img class="mediaImage" src="${baseUrl}/images/unitubas.png" alt=""/>
            </a>

            <div class="mediadetails">
                <a href="${baseUrl}/media.do?u=${user.accessCode}">${user.userName}</a>
            </div>
        </div>
    </div>
    </#list>
</div>
</#if>

</#if>




