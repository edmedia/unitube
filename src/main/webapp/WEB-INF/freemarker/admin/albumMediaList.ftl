<#import "../spring.ftl" as spring />
<#assign title>List All AlbumMedia</#assign>
<#include "/common/head_1.ftl" />
<script type="text/javascript">
<!--
<#assign message1><@spring.message "delete.select.first"/></#assign>
<#assign message2><@spring.message "delete.confirm.albumMedia"/></#assign>
function deleteRecords() {
    var message1 = "${message1?js_string}";
    if($('input:checkbox[name=id]:checked').size() == 0) {
        alert(message1);
        return false;
    }
    if(deleteConfirm()) {
        document.listForm.action = "albumMediaDelete.do";
        document.listForm.submit();
        return true;
    } else
        return false;
}

function deleteConfirm() {
    var message2 = "${message2?js_string}";
    return confirm(message2);
}

$(function() {
    // check or uncheck all checkboxes
    $('input:checkbox[name=all]').click(function() {
        $('input:checkbox[name=id]').attr('checked', $('input:checkbox[name=all]').is(':checked'));
    });

    $('a.delete').click(function() {
        if (deleteConfirm()) {
            this.href = this.rel;
        }
    });

    if (window.init)
        init();

});
//-->
</script>
<#include "/common/head_2.ftl" />
<#include "/common/header.ftl" />

<#include "custom/*/albumMediaListInside.ftl" />

<#include "/common/footer.ftl" />
