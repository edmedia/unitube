<#include "myOptions.ftl"/>

<@spring.bind "user.userName" />
<h2>Profile (${spring.status.value})</h2>

<@spring.bind "user.passWord" />
<#if spring.status.value??>
    <#assign oldPassWord= spring.status.value />
</#if>

<form action="${this_url}" name="userForm" method="post">
    <input type="hidden" name="passWord" value="${oldPassWord}"/>
    <table summary="">

        <!-- id: ID -->
        <tr class="hidden">
            <th>ID</th>
            <td>
            <@spring.bind "user.id" />
                <@displayInputBox/>
            </td>
            <td>
            </td>
        </tr>
        <!-- userName: User Name -->
        <tr class="hidden">
            <th>User Name</th>
            <td>
            <@spring.bind "user.userName" />
                <@displayInputBox/>
            </td>
            <td>
            </td>
        </tr>

    <@spring.bind "user.wayf" />
    <#if spring.status.value??>
        <#assign wayf= spring.status.value />
    </#if>
    <#if wayf == "embeddedWayf">
        <tr>
            <th>Old Password</th>
            <td>

                <input type="password" class="text" name="oldPassWord" value=""/>

            </td>
            <td>
            </td>
        </tr>

        <!-- passWord: Pass Word -->
    <@spring.bind "user.passWord" />
        <tr>
            <th>New Password</th>
            <td>

                <input type="password" class="text" name="thePassWord" value=""/>

            </td>
            <td>
            <@displayError/>
            </td>
        </tr>
        <tr>
            <th>Retype Password</th>
            <td>

                <input type="password" class="text" name="retypePassWord" value=""/>

            </td>
            <td>

            </td>
        </tr>
    </#if>
        <!-- firstName: First Name -->
        <tr>
            <th>First Name</th>
            <td>
            <@spring.bind "user.firstName" />
                <input type="text" class="text required" name="${spring.status.expression}"
                       id="${spring.status.expression}"
                       value="<#if spring.status.value?has_content><#if spring.status.value?is_number>${spring.status.value?c}<#else>${spring.status.value?html}</#if></#if>"/>
            </td>
            <td>
            <@displayError/>
            </td>
        </tr>
        <!-- lastName: Last Name -->
        <tr>
            <th>Last Name</th>
            <td>
            <@spring.bind "user.lastName" />
                <input type="text" class="text required" name="${spring.status.expression}"
                       id="${spring.status.expression}"
                       value="<#if spring.status.value?has_content><#if spring.status.value?is_number>${spring.status.value?c}<#else>${spring.status.value?html}</#if></#if>"/>
            </td>
            <td>
            <@displayError/>
            </td>
        </tr>
        <!-- email: Email Address -->
        <tr>
            <th>Email Address</th>
            <td>
            <@spring.bind "user.email" />
                <input type="text" class="text reuqired email" name="${spring.status.expression}"
                       id="${spring.status.expression}"
                       value="<#if spring.status.value?has_content><#if spring.status.value?is_number>${spring.status.value?c}<#else>${spring.status.value?html}</#if></#if>"/>
            </td>
            <td>
                Make sure your email address is correct and current.
            <@displayError/>
            </td>
        </tr>
        <!-- mobile: Mobile Number -->
        <tr>
            <th>Mobile Number</th>
            <td>
            <@spring.bind "user.mobile" />
                <@displayInputBox/>
            </td>
            <td>
            <@displayError/>
            </td>
        </tr>
        <!-- uploadAccessType: Default Upload Access Type -->
        <tr>
            <th>Default Upload Access Type</th>
            <td>
            <@spring.bind "user.uploadAccessType" />
                <input type="radio" name="${spring.status.expression}" value="0"
                       title="<@spring.message "media.access.public"/>"
                       <#if spring.status.value?? && spring.status.value?is_number && spring.status.value==0>checked="checked"</#if>/>
                <span title="<@spring.message "media.access.public"/>">Public</span>
                <input type="radio" name="${spring.status.expression}" value="10"
                       title="<@spring.message "media.access.hidden"/>"
                       <#if spring.status.value?? && spring.status.value?is_number && spring.status.value==10>checked="checked"</#if>/>
                <span title="<@spring.message "media.access.hidden"/>">Hidden</span>
                <input type="radio" name="${spring.status.expression}" value="20"
                       title="<@spring.message "media.access.private"/>"
                       <#if spring.status.value?? && spring.status.value?is_number && spring.status.value==20>checked="checked"</#if>/>
                <span title="<@spring.message "media.access.private"/>">Private</span>
            </td>
            <td>
            <@displayError/>
            </td>
        </tr>
        <!-- emailUploadAccessType: Default Email Upload Access Type -->
        <tr>
            <th>Default Email Upload Access Type</th>
            <td>
            <@spring.bind "user.emailUploadAccessType" />
                <input type="radio" name="${spring.status.expression}" value="0"
                       title="<@spring.message "media.access.public"/>"
                       <#if spring.status.value?? && spring.status.value?is_number && spring.status.value==0>checked="checked"</#if>/>
                <span title="<@spring.message "media.access.public"/>">Public</span>
                <input type="radio" name="${spring.status.expression}" value="10"
                       title="<@spring.message "media.access.hidden"/>"
                       <#if spring.status.value?? && spring.status.value?is_number && spring.status.value==10>checked="checked"</#if>/>
                <span title="<@spring.message "media.access.hidden"/>">Hidden</span>
                <input type="radio" name="${spring.status.expression}" value="20"
                       title="<@spring.message "media.access.private"/>"
                       <#if spring.status.value?? && spring.status.value?is_number && spring.status.value==20>checked="checked"</#if>/>
                <span title="<@spring.message "media.access.private"/>">Private</span>
            </td>
            <td>
            <@displayError/>
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

<script type="text/javascript">
    <!--
    var theOldPassWord;
    <#if oldPassWord??>
    theOldPassWord = "${oldPassWord}";
    </#if>

    $(function() {
        $('form[name=userForm]').submit(function() {
            var theForm = $(this);
            if (theForm.valid()) {
                if ($('input[name=oldPassWord]').val().length > 0) {
                    var md5_password = hex_md5($('input[name=oldPassWord]').val());
                    if (md5_password != theOldPassWord) {
                        alert("Your old password is wrong.");
                        $('input[name=oldPassWord]').focus();
                        return false;
                    }
                    if ($('input[name=thePassWord]').val().length == 0) {
                        alert("Please input new password.");
                        $('input[name=thePassWord]').focus();
                        return false;
                    }
                    if ($('input[name=thePassWord]').val() != $('input[name=retypePassWord]').val()) {
                        alert("Your passwords don't match. Please try again.");
                        $('input[name=retypePassWord]').focus();
                        return false;
                    }
                    $('input[name=passWord]').val(hex_md5($('input[name=retypePassWord]').val()));
                    return true;
                }
                return true;
            }
            return false;
        });
    });
    //-->
</script>
