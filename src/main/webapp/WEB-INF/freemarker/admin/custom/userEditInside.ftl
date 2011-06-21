<@spring.bind "user.id" />

<h2><#if spring.status.value??>Edit<#else>Create a new</#if> User</h2>

<form action="${this_url}" name="userForm" method="post">
<#if pageNumber??>
    <input type="hidden" name="p" value="${pageNumber?c}"/>
</#if>
<#if pageSize??>
    <input type="hidden" name="s" value="${pageSize?c}"/>
</#if>
    <input type="hidden" name="passWord" value=""/>
    <table summary="">

        <!-- id: ID -->
        <tr class="hidden">
            <th>ID</th>
            <td>
            <@spring.bind "user.id" />
<@displayInputBox/>

            </td>
            <td>

            <@displayError/>
            </td>
        </tr>
        <!-- userName: User Name -->
        <tr>
            <th>User Name</th>
            <td>
            <@spring.bind "user.userName" />
                <input type="text" class="text required" name="${spring.status.expression}"
                       id="${spring.status.expression}"
                       value="<#if spring.status.value?has_content><#if spring.status.value?is_number>${spring.status.value?c}<#else>${spring.status.value?html}</#if></#if>"/>

            </td>
            <td>

            <@displayError/>
            </td>
        </tr>
        <!-- passWord: Pass Word -->
        <tr>
            <th>New Password</th>
            <td>
            <@spring.bind "user.passWord" />
                <input type="password" class="text" name="thePassWord"
                       value="<#if spring.status.value??><#if spring.status.value?is_number>${spring.status.value?c}<#else>${spring.status.value?html}</#if></#if>"/>

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
                <input type="text" class="text required email" name="${spring.status.expression}"
                       id="${spring.status.expression}"
                       value="<#if spring.status.value?has_content><#if spring.status.value?is_number>${spring.status.value?c}<#else>${spring.status.value?html}</#if></#if>"/>

            </td>
            <td>

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
        <!-- isGuest: Is this user a guest? -->
        <tr>
            <th>Is this user a guest?</th>
            <td>
            <@spring.bind "user.isGuest" />
                <input type="radio" name="${spring.status.expression}"
                       value="true"<#if spring.status.value??><#if spring.status.value> checked="checked"</#if></#if> />
                True
                <input type="radio" name="${spring.status.expression}"
                       value="false"<#if spring.status.value??><#if !spring.status.value>
                       checked="checked"</#if></#if> /> False
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

<div id="bottomNav">
<#--
<a href="${baseUrl}/admin.do">Back to Admin List</a>
-->
    <a href="userList.do?aa=list<#if pageNumber??>&amp;p=${pageNumber?c}</#if><#if pageSize??>&amp;s=${pageSize?c}</#if>">Back
        to User list</a>
</div>

<script type="text/javascript">
    <!--
    var oldPassWord;
    <@spring.bind "user.passWord" />
    <#if spring.status.value??>
    oldPassWord = "${spring.status.value}";
    </#if>


    function checkForm(theForm) {
        // when id is null, mean we are creating a new user
        if (theForm.id.value == "") {
            if (theForm.userName.value == "") {
                alert("Please input username");
                theForm.userName.focus();
                return false;
            }
            if (theForm.thePassWord.value == "") {
                alert("Please input new password");
                theForm.thePassWord.focus();
                return false;
            }
            if (theForm.thePassWord.value != theForm.retypePassWord.value) {
                alert("Your passwords don't match");
                theForm.retypePassWord.focus();
                return false;
            }
            if (theForm.firstName.value == "") {
                alert("Please input firstname");
                theForm.firstName.focus();
                return false;
            }
            if (theForm.lastName.value == "") {
                alert("Please input lastname");
                theForm.lastName.focus();
                return false;
            }
            if (theForm.email.value == "") {
                alert("Please input email");
                theForm.email.focus();
                return false;
            }
            var md5_password = hex_md5(theForm.thePassWord.value);
            theForm.passWord.value = md5_password;
            return true;
        } else {
            if (theForm.thePassWord.value != oldPassWord) {
                if (theForm.thePassWord.value != theForm.retypePassWord.value) {
                    alert("Your passwords don't match");
                    theForm.retypePassWord.focus();
                    return false;
                }
                var md5_password = hex_md5(theForm.thePassWord.value);
                theForm.passWord.value = md5_password;
            } else {
                theForm.passWord.value = theForm.thePassWord.value;
            }
        }
    }

    $(function() {
        $('form[name=userForm]').submit(function() {
            var theForm = $(this);
            if (theForm.valid()) {
                if ($('input[name=id]').val().length == 0) {
                    // create a new user
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
                } else {
                    // modify old user
                    if ($('input[name=thePassWord]').val() != oldPassWord) {
                        // change password
                        if ($('input[name=thePassWord]').val() != $('input[name=retypePassWord]').val()) {
                            alert("Your passwords don't match. Please try again.");
                            $('input[name=retypePassWord]').focus();
                            return false;
                        }
                        $('input[name=passWord]').val(hex_md5($('input[name=retypePassWord]').val()));
                        return true;
                    } else {
                        // don't change password
                        $('input[name=passWord]').val($('input[name=thePassWord]').val());
                        return true;
                    }
                }
            } else
                return false;
        })
    });
    //-->
</script>
