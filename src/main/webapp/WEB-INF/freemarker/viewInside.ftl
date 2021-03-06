<div class="stage">
<#if obj?? && obj.status &gt; 1>
<div>
    <strong>${obj.title?html}</strong>
    <#if isOwner>
        &nbsp;
        <a href="${baseUrl}/myTube/edit.do?id=${obj.id?c}">Edit this media</a>
    </#if>
</div>
    <div class="info">
<#-- if media status is unrecognized, display message -->
    <#if obj.status == 9>
    <@spring.message "media.unrecognized"/>
<#-- if this media is upload only, display message -->
    <#elseif obj.uploadOnly>
    <@spring.message "media.uploadOnly"/>
<#-- if can not find media file, display message -->
    <#elseif !fileExists>
    <@spring.message "media.file.not.found"/>
    </#if>
    </div>

    <#include "viewHelper.ftl"/>

<#-- if media status is finished, is not upload only, and realFilename is not empty, display video, audio, flash or image -->
    <#if (obj.status ==2) && !obj.uploadOnly && obj.realFilename?has_content && fileExists>
    <div class="mediaBorder">
        <div id="unitube_media">
            <@viewMedia obj/>
        </div>
    </div>
    <div class="clearMe"></div>
    </#if>

    <#if obj.description?has_content>
    <p>
        <span class="title">Description:</span>
    </p>

    <div class="description">
    ${obj.description}
    </div>
    <div class="moreOrFewer">
        <a href="#" class="showMore">+ Show more</a>
        <a href="#" class="showFewer">- Show fewer</a>
    </div>
    </#if>

