<#--
    DO NOT REFORMAT CODE!!!
    IT WILL MESS UP EMBED CODE
-->

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
<#assign maxShortTitleLength = 16 />
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

<#assign pagesBeforeCurrent = 10 />
<#assign maxPages = 20 />

<#function getStartPage currentPage, totalPage>
    <#local startPage = 1/>
    <#if currentPage &gt; (pagesBeforeCurrent+1)>
        <#if totalPage &gt; (currentPage + maxPages - pagesBeforeCurrent -2) >
            <#local startPage = currentPage - pagesBeforeCurrent/>
            <#else>
                <#local startPage = totalPage - maxPages + 1/>
        </#if>
    </#if>
    <#return startPage/>
</#function>

<#function getEndPage currentPage, totalPage>
    <#local endPage = getStartPage(currentPage, totalPage) + maxPages -1/>
    <#if endPage &gt; totalPage>
        <#local endPage = totalPage/>
    </#if>
    <#return endPage/>
</#function>

<#--
    name: displayPager
    desc: dispaly pager when results need to display page by page
    @param: pager pager object
-->
<#macro displayPager pager>
    <#if pager.lastPageNumber &gt; 1>
    <div class="pageNumber">
        <ul>
            <#if (pager.pageNumber-1) &gt; 1>
                <li>
                    <a href="${this_url}?<#if searchWords??>q=${searchWords}&amp;</#if><#if pageSize??>s=${pager.pageSize?c}&amp;</#if><#if user??>u=${user.accessCode}&amp;</#if><#if mediaType??>t=${mediaType}&amp;</#if>p=1"
                       title="first page">
                        &lt;&lt; </a>
                </li>
            </#if>
            <#if pager.pageNumber &gt; 1>
                <li>
                    <a href="${this_url}?<#if searchWords??>q=${searchWords}&amp;</#if><#if pageSize??>s=${pager.pageSize?c}&amp;</#if><#if user??>u=${user.accessCode}&amp;</#if><#if mediaType??>t=${mediaType}&amp;</#if>p=${pager.pageNumber-1}"
                       title="prvious page">
                        &lt; </a>
                </li>
            </#if>
        <#-- if there are more than 20 pages, always display 20 pages.
             10 pages before current page and 9 pages after current page  -->
            <#local sPage=getStartPage(pager.pageNumber, pager.lastPageNumber)/>
            <#local ePage=getEndPage(pager.pageNumber, pager.lastPageNumber)/>
            <#list sPage..ePage as p>
                <#if p == pager.pageNumber>
                    <li class="current">${p}</li>
                    <#else>
                        <li>
                            <a href="${this_url}?<#if searchWords??>q=${searchWords}&amp;</#if><#if pageSize??>s=${pager.pageSize?c}&amp;</#if><#if user??>u=${user.accessCode}&amp;</#if><#if mediaType??>t=${mediaType}&amp;</#if>p=${p?c}">${p}</a>
                        </li>
                </#if>
            </#list>
            <#if pager.pageNumber &lt; pager.lastPageNumber>
                <li>
                    <a href="${this_url}?<#if searchWords??>q=${searchWords}&amp;</#if><#if pageSize??>s=${pager.pageSize?c}&amp;</#if><#if user??>u=${user.accessCode}&amp;</#if><#if mediaType??>t=${mediaType}&amp;</#if>p=${pager.pageNumber+1}"
                       title="next page">
                        &gt; </a>
                </li>
            </#if>
            <#if (pager.pageNumber+1) &lt; pager.lastPageNumber>
                <li>
                    <a href="${this_url}?<#if searchWords??>q=${searchWords}&amp;</#if><#if pageSize??>s=${pager.pageSize?c}&amp;</#if><#if user??>u=${user.accessCode}&amp;</#if><#if mediaType??>t=${mediaType}&amp;</#if>p=${pager.lastPageNumber}"
                       title="last page">
                        &gt;&gt; </a>
                </li>
            </#if>
        </ul>
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
            <a href="${this_url}?<#if pageSize??>s=${pager.pageSize?c}&amp;</#if>p=${pager.previousPageNumber?c}">&lt;
                Newer</a>
        </#if>
        <strong>${(pager.pageNumber-1)*pager.pageSize+1}
            - <#if pager.pageNumber == pager.lastPageNumber>${pager.totalNumberOfElements}<#else>${pager.pageNumber*pager.pageSize}</#if></strong> of <strong>${pager.totalNumberOfElements}</strong>
        <#if pager.pageNumber &lt; pager.lastPageNumber>
            <a href="${this_url}?<#if pageSize??>s=${pager.pageSize?c}&amp;</#if>p=${pager.nextPageNumber?c}">Older
                &gt;</a>
        </#if>
        <#if (pager.pageNumber+1) &lt; pager.lastPageNumber>
            <a href="${this_url}?<#if pageSize??>s=${pager.pageSize?c}&amp;</#if>p=${pager.lastPageNumber?c}">Oldest
                &gt;&gt;</a>
        </#if>
</span>
    </#if>
</#macro>

