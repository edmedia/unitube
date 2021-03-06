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
<#assign maxDescriptionLength = 128 />

<#--
    name: displayMediaList2
    desc: display media list, such as on media.do, search.do
    @param: elements list of media object to be displayed
-->
<#macro displayMediaList2 elements>
    <#if elements?has_content>
    <table summary="" width="100%">
        <tbody>
            <#list elements as entity>
                <#assign linkTitle>${entity.title!?html}</#assign>
            <tr>
                <td width="70"><@viewLinkWithThumbnail entity/></td>
                <td>
                    <a href="${baseUrl}/view?m=${entity.accessCode}"
                       title="${linkTitle}">${linkTitle}</a>
                    <span style="font-size: small; float: right; color: #666">${entity.uploadTimePast}</span>
                    <br/>
                    <span class="title">From:</span>
                    <a href="${baseUrl}/media.do?u=${entity.user.accessCode}">${entity.user.firstName} ${entity.user.lastName}</a>

                    <#if entity.duration != 0>
                        <br/>
                        <span class="title">Length: </span>
                        <#if entity.provider == 'image'>
                        ${entity.duration}
                            <#else>
                            ${getTimeCode(entity.duration)}
                        </#if>
                    </#if>
                    <#if entity.description?has_content>
                        <div><@displayBrief entity.description maxDescriptionLength/></div>
                    </#if>
                </td>
            </tr>
            </#list>
        </tbody>
    </table>
        <#else>
        <div class="stage">
            <div class="info"><@spring.message "no.media"/></div>
        </div>
    </#if>
</#macro>

<#--
    name: displayAlbumList
    desc: display album list, such as on search.do
    @param: elements list of album object to be displayed
-->
<#macro displayAlbumList elements>
    <#if elements?has_content>
    <table summary="" width="100%">
        <tbody>
            <#list elements as album>
            <tr>
                <td width="70"><a href="${baseUrl}/album?a=${album.accessCode}">
                    <img src="${baseUrl}/images/albums.png" alt=""/>
                </a></td>
                <td>
                    <a href="${baseUrl}/album?a=${album.accessCode}"
                       title="${album.albumName!?html}">${album.albumName!?html} (${album.mediaNum})</a>

                    <br/>
                    <span class="title">From:</span>
                    <a href="${baseUrl}/media.do?u=${album.owner.accessCode}">${album.owner.firstName} ${album.owner.lastName}</a>

                    <#if album.description?has_content>
                        <div><@displayBrief album.description maxDescriptionLength/></div>
                    </#if>
                </td>
            </tr>
            </#list>
        </tbody>
    </table>
        <#else>
        <div class="stage">
            <div class="info"><@spring.message "no.album"/></div>
        </div>
    </#if>
</#macro>

<#--
    name: displayUserList
    desc: display user list, such as on search.do
    @param: elements list of user object to be displayed
-->
<#macro displayUserList elements>
    <#if elements?has_content>
    <table summary="" width="100%">
        <tbody>
            <#list elements as user>
            <tr>
                <td width="70"><a href="${baseUrl}/media.do?u=${user.accessCode}">
                    <img src="${baseUrl}/images/unitubas.png" alt=""/>
                </a></td>
                <td>
                    <a href="${baseUrl}/media.do?u=${user.accessCode}">
                    ${user.firstName?html}  ${user.lastName?html}   (${user.mediaNum})
                    </a>

                </td>
            </tr>
            </#list>
        </tbody>
    </table>
        <#else>
        <div class="stage">
            <div class="info"><@spring.message "no.media"/></div>
        </div>
    </#if>
</#macro>

<#--
    name: displayMediaList
    desc: dispaly media list, such as on media.do, search.do
    @param: elements list of media object to be displayed
-->
<#macro displayMediaList elements>
    <#if elements?has_content>
    <div class="mediaDisplay2">
        <#list elements as entity>
        <@displayMediaInList entity />
        </#list>
    </div>
        <#else>
        <div class="stage">
            <div class="info"><@spring.message "no.media"/></div>
        </div>
    </#if>
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
    name: displayBrief
    desc: display brief information, up to MAX_LEN characters. all html tags will be removed.
    @param: content content to display
    @param: MAX_LEN maximum characters to display, 0 means display all
