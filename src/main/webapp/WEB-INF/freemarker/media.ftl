<#assign title>
All Media
<#if user?has_content>
from ${user.firstName} ${user.lastName}
<#else>
<#if mediaType?has_content>
(<#if mediaType == 1>Other</#if>
<#if mediaType == 5>Image</#if>
<#if mediaType == 10>Audio</#if>
<#if mediaType == 20>Video</#if>)
</#if>
</#if>
</#assign>
<#include "common/head_1.ftl" />
<link rel="alternate" type="application/rss+xml"
      title="RSS - <#if user??>Media from ${user.firstName} ${user.lastName}<#else>All Media</#if>"
      href="${baseUrl}/feed.do?topic=media<#if user??>&amp;u=${user.accessCode}</#if>"/>
<link rel="alternate" type="application/atom+xml"
      title="ATOM - <#if user??>Media from ${user.firstName} ${user.lastName}<#else>All Media</#if>"
      href="${baseUrl}/feed.do?topic=media<#if user??>&amp;u=${user.accessCode}</#if>&amp;atom"/>
<#include "common/head_2.ftl" />
<#assign title>
<a href="${baseUrl}/feed.do?topic=media<#if user??>&amp;u=${user.accessCode}</#if>" title="RSS Feed"><img
        src="${baseUrl}/images/feed-icon.png" alt=""/></a>
<#if mediaType?has_content>
    <a href="?">All Media</a>
<#else>
All Media
</#if>
<#if user?has_content>from ${user.firstName} ${user.lastName}
<#else>
(
<#if mediaType?has_content && mediaType == 20>
Video
<#else>
<a href="?t=20">Video</a>
</#if>
|
<#if mediaType?has_content && mediaType == 10>
Audio
<#else>
<a href="?t=10">Audio</a>
</#if>
|
<#if mediaType?has_content && mediaType == 5>
Image
<#else>
<a href="?t=5">Image</a>
</#if>
|
<#if mediaType?has_content && mediaType == 1>
Other
<#else>
<a href="?t=1">Other</a>
</#if>
)
</#if>
</#assign>

<#include "common/header.ftl" />

<#include "mediaInside.ftl" />

<#include "common/footer.ftl" />