<div id="left">
    <p>
        <span class="title">From:</span>
        <a href="${baseUrl}/media.do?u=${obj.user.accessCode}">${obj.user.firstName} ${obj.user.lastName}</a>
    <#--
    <a href="${baseUrl}/feed.do?topic=media&amp;u=${obj.user.accessCode}"
       title="Feed for ${obj.user.firstName?html} ${obj.user.lastName?html}">
        <img src="${baseUrl}/images/feed-icon.png" alt="Feed for ${obj.user.firstName?html} ${obj.user.lastName?html}"
             title="Feed for ${obj.user.firstName?html} ${obj.user.lastName?html}"/>
    </a> -->
    <span title="${obj.uploadTime?string('dd/MM/yyyy hh:mm:ss')}">${obj.uploadTimePast}</span>
    </p>
    <#assign linkTitle>${obj.title!?url}</#assign>
    <#assign linkTags>${obj.tags!?url}</#assign>
    <div class="sociable">
    <span class="title"
          title="These icons link to social bookmarking sites where readers can share and discover new web pages.">Share and Enjoy:</span>
        <ul>
            <li><a rel="nofollow" target="_blank"
                   href="http://digg.com/submit?phase=2&amp;url=${viewURL?url}&amp;title=${linkTitle}" title="Digg">
                <img src="${baseUrl}/images/sociable/digg.png" title="Digg" alt="Digg" class="sociable-hovers"/></a>
            </li>
        <#--
        <li><a rel="nofollow" target="_blank"
               href="http://sphinn.com/submit.php?url=${viewURL?url}&amp;title=${linkTitle}" title="Sphinn">
            <img src="${baseUrl}/images/sociable/sphinn.png" title="Sphinn" alt="Sphinn" class="sociable-hovers"/></a></li>
        -->
            <li><a rel="nofollow" target="_blank"
                   href="http://del.icio.us/post?url=${viewURL?url}&amp;title=${linkTitle}&amp;tags=${linkTags}"
                   title="del.icio.us">
                <img src="${baseUrl}/images/sociable/delicious.png" title="del.icio.us" alt="del.icio.us"
                     class="sociable-hovers"/></a></li>
            <li><a rel="nofollow" target="_blank"
                   href="http://www.facebook.com/sharer.php?u=${viewURL?url}&amp;t=${linkTitle}" title="Facebook">
                <img src="${baseUrl}/images/sociable/facebook.png" title="Facebook" alt="Facebook"
                     class="sociable-hovers"/></a>
            </li>
        <#--
        <li><a rel="nofollow" target="_blank" href="http://www.mixx.com/submit?page_url=${viewURL?url}&amp;title=${linkTitle}" title="Mixx">
            <img src="${baseUrl}/images/sociable/mixx.png" title="Mixx" alt="Mixx" class="sociable-hovers"/></a></li>
        -->
            <li><a rel="nofollow" target="_blank"
                   href="http://www.google.com/bookmarks/mark?op=edit&amp;bkmk=${viewURL?url}&amp;title=${linkTitle}&amp;labels=${linkTags}"
                   title="Google">
                <img src="${baseUrl}/images/sociable/googlebookmark.png" title="Google" alt="Google"
                     class="sociable-hovers"/></a></li>
        <#--
        <li><a rel="nofollow" target="_blank" href="http://blogmarks.net/my/new.php?mini=1&amp;simple=1&amp;url=${viewURL?url}&amp;title=${linkTitle}" title="blogmarks">
            <img src="${baseUrl}/images/sociable/blogmarks.png" title="blogmarks" alt="blogmarks" class="sociable-hovers" /></a></li>
        <li><a rel="nofollow" target="_blank" href="http://bluedot.us/Authoring.aspx?>u=${viewURL?url}&amp;title=${linkTitle}" title="Blue Dot">
            <img src="${baseUrl}/images/sociable/bluedot.png" title="Blue Dot" alt="Blue Dot" class="sociable-hovers" /></a></li>
        <li><a rel="nofollow" target="_blank" href="http://www.bumpzee.com/bump.php?u=${viewURL?url}" title="Bumpzee">
            <img src="${baseUrl}/images/sociable/bumpzee.png" title="Bumpzee" alt="Bumpzee" class="sociable-hovers" /></a></li>
        <li><a rel="nofollow" target="_blank" href="http://www.furl.net/storeIt.jsp?u=${viewURL?url}&amp;t=${linkTitle}" title="Furl">
            <img src="${baseUrl}/images/sociable/furl.png" title="Furl" alt="Furl" class="sociable-hovers" /></a></li>
        <li><a rel="nofollow" target="_blank" href="http://ma.gnolia.com/bookmarklet/add?url=${viewURL?url}&amp;title=${linkTitle}" title="Ma.gnolia">
            <img src="${baseUrl}/images/sociable/magnolia.png" title="Ma.gnolia" alt="Ma.gnolia" class="sociable-hovers" /></a></li>
        <li><a rel="nofollow" target="_blank" href="http://www.mister-wong.com/addurl/?bm_url=${viewURL?url}&amp;bm_description=${linkTitle}&amp;plugin=soc" title="MisterWong">
            <img src="${baseUrl}/images/sociable/misterwong.gif" title="MisterWong" alt="MisterWong" class="sociable-hovers" /></a></li>
        <li><a rel="nofollow" target="_blank" href="http://www.propeller.com/submit/?U=${viewURL?url}&amp;T=${linkTitle}" title="Propeller">
            <img src="${baseUrl}/images/sociable/propeller.gif" title="Propeller" alt="Propeller" class="sociable-hovers" /></a></li>
        <li><a rel="nofollow" target="_blank" href="http://reddit.com/submit?url=${viewURL?url}&amp;title=${linkTitle}" title="Reddit">
            <img src="${baseUrl}/images/sociable/reddit.png" title="Reddit" alt="Reddit" class="sociable-hovers" /></a></li>
        <li><a rel="nofollow" target="_blank" href="http://www.simpy.com/simpy/LinkAdd.do?href=${viewURL?url}&amp;title=${linkTitle}" title="Simpy">
            <img src="${baseUrl}/images/sociable/simpy.png" title="Simpy" alt="Simpy" class="sociable-hovers" /></a></li>
        <li><a rel="nofollow" target="_blank" href="http://www.stumbleupon.com/submit?url=${viewURL?url}&amp;title=${linkTitle}" title="StumbleUpon">
            <img src="${baseUrl}/images/sociable/stumbleupon.png" title="StumbleUpon" alt="StumbleUpon" class="sociable-hovers" /></a></li>
        -->
            <li><a rel="nofollow" target="_blank" href="https://twitter.com/intent/tweet?url=${viewURL?url}&amp;text=${linkTitle}"
                   title="TwitThis">
                <img src="${baseUrl}/images/twitter.png" title="Twitter" alt="Twitter"
                     class="sociable-hovers"/></a>
            </li>
        <#--
        <li><a rel="nofollow" target="_blank" href="http://www.wikio.com/vote?url=${viewURL?url}" title="Wikio">
            <img src="${baseUrl}/images/sociable/wikio.gif" title="Wikio" alt="Wikio" class="sociable-hovers" /></a></li>
        <li><a rel="nofollow" target="_blank" href="http://myweb2.search.yahoo.com/myresults/bookmarklet?u=${viewURL?url}&amp;=${linkTitle}" title="YahooMyWeb">
            <img src="${baseUrl}/images/sociable/yahoomyweb.png" title="YahooMyWeb" alt="YahooMyWeb" class="sociable-hovers" /></a></li>
        <li><a rel="nofollow" target="_blank" href="http://www.blinklist.com/index.php?Action=Blink/addblink.php&amp;Url=${viewURL?url}&amp;Title=${linkTitle}" title="BlinkList">
            <img src="${baseUrl}/images/sociable/blinklist.png" title="BlinkList" alt="BlinkList" class="sociable-hovers" /></a></li>
        <li><a rel="nofollow" target="_blank" href="http://www.newsvine.com/_tools/seed&amp;save?u=${viewURL?url}&amp;h=${linkTitle}" title="NewsVine">
            <img src="${baseUrl}/images/sociable/newsvine.png" title="NewsVine" alt="NewsVine" class="sociable-hovers" /></a></li>
        -->
            <li><a rel="nofollow" target="_blank"
                   href="http://www.bibsonomy.org/ShowBookmarkEntry?url=${viewURL?url}&amp;description=${linkTitle}"
                   title="BibSonomy">
                <img src="${baseUrl}/images/sociable/bibsonomy.png" title="BibSonomy" alt="BibSonomy"
                     class="sociable-hovers"/></a></li>
        </ul>
    </div>
    <p>
        <span class="title">Download:</span>
    <#-- proviod original file link for upload only, swf, unrecognized, images-->
        <#if obj.uploadOnly || obj.realFilename!?ends_with(".swf") || (obj.status == 9) || (obj.mediaType == 5) || obj.realFilename!?ends_with(".png")>
        <#if originalFileLink?has_content>
            <a href="${originalFileLink?html}&amp;t=download">original file</a>
        </#if>
            <#elseif mediaFileLink?has_content>
                <a href="${mediaFileLink?html}&amp;t=download">media file</a>
        </#if>
    <#-- for image files, display different size images -->
        <#if obj.mediaType == 5>
            <#if mediaFileLink??>
                | <a href="${mediaFileLink?html}&amp;t=download">medium size</a>
            </#if>
            <#if largeImageFileLink??>
                | <a href="${largeImageFileLink?html}&amp;t=download">large size</a>
            </#if>
            <#if veryLargeImageFileLink??>
                | <a href="${veryLargeImageFileLink?html}&amp;t=download">very large size</a>
            </#if>
            <#if extraLargeImageFileLink??>
                | <a href="${extraLargeImageFileLink?html}&amp;t=download">extra large size</a>
            </#if>
        </#if>
        <#if obj.convertTo?has_content && otherFormatFileLink?has_content>
            | <a href="${otherFormatFileLink?html}&amp;t=download">MPEG format</a>
        </#if>
    </p>
    <#if obj.albumMedias?has_content>
        <p><span class="title">Album(s):</span>
            <#list obj.albumMedias as albumMedia>
                <a href="${baseUrl}/album?a=${albumMedia.album.accessCode}">${albumMedia.album.albumName?html}</a>
                <#if albumMedia_has_next>|</#if>
            </#list>
        </p>
    </#if>
