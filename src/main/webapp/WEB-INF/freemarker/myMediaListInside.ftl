<div class="stage">

    <#include "uploadMedia.ftl" />

    <#if pager.elements?size = 0>
    <p>You have no media file.</p>
    <#else>

    <form action="" name="listForm">
        <#list pager.paramList as param>
        <input type="hidden" name="${param.name?html}" value="${param.value?html}"/>
        </#list>

        <div class="navDiv">
            <input type="button" value="Delete" title="Delete selected media file(s)."/>
            <#if userAlbumList?has_content>
            <select name="albumID" title="<@spring.message "add.album.to.media"/>">
                <option value="0">Add to Album</option>
                <#list userAlbumList as userAlbum>
                <option value="${userAlbum.album.id?c}">${userAlbum.album.albumName}</option>
                </#list>
            </select>
            </#if>
            <a href="albumEdit.do?aa=new">Create a new album</a>
            | <a href="" title="Transfer Ownership to other user" class="transferOwner">Transfer Owner</a>
            | <a href="avpEdit.do" title="" class="newAVP">Create a new AVP</a>

            <span class="emptySpace">&nbsp; &nbsp; </span>
            <@displayGmailStylePager pager/>
        </div>

        <table summary="" width="100%">
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="all"/>
                    #
                </th>
                <th>View</th>
                <th>Edit</th>
                <th>Title</th>
                <th>Created</th>
                <th>Album(s)</th>
                <th>Delete</th>
                <th>Other</th>
            </tr>
            </thead>
            <tbody>
            <#list pager.elements as obj>
            <#assign linkTitle>${obj.title!?html}</#assign>
            <tr>
                <td><input type="checkbox" name="id" value="${obj.id?c}"/></td>
                <td>
                    <@viewLinkWithThumbnail obj />
                </td>
                <td>
                    <a href="edit.do?id=${obj.id?c}${pager.parameters?html}"
                       title="Edit this media file">
                        Edit
                    </a>
                </td>
                <td>
                    <a href="edit.do?id=${obj.id?c}${pager.parameters?html}"
                       title="${linkTitle}">
                        <@getTitle obj.title/>
                    </a>
                </td>
                <td>${obj.uploadTime?string("dd/MM/yyyy")}</td>
                <td>
                    <#list obj.albumMedias as albumMedia>
                    <#assign albumName=["${albumMedia.album.albumName}"]/>
                <span id="albumMediaID_${albumMedia.id?c}"><a class="album_with_right_border"
                                                              title="<@spring.messageArgs  "go.to.album" albumName/>"
                                                              href="${baseUrl}/album?a=${albumMedia.album.accessCode}">
                    ${albumMedia.album.albumName}
                </a><a class="album"
                       title="<@spring.messageArgs  "remove.album.from.media" albumName/>"
                       href="#" rel="${albumMedia.id?c}">X</a> &nbsp;</span>
                    </#list>
                </td>
                <td>
                    <a href="#" rel="delete.do?id=${obj.id?c}${pager.parameters?html}" title="Delete this media file"
                       class="delete">delete</a>
                </td>
                <td>
                    <#if obj.accessType ==10>
                    <span title="<@spring.message "media.access.hidden"/>">hidden</span>
                    </#if>
                    <#if obj.accessType ==20>
                    <span title="<@spring.message "media.access.private"/>">private</span>
                    </#if>
                    <#if obj.status == 0>
                    <span title="waiting for conversion">Waiting</span>
                    <#elseif obj.status == 1>
                    <span title="is converting ...">Processing</span>
                    <#elseif obj.status == 9>
                    <span title="unrecognized media format">Unrecognized</span>
                    </#if>
                </td>
            </tr>
            </#list>
            </tbody>
        </table>

        <div class="navDiv">
            <input type="button" value="Delete" title="Delete selected media file(s)."/>
            <#if userAlbumList?has_content>
            <select name="albumID_1" title="<@spring.message "add.album.to.media"/>">
                <option value="0">Add to Album</option>
                <#list userAlbumList as userAlbum>
                <option value="${userAlbum.album.id?c}">${userAlbum.album.albumName}</option>
                </#list>
            </select>
            </#if>
            <a href="albumEdit.do?aa=new">Create a new album</a>
            <span class="emptySpace">&nbsp; &nbsp; </span>
            <@displayGmailStylePager pager/>
        </div>

    </form>

    <div id="user-lookup-form" title="Find user" class="hidden">
        <form action="${baseUrl}/myTube/transferOwner.do">
            <p class="validateTips"></p>

            <div>
                Please input the username of new owner:
                <input type="text" name="userName" id="userName" rel="${baseUrl}/myTube/userNameSearch.do"/>
            </div>
        </form>
    </div>

    </#if>

</div>