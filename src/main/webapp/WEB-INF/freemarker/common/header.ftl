<div id="container">
    <div id="header">
        <div class="uniLogo"><a href="http://www.otago.ac.nz"><img src="${baseUrl}/images/university_web_logo.gif"
                                                                   alt=""/></a></div>
        <div class="tubeLogo"><a href="${baseUrl}/home.do"><img src="${baseUrl}/images/unitube_logo.png" alt=""/></a>
        </div>

        <div class="home"><a href="${baseUrl}/home.do" onmouseout="MM_swapImgRestore()"
                             onmouseover="MM_swapImage('iHome','','${baseUrl}/images/navHome2.png',1)"><img
                src="${baseUrl}/images/navHome.png" name="iHome" width="64" height="36" border="0" alt="Home"
                /></a></div>

        <div class="unitubasLink"><a href="${baseUrl}/uniTubas.do" onmouseout="MM_swapImgRestore()"
                                     onmouseover="MM_swapImage('iUnitubas','','${baseUrl}/images/navUnitubas2.png',1)"><img
                src="${baseUrl}/images/navUnitubas.png" name="iUnitubas" width="83" height="36" border="0"
                alt="UniTubas"
                /></a></div>

        <div class="media"><a href="${baseUrl}/media.do" onmouseout="MM_swapImgRestore()"
                              onmouseover="MM_swapImage('iMedia','','${baseUrl}/images/navMedia2.png',1)"><img
                src="${baseUrl}/images/navMedia.png" name="iMedia" width="54" height="37" border="0" alt="Media"
                /></a></div>

        <div class="uploadLink"><a href="${baseUrl}/myTube/list.do" onmouseout="MM_swapImgRestore()"
                                   onmouseover="MM_swapImage('iUpload','','${baseUrl}/images/navUpload2.png',1)"><img
                src="${baseUrl}/images/navUpload.png" name="iUpload" width="59" height="36" border="0" alt="Upload"
                /></a></div>

        <div class="albumsLink"><a href="${baseUrl}/albums.do" onmouseout="MM_swapImgRestore()"
                                   onmouseover="MM_swapImage('iAlbums','','${baseUrl}/images/navAlbums2.png',1)"><img
                src="${baseUrl}/images/navAlbums.png" name="iAlbums" width="62" height="35" border="0" alt="Albums"
                /></a></div>

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
                   onmouseover="MM_swapImage('myMedia','','${baseUrl}/images/myMedia2.png',1)"><img
                        src="${baseUrl}/images/myMedia.png" alt="My Media" name="myMedia" width="80" height="30"
                        border="0"/></a>
                <a href="${baseUrl}/myTube/albumList.do" onmouseout="MM_swapImgRestore()"
                   onmouseover="MM_swapImage('myAlbums','','${baseUrl}/images/myAlbums2.png',1)"><img
                        src="${baseUrl}/images/myAlbums.png" alt="My Albums" name="myAlbums" width="80" height="30"
                        border="0"/>
                </a>
            </div>
            <div>
                <a href="${baseUrl}/myTube/avpList.do">My AVPs</a>
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
                   onmouseover="MM_swapImage('login','','${baseUrl}/images/login2.png',1)"><img
                        src="${baseUrl}/images/login.png" alt="Log in" name="login" width="97" height="27"
                        border="0"/></a>
            </div>
    </#if>
    </div>
    <!--end of header-->
    <div class="topLine">
    <#include "search.ftl" />
        <h1>${title}</h1>
    </div>
    <div class="mainStage">
