<a href="${imageFileLink?html}" title="${obj.title?html}" class="shadowbox">
    <img src="${mediaFileLink?html}" width="${width?c}" height="${height?c}" alt="${obj.title?html}"
         title="${obj.title?html}"/>
</a>

<#assign embedCode><iframe width="${width?c}" height="${(height)?c}" src="${embedURL?html}" frameborder="0" allowfullscreen></iframe></#assign>
<#assign embedCode><a href="${imageFileLink?html}" title="${obj.title?html}"><img src="${mediaFileLink?html}" width="${width?c}" height="${height?c}" alt="${obj.title?html}" title="${obj.title?html}"/></a></#assign>


<script type="text/javascript">
    <!--
    Shadowbox.init({
        skipSetup: true
    });

    Shadowbox.setup("a.shadowbox", {
        overlayOpacity: 0.85,
        player: 'img',
        counterType: 'skip'
    });
    //-->
</script>
