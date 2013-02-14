<#--
    owner and accessRules have to be set before using it.
    and before show the dialog, set ar_mediaID to media id.
-->
<div id="share-dialog" title="Sharing settings">
    <h2>Permissions:</h2>

    <ul id="ar_list">
        <li>
            <img src="${baseUrl}/images/lock.png" alt="private"/>
            Private - Only the people listed below can access
        </li>
    <#if owner?has_content>
        <li>
            <span title="${owner.email!}" class="left">${owner.firstName} ${owner.lastName} (you)</span>
            <span class="splitter"> -  </span>
            <span class="right">Is owner</span>
        </li>
    </#if>
    <#if accessRules?has_content>
        <#list accessRules as accessRule>
            <#if accessRule.user?has_content>
                <li id="ar_${accessRule.id?c}">
            <span title="${accessRule.user.email}"
                  class="left">${accessRule.user.firstName} ${accessRule.user.lastName}</span>
                    <span class="splitter"> -  </span>
                    <span class="right"><a href="#${accessRule.id?c}" class="delete"
                                           title="Remove this user">X</a></span>
                </li>
            <#else>
                <li id="ar_${accessRule.id?c}">
                    <span class="left">${accessRule.userInput?html}</span>
                    <span class="splitter"> -  </span>
                    <span class="right"><a href="#${accessRule.id?c}" class="delete"
                                           title="Remove this user">X</a></span>
                </li>
            </#if>
        </#list>
    </#if>
    </ul>

    <div>
        <form action="${baseUrl}/myTube/accessRuleAdd.do" name="addAccessRule">
            <input id="ar_mediaID" type="hidden" value=""/>

            <p>Add people:</p>
            Username:
            <input id="userInput" type="text" size="35"
                   rel="${baseUrl}/myTube/userSearch.do"/>
            <input type="button" value="Share" name="share"/>

            <p>&nbsp;</p>
        </form>
    </div>

</div>

<script type="text/javascript">
    <!--
    function shareDialog() {
        // info tag which will display information
        var info = $("<div/>").appendTo(document.body);
        info.dialog({
            autoOpen: false,
            title: 'Information'
        });


        // define share dialog here
        $("#share-dialog").dialog({
            autoOpen: false,
            width: 600,
            modal: true
        });

        // when clicking delete accessRule
        $('a.delete').click(function () {
            deleteAccessRule($(this));
        });

        // auto-complete username
        $('#userInput').autocomplete({
            minLength: 2,
            source: $('#userInput').attr("rel"),
            select: function (e, ui) {
                log("select " + ui.item.value);
                $('#userInput').val(ui.item.value);
            }
        });

        $('input:button[name=share]').click(function () {
            if ($('#userInput').val().length == 0) {
                info.html("<div>Please input username first.<\/div>").dialog('open');
                return false;
            }
            $(this).parent().submit();
        });

        // when submitting addAccessRule form
        $('form[name=addAccessRule]').submit(function () {
            // get media id from hidden input, must be set before show dialog
            var id = $('#ar_mediaID').val();
            log("id = " + id);
            var url = this.action + "?mediaID=" + id + "&userInput=" + $('#userInput').val();
            log("addAccessRule url = " + url);
            $.get(url, function (xml) {
                if ($("action", xml).attr("success") == "true") {
                    var detail = $("action", xml).attr("detail");
                    log("detail = " + detail);
                    if (detail.substring(0, 1) == "{") {
                        var obj = $.parseJSON(detail);
                        var li = $('<li/>').attr("id", "ar_" + obj.id)
                                .appendTo($('#ar_list'));
                        var left = $('<span/>')
                                .addClass('left')
                                .appendTo(li);
                        if (obj.email)
                            left.attr('title', obj.email);
                        if (obj.firstName && obj.lastName)
                            left.text(obj.firstName + " " + obj.lastName);
                        else
                            left.text(obj.userName);
                        var splitter = $('<span/>')
                                .addClass('splitter')
                                .text(' - ')
                                .appendTo(li);
                        var right = $('<span/>')
                                .addClass('right')
                                .appendTo(li);
                        var aa = $('<a/>')
                                .attr('href', '#' + obj.id)
                                .attr('title', 'Remove this user')
                                .addClass('delete')
                                .text('X')
                                .appendTo(right);
                    } else {
                        info.text(detail);
                        info.dialog("open");
                    }
                    $('a.delete').click(function () {
                        deleteAccessRule($(this));
                    });
                    $('#userInput').val('');
                } else
                    info.html("<div>" + $("action", xml).attr("detail") + "<\/div>").dialog('open');
            });
            return false;
        });

        function deleteAccessRule(thisElement) {
            if (confirm('Are you sure you want to remove this user?')) {
                var id = thisElement.attr('href').substring(1);
                var url = "${baseUrl}/myTube/accessRuleDelete.do?id=" + id;
                log("delete accessRule url " + url);
                $.get(url, function (xml) {
                    if ($("action", xml).attr("success") == "true") {
                        $('#ar_' + id).remove();
                    } else
                        info.html("<div>" + $("action", xml).attr("detail") + "<\/div>").dialog('open');
                });
            }
            return false;
        }

    }

    //-->
</script>