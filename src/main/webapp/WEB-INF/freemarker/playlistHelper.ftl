<#if playlist?has_content>
    <#if !width??>
        <#assign width = 480/>
    </#if>
    <#if !height??>
        <#assign height = 360/>
    </#if>
    <#if !playlistSize??>
        <#assign playlistSize = 250/>
    </#if>
    <#assign id>mediaplayer_${.now?string("yyyyMMddHHmmssS")}</#assign>
<div id='${id}'></div>
<script type="text/javascript">
    var embed = (document.URL.indexOf("embed") != -1);
    var width = ${(width+playlistSize)?c};
    var height = ${height?c};
    if (embed) {
        width = $(window).width();
        height = $(window).height();
    }
    jwplayer('${id}').setup({
        flashplayer: '${context_url}/jwplayer/player.swf',
        width: width,
        height: height,
        'playlist.position': 'right',
        'playlist.size': ${playlistSize?c},
        events: {
            'onBeforePlay': function() {
                log('onBeforePlay');
                if (jwplayer('${id}').getPlaylistItem().provider == 'image') {
                    log('image');
                    jwplayer('${id}').getPlugin('controlbar').hide();
                    log('open a new window here');
                    newWin(jwplayer('${id}').getPlaylistItem().file);
                    log('stop playing.');
                    jwplayer('${id}').stop();
                } else {
                    log('audio or video');
                    jwplayer('${id}').getPlugin('controlbar').show();
                }
            },
            'onPlaylistItem': function() {
                log('onPlaylistItem');
                if (jwplayer('${id}').getPlaylistItem().provider == 'image') {
                    jwplayer('${id}').getPlugin('controlbar').hide();
                } else {
                    jwplayer('${id}').getPlugin('controlbar').show();
                }
            }
        },
        repeat: 'list',
        'playlist': [
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
        ]
    });
</script>
    <#else>
    No media files found.
</#if>