<#import "../spring.ftl" as spring />
<#include "macros.ftl" />
<#setting url_escaping_charset="utf-8">
<#assign baseUrl><@spring.url ""/></#assign>
<!DOCTYPE html
        PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="Author" content="Educational Media, HEDC, University of Otago"/>
    <meta name="keywords" content="UniTube, Educational Media, HEDC, University of Otago"/>
    <link rel="shortcut icon" href="${baseUrl}/images/icon.png" type="image/x-icon"/>
<#if this_url?? && (this_url?contains("myTube/") || this_url?contains("avpSync")) >
    <link rel="stylesheet" type="text/css" media="screen"
          href="${baseUrl}/css/ui-lightness/jquery-ui-1.8.16.custom.css"/>
</#if>
<#if this_url?? && (this_url?contains('avp') || this_url?contains('view'))>
    <link rel="stylesheet" type="text/css" href="${baseUrl}/shadowbox/shadowbox.css"/>
</#if>
    <link rel="stylesheet" type="text/css" media="screen" href="${baseUrl}/css/global.css"/>
    <script type="text/javascript" src="${baseUrl}/javascript/mm.js"></script>
    <script type="text/javascript" src="${baseUrl}/javascript/jquery-1.5.2.min.js"></script>
<#if this_url?? && (this_url?contains("login") || this_url?contains("myTube/profile")|| this_url?contains("admin/userEdit")) >
<#-- md5.js is only needed when changing password -->
    <script type="text/javascript" src="${baseUrl}/javascript/md5.js"></script>
    <script type="text/javascript" src="${baseUrl}/javascript/jquery.validate.min.js"></script>
</#if>
<#if this_url?? && (this_url?contains("login") || this_url?contains("feedback")|| this_url?contains("view")) >
    <script type="text/javascript" src="${baseUrl}/javascript/jquery.form.js"></script>
</#if>
<#if this_url?? && this_url?contains("view") >
<#-- swfobject.js is only needed when displaying flash -->
    <script type="text/javascript" src="${baseUrl}/javascript/swfobject.js"></script>
</#if>
<#if this_url?? && (this_url?contains("myTube/") || this_url?contains("admin/"))>
    <script type="text/javascript" src="${baseUrl}/ckeditor/ckeditor.js"></script>
</#if>
<#if this_url?? && (this_url?contains("myTube/") || this_url?contains("avpSync")) >
    <script type="text/javascript" src="${baseUrl}/javascript/jquery-ui-1.8.16.custom.min.js"></script>
</#if>
<#if this_url?? && (this_url?contains('avp') || this_url?contains('view') || this_url?contains('embed'))>
    <script type="text/javascript" src="${baseUrl}/jwplayer/jwplayer.js"></script>
    <script type="text/javascript" src="${baseUrl}/shadowbox/shadowbox.js"></script>
</#if>
    <script type="text/javascript" src="${baseUrl}/javascript/media.js"></script>
<#if this_url?? && this_url?contains('avpSync')>
    <script type="text/javascript" src="${baseUrl}/javascript/jquery.timers-1.2.js"></script>
    <script type="text/javascript" src="${baseUrl}/javascript/jquery.easing.1.3.js"></script>
    <script type="text/javascript" src="${baseUrl}/javascript/jquery.galleryview-3.0.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${baseUrl}/galleryview/jquery.galleryview-3.0.css"/>
</#if>
<#if this_url?? && this_url?contains('stats')>
    <!--[if lt IE 9]><script language="javascript" type="text/javascript" src="${baseUrl}/jqplot/excanvas.js"></script><![endif]-->
    <script type="text/javascript" src="${baseUrl}/jqplot/jquery.jqplot.min.js"></script>
    <link rel="stylesheet" type="text/css" media="screen" href="${baseUrl}/jqplot/jquery.jqplot.min.css"/>
    <script type="text/javascript" src="${baseUrl}/jqplot/plugins/jqplot.barRenderer.min.js"></script>
    <script type="text/javascript" src="${baseUrl}/jqplot/plugins/jqplot.categoryAxisRenderer.min.js"></script>
    <script type="text/javascript" src="${baseUrl}/jqplot/plugins/jqplot.pieRenderer.min.js"></script>
    <script type="text/javascript" src="${baseUrl}/jqplot/plugins/jqplot.pointLabels.min.js"></script>
</#if>