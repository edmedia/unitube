<#--
    name: orderByLink
    desc: add a link to headers of the list, which will order by the field name
    @param: fieldName field name
-->
<#function orderByLink fieldName>
<#local link = pager.linkWithoutOrderBy + "&orderBy=" + fieldName + "&"/>
<#if orderBy! == fieldName>
<#local link = link + "orderByDesc=" + (!orderByDesc)?string/>
<#else>
<#local link = link + "orderByDesc=false"/>
</#if>
<#return link/>
</#function>

<#--
    name: displayInputBox
    desc: dispaly a text input box for user input field
-->
<#macro displayInputBox>
<input type="text" class="text" name="${spring.status.expression}" id="${spring.status.expression}"
       value="<#if spring.status.value?has_content><#if spring.status.value?is_number>${spring.status.value?c}<#else>${spring.status.value?html}</#if></#if>"/>
</#macro>

<#--
    name: displayTextArea
    desc: dispaly a textArea for user input field
-->
<#macro displayTextArea>
<textarea name="${spring.status.expression}" rows="2"
          cols="20"><#if spring.status.value??>${spring.status.value?html}</#if></textarea>
</#macro>

<#--
    name: displayError
    desc: dispaly error messages for each field
-->
<#macro displayError>
<#if spring.status.errorMessages?has_content>
<div class="errorMessage">
    <#list spring.status.errorMessages as error>
    <p>${error}</p>
    </#list>
</div>
</#if>
</#macro>

<#assign maxTitleLength = 32 />
<#assign maxDescriptionLength = 256 />

<#--
    name: displayMediaList
    desc: dispaly media list, such as on media.do, search.do
    @param: elements list of media object to be displayed
-->
<#macro displayMediaList elements>
<div class="mediaDisplay2">
    <#if elements?has_content>
    <#list elements as entity>
    <@displayMediaInList entity />
    </#list>
    <#else>
    <div class="info"><@spring.message "no.record"/></div>
    </#if>
</div>
</#macro>

<#--
    name: displayPager
    desc: dispaly pager when results need to display page by page
    @param: pager pager object
-->
<#macro displayPager pager>
<#if pager.lastPageNumber &gt; 1>
<div class="pageNumber">
    <p>
        <#if pager.pageNumber &gt; 1>
        <a href="${this_url}?<#if searchWords??>q=${searchWords}&amp;</#if><#if pageSize??>s=${pager.pageSize?c}&amp;</#if><#if user??>u=${user.accessCode}&amp;</#if><#if mediaType??>t=${mediaType}&amp;</#if>p=1">
            &lt;&lt; </a>
        <a href="${this_url}?<#if searchWords??>q=${searchWords}&amp;</#if><#if pageSize??>s=${pager.pageSize?c}&amp;</#if><#if user??>u=${user.accessCode}&amp;</#if><#if mediaType??>t=${mediaType}&amp;</#if>p=${pager.pageNumber-1}">
            &lt; </a>
        </#if>
        <#list 1..pager.lastPageNumber as p>
        <#if (p_index+1) == pager.pageNumber>
        <span class="pagerCurrent">${p}</span>
        </#if>
        <#if (p_index+1) != pager.pageNumber>
        <a href="${this_url}?<#if searchWords??>q=${searchWords}&amp;</#if><#if pageSize??>s=${pager.pageSize?c}&amp;</#if><#if user??>u=${user.accessCode}&amp;</#if><#if mediaType??>t=${mediaType}&amp;</#if>p=${p_index+1}"
           class="pagerNotCurrent">${p}</a>
        </#if>
        </#list>
        <#if pager.pageNumber &lt; pager.lastPageNumber>
        <a href="${this_url}?<#if searchWords??>q=${searchWords}&amp;</#if><#if pageSize??>s=${pager.pageSize?c}&amp;</#if><#if user??>u=${user.accessCode}&amp;</#if><#if mediaType??>t=${mediaType}&amp;</#if>p=${pager.pageNumber+1}">
            &gt; </a>
        <a href="${this_url}?<#if searchWords??>q=${searchWords}&amp;</#if><#if pageSize??>s=${pager.pageSize?c}&amp;</#if><#if user??>u=${user.accessCode}&amp;</#if><#if mediaType??>t=${mediaType}&amp;</#if>p=${pager.lastPageNumber}">
            &gt;&gt; </a>
        </#if>
    </p>
</div>
</#if>
</#macro>

<#--
    name: displayGmailStylePager
    desc: dispaly pager when results need to display page by page
    @param: pager pager object
-->
<#macro displayGmailStylePager pager>
<#if pager.lastPageNumber &gt; 1>
<span class="pageNav">
<#if pager.pageNumber &gt; 2>
<a href="${this_url}?<#if pageSize??>s=${pager.pageSize?c}&amp;</#if>p=1">&lt;&lt; Newest</a>
</#if>
<#if pager.pageNumber &gt; 1>
<a href="${this_url}?<#if pageSize??>s=${pager.pageSize?c}&amp;</#if>p=${pager.previousPageNumber?c}">&lt; Newer</a>
</#if>
<strong>${(pager.pageNumber-1)*pager.pageSize+1}
    - <#if pager.pageNumber == pager.lastPageNumber>${pager.totalNumberOfElements}<#else>${pager.pageNumber*pager.pageSize}</#if></strong> of <strong>${pager.totalNumberOfElements}</strong>
