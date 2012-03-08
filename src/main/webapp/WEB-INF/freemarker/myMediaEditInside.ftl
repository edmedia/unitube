<form action="${this_url}" name="mediaForm" method="post" enctype="multipart/form-data">
    <#if pageNumber??>
    <input type="hidden" name="p" value="${pageNumber?c}"/>
    </#if>
    <#if pageSize??>
    <input type="hidden" name="s" value="${pageSize?c}"/>
    </#if>
    <@spring.bind "media.id" />
    <input type="hidden" name="${spring.status.expression}"
           value="<#if spring.status.value??><#if spring.status.value?is_number>${spring.status.value?c}<#else>${spring.status.value?html}</#if></#if>"/>
    <table summary="">

        <!-- albumMedias: all AlbumMedias -->
        <@spring.bind "media.albumMedias" />
        <#if spring.status.value?has_content>
        <tr>
            <th>Album(s)</th>
            <td>

                <#list spring.status.value as albumMedia>
                    <#assign albumName=["${albumMedia.album.albumName}"]/>
                <span id="albumMediaID_${albumMedia.id?c}"><a class="album_with_right_border"
                                                              href="${baseUrl}/album?a=${albumMedia.album.accessCode}">${albumMedia.album.albumName}</a><a
                        class="album"
                        title="<@spring.messageArgs  "remove.album.from.media" albumName/>"
                        href="#" rel="${albumMedia.id?c}">X</a> &nbsp;</span>
                </#list>

            </td>
            <td>

                <#list spring.status.errorMessages as error>
                <b>${error}</b><br/>
                </#list>
            </td>
        </tr>
        </#if>
        <!-- title: title -->
        <tr>
            <th>Title</th>
            <td>
                <@spring.bind "media.title" />
                <@displayInputBox/>
            </td>
            <td>
                <@displayError/>
            </td>
        </tr>

        <@spring.bind "media.accessCode" />
        <#if (spring.status.value)??>
        <#assign accessCode>${spring.status.value}</#assign>
        <#assign mediaFileBaseUrl= baseUrl + "/file.do?m=" + accessCode />
        </#if>

        <!-- thumbnail: thumbnail picture-->
        <@spring.bind "media.thumbnail" />
        <#if spring.status.value??>
        <#assign thumbUrl = mediaFileBaseUrl + "&name=" + spring.status.value />
        <tr>
            <th>Thumbnail</th>
            <td>

                <img src="${thumbUrl?html}"
                        <@spring.bind "media.width"/>
 width="<#if spring.status.value?? && (spring.status.value &gt; 300)>300</#if>"
                     alt=""/>

            </td>
            <td>

            </td>
        </tr>
        </#if>
        <!-- tags: Tags -->
        <tr>
            <th>Tags</th>
            <td>
                <@spring.bind "media.tags" />
                <@displayInputBox/>
            </td>
            <td>
                <@displayError/>
            </td>
        </tr>
        <!-- accessType: Access Type -->
        <tr>
            <th>Sharing</th>
            <td>
                <@spring.bind "media.accessType" />
                <input type="radio" name="${spring.status.expression}" value="0"<#if spring.status.value == 0>
                       checked="checked"</#if>/>
                Public: <@spring.message "media.access.public"/> <br/>
                <input type="radio" name="${spring.status.expression}" value="10"<#if spring.status.value == 10>
                       checked="checked"</#if>/>
                Hidden: <@spring.message "media.access.hidden"/> <br/>
                <input type="radio" name="${spring.status.expression}" value="20"<#if spring.status.value == 20>
                       checked="checked"</#if>/>
                Private: <@spring.message "media.access.private"/>
                <a href="#" class="share">Share</a>
            </td>
            <td>
                <@displayError/>
            </td>
        </tr>
        <!-- description: description -->
        <tr>
            <th>Description</th>
            <td>
                <@spring.bind "media.description" />
                <@displayTextArea/>
              <script type="text/javascript">
                <!--
                    CKEDITOR.replace( '${spring.status.expression}', {
                        customConfig : '${baseUrl}/javascript/ckeditor_config.js'
                    });
                -->
              </script>
            </td>
            <td>
                <@displayError/>
            </td>
        </tr>

        <!-- uploadFile: Upload File -->
        <tr>
            <th>Replace File</th>
            <td>
                <@spring.bind "media.uploadFile" />
                <input type="file" name="${spring.status.expression}"/>
            </td>
            <td>
                <@displayError/>
                <@spring.bind "media.uploadFileUserName" />
                <#if spring.status.value?has_content>
                <#assign uploadFileUrl = mediaFileBaseUrl + "&name=" + spring.status.value />
                <a href="${uploadFileUrl?html}"><@getTitle spring.status.value! /></a>
                </#if>
            </td>
        </tr>

        <!-- convertTo: also convert to MPEG format? -->
        <@spring.bind "media.mediaType" />
        <#-- only display this when it's a video file -->
        <#if spring.status.value?? && (spring.status.value == 20)>
        <@spring.bind "media.convertTo" />
        <#if !spring.status.value??>
        <tr>
            <th>Also convert to MPEG format?</th>
            <td>
                <input name="convertTo" value="mpg" type="checkbox"/>
            </td>
            <td>
                <@displayError/>
            </td>
        </tr>
        </#if>
        </#if>

        <#-- convert again is only possible if original file is still there -->
                <@spring.bind "media.uploadFileUserName" />
                <#if spring.status.value?has_content>
        <tr>
            <th>Convert again?</th>
            <td>

                <input name="convertAgain" type="checkbox" value="true"/> YES

            </td>
            <td>
                If the conversion goes wrong for some reason, you can do it again. ONLY USE IT WHEN NECESSARY.
            </td>
        </tr>
                </#if>

        <@spring.bind "media.mediaType" />
        <#-- only display this when it's an image file -->
        <#if spring.status.value?? && (spring.status.value == 5)>
        <tr>
            <th>ImageViewer Option</th>
            <#assign msg><@spring.message "save.media.before.edit.ivoption"/></#assign>
            <@spring.bind "media.id" />
            <td><a href="ivOption.do?mediaID=${spring.status.value?c}<#if ivOption??>&amp;id=${ivOption.id?c}</#if>"
                   onclick="if(confirm('${msg?js_string}')) return true; return false;">Edit ImageViewer Option</a></td>
            <td></td>
        </tr>
        </#if>

        <tr>
            <td colspan="3">
                <input name="submit" type="submit" value="Save"/>
                <a href="list.do?aa=list<#if pageNumber??>&amp;p=${pageNumber?c}</#if><#if pageSize??>&amp;s=${pageSize?c}</#if>">
                    Return to My Media</a>
            </td>
        </tr>

    </table>
</form>

<#-- set owner and accessRules before including shareDialog.ftl -->
<@spring.bind "media.user" />
<#assign owner = spring.status.value/>
<@spring.bind "media.accessRules" />
<#assign accessRules = spring.status.value/>
<#include "shareDialog.ftl"/>

<script type="text/javascript">
    <!--
    $(function() {

        $('a.album').click(function() {
            return removeAlbumFromMedia(this.rel);
        });

    <@spring.bind "media.accessType" />
    <#if spring.status.value != 20>
        $('a.share').hide();
    </#if>
        // when change access type, hide or show share link
        $('input[name=accessType]').click(function() {
            if ($('input[name=accessType]:checked').val() == 20) {
                $('a.share').show();
                alert('<@spring.message "media.change.accessType.to.private.warning"/>');
            } else
                $('a.share').hide();
        });

        shareDialog();

        // when clicking "share" link for private media file
        $('a.share').click(function() {
            var id = $('input:hidden[name=id]').val();
            // set mediaID
            $('#ar_mediaID').val(id);
            // open share dialog
            $("#share-dialog").dialog('open');
            $('#userInput').focus();
            return false;
        });

    });
    //-->
</script>