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

<form action="">
    <div><img src="${baseUrl}/images/plus.png" border="0"/>Add a slide:
        <select name="slideNum">
            <option value="0">select a slide to add</option>
            <#list 1..presentation.duration as i>
                <option value="${i?c}">Slide ${i?c}</option>
            </#list>
        </select>
        <input type="button" name="save" value="Save"/>
    </div>
</form>

<div class="scroll-pane ui-widget  ui-corner-all">
    <div class="scroll-content">


    </div>
    <div class="scroll-bar-wrap ui-corner-bottom">
        <div class="scroll-bar"></div>
    </div>
</div>


<div>
    <ul id="gallaryView">
        <#list 1..presentation.duration as i>
            <li>
                <img src="${presentationFileLink?replace('-(\\d)+\\.', '-' + (i-1) + '.', 'r')?html}"
                     width="${presentationWidth?c}"
                     height="${presentationHeight?c}" title="Slide ${i}" alt="Slide ${i}"/>
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
    $('select[name=slideNum]').attr("disabled", "disabled");
    // hide save button first
    $('input[name=save]').hide();

    var avDuration = ${(obj.duration/1000)?c};
    // minimum time for a slide, in seconds
    var minSlideTime = 1;

    // how long each slide lasts in seconds
    // by default, it will be audio/video duration divided by slide number
    var slideDuration = 10;

    // each slideInfo has start time, end time, which slide (1 based), title(optional)
    function slideInfo(sTime, eTime, num, title) {
        this.sTime = sTime;
        this.eTime = eTime;
        this.num = num;
        this.title = title;
    }

    $('select[name=slideNum]').change(function() {
        var allItems = $('.scroll-content .scroll-content-item');
        var whichSlide = parseInt($('select[name=slideNum] option:selected').attr('value'));
        if (whichSlide > 0) {
            var startTime = 0;
            // if not first slide
            if (allItems.length > 0)
                startTime = allItems.eq(allItems.length - 1).data('slideInfo').eTime;
            var endTime = startTime + slideDuration;
            if (endTime > avDuration)
                endTime = avDuration;
            addSlide(whichSlide, startTime, endTime, '');
        }
    });

    function addSlide(whichSlide, sTime, eTime, title) {
        var allItems = $('.scroll-content .scroll-content-item');
        $('input[name=save]').show();
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
        var del = $('<img/>').attr('src', '${baseUrl}/images/remove.png').addClass('right').appendTo(div2);
        var img = $('img[src$="-' + (whichSlide - 1) + '.png"]').eq(0).clone();
        img.appendTo(div);
        var divTime = $('<div/>').addClass('time').appendTo(div);
        $('<span/>').addClass('left').text(convertSecondsToTimecode(sTime)).appendTo(divTime);
        $('<span/>').addClass('right').text(convertSecondsToTimecode(eTime)).appendTo(divTime);
        var cap = $('<div/>').addClass('caption').appendTo(div);
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

        img.click(function() {
            var item = $(this).parent().parent();
            slideSelected(item);
        });
        img.click();

        cap.click(function() {
            var cap = $(this);
            var item = cap.parent().parent();
            var allItems = $('.scroll-content .scroll-content-item');

            var inputDiv = $('<div/>').addClass('input').insertAfter(cap);
            var input = $('<input/>').attr('type', 'text').val(cap.attr('title')).css('width', '${presentationWidth?c}px').appendTo(inputDiv);
            input.focus();
            cap.hide();
            input.blur(function() {
                var theInput = $(this);
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
            });
        });

        del.click(function() {
            var item = $(this).parent().parent().parent();
            deleteSlide(item);
        });
        setupSlideScroller();

    }

    function slideSelected(item) {
        var allItems = $('.scroll-content .scroll-content-item');
        var minTime = 0;
        var maxTime = avDuration;
        // if has previous slide, minTime will be previous slide's start time plus minSlideTime
        if (item.prev().length > 0)
            minTime = item.prev().data('slideInfo').sTime + minSlideTime;
        // if has next slide, maxTime will be next slide's end time minus minSlideTime
        if (item.next().length > 0)
            maxTime = item.next().data('slideInfo').eTime - minSlideTime;
        log('minimum and maximum time for this slide:' + minTime + ' - ' + maxTime);
        // remove currentSide class
        allItems.removeClass('currentSlide');
        // add currentSlide class to this slide
        item.addClass('currentSlide');
        item.focus();
        // calculate width for slider
        var width = Math.round((maxTime - minTime) * ${player1Width}/avDuration);
        // calculate left margin
        var marginLeft = Math.round(minTime * ${player1Width}/avDuration);
        $('#slider-container').css('width', width)
                .css('margin-left', marginLeft);
        $('#slider-container').show();
        $("#slider-range").slider('destroy');
        $("#slider-range").slider({
                    range: true,
                    animate: true,
                    min: minTime,
                    max: maxTime,
                    step: 0.1,
                    values: [ item.data('slideInfo').sTime, item.data('slideInfo').eTime ],
                    create: function(event, ui) {
                        var lowHandle = $('#slider-range .ui-slider-handle').eq(0);
                        var highHandle = $('#slider-range .ui-slider-handle').eq(1);
                        // div to display current time
                        $('<div/>').text(convertSecondsToTimecode(item.data('slideInfo').sTime))
                                .addClass('startTime')
                                .appendTo(lowHandle);
                        $('<div/>').text(convertSecondsToTimecode(item.data('slideInfo').eTime))
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

    $('input[name=save]').click(function() {
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
    });

    function initSlides() {
        <#if avp.slideInfos?has_content>
            <#list avp.slideInfos as slideInfo>
                addSlide(${slideInfo.num?c}, ${slideInfo.sTime}, ${slideInfo.eTime}, '${slideInfo.title}');
            </#list>
        </#if>
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
                            // enable slide selector, if disabled
                            if ($('select[name=slideNum]').attr('disabled')) {
                                log("enable slide selector ...");
                                $('select[name=slideNum]').removeAttr("disabled");
                            }
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
                            initSlides();
                            firstPlay = false;
                        }
                    }

                }
            });

    function setupSlideScroller() {
        //scrollpane parts
        var scrollPane = $(".scroll-pane"),
                scrollContent = $(".scroll-content"),
                scrollContentItem = $(".scroll-content-item");
        var itemWidth = scrollContentItem.width();
        var contentWidth = scrollContentItem.length * itemWidth;
        var paneWidth = scrollPane.width();

        scrollContent.css('width', (contentWidth + 60) + 'px');

        if (scrollContent.width() > scrollPane.width()) {
            $(".scroll-bar-wrap").show();
            //build slider
            var scrollbar = $(".scroll-bar").slider({
                        slide: function(event, ui) {
                            if (scrollContent.width() > scrollPane.width()) {
                                scrollContent.css("margin-left", Math.round(
                                        ui.value / 100 * ( scrollPane.width() - scrollContent.width() )
                                ) + "px");
                            } else {
                                scrollContent.css("margin-left", 0);
                            }
                        }
                    });

            //append icon to handle
            var handleHelper = scrollbar.find(".ui-slider-handle")
                    .mousedown(function() {
                        scrollbar.width(handleHelper.width());
                    })
                    .mouseup(function() {
                        scrollbar.width("100%");
                    })
                    .append("<span class='ui-icon ui-icon-grip-dotted-vertical'></span>")
                    .wrap("<div class='ui-handle-helper-parent'></div>").parent();

            //change overflow to hidden now that slider handles the scrolling
            scrollPane.css("overflow", "hidden");

            //size scrollbar and handle proportionally to scroll distance
            function sizeScrollbar() {
                var remainder = scrollContent.width() - scrollPane.width();
                var proportion = remainder / scrollContent.width();
                var handleSize = scrollPane.width() - ( proportion * scrollPane.width() );
                scrollbar.find(".ui-slider-handle").css({
                            width: handleSize,
                            "margin-left": -handleSize / 2
                        });
                handleHelper.width("").width(scrollbar.width() - handleSize);
            }

            //reset slider value based on scroll content position
            function resetValue() {
                var remainder = scrollPane.width() - scrollContent.width();
                var leftVal = scrollContent.css("margin-left") === "auto" ? 0 :
                        parseInt(scrollContent.css("margin-left"));
                var percentage = Math.round(leftVal / remainder * 100);
                scrollbar.slider("value", percentage);
            }

            //if the slider is 100% and window gets larger, reveal content
            function reflowContent() {
                var showing = scrollContent.width() + parseInt(scrollContent.css("margin-left"), 10);
                var gap = scrollPane.width() - showing;
                if (gap > 0) {
                    scrollContent.css("margin-left", parseInt(scrollContent.css("margin-left"), 10) + gap);
                }
            }

            //change handle position on window resize
            $(window).resize(function() {
                resetValue();
                sizeScrollbar();
                reflowContent();
            });
            //init scrollbar size
            setTimeout(sizeScrollbar, 10);//safari wants a timeout
        } else {
            $(".scroll-bar-wrap").hide();
            scrollPane.css("overflow", "hidden");
        }
    }


});


//-->
</script>

    <#else>
    Can not find this Audio/Video Presentation.
</#if>
</div>