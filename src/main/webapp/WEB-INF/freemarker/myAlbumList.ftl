<#assign title>My Albums</#assign>
<#include "common/head_1.ftl" />
<script type="text/javascript">
    <!--
    <#assign message1><@spring.message "delete.select.first.album"/></#assign>
    <#assign message2><@spring.message "delete.confirm.album"/></#assign>
    var message1 = "${message1?js_string}";
    function deleteRecords() {
        if ($('input:checkbox[name=id]:checked').size() == 0) {
            alert(message1);
            return false;
        }
        if (deleteConfirm()) {
            document.listForm.action = "${baseUrl}/myTube/albumDelete.do";
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

        $('input:button[value=Delete]').click(function() {
            deleteRecords();
        });

        $('select[name^=userID]').change(function() {
            applyUser();
        });

        $('a.album').click(function() {
            return removeUserFromAlbum(this.rel);
        });

        if (window.init)
            init();

    });

    function applyUser() {
        if ($('input:checkbox[name=id]:checked').size() == 0) {
            alert(message1);
            $("select[name^=userID]").val("0");
            return false;
        }
        if ($("select[name=userID]").val() == "0")
            $("select[name=userID]").val($("select[name=userID_1]").val());
        document.listForm.action = "applyUser.do";
        document.listForm.submit();
        return true;
    }

    //-->
</script>
<#include "common/head_2.ftl" />
<#include "common/header.ftl" />

<#include "myAlbumListInside.ftl" />

<#include "common/footer.ftl" />
