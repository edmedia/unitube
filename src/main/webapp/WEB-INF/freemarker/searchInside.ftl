<#-- if no media, album or user matched, display no result information -->
<#if !mediaList?has_content && !albumList?has_content && !userList?has_content>
<div class="stage">
    <div class="info">
        <#assign term=["${searchWords}"]/>
        <@spring.messageArgs "search.no.result" term/>
    </div>
</div>
    <#else>

    <div id="tabs" style="width: 1004px; margin-top: 30px; float: left">
        <ul>
            <#if mediaList?has_content>
                <li><a href="#tabs-1">Matched Media (${mediaList?size})</a></li>
            </#if>
            <#if albumList?has_content>
                <li><a href="#tabs-2">Matched Albums (${albumList?size})</a></li>
            </#if>
            <#if userList?has_content>
                <li><a href="#tabs-3">Matched UniTubas (${userList?size})</a></li>
            </#if>
        </ul>

        <#if mediaList?has_content>
            <div id="tabs-1">
            <@displayMediaList2 mediaList/>
            </div>
        </#if>

        <#if albumList?has_content>
            <div id="tabs-2">
            <@displayAlbumList albumList/>
            </div>
        </#if>

        <#if userList?has_content>
            <div id="tabs-3">
            <@displayUserList userList/>
            </div>
        </#if>

    </div>

    <script type="text/javascript">
        <!--
        $(function() {
            $("#tabs").tabs();
        });
        //-->
    </script>
</#if>