<#macro getShortTitle realTitle>
    <#if realTitle?? && (realTitle?length &gt; maxShortTitleLength)>${realTitle?substring(0, maxShortTitleLength-3)?html} ...<#else>${realTitle?html}</#if>
</#macro>

<#macro getTitle realTitle>
    <#if realTitle?? && (realTitle?length &gt; maxTitleLength)>${realTitle?substring(0, maxTitleLength-3)?html} ...<#else>${realTitle?html}</#if>
</#macro>

<#macro getDesc realDesc>
    <#if realDesc?? && (realDesc?length &gt; maxDescriptionLength)>${realDesc?replace("<.+>", " ", "r")?substring(0, maxDescriptionLength-3)} ...<#else>${realDesc}</#if>
</#macro>

<#--
    name: displayMediaInList
    desc: dispaly a media object in list, such as on media.do, search.do
    @param: entity a media object to be displayed
-->
<#macro displayMediaInList entity>
    <#if entity != '-'>
    <div class="mediaBox">
        <div class="mediaLine"></div>
        <div class="mediacontent">
            <div class="mediaImage">
            <@viewLinkWithThumbnail entity />
            </div>
            <div class="mediadetails">
                <a href="${baseUrl}/view?m=${entity.accessCode}" title="${entity.title!?html}">
                <@getShortTitle entity.title/>
                </a>
                <br/>
                From: <a
                    href="${baseUrl}/media.do?u=${entity.user.accessCode}"
                    title="${entity.user.firstName?html} ${entity.user.lastName?html}">${entity.user.firstName?html} ${entity.user.lastName?html}</a>
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

<#--
    name: getEmbedCode
    desc: get embed code for given media
    @param: media media object
-->
<#function getEmbedCode media>
    <#local embedCode=""/>
    <#if media?has_content>
        <#local embedURL>${context_url}/embed.do?m=${media.accessCode}</#local>
        <#local fileURL>${context_url}/file.do?m=${media.accessCode}</#local>
        <#local mediaFileLink>${fileURL}</#local>
        <#local viewURL>${context_url}/view?m=${media.accessCode}</#local>

    <#-- for image files -->
        <#if media.mediaType == 5>
            <#local imageFileLink>${mediaFileLink}</#local>
        <#-- if image is wider or higher than 800, display medium size by default -->
            <#if (media.width &gt; 800) || (media.height &gt; 800)>
                <#local mediaFileLink>${fileURL}&name=image-m.jpg</#local>
            </#if>
            <#if (media.width &gt; 1024) || (media.height &gt; 1024)>
                <#local largeImageFileLink>${fileURL}&name=image-l.jpg</#local>
                <#local imageFileLink>${largeImageFileLink}</#local>
            </#if>
            <#if (media.width &gt; 2048) || (media.height &gt; 2048)>
                <#local veryLargeImageFileLink>${fileURL}&name=image-v.jpg</#local>
            </#if>
            <#if (media.width &gt; 4096) || (media.height &gt; 4096)>
                <#local extraLargeImageFileLink>${fileURL}&name=image-e.jpg</#local>
            </#if>
        </#if>

    <#-- maximum width to display media file -->
        <#local MAX_WIDTH=800 />
    <#-- default width -->
        <#local width=320/>
        <#if media.width &gt; 0>
            <#local width=media.width/>
        </#if>
    <#-- default height -->
        <#local height=240/>
        <#if media.height &gt; 0>
            <#local height=media.height/>
        </#if>
        <#if width &gt; MAX_WIDTH>
            <#local height=(height*MAX_WIDTH/width)?round/>
            <#local width=MAX_WIDTH/>
        </#if>
        <#if media.mediaType == 10>
            <#local width=480/>
            <#local height=24/>
        </#if>

        <#-- for image files -->
        <#if media.mediaType == 5>
            <#local embedCode><a href="${imageFileLink?html}" title="${media.title?html}"><img src="${mediaFileLink?html}" width="${width?c}" height="${height?c}" alt="${media.title?html}" title="${media.title?html}"/></a></#local>
            <#else>
                <#local extraHeight=0>
                 <#if media.realFilename?? && media.realFilename?ends_with(".png")>
                    <#if media.duration &gt; 1>
                        <#local extraHeight=20>
                    </#if>
                 </#if>
                <#local embedCode><iframe width="${width?c}" height="${(height+extraHeight)?c}" src="${embedURL?html}" frameborder="0" allowfullscreen></iframe></#local>
        </#if>
        <#-- add "Hosted by UniTube" and link to embed code -->
        <#if media.accessType == 0>
            <#local embedCode><div id="__unitube_${media.id?c}" style="width:${width?c}px">${embedCode}<div style="text-align:center"><a href="${viewURL}">Hosted by UniTube</a></div></div></#local>
            <#else>
                <#local embedCode><div id="__unitube_${media.id?c}" style="width:${width?c}px">${embedCode}<div style="text-align:center"><a href="${context_url}">Hosted by UniTube</a></div></div></#local>
        </#if>
    </#if>
    <#return embedCode/>
</#function>