<#-- QR code
<img src="https://chart.googleapis.com/chart?chs=150x150&cht=qr&chl=${viewURL?html}" alt="QR code" title=""/>
-->
</div>

<div id="right">
    <p>
        <span class="title">Views:</span> ${obj.accessTimes}
    </p>

    <form action="" name="linkForm">
        <#if viewURL?has_content>
            <p>
                <span class="title">URL:</span>
                <input type="text"
                       value="${viewURL?html}"
                       readonly="readonly"
                       size="50"
                       class="linkURL"
                        />
            </p>
        </#if>
    <#-- hide embed for private and upload only media file  -->
        <#if (obj.accessType != 20) && !obj.uploadOnly>
            <p>
                <span class="title">Embed:</span>
                <input type="text"
                       value="${getEmbedCode(obj)?html}"
                       readonly="readonly"
                       size="50"
                       class="linkURL"
                        />
                <span title="To embed a media file, copy this code from the &quot;Embed&quot; box and paste it into your web page to embed it">?</span>
            </p>
        </#if>
    </form>
<#-- for finished image files -->
    <#if (obj.mediaType == 5) && (obj.status == 2)>
        <p>
            <span class="title">Image Viewer:</span>
            <a href="${baseUrl}/imageViewer.do?i=${obj.accessCode}">View Image in ImageViewer</a>
            <#if isOwner?has_content || !ivOption??  || ivOption.otherCanAnnotate>
                |
                <a href="${baseUrl}/myTube/annotAuthor.do?i=${obj.id?c}">Add New Annotation</a>
            </#if>
        </p>
    </#if>