<#if pager.pageNumber &lt; pager.lastPageNumber>
<a href="${this_url}?<#if pageSize??>s=${pager.pageSize?c}&amp;</#if>p=${pager.nextPageNumber?c}">Older &gt;</a>
</#if>
<#if (pager.pageNumber+1) &lt; pager.lastPageNumber>
<a href="${this_url}?<#if pageSize??>s=${pager.pageSize?c}&amp;</#if>p=${pager.lastPageNumber?c}">Oldest &gt;&gt;</a>
</#if>
</span>
</#if>
</#macro>

<#macro getTitle realTitle>
<#if realTitle?? && (realTitle?length &gt; maxTitleLength)>${realTitle?substring(0, maxTitleLength-3)?html}...<#else>${realTitle?html}</#if>
</#macro>

<#macro getDesc realDesc>
<#if realDesc?? && (realDesc?length &gt; maxDescriptionLength)>${realDesc?substring(0, maxDescriptionLength-3)?html}...<#else>${realDesc}</#if>
</#macro>

<#--
    name: displayMediaInList
    desc: dispaly a media object in list, such as on media.do, search.do
    @param: entity a media object to be displayed
-->
<#macro displayMediaInList entity>
<#if entity != '-'>
<#local linkTitle>${entity.title!?html}</#local>
<div class="mediaBox">
    <div class="mediaLine"></div>
    <div class="mediacontent">
        <div class="mediaImage">
            <@viewLinkWithThumbnail entity />
        </div>
        <div class="mediadetails">
            <a href="${baseUrl}/view?m=${entity.accessCode}" title="${linkTitle}">
                <@getTitle entity.title/>
            </a>
            <br/>
            From: <a
                href="${baseUrl}/media.do?u=${entity.user.accessCode}">${entity.user.firstName} ${entity.user.lastName}</a>
        </div>
    </div>
</div>
<#else>
&nbsp;
</#if>
</#macro>

<#function countMediaNum mediaList>
<#local num = 0 />
<#if mediaList?has_content>
<#list mediaList as media>
<#if media.accessType == 0>
<#local num=num+1 />
</#if>
</#list>
</#if>
<#return num />
</#function>

<#function countMediaNumFromAlbumMediaList albumMediaList>
<#local num = 0 />
<#if albumMediaList?has_content>
<#list albumMediaList as albumMedia>
<#if albumMedia.media.accessType == 0>
<#local num=num+1 />
</#if>
</#list>
</#if>
<#return num />
</#function>

<#function countAlbumNum user>
<#local num = 0 />
<#if user.userAlbums?has_content>
<#list user.userAlbums as userAlbum>
<#if (userAlbum.album.accessType == 0) && (countMediaNumFromAlbumMediaList(userAlbum.album.albumMedias) &gt; 0)>
<#local num=num+1 />
</#if>
</#list>
</#if>
<#return num />
</#function>

<#macro thumbnailLink media>
<#local link>${baseUrl}/file.do?m=${media.accessCode}&name=<#if media.mediaType == 20>thumbnail.gif<#else>${media.thumbnail}</#if></#local>
${link?html}</#macro>

<#macro viewLinkWithThumbnail media>
<#-- default width and height -->
<#local defaultWidth = 60 />
<#local defaultHeight = 50 />
<#local realWidth = defaultWidth />
<#local realHeight = defaultHeight />
<#-- calculate real width and height according media's width and height -->
<#if media.width != 0 && media.height != 0>
<#if (defaultWidth/defaultHeight) &gt; (media.width/media.height)>
<#local realWidth = (defaultHeight*media.width/media.height)?round />
<#local realHeight = defaultHeight/>
<#else>
<#local realWidth = defaultWidth/>
<#local realHeight = (defaultWidth*media.height/media.width)?round />
</#if>
</#if>
<#local linkTitle>${media.title!?html}</#local>
<a href="${baseUrl}/view?m=${media.accessCode}" title="${linkTitle?html}">
    <#if media.thumbnail?has_content>
    <img src="<@thumbnailLink media/>"
         width="${realWidth?c}" height="${realHeight?c}"
         title="${linkTitle?html}"
         alt="${linkTitle?html}"/>
    <#elseif media.realFilename!?ends_with("mp3")>
    <img src="${baseUrl}/images/mp3.jpg" width="${defaultWidth?c}" height="${defaultHeight?c}"
         title="${linkTitle?html}"
         alt="${linkTitle?html}"/>
    <#else>
    <img src="${baseUrl}/images/general.gif" width="${defaultHeight?c}" height="${defaultHeight?c}"
         title="${linkTitle?html}"
         alt="${linkTitle?html}"/>
    </#if>
</a>
</#macro>


<#macro displayComment comment>
<li>
    <div>
        <a href="${baseUrl}/media.do?u=${comment.author.accessCode}">${comment.author.firstName} ${comment.author.lastName}</a>
        (${comment.msgTimePast})
        <a href="#" class="commentLink">Reply</a>

        <div>
            <a name="comment_${comment.id?c}"></a>
            ${comment.msg?html}
        </div>
    </div>
    <@displayCommentForm comment/>
    <#if comment.childComments?has_content>
    <ul>
        <#list comment.childComments as c>
        <@displayComment c/>
        </#list>
    </ul>
    </#if>
</li>
</#macro>

<#macro displayCommentForm comment>
<div class="commentForm">
    <form action="">
        <input type="hidden" name="mediaID" value="${obj.id?c}"/>
        <#if comment?? && comment!="">
        <input type="hidden" name="commentID" value="${comment.id?c}"/>
        </#if>
        <textarea cols="60" rows="4" name="msg"></textarea><br/>
        <input type="button" name="postCommentButton" value="Post Comment"/>
        <input type="button" name="discard" value="Discard"/>
        <span>Remaining character count:</span>
        <span>255</span>
    </form>
</div>
</#macro>
