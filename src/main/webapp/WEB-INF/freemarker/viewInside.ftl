<#-- don't reformat code!!! -->
<div class="stage">
<#if obj??>
<div>
    <strong>${obj.title?html}</strong>
    <#if isOwner>
        (
        <a href="${baseUrl}/myTube/edit.do?id=${obj.id?c}">Edit this media</a>
    <#--
     <#if obj.accessType == 20>
    |
    <a href="#" class="share">Share</a>
    </#if>
    -->
        )
    </#if>
</div>
<#-- if media status is waiting or processing, display message -->
    <#if (obj.status == 0) || (obj.status == 1)>
    <div class="info">
    <@spring.message "media.processing"/>
    </div>
    </#if>
<#-- if media status is unrecognized, display message -->
    <#if obj.status == 9>
    <div class="info">
    <@spring.message "media.unrecognized"/>
    </div>
    </#if>
<#-- if this media is upload only, display message -->
    <#if obj.uploadOnly>
    <div class="info">
    <@spring.message "media.uploadOnly"/>
    </div>
    </#if>

    <#include "viewHelper.ftl"/>

<#-- if media status is finished, is not upload only, and realFilename is not empty, display video, audio, flash or iamge -->
    <#if (obj.status ==2) && !obj.uploadOnly && obj.realFilename?has_content>
    <div class="mediaBorder">
        <#assign idDiv="unitube___media___"/>
        <div id="unitube_media">
            <#if obj.mediaType == 5>
                <#include "viewImage.ftl"/>
                <#elseif (obj.mediaType == 1) && obj.realFilename?ends_with(".png")>
                    <#include "viewImages.ftl"/>
                <#else>
                    <div id="${idDiv}">
                    <#--
                     html 5 by default.
                     will be replaced with flash if supported and up to date.
                    -->
                        <#if obj.mediaType == 10>
                            <#assign width=480/>
                            <#assign height=24/>
                        <#-- audio format -->
                            <audio width="${width?c}" height="${height?c}"
                                   src="${mediaFileLink?html}"
                                   controls preload="auto" id="avPlayer">
                                Your browser does not support the audio element.
                            </audio>
                            <#elseif obj.mediaType == 20>
                            <#-- video format-->
                                <video width="${width?c}" height="${height?c}"
                                       src="${mediaFileLink?html}"
                                    <#if thumnailFileLink?has_content>
                                       poster="${thumnailFileLink?html}"
                                    </#if>
                                       controls preload="auto" id="avPlayer">
                                    Your browser does not support the video element.
                                </video>
                            <#else>
                            <#-- flash format-->
                                You need to install Adobe Flash Player.<br/>
                                <a href="http://www.macromedia.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash">Click
                                    here to download Adobe Flash Player</a>
                        </#if>
                    </div>
            </#if>
        </div>
    </div>
    <div class="clearMe"></div>
    <#-- javascript here to display flash, or jwplay to play video and audio -->
        <#if obj.realFilename?ends_with(".swf")> <#-- flash -->
            <#include "viewFlash.ftl"/>
            <#elseif obj.mediaType == 10> <#-- audio -->
                <#include "viewAudio.ftl"/>
            <#elseif obj.mediaType == 20> <#-- video -->
                <#include "viewVideo.ftl"/>
        </#if>

    <!--
    // DEBUG INFO
    // original size: ${obj.width}x${obj.height}
    // display size:${width}x${height}
    // flowPlayer=${flowPlayer}
    // JWPLAYER=${JWPLAYER}
    // originalFileLink=${originalFileLink!}
    // mediaFileLink=${mediaFileLink!}
    // otherFormatFileLink=${otherFormatFileLink!}
-->
    </#if>

<#-- for finished image files -->
    <#if (obj.mediaType == 5) && (obj.status == 2)>
    <p>
        <a href="${baseUrl}/imageViewer.do?i=${obj.accessCode}">View Image in ImageViewer</a>
        <#if isOwner?has_content || !ivOption??  || ivOption.otherCanAnnotate>
            |
            <a href="${baseUrl}/myTube/annotAuthor.do?i=${obj.id?c}">Add New Annotation</a>
        </#if>
    </p>
    </#if>

    <#if obj.description?has_content>
    <p>
        <span class="title">Description:</span>
    </p>

    <div>
    ${obj.description}
    </div>
    </#if>

