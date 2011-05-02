<div class="stage">
    <div>
        <table summary="">
            <tr>
                <td>
                    <@viewLinkWithThumbnail obj />
                </td>
                <td>&nbsp; </td>
                <td>
                    <p><a href="view?m=${obj.accessCode}">${obj.title?html}</a></p>
                    Added: ${obj.uploadTimePast}<br/>
                    From: <a
                        href="media.do?u=${obj.user.accessCode}">${obj.user.firstName} ${obj.user.lastName}</a><br/>
                    Views: ${obj.accessTimes}
                </td>
            </tr>
        </table>
    </div>
    <#include "viewComment.ftl"/>
</div>
