<#include "common/info.ftl" />
<#--
<#if featured?has_content>
<h2 class='homeHeader'>Featured Videos</h2>
    <#assign playlist = featured/>
<div style="float:left">
    <#include "playlistHelper.ftl"/>
</div>
</#if>
-->
<div id="tabs" style="width: 730px; margin-top: 30px; float: left">
    <ul>
    <#if mostViewed?has_content>
        <li><a href="#tabs-1">Most Viewed Media</a></li>
    </#if>
    <#if mostRecent?has_content>
        <li><a href="#tabs-2">Recently Added Media</a></li>
    </#if>
    </ul>

<#if mostViewed?has_content>
    <div id="tabs-1">
    <@displayMediaList2 mostViewed/>
    </div>
</#if>

<#if mostRecent?has_content>
    <div id="tabs-2">
    <@displayMediaList2 mostRecent/>
    </div>
</#if>

</div>

<div class="pageNumber">
    <p><a href="${baseUrl}/media.do">More media ...</a></p>
</div>

<script type="text/javascript">
    <!--
    $(function() {
        $("#tabs").tabs();
    });
    //-->
</script>