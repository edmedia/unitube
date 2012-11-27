<script type="text/javascript">
    var width = ${width?c};
    var height = ${height?c};
    var embed = (document.URL.indexOf("embed") != -1);
    var embedPlaylist = (document.URL.indexOf("embedPlaylist") != -1);
    var navigationHeight = 15;
    if (embed) {
        width = $(window).width();
        height = $(window).height();
    }
    <#if playerWidth?? && playerHeight?? && playlistNav??>
    width = ${playerWidth?c};
        <#if obj.duration &gt; 1>
        height = ${playerHeight!?c} - navigationHeight;
            <#else>
            height = ${playerHeight!?c}
        </#if>
    if (embedPlaylist) {
        width = $(window).width() - ${playlistNav?c};
        <#if obj.duration &gt; 1>
            height = $(window).height() - navigationHeight;
            <#else>
                height = $(window).height();
        </#if>
    }
    </#if>
    // keep image ratio
    var tmp = (${obj.width?c} * height / ${obj.height?c})
    ;
    if (tmp > width)
        height = (${obj.height?c}* width / ${obj.width?c})
    ;
    else
    width = tmp;
</script>
<#if obj.duration &gt; 1>
    <#assign usingSlides=true/>
    <#if usingSlides>
    <div id="slides__${obj.id?c}">
        <div class="slides_container">
            <#list 1..obj.duration as i>
                <div class="slide">
                    <img src="${mediaFileLink?replace('-(\\d)+\\.', '-' + (i-1) + '.', 'r')?html}" alt="Slide ${i}"/>
                </div>
            </#list>
        </div>
    </div>
    <script type="text/javascript" src="${baseUrl}/javascript/slides.min.jquery.js"></script>
    <script type="text/javascript">
        var __slides = $('#slides__${obj.id?c}');
        $('div.slide img', __slides).css('width', width + 'px').css('height', height + 'px');
        $('div.slides_container', __slides).css('width', width + 'px');
        $('ul.pagination', __slides).css('width', width + 'px');
        __slides.slides({
            preload: true,
            preloadImage: '',
            bigTarget: true,
            hoverPause: true,
            dummy: true
        });
            <#if obj.duration &gt; 40>
            // set float to left if there are more than 40 slides
            $('ul.pagination li', __slides).css('float', 'left');
            </#if>
    </script>
        <#else>
        <#-- try GalleryView here -->
        <ul id="gallaryView__${obj.id?c}">
            <#list 1..obj.duration as i>
                <li><img src="${mediaFileLink?replace('-(\\d)+\\.', '-' + (i-1) + '.', 'r')?html}" width="${width?c}"
                         height="${height?c}" title="Slide ${i}" alt="Slide ${i}"/></li>
            </#list>
        </ul>
        <script type="text/javascript" src="${baseUrl}/javascript/jquery.timers-1.2.js"></script>
        <script type="text/javascript" src="${baseUrl}/javascript/jquery.easing-1.3.js"></script>
        <script type="text/javascript" src="${baseUrl}/javascript/jquery.galleryview-3.0.min.js"></script>
        <link type="text/css" rel="stylesheet" href="${baseUrl}/galleryview/jquery.galleryview-3.0.css"/>
        <script type="text/javascript">
            <!--
            $('#gallaryView__${obj.id?c}').galleryView({
                transition_interval: 10000,
                panel_width: ${width?c},
                panel_height: ${height?c},
                frame_width: ${(width/6)?c},
                frame_height: ${(height/6)?c},
                //filmstrip_style: 'show all',
                frame_opacity: 0.8,
                show_captions: true,
                pause_on_hover: true,
                dummy: true
            });
            //-->
        </script>
    </#if>
    <#else>
    <div>
        <a href="${mediaFileLink?html}" title="${obj.title?html}">
            <img src="${mediaFileLink?html}" width="${width?c}"
                 height="${height?c}" alt="${obj.title?html}" class="image__${obj.id?c}"/>
        </a>
    </div>
    <script type="text/javascript">
        $('img.image__${obj.id?c}').css('width', width + 'px').css('height', height + 'px');
    </script>
</#if>
