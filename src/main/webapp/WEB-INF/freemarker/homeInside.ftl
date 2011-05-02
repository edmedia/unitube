<#include "common/info.ftl" />

<div class="mediaDisplay">

    <div class="mediaBox">
        <div class="mediaTitle">
            <div class="titleText"> Most Viewed</div>
        </div>
    </div>

    <#if mostVisited?has_content>
    <#list mostVisited as media>
    <@displayMediaInList media />
    </#list>
    </#if>

</div>

<div class="mediaDisplay">

    <div class="mediaBox">
        <div class="mediaTitle">
            <div class="titleText"> Recently Added</div>
        </div>
    </div>

    <#if mostRecent?has_content>
    <#list mostRecent as media>
    <@displayMediaInList media />
    </#list>
    </#if>

</div>

<div class="pageNumber">
    <p><a href="${baseUrl}/media.do">More media ...</a></p>
</div>
