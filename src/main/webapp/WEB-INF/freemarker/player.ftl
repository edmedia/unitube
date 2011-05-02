<#-- only deal with audio or video files with FlowPlayer -->
<#if obj?? && ( obj.mediaType == 10 || obj.mediaType == 20 )>
<#include "viewHelper.ftl"/>
{
<#-- if it's an audio media file -->
<#if obj.mediaType == 10>
    'playlist': [ {
        'url': '${mediaFileLink}',
        'autoPlay': <#if autoPlay?? && autoPlay>true<#else>false</#if>,
        'autoBuffering': <#if autoBuffering?? && autoBuffering>true<#else>false</#if>
    } ],
    'plugins': {
        'controls': {
            'bottom': 0,
            'height': ${controllerHeight},
<#-- for flowplayer 3.2, we need add 'autoHide': false, here -->
            'fullscreen': false
        },
        'audio': { 'url': '${audioPlugin}' },
        'c1': {
            'url': '${contentPlugin}',
<#-- link to media file for public file, link to home page for private file -->
<#if obj.accessType == 0>
            'html': '<p align=\"center\"><b><a href=\"${viewURL}\">Hosted by UniTube</a></b></p>',
<#else>
            'html': '<p align=\"center\"><b><a href=\"${context_url}\">Hosted by UniTube</a></b></p>',
</#if>
            'style': {
                'border': { 'border': 0 },
                'p': { 'color': '#ffffff' }
            },
            'borderRadius': 0,
            'width': 480,
            'bottom': ${controllerHeight?c},
            'height': 28,
            'textDecoration': 'outline',
            'dummy': 'none'
        }
    }
<#-- if it's an video media file -->
<#elseif obj.mediaType == 20>
    'playlist': [ <#if obj.thumbnail?has_content>{'url':'${thumnailFileLink}'}, </#if>{
            'url': '${mediaFileLink}',
            'autoPlay': <#if autoPlay?? && autoPlay>true<#else>false</#if>,
            'autoBuffering': <#if autoBuffering?? && autoBuffering>true<#else>false</#if>,
            'scaling': 'orig',
            'accelerated': true
    } ],
    'plugins': {
        'controls': {
            'bottom': 0,
            'height': ${controllerHeight?c}
        },
        'c1': {
            'url': '${contentPlugin}',
<#-- link to media file for public file, link to home page for private file -->
<#if obj.accessType == 0>
            'html': '<p align=\"center\"><b><a href=\"${viewURL}\">Hosted by UniTube</a></b></p>',
<#else>
            'html': '<p align=\"center\"><b><a href=\"${context_url}\">Hosted by UniTube</a></b></p>',
</#if>
            'style': {
                'border': { 'border': 0 },
                'p': { 'color': '#ffffff' }
            },
            'borderRadius': 28,
            'bottom': ${(controllerHeight+8)?c},
            'width': 180,
            'height': 28,
            'textDecoration': 'outline',
            'closeButton': true,
            'dummy': 'none'
        }
    }
</#if>
}
</#if>
