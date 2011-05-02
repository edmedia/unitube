<#if obj?? && presentation??>

<#include "viewHelper.ftl"/>
<#assign videoWidth = width />
<#assign videoHeight = height />
<#if videoWidth &gt; 420>
<#assign videoWidth = (width/1.5)?round />
<#assign videoHeight = (height/1.5)?round />
</#if>
<#assign presentationWidth = videoWidth />
<#assign presentationHeight = (presentationWidth*presentation.height/presentation.width)?round />

<#assign presentationFileBase><#if !uploadLocation.absolutePath>${context_url}/</#if>${uploadLocation.baseLink}${presentation.user.accessCode}/${presentation.randomCode}</#assign>
<#assign presentationFileLink>${presentationFileBase}/${presentation.realFilename}</#assign>

<#assign FWPLAYER>${baseUrl}/jwplayer/player.swf</#assign>
<#assign FWPLAYER_SKIN>${baseUrl}/jwplayer/glow.zip</#assign>
<div id="avpContainer">
    <div id="leftColumn">
        <video width="${videoWidth?c}" height="${videoHeight?c}" poster="${thumnailFileLink}" controls
               preload="true" id="player">
            <!-- MP4 for Safari, IE9, iPhone, iPad, Android, and Windows Phone 7 -->
            <source type="video/mp4" src="${mediaFileLink}"/>
        </video>
        <div class="info">
            <span id="current-time">0:00</span> <span id="current-slide"></span>
        </div>

        <#if obj2??>
        <#include "avpSlides.ftl"/>
        </#if>


    </div>


    <div id="rightColumn">

        <#if obj2??>
        <#assign av2FileBase><#if !uploadLocation.absolutePath>${context_url}/</#if>${uploadLocation.baseLink}${obj2.user.accessCode}/${obj2.randomCode}</#assign>
        <#assign av2FileLink>${av2FileBase}/${obj2.realFilename}</#assign>
        <#if obj2.thumbnail?has_content>
        <#assign av2ThumnailFileLink>${av2FileBase}/${obj.thumbnail}</#assign>
        </#if>

        <video width="${videoWidth?c}" height="${videoHeight?c}" poster="${av2ThumnailFileLink}" controls
               preload="true" id="player2">
            <!-- MP4 for Safari, IE9, iPhone, iPad, Android, and Windows Phone 7 -->
            <source type="video/mp4" src="${av2FileLink}"/>
        </video>
        <div class="info">
            <span id="current-time2">0:00</span>
        </div>
        <#else>
        <#include "avpSlides.ftl"/>
        </#if>

        <h2>Slides (click to jump to particular slide)</h2>

        <div id="pagination">
        </div>
    </div>
    <div class="clear"></div>
</div>

