<div id="container">
    <div id="header">
        <!-- added skip navigation link - jumps to mainstage -->
        <a id="skipNavigation" href="#mainStage">Skip Navigation</a>

        <div id="headerWrapper">
            <img src="${baseUrl}/images/UniTube_nomap.png" width="960" height="183" alt="unitube"
                 usemap="#headerImage"/>

            <a href="${baseUrl}/home.do"
               style="top:75px;left:330px;text-transform:none;font-size:35px;font-family:Arial" accesskey="0">
                <b>Uni</b>Tube
            </a>

            <!-- Top row -->
            <a href="${baseUrl}/home.do" style="top:77px;left:507px;" accesskey="0">Home</a>
            <a href="${baseUrl}/media.do" style="top:77px;left:620px;" accesskey="1">Media</a>
            <a href="${baseUrl}/myTube/list.do" style="top:77px;left:720px;" accesskey="2">My Media</a>

            <!-- Bottom row -->
            <a href="${baseUrl}/uniTubas.do" style="top:124px;left:533px;" accesskey="3">UniTubas</a>
            <a href="${baseUrl}/albums.do" style="top:124px;left:652px;" accesskey="4">Albums</a>
            <a href="${baseUrl}/help.do" style="top:124px;left:753px;" accesskey="5">Help</a>

        </div>


        <div id="uniLogo">
            <a href="http://www.otago.ac.nz" target="_blank">
                <img class="weblogo"
                     src="http://www.otago.ac.nz/prodcons/fragments/otg_assets/gfx/grid16/logo-horizontal.gif"
                     alt="University of Otago logo" width="160" height="80"/>
            </a>
        </div>

        <div class="topNavLinks">
            <a href="${baseUrl}/copyright.do" accesskey="6">Copyright &amp; IP</a><br/>
            <a href="${baseUrl}/feedback.do" accesskey="7">
                <img src="${baseUrl}/images/twitter.ico" style="float:left;margin-right:5px" alt="Twitter Icon"/>
                Tweet about UniTube</a><br/>
            <a href="${baseUrl}/about.do" accesskey="8">About</a>
        </div>

    <#if authUser??>
        <div class="registration">
            Welcome <strong>${authUser.userName}</strong>!
            <a href="#"
               onclick="logout('${baseUrl}/logout.do'<#if appInfo.usingCAS && authUser.wayf != "embeddedWayf" && appInfo.logoutUrl??>, '${appInfo.logoutUrl}'</#if>); return false;">Logout</a>
        </div>
        <#else>
            <div class="registration"> Welcome! Please <a href="${baseUrl}/myTube/list.do"> login</a></div>
    </#if>
    </div>
    <!--end of header-->
    <div class="topLine">
    <#include "search.ftl" />
        <h1>${title}</h1>
    </div>

    <div class="mainStage">
        <a name="mainStage" id="mainStage"></a>