-->
<#macro displayBrief content MAX_LEN>
<#-- replace &nbsp; with space -->
    <#local brief = content?replace("&nbsp;", " ")/>
<#-- replace open html tag "<blah blah="blah ...">" with space -->
    <#local brief = brief?replace("<\\w+[^>]*>", " ", "rm")/>
<#-- replace open html tag "</blah blah="blah ...">" with space -->
    <#local brief = brief?replace("<\\/\\w+[^>]*>", " ", "rm")/>
<#-- replace any empty characters with only one space -->
    <#local brief = brief?replace("\\s+", " ", "rm")/>
    <#if MAX_LEN &gt; 0>
        <#if brief?length &gt; MAX_LEN>
            <#local index = MAX_LEN/>
            <#if brief?contains(" ")>
                <#local index = brief?index_of(" ")/>
                <#list 0..MAX_LEN as i>
                    <#local next = brief?index_of(" ", index+1)/>
                    <#if next == -1>
                        <#break />
                    </#if>
                    <#if next &gt; MAX_LEN>
                        <#break />
                        <#else>
                            <#local index = next/>
                    </#if>
                </#list>
            </#if>
        <#-- only return MAX_LEN characters -->
            <#local brief = brief?substring(0, index) + " ..."/>
        </#if>
    </#if>
<#-- output content -->
${brief}
</#macro>

<#--
    name: displayContent
    desc: display content. If there is html tag inside, display as is, otherwise, replace \n with <br/>
    @param: content content to display
-->
<#macro displayContent content>
    <#if content?matches("<\\w+>", "m") || content?matches("<\\w+[^>]*>", "m")>
    ${content}
        <#else>
        ${content!?html?replace("\n", "<br/>")}
    </#if>
</#macro>

<#--
    name: displayMediaInList
    desc: display a media object in list, such as on media.do, search.do
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

<#--
    name: displayAVPInList
    desc: display an AVP object in list, such as on avps.do
    @param: entity an AVP object to be displayed
-->
<#macro displayAVPInList entity>
    <#if entity != '-'>
    <div class="mediaBox">
        <div class="mediaLine"></div>
        <div class="mediacontent">
            <div class="mediaImage">
                <#if entity.av1?? && (entity.av1.mediaType == 20)>
                <@viewAVPLinkWithThumbnail entity.av1 entity />
                <#elseif entity.presentation??>
                <@viewAVPLinkWithThumbnail entity.presentation entity />
                </#if>
            </div>
            <div class="mediadetails">
                <a href="avp.do?a=${entity.accessCode}">
                <@getShortTitle entity.meaningfulName/>
                </a>
                <br/>
                From: <a
                    href="${baseUrl}/media.do?u=${entity.owner.accessCode}"
                    title="${entity.owner.firstName?html} ${entity.owner.lastName?html}">${entity.owner.firstName?html} ${entity.owner.lastName?html}</a>
            </div>
        </div>
    </div>
        <#else>
        &nbsp;
    </#if>
</#macro>

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
        <#elseif media.mediaType == 10>  <#-- for audio file -->
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

<#macro getThumbnail media>
<#-- default width and height -->
    <#local defaultWidth = 80 />
    <#local defaultHeight = 50 />
    <#local realWidth = defaultWidth />
    <#local realHeight = defaultHeight />
    <#local useWidth = false/>
<#-- calculate real width and height according media's width and height -->
    <#if media.width != 0 && media.height != 0>
        <#if (defaultWidth/defaultHeight) &gt; (media.width/media.height)>
            <#local useWidth = true/>
            <#local realWidth = defaultWidth/>
            <#local realHeight = (defaultWidth*media.height/media.width)?round />
            <#else>
                <#local useWidth = false/>
                <#local realWidth = (defaultHeight*media.width/media.height)?round />
                <#local realHeight = defaultHeight/>
        </#if>
    </#if>
    <#local linkTitle>${media.title!?html}</#local>
