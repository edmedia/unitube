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

                <input type="password" class="text required" name="oldPassWord" value=""/>

            </td>
            <td>
            </td>
        </tr>

        <!-- passWord: Pass Word -->
        <@spring.bind "user.passWord" />
        <tr>
            <th>New Password</th>
            <td>

                <input type="password" class="text required" name="thePassWord" value=""/>

            </td>
            <td>
                <@displayError/>
            </td>
        </tr>
        <tr>
            <th>Retype Password</th>
            <td>

                <input type="password" class="text required" name="retypePassWord" value=""/>

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
                       <#if spring.status.value?? && spring.status.value==0>checked="checked"</#if>/>
                <span title="<@spring.message "media.access.public"/>">Public</span>
                <input type="radio" name="${spring.status.expression}" value="10"
                       title="<@spring.message "media.access.hidden"/>"
                       <#if spring.status.value?? && spring.status.value==10>checked="checked"</#if>/>
                <span title="<@spring.message "media.access.hidden"/>">Hidden</span>
                <input type="radio" name="${spring.status.expression}" value="20"
                title="<@spring.message "media.access.private"/>"

                       <#if spring.status.value?? && spring.status.value==20>checked="checked"</#if>/>
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
                       <#if spring.status.value?? && spring.status.value==0>checked="checked"</#if>/>
                <span title="<@spring.message "media.access.public"/>">Public</span>
                <input type="radio" name="${spring.status.expression}" value="10"
                       title="<@spring.message "media.access.hidden"/>"
                       <#if spring.status.value?? && spring.status.value==10>checked="checked"</#if>/>
                <span title="<@spring.message "media.access.hidden"/>">Hidden</span>
                <input type="radio" name="${spring.status.expression}" value="20"
                title="<@spring.message "media.access.private"/>"

                       <#if spring.status.value?? && spring.status.value==20>checked="checked"</#if>/>
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
    function checkForm(theForm) {
        // firstname, lastname and email address can't be empty
        if (theForm.firstName.value.length == 0) {
            alert("Please input First Name");
            theForm.firstName.focus();
            return false;
        }
        if (theForm.lastName.value.length == 0) {
            alert("Please input Last Name");
            theForm.lastName.focus();
            return false;
        }
        if (theForm.email.value.length == 0) {
            alert("Please input Email Address");
            theForm.email.focus();
            return false;
        }
        // only change password when all three password fields are not empty
        if (theForm.oldPassWord.value.length > 0) {
            var md5_password = hex_md5(theForm.oldPassWord.value);
            if (md5_password != theOldPassWord) {
                alert("Your old password is wrong.");
                theForm.oldPassWord.focus();
                return false;
            }
            if (theForm.thePassWord.value.length == 0) {
                alert("Please input new password");
                theForm.thePassWord.focus();
                return false;
            }
            if (theForm.thePassWord.value != theForm.retypePassWord.value) {
                alert("Your passwords don't match");
                theForm.retypePassWord.focus();
                return false;
            }
            theForm.passWord.value = hex_md5(theForm.retypePassWord.value);
        }
        return true;
    }

    $(function() {
        $('form[name=userForm]').validate();
        $('form[name=userForm]').submit(function() {
        <#-- TODO: rewrite checkForm -->
            //if (checkForm(document.userForm))
            //   this.submit();
        });
    });
    //-->
</script>
