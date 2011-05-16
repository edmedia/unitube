<div class="stage">
    <@spring.bind "album.id" />

    <h2><#if spring.status.value??>Edit<#else>Create a new</#if> Album</h2>

    <form action="${this_url}" name="albumForm" method="post">
        <#if pageNumber??>
        <input type="hidden" name="p" value="${pb.p?c}"/>
        </#if>
        <#if pageSize??>
        <input type="hidden" name="s" value="${pb.s?c}"/>
        </#if>
        <table summary="">

            <!-- id: ID -->
            <@spring.bind "album.id" />
            <tr class="hidden">
                <th>ID</th>
                <td>

                    <input type="hidden" class="text" name="${spring.status.expression}"
                           value="<#if spring.status.value??><#if spring.status.value?is_number>${spring.status.value?c}<#else>${spring.status.value?html}</#if></#if>"/>

                </td>
                <td>

                    <#list spring.status.errorMessages as error>
                    <b>${error}</b><br/>
                    </#list>
                </td>
            </tr>
            <!-- albumName: Album Name -->
            <@spring.bind "album.albumName" />
            <tr>
                <th>Album Name</th>
                <td>

                    <input type="text" class="text" name="${spring.status.expression}"
                           value="<#if spring.status.value??><#if spring.status.value?is_number>${spring.status.value?c}<#else>${spring.status.value?html}</#if></#if>"/>

                </td>
                <td>

                    <#list spring.status.errorMessages as error>
                    <b>${error}</b><br/>
                    </#list>
                </td>
            </tr>
            <!-- description: Album Description -->
            <@spring.bind "album.description" />
            <tr>
                <th>Album Description</th>
                <td>

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
                    <#list spring.status.errorMessages as error>
                    <b>${error}</b><br/>
                    </#list>
                </td>
            </tr>
            <!-- accessType: access type(0: public, 10: hidden, 20: private) -->
            <@spring.bind "album.accessType" />
            <tr>
                <th>Access Type</th>
                <td>
                    <input type="radio" name="${spring.status.expression}"
                           value="0"<#if spring.status.value ==0> checked="checked"</#if>/> Public
                    <input type="radio" name="${spring.status.expression}"
                           value="10"<#if spring.status.value ==10> checked="checked"</#if>/> Hidden
                    <#--
                    <input type="radio" name="${spring.status.expression}"
                           value="20"<#if spring.status.value ==20> checked="checked"</#if>/> Private
                           -->
                </td>
                <td>
                    <#list spring.status.errorMessages as error>
                    <b>${error}</b><br/>
                    </#list>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <input name="submit" type="submit" value="Save"/>
                    <input name="reset" type="reset" value="Reset"/>
                </td>
            </tr>

        </table>
    </form>

    <div id="bottomNav">
        <#--
<a href="<@spring.url "/admin.do"/>">Back to Admin List</a>
-->
        <a href="albumList.do?aa=list<#if pageNumber??>&amp;p=${pb.p?c}</#if><#if pageSize??>&amp;s=${pb.s?c}</#if>">Back
            to Album list</a>
    </div>

</div>