<script type="text/javascript">
<!--
$(function() {

    // slides data, load from xml
    var slidesData = null;

    // each slideInfo has time, title
    function slideInfo(time, title) {
        this.time = time;
        this.title = title;
    }

    /** load data from xml and set all images, pagination, events, etc. */
    function getData(url) {
        $.get(url, function(xml) {
            slidesData = new Array();
            var pagination = $('#pagination');
            // empty pagination first
            pagination.empty();
            var ul = $('<ul/>').appendTo(pagination);
            $("avpData", xml).children().each(function(i) {
                slidesData[i] = new slideInfo(convertTimecodeToSeconds($(this).attr("time")), $(this).attr("title"))
                var li = $('<li/>').appendTo(ul);
                $('<a/>').text($(this).attr("time") + " " + slidesData[i].title)
                        .attr("href", "#" + i).appendTo(li);
                $('#slide_' + i + " a").attr("title", $(this).attr("title"));
                $('#slide_' + i + " img").attr("alt", $(this).attr("title"));
            });

            $('#pagination ul li a').click(function() {
                var i = parseInt($(this).attr('href').substring(1));
                if (window.console) console.log("jump to slide " + (i + 1));
                jwplayer('player').play(true).seek(parseFloat(slidesData[i].time) + 5);
                isShowingCurrentSlide = true;
                currentSlide = i;
                showCurrentSlide();
                return false;
            }).hover(function() {
                // show hovered slide
                var i = parseInt($(this).attr('href').substring(1));
                showSlide(i);
                // set isShowingCurrentSlide flag to false
                isShowingCurrentSlide = false;
                return false;
            }, function() {
                // set isShowingCurrentSlide flag to true
                isShowingCurrentSlide = true;
                // change back to current slide
                showCurrentSlide();
                return false;
            });
        });
    }

    var url = "${baseUrl}/avpData.do?xml=${xml}";

    getData(url);
    $('#slide_-1').show();

    var lastSlide = -1;
    var currentSlide = -1;
    var isShowingCurrentSlide = true;
    var playerIsReady = false;
    var player2IsReady = true;
    var playerIsPlaying = false;
    var player2IsPlaying = true;
    var playerLastTime = 0;
    var player2LastTime = 0;
    var MAXIMUM_GAP = 2;

    jwplayer('player').setup({
        flashplayer: '${FWPLAYER}',
        bufferlength: 5,
        skin: '${FWPLAYER_SKIN}',
        events: {
            onTime: function(event) {
                // get current time
                var currentTime = event.position;
                // set "current-time" text
                $('#current-time').text(convertSecondsToTimecode(currentTime));
                currentSlide = -1;
                // assume slidesData is ordered by time
                for (var i = 0; i < slidesData.length; i++) {
                    if (currentTime >= slidesData[i].time)
                        currentSlide = i;
                    else
                        break;
                }
                showCurrentSlide();
                playerLastTime = currentTime;
            },
            onPlay: function() {
                playerIsPlaying = true;
            <#if obj2??>
                if (window.console) console.log("video one is playing, start playing video two");
                jwplayer('player2').play(true);
            </#if>
            },
            onPause: function() {
                playerIsPlaying = false;
            <#if obj2??>
                if (window.console) console.log("video one is pausing, pausing video two");
                jwplayer('player2').pause(true);
            </#if>
            },
            onReady: function() {
                if (window.console) console.log("player is ready");
                playerIsReady = true;
            }
        }
    });
<#if obj2??>
    player2IsReady = false;
    player2IsPlaying = false;
    jwplayer('player2').setup({
        flashplayer: '${FWPLAYER}',
        bufferlength: 5,
        volume: 0,
        skin: '${FWPLAYER_SKIN}',
        //controlbar: 'none',
        events: {
            onTime: function(event) {
                // get current time
                var currentTime = event.position;
                // set "current-time" text
                $('#current-time2').text(convertSecondsToTimecode(currentTime));
                player2LastTime = currentTime;
            },
            onPlay: function() {
                player2IsPlaying = true;
                jwplayer('player').play(true);
            },
            onPause: function() {
                player2IsPlaying = false;
                jwplayer('player').pause(true);
            },
            onReady: function() {
                if (window.console) console.log("player2 is ready");
                player2IsReady = true;

            }
        }
    });
</#if>

    function showSlide(i) {
        // hide all slides first
        $('div.slide').hide();
        // only show required slide
        $('#slide_' + i).show();
        // change "current-slide" text
        $('#current-slide').text("slide " + (i + 1));
    }

    function showCurrentSlide() {
        if (lastSlide != currentSlide) {
            if (window.console)  console.log("show slide " + (currentSlide + 1));
            lastSlide = currentSlide;
        }
        if (isShowingCurrentSlide)
            showSlide(currentSlide);
        // remove "current" class for all slide links
        $('#pagination ul li a').removeClass("current");
        // add "current" class to current slide link, and set focus so it will display
        $('#pagination ul li a[href=#' + currentSlide + ']').addClass("current").focus();
    }

    function convertSecondsToTimecode(seconds) {
        var ss = Math.round(seconds);
        minutes = Math.floor(ss / 60);
        minutes = (minutes >= 10) ? minutes : "0" + minutes;
        ss = Math.floor(ss % 60);
        ss = (ss >= 10) ? ss : "0" + ss;
        return minutes + ":" + ss;
    }

    function convertTimecodeToSeconds(timecode) {
        var seconds = 0;
        var ss = timecode.split(":");
        for (var i = ss.length; i > 0; i--)
            seconds = seconds * 60 + parseFloat(ss[ss.length - i]);
        return seconds;
    }

    var readyIntervalID;

    function checkReady() {
        if (window.console) console.log("check if players are ready");
        if (playerIsReady && player2IsReady) {
            if (window.console) console.log("all ready, clear interval and start playing video one and video two");
            window.clearInterval(readyIntervalID);
            jwplayer('player').play();
        <#if obj2??>
            jwplayer('player2').play();
        </#if>
        }
    }

    readyIntervalID = window.setInterval(checkReady, 1000);
<#if obj2??>
    var syncIntervalID;

    function checkSync() {
        if (playerIsPlaying && player2IsPlaying)
            if (Math.abs(playerLastTime - player2LastTime) > MAXIMUM_GAP) {
                if (window.console) console.log("playerLastTime = " + playerLastTime + " player2LastTime = " + player2LastTime);
                if (window.console) console.log("adjust video two to be in sync with video one");
                jwplayer('player2').play(true).seek(playerLastTime);
            }
    }

    syncIntervalID = window.setInterval(checkSync, 1000);

</#if>

    Shadowbox.init({
        counterLimit: 30,
        overlayOpacity: 0.85,
        counterType: 'skip'
    });

    //$('#slides_gallery a').lightBox();
});
//-->
</script>

<#else>
Can not find this AV Presentation.
</#if>
