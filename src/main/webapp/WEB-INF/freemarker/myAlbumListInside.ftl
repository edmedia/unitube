<#include "myOptions.ftl"/>

<div class="stage">
    <form action="" name="listForm">
    <#list pager.paramList as param>
        <input type="hidden" name="${param.name?html}" value="${param.value?html}"/>
    </#list>

        <div class="navDiv">
        <#if pager.elements?has_content>
            <input type="button" value="Delete"/>
            <select name="userID" title="<@spring.message "add.user.to.album"/>">
                <option value="0">Add User</option>
                <#list userList?sort_by("lastName") as u>
                    <#if (u.userName != user.userName) && !u.isGuest>
                        <option value="${u.id?c}">${u.userName} (${u.firstName} ${u.lastName})</option>
                    </#if>
                </#list>
            </select>
        </#if>
            <a href="albumEdit.do?aa=new">Create a new album</a>
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
                <th>User(s)</th>
                <th></th>
            </tr>

            <#list pager.elements as obj>
                <tr>
                    <td><input type="checkbox" name="id" value="${obj.id?c}"/></td>
                    <td>
                        <a href="albumEdit.do?id=${obj.id?c}${pager.parameters?html}">${obj.meaningfulName?html}</a>
                    </td>
                    <td>
                        <a href="${baseUrl}/album?a=${obj.accessCode}">${baseUrl}/album?a=${obj.accessCode}</a>
                    </td>
                    <td>
                        <#list obj.userAlbums as userAlbum>
                            <#if userAlbum.user.userName != user.userName>
                                <#assign userName=["${userAlbum.user.userName}"]/>
                                <span id="userAlbumID_${userAlbum.id?c}"><a class="album_with_right_border"
                                                                            href="${baseUrl}/media.do?u=${userAlbum.user.accessCode}">
                                ${userAlbum.user.userName} (${userAlbum.user.firstName} ${userAlbum.user.lastName})
                                </a><a class="album"
                                       title="<@spring.messageArgs "remove.user.from.album" userName/>"
                                       href="#" rel="${userAlbum.id?c}">X</a> &nbsp;</span>
                            </#if>
                        </#list>
                    </td>
                    <td>
                        <a href="#" rel="albumDelete.do?id=${obj.id?c}${pager.parameters?html}"
                           class="delete">delete</a>
                    </td>
                </tr>
            </#list>

        </table>

        <div class="navDiv">
            <input type="button" value="Delete"/>
            <select name="userID_1" title="<@spring.message "add.user.to.album"/>">
                <option value="0">Add User</option>
                <#list userList?sort_by("lastName") as u>
                    <#if (u.userName != user.userName) && !u.isGuest>
                        <option value="${u.id?c}">${u.userName} (${u.firstName} ${u.lastName})</option>
                    </#if>
                </#list>
            </select>
            <a href="albumEdit.do?aa=new">Create a new album</a>
            <span class="emptySpace">&nbsp; &nbsp; </span>
        <@displayGmailStylePager pager/>
        </div>
    </#if>
    </form>
</div>
