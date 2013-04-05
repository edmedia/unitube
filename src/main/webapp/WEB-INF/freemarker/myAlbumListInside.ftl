<#include "myOptions.ftl"/>

<div class="stage">
    <form action="" name="listForm">
    <#list pager.paramList as param>
        <input type="hidden" name="${param.name?html}" value="${param.value?html}"/>
    </#list>

        <div class="navDiv">
        <#if pager.elements?has_content>
            <input type="button" value="Delete"/>
        </#if>
            <a href="albumEdit.do?aa=new">Create a new album</a>
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
                <th><span title="<@spring.message "album.user.title"/>">User(s)</span></th>
                <th></th>
                <th>Other</th>
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
                        <a href="#" class="addUser" title="<@spring.message "add.user.to.album"/>"
                           rel="${obj.id?c}">+</a>
                    </td>
                    <td>
                        <a href="#" rel="albumDelete.do?id=${obj.id?c}${pager.parameters?html}"
                           class="delete">delete</a>
                    </td>
                    <td>
                        <#if obj.accessType ==10>
                            <span title="<@spring.message "album.access.hidden"/>">hidden</span>
                        </#if>
                    </td>
                </tr>
            </#list>

        </table>

        <div class="navDiv">
            <input type="button" value="Delete"/>
            <a href="albumEdit.do?aa=new">Create a new album</a>
            <span class="emptySpace">&nbsp; &nbsp; </span>
            <@displayGmailStylePager pager/>
        </div>
    </#if>
    </form>
</div>

<div id="addUserToAlbum" title="Add user who can add media into your album" class="false">
    <form action="${baseUrl}/myTube/applyUser.do" name="applyUser">
        <input id="albumID" name="albumID" type="hidden" value=""/>

        <p>Add user:</p>
        Username:
        <input id="userName" name="userName" type="text" size="35"
               rel="${baseUrl}/myTube/userSearch.do"/>
        <input type="submit" value="Add" name="add"/>

        <p>&nbsp;</p>
    </form>
</div>

<script type="text/javascript">
    <!--

    function init() {
        addUserToAlbum();
    }

    function addUserToAlbum() {

        // define share dialog here
        $("#addUserToAlbum").dialog({
            autoOpen: false,
            width: 500,
            modal: true
        });

        var userName = $('#userName');
        // auto-complete username
        userName.autocomplete({
            minLength: 2,
            source: userName.attr("rel"),
            select: function (e, ui) {
                var value = ui.item.value;
                log("select " + value);
                if (value.indexOf(" ") != -1)
                    value = value.substring(0, value.indexOf(" "));
                userName.val(value);
                return false;
            }
        });

        $('a.addUser').on("click", function () {
            // set albumID
            log($(this).attr("rel"));
            $('#albumID').val($(this).attr("rel"));
            // open share dialog
            $("#addUserToAlbum").dialog('open');
            $('#userInput').focus();
            return false;

        });
    }
    //-->
</script>