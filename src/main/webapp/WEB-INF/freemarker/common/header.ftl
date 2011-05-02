<#if context_url!?starts_with('http://unitube.otago.ac.nz')>
<script type="text/javascript">
    var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
    document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
    try {
        // page tracker for http://unitube.otago.ac.nz
        var pageTracker = _gat._getTracker("UA-1164659-9");
        pageTracker._trackPageview();
        // page tracker for http://media.otago.ac.nz
        var mediaTracker = _gat._getTracker("UA-1164659-10");
    } catch(err) {
    }</script>
</#if>
<div id="container">
    <div id="header">
        <div class="uniLogo"><a href="http://www.otago.ac.nz"><img src="${baseUrl}/images/university_web_logo.gif"
                                                                   alt=""/></a></div>
        <div class="tubeLogo"><a href="${baseUrl}/home.do"><img src="${baseUrl}/images/logo_03.png" alt=""/></a></div>

        <div class="home"><a href="${baseUrl}/home.do" onmouseout="MM_swapImgRestore()"
                             onmouseover="MM_swapImage('Image10','','${baseUrl}/images/UniTube_navigationHover_03.png',1)"><img
                src="${baseUrl}/images/UniTube_navigation_03.png" name="Image10" width="64" height="36" border="0"
                alt=""
                id="Image10"/></a></div>

        <div class="unitubasLink"><a href="${baseUrl}/uniTubas.do" onmouseout="MM_swapImgRestore()"
                                     onmouseover="MM_swapImage('Image8','','${baseUrl}/images/UniTube_NavigationHover_12.png',1)"><img
                src="${baseUrl}/images/UniTube_navigation_12.png" name="Image8" width="83" height="36" border="0"
                id="Image8" alt=""/></a>
        </div>

        <div class="media"><a href="${baseUrl}/media.do" onmouseout="MM_swapImgRestore()"
                              onmouseover="MM_swapImage('Image12','','${baseUrl}/images/UniTube_50_07Hover.png',1)"><img
                src="${baseUrl}/images/UniTube_50_07.png" name="Image12" width="54" height="37" border="0" id="Image12"
                alt=""/></a></div>

        <div class="uploadLink"><a href="${baseUrl}/myTube/list.do" onmouseout="MM_swapImgRestore()"
                                   onmouseover="MM_swapImage('Image11','','${baseUrl}/images/UniTube_50_10Hover.png',1)"><img
                src="${baseUrl}/images/UniTube_50_10.png" name="Image11" width="59" height="36" border="0" id="Image11"
                alt=""/></a></div>

        <div class="albumsLink"><a href="${baseUrl}/albums.do" onmouseout="MM_swapImgRestore()"
                                   onmouseover="MM_swapImage('Image9','','${baseUrl}/images/UniTube_NavigationHover_15.png',1)"><img
                src="${baseUrl}/images/UniTube_navigation_15.png" name="Image9" width="62" height="35" border="0"
                id="Image9" alt=""/></a>
        </div>

        <div class="topNavLinks">
            <a href="${baseUrl}/copyright.do">Copyright</a>
            <a href="${baseUrl}/feedback.do">Feedback</a>
            <a href="${baseUrl}/credits.do">Credits</a>
            <a href="${baseUrl}/help.do">Help</a>
        </div>

        <#if authUser??>
        <div class="headerText">Welcome <strong>${authUser.userName}</strong>!</div>
        <div class="registration">
            <div>Your area:</div>
        </div>
        <div class="mytube">
            <div>
                <a href="${baseUrl}/myTube/list.do" onmouseout="MM_swapImgRestore()"
                   onmouseover="MM_swapImage('Image13','','${baseUrl}/images/my-media-btn2.png',1)"><img
                        src="${baseUrl}/images/my-media-btn.png" alt="My Media" name="Image13" width="80" height="30"
                        border="0" id="Image13"/></a>
                <a href="${baseUrl}/myTube/albumList.do" onmouseout="MM_swapImgRestore()"
                   onmouseover="MM_swapImage('Image14','','${baseUrl}/images/my-albums-btn2.png',1)"><img
                        src="${baseUrl}/images/my-albums-btn.png" alt="My Albums" name="Image14" width="80" height="30"
                        border="0" id="Image14"/>
                </a>
            </div>
            <div>
                <a href="${baseUrl}/myTube/profile.do">My Profile</a>
                <#if authUser.isInstructor>
                <a href="${baseUrl}/admin/userList.do">Admin</a>
                </#if>
                <a href="#"
                   onclick="logout('${baseUrl}/logout.do'<#if appInfo.usingCAS && authUser.wayf != "embeddedWayf">, '${appInfo.logoutUrl}'</#if>); return false;">Logout</a>
            </div>
        </div>
        <#else>
        <div class="headerText">Are you a <strong>UniTuba</strong>?</div>
        <div class="registration">Welcome! Please</div>
        <div class="loging">
            <a href="${baseUrl}/login.do" onmouseout="MM_swapImgRestore()"
               onmouseover="MM_swapImage('Image3','','${baseUrl}/images/login2_06.png',1)"><img
                    src="${baseUrl}/images/login1_06.png" alt="Log in" name="Image3" width="97" height="27" border="0"
                    id="Image3"/></a>
        </div>
        </#if>
    </div>
    <!--end of header-->
    <div class="topLine">
        <#include "search.ftl" />
        <h1>${title}</h1>
    </div>
    <div class="mainStage">
