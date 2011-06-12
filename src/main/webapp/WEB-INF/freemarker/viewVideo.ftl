<script type="text/javascript">
    <!--
    jwplayer('avPlayer').setup({
                flashplayer: '${JWPLAYER}',
                bufferlength: 5,
            <#if obj.duration &gt; 0>
                duration: ${(obj.duration/1000)?c},
            </#if>
                provider: 'video'
            });
    //-->
</script>
