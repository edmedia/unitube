<div class="infoBox">
    <div class="info">

        <p>Go to <a href="${baseUrl}/myTube/list.do">My Media</a> to upload the files you require. You will then be
            able to select them from the dropdown lists on the left. </p>

        <p><a href="${baseUrl}/myTube/avplist.do">Find out more about AVPs</a></p>

    </div>
</div>
<#include "myOptions.ftl"/>

<div class="stage">
<@spring.bind "avp.id" />

    <h2><#if spring.status.value??>Edit<#else>Create a new</#if> AVP</h2>

    <form action="${this_url}" name="albumForm" method="post">
    <#if pageNumber??>
        <input type="hidden" name="p" value="${pb.p?c}"/>
    </#if>
    <#if pageSize??>
        <input type="hidden" name="s" value="${pb.s?c}"/>
    </#if>
        <table summary="">

            <!-- id: ID -->
            <tr class="hidden">
                <th>ID</th>
                <td>
                <@spring.bind "avp.id" />
                <@displayInputBox/>
                </td>
                <td>
                <@displayError/>
                </td>
            </tr>
            <!-- title: AVP title -->
            <tr>
                <th>Title</th>
                <td>
                <@spring.bind "avp.title" />
                <@displayInputBox/>
                </td>
                <td>
                <@displayError/>
                </td>
            </tr>
            <!-- description: AVP Description -->
        <#--
    <tr>
        <th>Description</th>
        <td>
        <@spring.bind "avp.description" />
            <@displayTextArea/>
          <script type="text/javascript">
                CKEDITOR.replace( '${spring.status.expression}', {
                    customConfig : '${baseUrl}/javascript/ckeditor_config.js'
                });
          </script>
        </td>
        <td>
        <@displayError/>
        </td>
    </tr>
        -->
            <!-- av1: Audio/Video one -->
            <tr>
                <th>Audio/Video one</th>
                <td>
                <@spring.bind "avp.av1ID" />
                    <select name="av1ID">
                        <option value="0">&nbsp;</option>
                    <#list avList as option>
                        <option value="${option.id?c}">${option.meaningfulName}</option>
                    </#list>
                    </select>
                </td>
                <td>
                <@displayError/>
                </td>
            </tr>
            <!-- av2: Audio/Video two -->
            <tr>
                <th>Audio/Video two</th>
                <td>
                <@spring.bind "avp.av2ID" />
                    <select name="av2ID">
                        <option value="0">&nbsp;</option>
                    <#list avList as option>
                        <option value="${option.id?c}">${option.meaningfulName}</option>
                    </#list>
                    </select>
                </td>
                <td>
                <@displayError/>
                </td>
            </tr>
            <!-- presentation: Presentation -->
            <tr>
                <th>Presentation</th>
                <td>
                <@spring.bind "avp.presentationID" />
                    <select name="presentationID">
                        <option value="0">&nbsp;</option>
                    <#list pList as option>
                        <option value="${option.id?c}">${option.meaningfulName}</option>
                    </#list>
                    </select>
                </td>
                <td>
                <@displayError/>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <input name="submit" type="submit" value="Save"/>
                </td>
            </tr>

        </table>
    </form>

    <div id="bottomNav">
    <#--
    <a href="<@spring.url "/admin.do"/>">Back to Admin List</a>
    -->
        <a href="avpList.do?aa=list<#if pageNumber??>&amp;p=${pb.p?c}</#if><#if pageSize??>&amp;s=${pb.s?c}</#if>">Back
            to AVP list</a>
    </div>

</div>
<script type="text/javascript">
    <!--
    $(function() {

    <@spring.bind "avp.av1" />
    <#if (spring.status.value.id)??>
        $('[name=av1ID]').val("${spring.status.value.id?c}");
    </#if>


    <@spring.bind "avp.av2" />
    <#if (spring.status.value.id)??>
        $('[name=av2ID]').val("${spring.status.value.id?c}");
    </#if>

    <@spring.bind "avp.presentation" />
    <#if (spring.status.value.id)??>
        $('[name=presentationID]').val("${spring.status.value.id?c}");
    </#if>
    });
    //-->
</script>
