<#if avp??>
    <#if avp.av1??>
        <#assign obj=avp.av1/>
    </#if>
    <#if avp.av2??>
        <#assign obj2 = avp.av2/>
    </#if>
    <#if avp.presentation??>
        <#assign presentation=avp.presentation/>
    </#if>
</#if>

<#if !obj?? || !presentation??>

<div class="info">
    <#if accessDenied>
<@spring.message "avp.access.deny"/>
<#else>
    <@spring.message "avp.not.found"/>
    </#if>
</div>

</#if>

<#if obj?? && presentation??>
    <#include "viewHelper.ftl"/>
<#-- maximum width is 420 -->
    <#assign maxWidth = 420/>
    <#if obj.mediaType == 10>
        <#assign player1Width=maxWidth/>
        <#assign player1Height=24/>
        <#else>
            <#assign player1Width = obj.width />
            <#assign player1Height = obj.height />
    </#if>
    <#if player1Width &gt; maxWidth>
        <#assign player1Width = maxWidth />
        <#assign player1Height = (player1Width*height/width)?round />
    </#if>
    <#assign presentationWidth = maxWidth />
    <#assign presentationHeight = (presentationWidth*presentation.height/presentation.width)?round />
    <#assign presentationFileLink = context_url + "/file.do?m=" + presentation.accessCode+ "&name=" + presentation.realFilename/>

<div id="avpContainer">
    <div id="leftColumn">
        <#if obj.mediaType == 10>
            <audio width="${player1Width?c}" height="${player1Height?c}"
                   src="${mediaFileLink?html}"
                   controls preload="auto" id="player1">
                Your browser does not support the audio element.
            </audio>
            <#elseif obj.mediaType ==20>
                <video width="${player1Width?c}" height="${player1Height?c}"
                       src="${mediaFileLink?html}"
                    <#if thumnailFileLink?has_content>
                       poster="${thumnailFileLink?html}"
                    </#if>
                       controls preload="true" id="player1">
                    Your browser does not support the video element.
                </video>
        </#if>
        <div class="info">
            <span id="current-time1">0:00</span> <span id="current-slide"></span>
            <a href="${baseUrl}/view?m=${obj.accessCode}" class="right">
                <#if obj.mediaType ==10>
                    Listen to this audio only
                    <#else>
                        Watch this video only
                </#if>
            </a>
        </div>
        <#if obj2?? || (obj.mediaType == 10) >
            <#include "avpSlides.ftl"/>
        </#if>
    </div>
    <div id="rightColumn">
        <#if obj2??>
            <#if obj2.mediaType == 10>
                <#assign player2Width=420/>
                <#assign player2Height=24/>
                <#else>
                    <#assign player2Width = obj2.width />
                    <#assign player2Height = obj2.height />
            </#if>
        <#-- maximum width is 420 -->
            <#if player2Width &gt; 420>
                <#assign player2Width = 420 />
                <#assign player2Height = (player2Width*obj2.height/obj2.width)?round />
            </#if>
            <#assign av2FileURL>${context_url}/file.do?m=${obj2.accessCode}</#assign>
            <#assign av2FileLink>${av2FileURL}</#assign>
            <#if obj2.thumbnail?has_content>
                <#assign av2ThumnailFileLink>${av2FileURL}&name=${obj2.thumbnail}</#assign>
            </#if>
            <#if obj2.mediaType ==10>
                <audio width="${player2Width?c}" height="${player2Height?c}"
                       src="${av2FileLink?html}"
                       controls preload="auto" id="player2">
                    Your browser does not support the audio element.
                </audio>
                <#elseif obj2.mediaType ==20>
                    <video width="${player2Width?c}" height="${player2Height?c}"
                           src="${av2FileLink?html}"
                        <#if av2ThumnailFileLink?has_content>
                           poster="${av2ThumnailFileLink?html}"
                        </#if>
                           controls preload="true" id="player2">
                        Your browser does not support the video element.
                    </video>
            </#if>
            <div class="info">
                <span id="current-time2">0:00</span>
                <a href="${baseUrl}/view?m=${obj2.accessCode}" class="right">
                    <#if obj2.mediaType ==10>
                        Listen to this audio only
                        <#else>
                            Watch this video only
                    </#if>
                </a>
            </div>
            <#elseif obj.mediaType != 10>
                <#include "avpSlides.ftl"/>
        </#if>
        <h2>Slides (click to jump to particular slide)</h2>

        <div id="slideList">
        </div>
    </div>
    <div class="clear"></div>
</div>

