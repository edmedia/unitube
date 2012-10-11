<div class="myOptions">

    <a href="${baseUrl}/myTube/list.do"
    <#if this_url?? && this_url?contains("list.do")> class="currentPage"</#if>>
        My Media </a> |
    <a href="${baseUrl}/myTube/albumList.do"
    <#if this_url?? && this_url?contains("albumList.do")> class="currentPage"</#if>>
        My Albums</a> |
    <a href="${baseUrl}/myTube/avpList.do"
    <#if this_url?? && this_url?contains("avpList.do")> class="currentPage"</#if>>
        My Presenter </a> |
    <a href="${baseUrl}/myTube/profile.do"
    <#if this_url?? && this_url?contains("profile.do")> class="currentPage"</#if>>
        My Profile </a>
<#if authUser?? && authUser.isInstructor>
    | <a href="${baseUrl}/admin/userList.do"<#if this_url?? && this_url?contains("userList.do")>
         class="currentPage"</#if>> Admin</a>
</#if>
</div>
