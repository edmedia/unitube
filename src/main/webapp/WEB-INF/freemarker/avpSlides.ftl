<div>
    <div id="slide_-1" class="slide">
        <img src="${baseUrl}/images/slide.png" width="${presentationWidth?c}"
             height="${presentationHeight?c}"
             alt="Slides here"/>
    </div>
<#list 1..presentation.duration as i>
    <div id="slide_${(i-1)?c}" class="slide">
        <a href="${presentationFileLink?replace('-(\\d)+\\.', '-' + (i-1) + '.', 'r')?html}" class="slideLink">
            <img src="${presentationFileLink?replace('-(\\d)+\\.', '-' + (i-1) + '.', 'r')?html}"
                 width="${presentationWidth?c}"
                 height="${presentationHeight?c}"
                 alt="slide ${i}"/>
        </a>
    </div>
</#list>
    <div>
        <a href="${baseUrl}/view?m=${presentation.accessCode}">View this presentation only</a>
    </div>
</div>