<script type="text/javascript">
$(function() {

    var howManySlides = ${presentation.duration?c};
    var seekDelta = 0;
    var avDuration = ${(obj.duration/1000)?c};
    // slides data, load from database (old one from xml)
    var slidesData = null;
    var lastSlide = -1;
    var currentSlide = -1;
    var currentSeq = -1;
    var isShowingCurrentSlide = true;
    var player1IsReady = false;
    var player1IsPlaying = false;
    var player1LastTime = 0;
    var player2IsReady = true;
    var player2IsPlaying = true;
    var player2LastTime = 0;
    // maximum gap between player1 and player2 is 2 seconds
    var MAXIMUM_GAP = 2;

    // load data from xml and set all images, slide list, events, etc.
    function getData() {
        var slideList = $('#slideList');
        // empty slide list first
        slideList.empty();
        var ul = $('<ul/>').appendTo(slideList);
        <#if avp??>
            slidesData = new Array();
            var seq;
            var num;
            var slideNum;
            var li;
            var text;
            <#list avp.slideInfos as si>
                seq = ${si_index?c};
                num = ${si.num?c};
                if (num < 1)
                    num = 1;
                if (num > howManySlides)
                    num = howManySlides;
                slidesData[seq] = new slideInfo(
                ${si.sTime?c},
                ${si.eTime?c},
                        num,
                        "${si.title!?js_string}"
                );
                // which slide (0 based)
                slideNum = parseInt(slidesData[seq].num) - 1;
                li = $('<li/>').appendTo(ul);
                text = convertSecondsToTimecode(slidesData[seq].sTime) + " slide " + slidesData[seq].num;
                if (slidesData[seq].title)
                    text += " - " + slidesData[seq].title;
                $('<a/>').text(text).attr("href", "#" + seq).appendTo(li);
                $('#slide_' + slideNum + " a").attr("title", $(this).attr("title"));
            </#list>
            <#else>
                var url = "${baseUrl}/${xml}";
                $.get(url, function(xml) {
                    slidesData = new Array();
                    $("avpData", xml).children().each(function(seq) {
                        slidesData[seq] = new slideInfo(
                                convertTimecodeToSeconds($(this).attr("time")),
                                0,
                                $(this).attr("num"),
                                $(this).attr("title"))
                        // which slide (0 based)
                        var slideNum = parseInt(slidesData[seq].num) - 1;
                        var li = $('<li/>').appendTo(ul);
                        var text = $(this).attr("time") + " slide " + slidesData[seq].num;
                        if (slidesData[seq].title)
                            text += " - " + slidesData[seq].title;
                        $('<a/>').text(text).attr("href", "#" + seq).appendTo(li);
                        $('#slide_' + slideNum + " a").attr("title", $(this).attr("title"));
                    });
                });
        </#if>
        $('ul li a', slideList).click(
                function() {
                    var seq = parseInt($(this).attr('href').substring(1));
                    // which slide to show (0 based)
                    var slideNum = parseInt(slidesData[seq].num) - 1;
                    log("jump to slide " + (slideNum + 1));
                    jwplayer('player1').seek(parseFloat(slidesData[seq].sTime) + seekDelta).play(true);
                    isShowingCurrentSlide = true;
                    currentSeq = seq;
                    showCurrentSlide();
                    return false;
                }).hover(function() {
                    // show hovered slide
                    var seq = parseInt($(this).attr('href').substring(1));
                    // which slide to show (0 based)
                    var slideNum = parseInt(slidesData[seq].num) - 1;
                    // set isShowingCurrentSlide flag to false
                    isShowingCurrentSlide = false;
                    showSlide(slideNum);
                    return false;
                }, function() {
                    // set isShowingCurrentSlide flag to true
                    isShowingCurrentSlide = true;
                    // change back to current slide
                    showCurrentSlide();
                    return false;
                });
    }


    getData();
    $('#slide_-1').show();


    var firstPlay = true;
    jwplayer('player1').setup({
        flashplayer: '${JWPLAYER}',
        bufferlength: 5,
        <#if obj.mediaType == 10>
            controlbar: {position: 'bottom', idlehide: false},
            provider: 'sound',
            <#elseif obj.mediaType ==20>
                provider: 'video',
        </#if>
        events: {
            onTime: function(event) {
                // get current time
                var currentTime = event.position;
                // set "current-time1" text
                $('#current-time1').text(convertSecondsToTimecode(currentTime));
                var whichSeq = -1;
                // assume slidesData is ordered by time
                for (var i = 0; i < slidesData.length; i++) {
                    if (currentTime >= slidesData[i].sTime)
                        whichSeq = i;
                    else
                        break;
                }
                if (whichSeq != currentSeq) {
                    currentSeq = whichSeq;
                    showCurrentSlide();
                }
                player1LastTime = currentTime;
            },
            onPlay: function() {
                if (firstPlay) {
                    log('avDuration = ' + avDuration);
                    log("player duration = " + jwplayer('player1').getDuration());
                    if (avDuration != jwplayer('player1').getDuration()) {
                        log("set avDuration to " + jwplayer('player1').getDuration());
                        avDuration = jwplayer('player1').getDuration();
                    }
                    <#if obj.mediaType == 20>
                        // for video file, set seek delta to 1/1000 of length
                        seekDelta = Math.round(avDuration / 1000);
                        log("seek delta = " + seekDelta);
                    </#if>
                    firstPlay = false;
                }
                player1IsPlaying = true;
                <#if obj2??>
                    log("video one is playing, start playing video two");
                    jwplayer('player2').play(true);
                </#if>
            },
            onPause: function() {
                player1IsPlaying = false;
                <#if obj2??>
                    log("video one is pausing, pausing video two");
                    jwplayer('player2').pause(true);
                </#if>
            },
            onReady: function() {
                log("player1 is ready");
                player1IsReady = true;
            }
        }
    });
    <#if obj2??>
        player2IsReady = false;
        player2IsPlaying = false;
        jwplayer('player2').setup({
            flashplayer: '${JWPLAYER}',
            bufferlength: 5,
            // mute player2
            volume: 0,
            <#if obj2.mediaType == 10>
                controlbar: {position: 'bottom', idlehide: false},
                provider: 'sound',
                <#elseif obj2.mediaType ==20>
                    provider: 'video',
            </#if>
            //controlbar: 'none',
            events: {
                onTime: function(event) {
                    // get current time
                    var currentTime = event.position;
                    // set "current-time2" text
                    $('#current-time2').text(convertSecondsToTimecode(currentTime));
                    player2LastTime = currentTime;
                },
                onPlay: function() {
                    player2IsPlaying = true;
                    jwplayer('player1').play(true);
                },
                onPause: function() {
                    player2IsPlaying = false;
                    jwplayer('player1').pause(true);
                },
                onReady: function() {
                    log("player2 is ready");
                    player2IsReady = true;
                }
            }
        });
    </#if>

    function showSlide(i) {
        if (i != lastSlide) {
            // hide all slides first
            $('div.slide').hide();
            // only show required slide
            $('#slide_' + i).show();
            log("show slide " + (i + 1));
            // change "current-slide" text
            $('#current-slide').text("slide " + (i + 1));
            lastSlide = i;
        }
    }

    function showCurrentSlide() {
        currentSlide = parseInt(slidesData[currentSeq].num) - 1;
        if (isShowingCurrentSlide)
            showSlide(currentSlide);
        var slideList = $('#slideList');
        // remove "current" class for all slide links
        $('ul li a', slideList).removeClass("current");
        var currentSlideLink = $('ul li a[href=#' + currentSeq + ']', slideList);
        // add "current" class to current slide link, and set focus so it will display
        currentSlideLink.addClass("current");
        // make sure current slide link is always visible
        var currentSlideTop = currentSlideLink.offset().top;
        var slideListTop = slideList.offset().top;
        var slideListHeight = slideList.innerHeight();
        var slideListScrollTop = slideList.scrollTop();
        log("currentSlideLink.offset().top = " + currentSlideLink.offset().top + " slideList.offset().top = " + slideList.offset().top + " slideList.height() = " + slideList.height() + " slideList.innerHeight() = " + slideList.innerHeight());
        if ((slideListScrollTop + currentSlideTop - slideListTop) > slideListHeight) {
            slideList.scrollTop(slideListScrollTop + currentSlideTop - slideListTop - slideListHeight + currentSlideLink.innerHeight());
            log(currentSlideTop - slideListTop - slideListHeight + currentSlideLink.innerHeight());
        }
        if (currentSlideTop < slideListTop)
            slideList.scrollTop(slideListScrollTop - slideListTop + currentSlideTop);
    }

    function checkReady() {
        <#if obj2??>
            log("check if players are ready");
            <#else>
                log("check if player1 is ready");
        </#if>
        if (player1IsReady && player2IsReady) {
            <#if obj2??>
                log("all ready, clear interval and start playing player1 and player2");
                <#else>
                    log("all ready, clear interval and start playing player1");
            </#if>
            window.clearInterval(readyIntervalID);
            jwplayer('player1').play();
            log("player1 started");
            <#if obj2??>
                jwplayer('player2').play();
                log("player2 started");
            </#if>
        }
    }

    var readyIntervalID = window.setInterval(checkReady, 1000);
    <#if obj2??>
        function checkSync() {
            if (player1IsPlaying && player2IsPlaying)
                if (Math.abs(player1LastTime - player2LastTime) > MAXIMUM_GAP) {
                    log("player1LastTime = " + player1LastTime + " player2LastTime = " + player2LastTime);
                    log("adjust video two to be in sync with video one");
                    jwplayer('player2').seek(player1LastTime).play(true);
                }
        }

        var syncIntervalID = window.setInterval(checkSync, 1000);
    </#if>
    Shadowbox.init({
        skipSetup: true
    });

    Shadowbox.setup("a.slideLink", {
        gallery: 'Presentation',
        counterLimit: 40,
        overlayOpacity: 0.85,
        player: 'img',
        counterType: 'skip'
    });
});
</script>

</#if>
