<#if pager.elements?has_content>
<div class="mediaDisplay2">
    <#list pager.elements  as avp>
    <@displayAVPInList avp />
    </#list>
</div>
    <#else>
    <div class="stage">
        <div class="info">
            <@spring.message "no.avp"/>
        </div>
    </div>
</#if>