<a href="${baseUrl}/view?m=${media.accessCode}" title="${linkTitle?html}">
    <img
        <#if useWidth>
                width="${defaultWidth?c}"
            <#if realHeight &gt; defaultHeight>
                style="margin-top: -${((realHeight-defaultHeight)/2)?round?c}px"
            </#if>
            <#else>
                height="${defaultHeight?c}"
                <#if realWidth &gt; defaultWidth>
                style="margin-left: -${((realWidth-defaultWidth)/2)?round?c}px"
                </#if>
        </#if>
        <#if media.mediaType == 20> <#-- for video file -->
                src="${baseUrl}/file.do?m=${media.accessCode}&name=${media.thumbnail}"
            <#elseif media.mediaType == 10>  <#-- for audio file -->
                src="${baseUrl}/images/mp3.jpg"
            <#elseif media.mediaType == 5>  <#-- for image file -->
                src="${baseUrl}/file.do?m=${media.accessCode}"
            <#elseif media.mediaType == 1>  <#-- for other file -->
                src="${baseUrl}/file.do?m=${media.accessCode}"
        </#if>
                title="${linkTitle?html}"
                alt="${linkTitle?html}"/>
</a>
</#macro>

<#macro viewAVPLinkWithThumbnail media avp>
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
<a href="${baseUrl}/avp.do?a=${avp.accessCode}" title="${linkTitle?html}">
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
        <a href="#" class="commentLink" name="comment_${comment.id?c}">Reply</a>

        <div>
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

<#macro viewMedia obj>
    <#assign idDiv="unitube___media___${obj.id?c}"/>
<div id="${idDiv}" class="media">
    <#if obj.mediaType == 5>
        <#include "../viewImage.ftl"/>
        <#elseif (obj.mediaType == 1) && obj.realFilename?ends_with(".png")>
            <#include "../viewImages.ftl"/>
        <#else>
            <#if (obj.mediaType == 10) || (obj.mediaType ==20)>
                <div id="avPlayer__${obj.id?c}">
                    Your browser can not support this file. <a href="${mediaFileLink?html}&amp;t=download">Click here to
                    download this file.</a>
                </div>
                <#else>
                <#-- flash format-->
                    You need to install Adobe Flash Player.<br/>
                    <a href="http://www.macromedia.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash">
                        Click here to download Adobe Flash Player</a>
            </#if>
    </#if>
</div>
<#-- javascript here to display flash, or jwplay to play video and audio -->
    <#if obj.realFilename?ends_with(".swf")> <#-- flash -->
        <#include "../viewFlash.ftl"/>
        <#elseif (obj.mediaType == 10) || (obj.mediaType == 20)> <#-- audio/video -->
            <#include "../viewAV.ftl"/>
    </#if>
<!--
    // DEBUG INFO
    // original size: ${obj.width}x${obj.height}
    // display size:${width}x${height}
    // JWPLAYER=${JWPLAYER}
    // originalFileLink=${originalFileLink!}
    // mediaFileLink=${mediaFileLink!}
    // otherFormatFileLink=${otherFormatFileLink!}
-->
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

<#function getTimeCode timeInMilliseconds>
    <#local tt = ''/>
    <#local h = (timeInMilliseconds/1000/60/60)?floor />
    <#if h &gt; 0>
        <#local tt>${h}:</#local>
    </#if>
    <#local m = (timeInMilliseconds/1000/60 - h*60)?floor/>
    <#if m &lt; 10>
        <#local tt>${tt}0${m}:</#local>
        <#else>
            <#local tt>${tt}${m}:</#local>
    </#if>
    <#local s = (timeInMilliseconds/1000 - h*60*60 - m*60)?floor/>
    <#if s &lt; 10>
        <#local tt>${tt}0${s}</#local>
        <#else>
            <#local tt>${tt}${s}</#local>
    </#if>
    <#return tt/>
</#function>
