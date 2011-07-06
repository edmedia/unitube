<div class="stage">
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
<#if obj?? && presentation??>
    <#include "viewHelper.ftl"/>
    <#if obj.mediaType == 10>
        <#assign player1Width=600/>
        <#assign player1Height=24/>
        <#else>
            <#assign player1Width = 600 />
            <#assign player1Height = (player1Width*obj.height/obj.width)?round />
    </#if>
    <#assign presentationWidth = 200 />
    <#assign presentationHeight = (presentationWidth*presentation.height/presentation.width)?round />
    <#assign presentationFileLink = context_url + "/file.do?m=" + presentation.accessCode+ "&name=" + presentation.realFilename/>

<div id="avContainer" class="mediaBorder">
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
    <div id="slider-container" class="clear">
        <div id="slider-range"></div>
    </div>
</div>

<h2 class="clear">Synchronisation Workspace</h2>

    <div id="actionButton">
        <a href="#" class="addSlide" title="Add a slide">
            <img src="${baseUrl}/images/plus.png" border="0"/>
            Add a slide
        </a>
        |
        <a href="${baseUrl}/avp.do?a=${avp.accessCode}" target="_blank">View this AVP (in new window or tab)</a>

        <span class="right">
        <a href="#" class="save" title="Save AVP">
            <img src="${baseUrl}/images/save.png" border="0"/>
        </a>
        </span>
    </div>

<div class="scroll-pane ui-corner-all clear">
    <div class="scroll-content">
    </div>
</div>

<div id="slidesSelector">
    <h2>Choose a slide to add (click to add)</h2>
    <ul>
        <#list 1..presentation.duration as i>
            <li>
                <div class="caption">Slide ${i?c}</div>
                <img src="${presentationFileLink?replace('-(\\d)+\\.', '-' + (i-1) + '.', 'r')?html}"
                     width="${presentationWidth?c}"
                     height="${presentationHeight?c}"
                     class="slide"
                     title="Slide ${i?c}" alt="Slide ${i?c}"/>
            </li>
        </#list>
    </ul>
</div>

