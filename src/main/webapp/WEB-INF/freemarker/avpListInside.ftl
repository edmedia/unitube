<#include "myOptions.ftl"/>

<div class="stage">
    <form action="" name="listForm">
    <#list pager.paramList as param>
        <input type="hidden" name="${param.name?html}" value="${param.value?html}"/>
    </#list>

        <p>Presenter allows you to combine different types of media to create your own presentation. This would be handy
            if you wanted to present an audio or video recording
            of your lecture together with your Powerpoint slides. You can specify the timing of the PowerPoint slideshow
            to synchronise it with the audio/video file. <a href="${baseUrl}/myPresenterGuide.do">Find out more about
                how to use Presenter.</p>

        <div class="navDiv">
        <#if pager.elements?has_content>
            <input type="button" value="Delete"/>
        </#if>
            <a href="avpEdit.do?aa=new">Create a new presentation</a>
            <span class="emptySpace">&nbsp; &nbsp; </span>
        <@displayGmailStylePager pager/>
        </div>

    <#if pager.elements?has_content>
        <table summary="" width="100%">

            <tr>
                <th class="checkbox">
                    <input type="checkbox" name="all"/> #
                </th>
                <th>name</th>
                <th>URL</th>
                <th>Synchronisation Tool</th>
                <th></th>
            </tr>

            <#list pager.elements as obj>
                <tr>
                    <td><input type="checkbox" name="id" value="${obj.id?c}"/></td>
                    <td>
                        <a href="avpEdit.do?id=${obj.id?c}${pager.parameters?html}">${obj.meaningfulName?html}</a>
                    </td>
                    <td>
                        <a href="${baseUrl}/avp.do?a=${obj.accessCode}">${baseUrl}/avp.do?a=${obj.accessCode}</a>
                    </td>
                    <td>
                        <a href="avpSync.do?a=${obj.accessCode}">Sync</a>
                    </td>
                    <td>
                        <a href="#" rel="avpDelete.do?id=${obj.id?c}${pager.parameters?html}" class="delete">delete</a>
                    </td>
                </tr>
            </#list>

        </table>

        <div class="navDiv">
            <input type="button" value="Delete"/>
            <a href="avpEdit.do?aa=new">Create a new presentation</a>
            <span class="emptySpace">&nbsp; &nbsp; </span>
        <@displayGmailStylePager pager/>
        </div>
    </#if>
    </form>
</div>

<script type="text/javascript">
    <!--
    <#assign message1><@spring.message "delete.select.first.AVP"/></#assign>
    <#assign message2><@spring.message "delete.confirm.AVP"/></#assign>
    var message1 = "${message1?js_string}";
    var info;
    function deleteRecords() {
        if ($('input:checkbox[name=id]:checked').size() == 0) {
            alert(message1);
            return false;
        }
        if (deleteConfirm()) {
            document.listForm.action = "${baseUrl}/myTube/avpDelete.do";
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

    });
    //-->
</script>