<p>
    <span class="title">From:</span>
    <a href="${baseUrl}/media.do?u=${obj.user.accessCode}">${obj.user.firstName} ${obj.user.lastName}</a>
    <a href="${baseUrl}/feed.do?topic=media&amp;u=${obj.user.accessCode}"
       title="Feed for ${obj.user.firstName?html} ${obj.user.lastName?html}">
        <img src="${baseUrl}/images/feed-icon.png" alt="Feed for ${obj.user.firstName?html} ${obj.user.lastName?html}"
             title="Feed for ${obj.user.firstName?html} ${obj.user.lastName?html}"/>
    </a>
</p>

<p>
    <span class="title">Views:</span> ${obj.accessTimes}
</p>

<p><span class="title">Added:</span> ${obj.uploadTimePast}</p>

    <#if obj.albumMedias?has_content>
    <p><span class="title">Album(s):</span>
        <#list obj.albumMedias as albumMedia>
            <a href="${baseUrl}/album?a=${albumMedia.album.accessCode}">${albumMedia.album.albumName?html}</a>
        </#list>
    </p>
    </#if>

    <#assign linkTitle>${obj.title!?url}</#assign>
    <#assign linkTags>${obj.tags!?url}</#assign>
<div class="sociable">
    <span class="title"
          title="These icons link to social bookmarking sites where readers can share and discover new web pages.">Share and Enjoy:</span>
    <ul>
        <li><a rel="nofollow" target="_blank"
               href="http://digg.com/submit?phase=2&amp;url=${viewURL?url}&amp;title=${linkTitle}" title="Digg">
            <img src="${baseUrl}/images/sociable/digg.png" title="Digg" alt="Digg" class="sociable-hovers"/></a></li>
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
            <img src="${baseUrl}/images/sociable/facebook.png" title="Facebook" alt="Facebook" class="sociable-hovers"/></a>
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
        <li><a rel="nofollow" target="_blank" href="http://twitthis.com/twit?url=${viewURL?url}"
               title="TwitThis">
            <img src="${baseUrl}/images/sociable/twitter.png" title="TwitThis" alt="TwitThis" class="sociable-hovers"/></a>
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
<#-- hide embed for private media file -->
    <#if obj.accessType != 20>
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

<p>
    <span class="title">Download:</span>
<#-- proviod original file link for upload only, swf, unrecognized, images-->
    <#if obj.uploadOnly || obj.realFilename!?ends_with(".swf") || (obj.status == 9) || (obj.mediaType == 5) || obj.realFilename!?ends_with(".png")>
        <a href="${originalFileLink?html}">original file</a>
        <#elseif mediaFileLink?has_content>
            <a href="${mediaFileLink?html}">media file</a>
    </#if>
<#-- for image files, display different size images -->
    <#if obj.mediaType == 5>
        <#if mediaFileLink??>
            | <a href="${mediaFileLink?html}">medium size</a>
        </#if>
        <#if largeImageFileLink??>
            | <a href="${largeImageFileLink?html}">large size</a>
        </#if>
        <#if veryLargeImageFileLink??>
            | <a href="${veryLargeImageFileLink?html}">very large size</a>
        </#if>
        <#if extraLargeImageFileLink??>
            | <a href="${extraLargeImageFileLink?html}">extra large size</a>
        </#if>
    </#if>
    <#if obj.convertTo?has_content && otherFormatFileLink?has_content>
        | <a href="${otherFormatFileLink?html}">MPEG format</a>
    </#if>
</p>

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
        $('input.linkURL').click(function() {
            $(this).focus();
            $(this).select();
        });

        displayComment();
    <#--
    <#if isOwner && obj.accessType == 20>
        shareDialog();
        // when clicking "share" link for private media file
        $('a.share').click(function() {
            // set mediaID
            $('#ar_mediaID').val("${obj.id?c}");
            // open share dialog
            $("#share-dialog").dialog('open');
            $('#userInput').focus();
            return false;
        });
    </#if>
    -->
    });
    //-->
</script>

    <#else>
    <div class="info">
        <#if accessDenied>
    <@spring.message "media.access.deny"/>
    <#else>
        <@spring.message "media.not.found"/>
        </#if>
    </div>
</#if>
</div>
