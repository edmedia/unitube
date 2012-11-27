<#if playlist?has_content>
    <#if !playerWidth??>
        <#assign playerWidth = 480/>
    </#if>
    <#if !playerHeight??>
        <#assign playerHeight = 360/>
    </#if>
    <#if !playlistNav??>
        <#assign playlistNav = 260/>
    </#if>
    <#assign id>mediaplayer_${.now?string("yyyyMMddHHmmssS")}</#assign>
<div id='${id}'>
    <div class="playlistPlayer">
        <#if playlist?has_content>
            <ul>
                <#list playlist as obj>
                    <li class="player">
                        <#include "viewHelper.ftl"/>
                        <#assign width =  playerWidth/>
                        <#assign height = playerHeight/>
                    <@viewMedia obj/>
                    </li>
                </#list>
            </ul>
        </#if>
    </div>

    <div class="playlistNav">
        <#if playlist?has_content>
            <ul>
                <#list playlist as media>
                    <li data-index="${media_index?c}" data-id="${media.id?c}"
                        data-type="<#if (media.mediaType ==10) || (media.mediaType == 20)>av</#if>">
                        <div>
                    <span class="thumbnail">
                    <@getThumbnail media/>
                    </span>
                     <span class="mediaDetail">
                     <strong><@getShortTitle  media.title/></strong>
                         <br/>
                     ${media.user.firstName?html}  ${media.user.lastName?html}
                     </span>

                            <div class="clearMe"/>

                        </div>
                    </li>
                </#list>
            </ul>
        </#if>

    </div>
    <div class="clearMe"></div>

</div>

<#--
<div>
    <#if playlist?has_content>
        <#list playlist as media>
            <#assign file>${context_url}/file.do?m=${media.accessCode}</#assign>
            <#if media.provider == 'image'>
                <#assign file>${context_url}/view?m=${media.accessCode}</#assign>
            </#if>
            <#assign image>${context_url}/file.do?m=${media.accessCode}</#assign>
            <#if media.mediaType == 1>
                <#assign image>${context_url}/file.do?m=${media.accessCode}&name=${media.realFilename}</#assign>
            </#if>
            <#if media.provider == 'video'>
                <#assign image>${context_url}/file.do?m=${media.accessCode}&name=${media.thumbnail}</#assign>
            </#if>
            <#if media.provider == 'sound'>
                <#assign image>${context_url}/images/mp3.jpg</#assign>
            </#if>
            {
            'file': '${file}',
            'title': "${media.title?html}",
            'description': "${media.user.firstName} ${media.user.lastName}",
            'provider': '${media.provider}',
            <#if (media.provider != 'image') && (media.duration != 0)>
                'duration': '${media.duration}',
            </#if>
            'image': '${image}'
            }<#if media_has_next>,</#if>
        </#list>
    </#if>
</div>
  -->

<script type="text/javascript">
    var embed = (document.URL.indexOf("embedPlaylist") != -1);
    var playerWidth = ${playerWidth?c};
    var navWidth = ${playlistNav?c};
    var width = ${(playerWidth+playlistNav)?c};
    var height = ${playerHeight?c};
    if (embed) {
        width = $(window).width();
        height = $(window).height();
        playerWidth = width - navWidth;
    }
    log('playerWidth = ' + playerWidth + ' navWidth = ' + navWidth + ' height = ' + height);

    // set player width and height
    $('div.playlistPlayer').css('width', playerWidth + 'px').css('height', height + 'px');
    // set navigation width and height
    $('div.playlistNav').css('width', navWidth + 'px').css('height', height + 'px');

        $('div.playlistNav ul').slimScroll({
    height: height + 'px'
});

    $('div.playlistNav li').click(function() {
        var i = $(this).data('index');
        var id = $(this).data('id');
        var type = $(this).data('type');
        log('play media ' + i + ' id = ' + id + ' type = ' + type);
        $('div.playlistPlayer li.player').each(function(index) {
            if(index != i)
            $(this).hide();
            else
            $(this).show();
        });

        //$($('div.playlistPlayer ul').children()[i]).show();
        if (type == 'av') {
            var playerId = 'avPlayer__' + id;
            log('play av file' + playerId + ' stop');
            jwplayer(playerId).stop();
            log('play av file' + playerId + ' play');

        jwplayer(playerId).play(true);
        }
        return false;
    });

    $('div.playlistPlayer li.player').each(function(index) {
        if (index == 0)
            $(this).show();
        else
            $(this).hide();
    });


</script>
    <#else>
    No media files found.
</#if>