</div>
    <#include "viewComment.ftl"/>
<#-- change share settings
<#if isOwner && obj.accessType == 20>
<#assign owner = obj.user/>
<#assign accessRules = obj.accessRules/>
<#include "shareDialog.ftl"/>
</#if>
-->

<script type="text/javascript">
    <!--
    $(function() {
        // if has description
        if ($('div.description').length > 0) {
            var max_height = 80;
            var d_height = $('div.description').height();
            var d_padding_bottom = parseInt($('div.description').css('padding-bottom'));
            var d_scrollHeight = $('div.description')[0].scrollHeight;
            log('height = ' + d_height + " padding-bottom = " + d_padding_bottom + " scrollHeight = " + d_scrollHeight);
            // if description is very short, just display normally
            if (d_height < max_height) {
                $('a.showMore').hide();
                $('a.showFewer').hide();
            } else {
                // otherwise, set height to max height, and display "Show more" link
                $('div.description').css('height', max_height + 'px');
                var d_height = $('div.description').height();
                $('a.showMore').show();
                $('a.showFewer').hide();
                $('a.showMore').click(function() {
                    $('div.description').css('height', (d_scrollHeight - d_padding_bottom) + 'px');
                    showMoreOrFewer();
                    return false;
                });
                $('a.showFewer').click(function() {
                    $('div.description').css('height', d_height + 'px');
                    showMoreOrFewer();
                    return false;
                });
                function showMoreOrFewer() {
                    if ($('div.description').height() >= (d_scrollHeight - d_padding_bottom)) {
                        $('a.showMore').hide();
                        $('a.showFewer').show();
                    } else {
                        $('a.showMore').show();
                        $('a.showFewer').hide();
                    }
                }
            }
        }

        $('input.linkURL').click(function() {
            $(this).focus();
            $(this).select();
        });

        displayComment();
    });
    //-->
</script>
    <#else>
    <div class="info">
        <#if accessDenied>
    <@spring.message "media.access.deny"/>
    <#elseif obj?? && ((obj.status == 0) || (obj.status == 1))>
<#-- if media status is waiting or processing, display message -->
    <@spring.message "media.processing"/>
    <#else>
        <@spring.message "media.record.not.found"/>
        </#if>
    </div>
</#if>
</div>
