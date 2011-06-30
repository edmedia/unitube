<div class="stage">
    <form action="" name="listForm">
        <#list pager.paramList as param>
        <input type="hidden" name="${param.name?html}" value="${param.value?html}"/>
        </#list>

        <div class="navDiv">
            <#if pager.elements?has_content>
            <input type="button" value="Delete"/>
            </#if>
            <a href="avpEdit.do?aa=new">Create a new AVP</a>
            |
            <a href="list.do">My Media</a>

            <span class="emptySpace">&nbsp; &nbsp; </span>
            <@displayGmailStylePager pager/>
        </div>

        <#if pager.elements?has_content>
        <table summary="">

            <tr>
                <th>
                    <input type="checkbox" name="all"/>
                    #
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
                    <a href="${baseUrl}/avp.do?a=${obj.accessCode}">${baseUrl}/avp?a=${obj.accessCode}</a>
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
            <a href="avpEdit.do?aa=new">Create a new AVP</a>
            |
            <a href="list.do">My Media</a>
            <span class="emptySpace">&nbsp; &nbsp; </span>
            <@displayGmailStylePager pager/>
        </div>

    </form>

    </#if>
</div>
