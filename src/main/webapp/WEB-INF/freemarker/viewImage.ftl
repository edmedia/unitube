<a href="${imageFileLink?html}" title="${obj.title?html}" class="shadowbox">
    <img src="${mediaFileLink?html}" width="${width?c}" height="${height?c}" alt="${obj.title?html}"
         title="${obj.title?html}"/>
</a>

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
