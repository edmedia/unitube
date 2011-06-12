<#if obj.duration &gt; 1>
    <#assign usingSlides=true/>
    <#if usingSlides>
    <div id="slides">
        <div class="slides_container">
            <#list 1..obj.duration as i>
                <div class="slide">
                    <img src="${mediaFileLink?replace('-(\\d)+\\.', '-' + (i-1) + '.', 'r')?html}" width="${width?c}"
                         height="${height?c}" alt="Slide ${i}"/>
                </div>
            </#list>
        </div>
    </div>
    <script type="text/javascript" src="${baseUrl}/javascript/slides.min.jquery.js"></script>
    <script type="text/javascript">
        $('.slides_container').css('width', '${width?c}px');
        $('ul.pagination').css('width', '${width?c}px');
        $('#slides').slides({
                    preload: true,
                    preloadImage: '',
                    bigTarget: true,
                    hoverPause: true
                });
    </script>
        <#else>
        <#-- try GalleryView here -->
        <ul id="gallaryView">
            <#list 1..obj.duration as i>
                <li><img src="${mediaFileLink?replace('-(\\d)+\\.', '-' + (i-1) + '.', 'r')}" width="${width?c}"
                         height="${height?c}" title="Slide ${i}" alt="Slide ${i}"/></li>
            </#list>
        </ul>
        <script type="text/javascript" src="${baseUrl}/javascript/jquery.timers-1.2.js"></script>
        <script type="text/javascript" src="${baseUrl}/javascript/jquery.easing-1.3.js"></script>
        <script type="text/javascript" src="${baseUrl}/javascript/jquery.galleryview-3.0.min.js"></script>
        <link type="text/css" rel="stylesheet" href="${baseUrl}/galleryview/jquery.galleryview-3.0.css"/>
        <script type="text/javascript">
            <!--
            $('#gallaryView').galleryView({
                        transition_interval: 10000,
                        panel_width: ${width?c},
                        panel_height: ${height?c},
                        frame_width: ${(width/12)?c},
                        frame_height: ${(height/12)?c},
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
                 height="${height?c}" alt="${obj.title?html}"/>
        </a>
    </div>
</#if>
