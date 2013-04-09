<#include "/myOptions.ftl"/>

<h2>Search Results</h2>

<form action="" name="search">
    <input type="text" name="q" value="${adminSearchWords!?html}" class="searchText" x-webkit-speech/>
    <input type="submit" value="Admin Search"/>
</form>

<#-- if no user or media matched, display no result information -->
<#if !mediaList?has_content && !userList?has_content>
<div class="stage">
    <div class="info">
        <#assign term=["${adminSearchWords}"]/>
        <@spring.messageArgs "search.no.result" term/>
    </div>
</div>
<#else>

<div id="tabs" style="width: 1004px; margin-top: 30px; float: left">
    <ul>
        <#if userList?has_content>
            <li><a href="#tabs-1">Matched Users (${userList?size})</a></li>
        </#if>
        <#if mediaList?has_content>
            <li><a href="#tabs-2">Matched Media (${mediaList?size})</a></li>
        </#if>
    </ul>

    <#if userList?has_content>
        <div id="tabs-1">

            <table summary="">
                <thead>
                <tr>
                    <th>Username</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Mobile Number</th>
                    <th>Login Times</th>
                    <th>Last Login Time</th>
                    <th>Last Login IP</th>
                    <th>Is this user a guest?</th>
                    <th>All Medias</th>
                </tr>
                </thead>
                <tbody>
                    <#list userList as obj>
                    <tr>
                        <td><a href="userEdit.do?id=${obj.id?c}">${obj.meaningfulName}</a></td>
                        <td>
                        ${obj.firstName!?string}
                        </td>
                        <td>
                        ${obj.lastName!?string}
                        </td>
                        <td>
                        ${obj.mobile!?string}
                        </td>
                        <td>
                        ${obj.loginTimes!?string}
                        </td>
                        <td>
                        ${obj.lastLoginTime!?string}
                        </td>
                        <td>
                        ${obj.lastLoginIP!?string}
                        </td>
                        <td>
                        ${obj.isGuest!?string}
                        </td>
                        <td>
                            <a href="mediaList.do?userID=${obj.id?c}">all medias (${obj.medias?size})</a>
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </#if>

    <#if mediaList?has_content>
        <div id="tabs-2">
            <table summary="" width="100%">
                <thead>
                <tr>
                    <th>Title</th>
                    <th>Media Type</th>
                    <th>Width</th>
                    <th>Height</th>
                    <th>Access Type</th>
                    <th>Owner</th>
                    <th>Link</th>
                </tr>
                </thead>
                <tbody>
                    <#list mediaList as entity>
                    <tr>
                        <td>${entity.meaningfulName?html}</td>
                        <td>
                            <#if entity.mediaType == 1>
                                Other Media
                            <#elseif entity.mediaType == 5>
                                Image
                            <#elseif entity.mediaType == 10>
                                Audio
                            <#elseif entity.mediaType == 20>
                                Video
                            </#if>
                        </td>
                        <td>
                        ${entity.width!?string}
                        </td>
                        <td>
                        ${entity.height!?string}
                        </td>
                        <td>
                            <#if entity.accessType ==10>
                                <span title="<@spring.message "media.access.hidden"/>">hidden</span>
                            </#if>
                            <#if entity.accessType ==20>
                                <span title="<@spring.message "media.access.private"/>">private</span>
                                <#if entity.accessRules?has_content>
                                    <p>Only owner and those people have access</p>
                                    <ul>
                                        <#list entity.accessRules as ar>
                                            <#if ar.user?has_content>
                                                <li>${ar.user.userName?html}
                                                    (${ar.user.firstName?html} ${ar.user.lastName?html})
                                                </li>
                                            <#else>
                                                <li>${ar.userInput?html}</li>
                                            </#if>
                                        </#list>
                                    </ul>
                                <#else>
                                    <p>Only owner has access</p>
                                </#if>
                            </#if>
                        </td>
                        <td>
                            <a href="mediaList.do?userID=${entity.user.id?c}">${entity.user.userName?html} (${entity.user.firstName?html} ${entity.user.lastName?html}) </a>
                        </td>
                        <td>
                            <a href="${baseUrl}/view?m=${entity.accessCode}">view</a>
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </#if>



</div>

<script type="text/javascript">
    <!--
    $(function () {
        $("#tabs").tabs();
    });
    //-->
</script>
</#if>

<div id="bottomNav">
    <a href="${baseUrl}/myTube/admin.do">Back to user list</a>
</div>
