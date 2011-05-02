<#include "common/head_1.ftl" />

<#if obj??>
<link rel="alternate" type="application/rss+xml" title="RSS - ${(obj.albumName)!?html}"
      href="${baseUrl}/feed.do?topic=album&amp;a=${obj.accessCode}"/>
<link rel="alternate" type="application/atom+xml" title="ATOM - ${(obj.albumName)!?html}"
      href="${baseUrl}/feed.do?topic=album&amp;a=${obj.accessCode}&amp;atom"/>
</#if>

<#include "common/head_2.ftl" />

<#include "common/header.ftl" />

<#include "albumInside.ftl" />

<#include "common/footer.ftl" />
