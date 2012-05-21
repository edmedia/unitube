<div id="container">
    <div id="header">

        <img src="${baseUrl}/images/UniTube_01.png" width="960" height="183" alt="unitube" usemap="#headerImage"/>

        <map id="headerImage" name="headerImage">
            <area shape="rect" alt="UniTube home" title="" coords="320,56,462,105" href="${baseUrl}/home.do"/>
            <area shape="rect" alt="UniTube home" title="" coords="499,70,551,101" href="${baseUrl}/home.do"/>
            <area shape="rect" alt="University of Otago" title="" coords="45,40,207,121"
                  href="http://www.otago.ac.nz" target="_blank"/>
            <area shape="rect" alt="Unitubas" title="" coords="523,118,600,145" href="${baseUrl}/uniTubas.do"/>
            <area shape="rect" alt="Media" title="" coords="610,70,666,101" href="${baseUrl}/media.do"/>
            <area shape="rect" alt="Albums" title="" coords="642,121,707,145" href="${baseUrl}/albums.do"/>
            <area shape="rect" alt="My Media" title="" coords="711,71,820,103" href="${baseUrl}/myTube/list.do"/>
            <area shape="rect" alt="Help" title="" coords="741,121,794,146" href="${baseUrl}/help.do"/>
        </map>

        <div id="uniLogo">
            <a href="http://www.otago.ac.nz" target="_blank">
                <img class="weblogo"
                     src="http://www.otago.ac.nz/prodcons/fragments/otg_assets/gfx/grid16/logo-horizontal.gif"
                     alt="University of Otago logo" width="160" height="80"/>
            </a>
        </div>

        <div class="topNavLinks">
            <a href="${baseUrl}/copyright.do">Copyright</a> |
            <a href="${baseUrl}/feedback.do">Feedback</a> |
            <a href="${baseUrl}/credits.do">Credits</a>
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
