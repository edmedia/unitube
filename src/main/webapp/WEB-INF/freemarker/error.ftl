<#assign title>Error</#assign>
<#include "common/head.ftl" />

<#include "common/header.ftl" />

<div class="stage">

    <div><strong>${title}<#if url??> when accessing ${url?html}</#if></strong></div>

    <#if handler??>
    <div>${handler}</div>
    </#if>
    <#if message??>
    <div>${message}</div>
    </#if>

</div>

<#include "common/footer.ftl" />
