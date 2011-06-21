<#assign title>My Media</#assign>
<#include "common/head_1.ftl" />
<script type="text/javascript">
    <!--
    <#assign message1><@spring.message "delete.select.first.media"/></#assign>
    <#assign message2><@spring.message "delete.confirm.media"/></#assign>
    var message1 = "${message1?js_string}";
    var info;
    function deleteRecords() {
        if ($('input:checkbox[name=id]:checked').size() == 0) {
            alert(message1);
            return false;
        }
        if (deleteConfirm()) {
            document.listForm.action = "${baseUrl}/myTube/delete.do";
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

        $('select[name^=albumID]').change(function() {
            applyAlbum();
        });

        $('a.album').click(function() {
            return removeAlbumFromMedia(this.rel);
        });

        initUploadForm();
        if (window.init)
            init();

        // info tag which will display information
        info = $("<div/>").appendTo(document.body);
        info.dialog({
                    autoOpen: false,
                    title: 'Information'
                });

        transferOwner();
    });


    function applyAlbum() {
        if ($('input:checkbox[name=id]:checked').size() == 0) {
            alert(message1);
            $("select[name^=albumID]").val("0");
            return false;
        }
        if ($("select[name=albumID]").val() == "0")
            $("select[name=albumID]").val($("select[name=albumID_1]").val());
        document.listForm.action = "applyAlbum.do";
        document.listForm.submit();
        return true;
    }


    function transferOwner() {
        $('a.transferOwner').click(function() {
            if ($('input:checkbox[name=id]:checked').size() == 0) {
                alert(message1);
                return false;
            }
            var userName = $("#userName");
            var tips = $(".validateTips");
            if (userName) {
                // add auto complete to username
                userName.autocomplete({
                            minLength: 2,
                            source: userName.attr("rel"),
                            select: function(e, ui) {
                                if (window.console) console.log("select " + ui.item.value);
                                userName.val(ui.item.value);
                                // $('#user-lookup-form').children().first().submit();
                            }
                        });
                userName.keydown(function(e) {
                    // when press "enter" key
                    if (e.keyCode == 13) {
                        $('#user-lookup-form').children().first().submit();
                        return false;
                    }
                });
            }

            function updateTips(t) {
                tips.text(t)
                        .addClass('ui-state-highlight');
                setTimeout(function() {
                    tips.removeClass('ui-state-highlight', 1500);
                }, 500);
            }

            function required(o, n) {
                if (o.val() == 0) {
                    o.addClass("ui-state-error");
                    updateTips(n + " is compulsory.");
                    o.focus();
                    return false;
                } else {
                    return true;
                }
            }

            // user lookup form dialog
            $("#user-lookup-form").dialog({
                        autoOpen: false,
                        width: 540,
                        modal: true,
                        buttons: {
                            'Transfer Owner': function() {
                                var bValid = true;
                                userName.removeClass("ui-state-error");
                                bValid = bValid && required(userName, "username");
                                if (bValid)
                                    $(this).children().first().submit();
                            },
                            Cancel: function() {
                                $(this).dialog('close');
                            }
                        },
                        close: function() {
                            userName.val("").removeClass("ui-state-error");
                        }
                    });

            // when submitting user lookup form
            $('#user-lookup-form').children().first().submit(function() {
                var allVals = [];
                $('input:checkbox[name=id]:checked').each(function() {
                    allVals.push($(this).val());
                });
                if (window.console) console.log("id = " + allVals);

                var url = this.action + "?mediaIds=" + allVals + "&userName=" + userName.val();
                if (window.console) console.log("transferOwner url = " + url);
                $.get(url, function(xml) {
                    if ($("action", xml).attr("success") == "true")
                        info.html("<div>Transfer Ownership successfully.<\/div>").dialog('open');
                    else
                        info.html("<div>" + $("action", xml).attr("detail") + "<\/div>").dialog('open');
                });
                return false;
            });
            $("#user-lookup-form").dialog('open');
            userName.focus();
            return false;
        });
    }
    //-->
</script>

<#include "common/head_2.ftl" />

<#include "common/header.ftl" />

<#include "myMediaListInside.ftl" />

<#include "common/footer.ftl" />
