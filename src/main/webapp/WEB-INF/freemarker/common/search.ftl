<div class="search">
    <form action="${baseUrl}/search.do" method="get" onsubmit="if(this.q.value.length == 0) return false;">
    <span class="searchOptions">
        <label><input type="radio" name="t" value="20"<#if (mediaType!0) == 20>
                      checked="checked"</#if>/>Video</label>
        <label><input type="radio" name="t" value="10"<#if (mediaType!0) == 10>
                      checked="checked"</#if>/>Audio</label>
        <label><input type="radio" name="t" value="5"<#if (mediaType!0) == 5>
                      checked="checked"</#if>/>Image</label>
        <#--
        <label><input type="radio" name="t" value="1"<#if (mediaType!0) == 1>
                      checked="checked"</#if>/>Other</label>
                      -->
        <label><input type="radio" name="t" value="0"<#if (mediaType!0) == 0>
                      checked="checked"</#if>/>All</label>
    </span>
        <input name="q" type="text" value="${searchWords!}" class="searchText"/>
        <input type="submit" name="button" value="Search"/>
    </form>
</div>