<script type="text/javascript">
<!--
$(function() {

    var seekDelta = 0;
    var howManySlides = ${presentation.duration?c};
    // disable slide selector first
    $('#actionButton').hide();

    var avDuration = ${(obj.duration/1000)?c};
    // minimum time for a slide, in seconds
    var minSlideTime = 1;

    // how long each slide lasts in seconds
    // by default, it will be audio/video duration divided by slide number
    var slideDuration = 10;
    var isAddSlide = true;
    var itemToBeReplaced;

    // when click "add slide"
    $('a.addSlide').click(function() {
        isAddSlide = true;
        $('#slidesSelector').show();
        var winW = $(window).width();
        var winH = $(window).height();
        log('window width and height (' + winW + ', ' + winH + ')');
        var dW = 900;
        var dH = 660;
        if (dW > winW)
            dW = winW;
        if (dH > winH)
            dH = winH;
        log('dialog width and height (' + dW + ', ' + dH + ')');
        $('#slidesSelector h2').text('Choose a slide to add (click to add)');
        $('#slidesSelector').dialog({
            width: dW,
            height: dH,
            modal: true
        });
        return false;
    });

    // when select a slide to add or replace
    $('#slidesSelector ul li').click(function() {
        var li = $(this);
        var i = $('#slidesSelector ul li').index(li);
        i = i + 1;
        if (i > 0) {
            if (isAddSlide) {
                // add a new slide
                var allItems = $('.scroll-content .scroll-content-item');
                var startTime = 0;
                // if not first slide
                if (allItems.length > 0)
                    startTime = allItems.eq(allItems.length - 1).data('slideInfo').eTime;
                var endTime = startTime + slideDuration;
                if (endTime > avDuration)
                    endTime = avDuration;
                addSlide(i, startTime, endTime, '');
            } else {
                //replace slide
                itemToBeReplaced.data('slideInfo').num = i;
                // change slide picture
                $('img.slide', itemToBeReplaced).attr('src', $('img[src$="-' + (i - 1) + '.png"]').eq(0).attr('src'));
                // change slide number
                $('span.left', itemToBeReplaced).eq(0).text('Slide ' + i);
            }
            $('#slidesSelector').dialog('close');
            $('#slidesSelector').hide();
        }
    });

    // add a slide
    function addSlide(whichSlide, sTime, eTime, title) {
        var allItems = $('.scroll-content .scroll-content-item');
        log("add slide " + whichSlide);
        // first, check if there's any time remained
        if (allItems.length > 0) {
            var last = allItems.eq(allItems.length - 1);
            log("previous slide's end time = " + last.data('slideInfo').eTime);
            // if previous slide's end time is the end of the video, no more slide can be added
            if (last.data('slideInfo').eTime >= avDuration) {
                log("You have reached the end of the Audio/Video. No more slide can be added.");
                alert("You have reached the end of the Audio/Video. No more slide can be added.");
                return;
            }
        }
        var content = $('.scroll-content');
        var item = $('<div/>').addClass('scroll-content-item').appendTo(content);
        var div = $('<div/>').appendTo(item);
        var div2 = $('<div/>').appendTo(div);
        var span = $('<span/>').text('Slide ' + whichSlide).addClass('left').appendTo(div2);
        var del = $('<img/>').attr('src', '${baseUrl}/images/remove.png').attr('alt', 'Remove').attr('title', 'Remove this slide from AVP').addClass('right').appendTo(div2);
        var replace = $('<img/>').attr('src', '${baseUrl}/images/replace.png').attr('alt', 'Replace').attr('title', 'Replace this slide with other slide').addClass('right').appendTo(div2);
        var img = $('img[src$="-' + (whichSlide - 1) + '.png"]').eq(0).clone();
        img.appendTo(div);
        var divTime = $('<div/>').addClass('time').appendTo(div);
        $('<span/>').addClass('left').text(convertSecondsToTimecode(sTime)).appendTo(divTime);
        $('<span/>').addClass('right').text(convertSecondsToTimecode(eTime)).appendTo(divTime);
        var cap = $('<div/>').addClass('caption').addClass('clear').appendTo(div);
        var defaultText = 'click here to add title';
        if (title.length == 0) {
            cap.attr('title', '');
            cap.text(defaultText);
        } else {
            cap.attr("title", title);
            if (title.length > 28)
                cap.text(title.substring(0, 25) + ' ...');
            else
                cap.text(title);
        }
        item.data('slideInfo', new slideInfo(sTime, eTime, whichSlide, title));
        log('div.height = ' + div.height());
        // change item's height to have enough space to hold the slide
        item.css('height', (div.height() + 10) + 'px');
        item.click(function() {
            var item = $(this);
            slideSelected(item);
        });
        slideSelected(item);

        cap.click(function() {
            var cap = $(this);
            var item = cap.parent().parent();
            var inputDiv = $('<div/>').addClass('input').insertAfter(cap);
            var input = $('<input/>').attr('type', 'text').val(cap.attr('title')).css('width', '${presentationWidth?c}px').appendTo(inputDiv);
            input.focus();
            cap.hide();
            input.blur(function() {
                afterEdit($(this))
            });
            input.keydown(function(event) {
                if (event.keyCode == 13) {
                    afterEdit($(this))
                    event.preventDefault();
                }
            });

            function afterEdit(theInput) {
                if (theInput.val().length == 0) {
                    cap.attr('title', '');
                    cap.text(defaultText);
                } else {
                    cap.attr('title', theInput.val());
                    if (theInput.val().length > 28)
                        cap.text(theInput.val().substring(0, 25) + ' ...');
                    else
                        cap.text(theInput.val());
                }
                item.data('slideInfo').title = theInput.val();
                theInput.parent().remove();
                cap.show();
            }
        });

        del.click(function() {
            var item = $(this).parent().parent().parent();
            deleteSlide(item);
        });
        replace.click(function() {
            itemToBeReplaced = $(this).parent().parent().parent();
            isAddSlide = false;
            $('#slidesSelector').show();
            var winW = $(window).width();
            var winH = $(window).height();
            log('window width and height (' + winW + ', ' + winH + ')');
            var dW = 900;
            var dH = 660;
            if (dW > winW)
                dW = winW;
            if (dH > winH)
                dH = winH;
            log('dialog width and height (' + dW + ', ' + dH + ')');
            $('#slidesSelector h2').text('Choose a slide to replace your current slide');
            $('#slidesSelector').dialog({
                width: dW,
                height: dH,
                modal: true
            });
            return false;
        });
        setupSlideScroller();
        log('item.offset() = ' + item.offset().left + ', ' + item.offset().top);
        $('.scroll-pane').scrollLeft(item.offset().left);
    }

    function slideSelected(item) {
        var allItems = $('.scroll-content .scroll-content-item');
        var minTime = 0;
        var maxTime = avDuration;
        var sTime = item.data('slideInfo').sTime;
        var eTime = item.data('slideInfo').eTime;
        if (eTime < sTime)
            eTime = sTime + slideDuration;
        // if has previous slide, minTime will be previous slide's start time plus minSlideTime
        if (item.prev().length > 0)
            minTime = item.prev().data('slideInfo').sTime + minSlideTime;
        // if has next slide, maxTime will be next slide's end time minus minSlideTime
        if (item.next().length > 0)
            maxTime = item.next().data('slideInfo').eTime - minSlideTime;
        if (maxTime < minTime)
            maxTime = avDuration;

        log('minimum and maximum time for this slide: (' + minTime + ', ' + maxTime + ')');
        // remove currentSide class
        allItems.removeClass('currentSlide');
        //allItems.css('opacity', '0.75');
        // add currentSlide class to this slide
        //item.css('opacity', '1');
        item.addClass('currentSlide');
        item.focus();
        // calculate width for slider
        var width = Math.round((maxTime - minTime) * ${player1Width}/avDuration);
        // calculate left margin
        var marginLeft = Math.round(minTime * ${player1Width}/avDuration);
        $('#slider-container').css('width', width)
                .css('margin-left', marginLeft);
        $("#slider-range").slider('destroy');
        $("#slider-range").slider({
            range: true,
            animate: true,
            min: minTime,
            max: maxTime,
            step: 0.1,
            values: [ sTime, eTime ],
            create: function(event, ui) {
                var lowHandle = $('#slider-range .ui-slider-handle').eq(0);
                var highHandle = $('#slider-range .ui-slider-handle').eq(1);
                // div to display current time
                $('<div/>').text(convertSecondsToTimecode(sTime))
                        .addClass('startTime')
                        .appendTo(lowHandle);
                $('<div/>').text(convertSecondsToTimecode(eTime))
                        .addClass('endTime')
                        .appendTo(highHandle);
            },
            slide: function(event, ui) {
                var movedV = ui.value;
                var minV = ui.values[0];
                var maxV = ui.values[1];
                if (minV == maxV) {
                    //if movedV equals maxV, means we are moving high handler
                    if (movedV == maxV)
                        maxV = minV + 1;
                    else
                        minV = maxV - 1;
                }
                // if has previous slide, and low handler changed, change previous slide's end time to minV
                if ((item.prev().length > 0) && (movedV == minV)) {
                    item.prev().data('slideInfo').eTime = minV;
                    updateTimeOnslide(item.prev());
                }
                // if has next slide, and high handler changed, change next slide's start time to maxV
                if ((item.next().length > 0) && (movedV == maxV)) {
                    item.next().data('slideInfo').sTime = maxV;
                    updateTimeOnslide(item.next());
                }
                item.data('slideInfo').sTime = minV;
                item.data('slideInfo').eTime = maxV;
                updateTimeOnslide(item);

                // which handle
                var handle;
                // if ui.value equals ui.values[0], means low handle moved
                if (ui.value == ui.values[0]) {
                    handle = $('#slider-range .ui-slider-handle').eq(0);
                    // remove existing DIVs
                    $('div', handle).remove();
                    // div to display current time
                    $('<div/>').text(convertSecondsToTimecode(ui.value))
                            .addClass('startTime')
                            .appendTo(handle);
                } else {
                    handle = $('#slider-range .ui-slider-handle').eq(1);
                    // remove existing DIVs
                    $('div', handle).remove();
                    // div to display current time
                    $('<div/>').text(convertSecondsToTimecode(ui.value))
                            .addClass('endTime')
                            .appendTo(handle);
                }
                jwplayer('player1').seek(ui.value + seekDelta).play(true);
            },
            stop: function(event, ui) {
            }
        });
        jwplayer('player1').seek(sTime + seekDelta).pause(true);

    }

    function updateTimeOnslide(item) {
        $('.time .left', item).text(convertSecondsToTimecode(item.data('slideInfo').sTime));
        $('.time .right', item).text(convertSecondsToTimecode(item.data('slideInfo').eTime));
    }

    function deleteSlide(item) {
        if (confirm('Are you sure you want to remove this slide from your AVP?')) {
            // if this is not the last slide, change start time for next slide
            if (item.next().length > 0) {
                item.next().data('slideInfo').sTime = item.data('slideInfo').sTime;
                updateTimeOnslide(item.next());
            }
            // remove from interface
            item.remove();
            setupSlideScroller();
        }
    }

    $('a.save').click(function() {
        var allItems = $('.scroll-content .scroll-content-item');
        var slidesData = new Array();
        for (var i = 0; i < allItems.length; i++) {
            var item = allItems.eq(i);
            slidesData[i] = item.data('slideInfo');
        }
        var jsonText = JSON.stringify(slidesData);
        log('slidesData = ' + jsonText);
        $.ajax({
            url: 'avpSave.do',
            type: 'POST',
            data: {id: ${avp.id?c}, timeline: jsonText},
            success: function(xml) {
                if ($("action", xml).attr("success") == "true") {
                    alert("Your AVP has been saved successfully.");
                } else
                    alert($("action", xml).attr("detail"));
            }
        });
        return false;
    });

    function initSlides() {
        <#if avp.slideInfos?has_content>
            <#list avp.slideInfos as slideInfo>
                addSlide(${slideInfo.num?c}, ${slideInfo.sTime?c}, ${slideInfo.eTime?c}, '${slideInfo.title}');
            </#list>
        </#if>
        // enable slide selector, if disabled
        $('#actionButton').show();
    }


    var firstPlay = true;
    jwplayer('player1').setup({
        flashplayer: '${JWPLAYER}',
        autostart: true,
        bufferlength: 5,
        skin: '${baseUrl}/jwplayer/no-timeslider.zip',
        //controlbar: 'none',
        <#if obj.mediaType == 10>
            controlbar: {position: 'bottom', idlehide: false},
            provider: 'sound',
            <#elseif obj.mediaType ==20>
                height: ${(player1Height+30)?c},
                controlbar: {position: 'bottom', idlehide: false},
                provider: 'video',
        </#if>
        events: {
            onPlay: function() {
                if (firstPlay) {
                    log("player is playing for the first time ...");
                    jwplayer('player1').seek(0).pause();
                    log("pause player in the first time");
                    log('avDuration = ' + avDuration);
                    log("player duration = " + jwplayer('player1').getDuration());
                    if (avDuration == 0) {
                        log("set avDuration to " + jwplayer('player1').getDuration());
                        avDuration = jwplayer('player1').getDuration();
                    }
                    <#if obj.mediaType == 20>
                        // for video file, set seek delta to 1/1000 of length
                        seekDelta = Math.round(avDuration / 1000);
                        log("seek delta = " + seekDelta);
                    </#if>
                    if (howManySlides > 0) {
                        slideDuration = Math.round(avDuration / howManySlides);
                        log("slide duration = " + slideDuration);
                    }
                    firstPlay = false;
                }
            }

        }
    });

    function checkBuffer() {
        log("buffer = " + jwplayer('player1').getBuffer());
        $('#slider-range').text('Loading Audio/Video ... Please wait ...');
        if (jwplayer('player1').getBuffer() == 100) {
            log('All Audio/Video is in buffer.');
            $('#slider-range').text('');
            window.clearInterval(bufferIntervalID);
            initSlides();
        }
    }

    var bufferIntervalID = window.setInterval(checkBuffer, 3000);

    function setupSlideScroller() {
        //scrollpane parts
        var scrollPane = $(".scroll-pane"),
                scrollContent = $(".scroll-content"),
                scrollContentItem = $(".scroll-content-item");
        var itemWidth = scrollContentItem.width();
        var contentWidth = scrollContentItem.length * (itemWidth + 12);
        var paneWidth = scrollPane.width();
        scrollContent.css('width', contentWidth + 'px');

        log('scrollPane.outer = ' + scrollPane.outerWidth() + ', ' + scrollPane.outerHeight()
                + ' scrollContentItem.outer = ' + scrollContentItem.outerWidth() + ', ' + scrollContentItem.outerHeight()
                + ' scrollContentItem.inner = ' + scrollContentItem.innerWidth() + ', ' + scrollContentItem.innerHeight()
                + ' scrollContentItem = ' + scrollContentItem.width() + ', ' + scrollContentItem.height()
        );
    }


});


//-->
</script>

    <#else>
    Can not find this Audio/Video Presentation.
</#if>
</div>