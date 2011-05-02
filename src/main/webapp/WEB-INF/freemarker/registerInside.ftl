<h2>Registration</h2>

<form action="${this_url}" name="userForm" method="post">
    <table summary="">

        <!-- id: ID -->
        <@spring.bind "user.id" />
        <tr class="hidden">
            <th>ID</th>
            <td>

                <input type="hidden" class="text" name="${spring.status.expression}"
                       value="<#if spring.status.value??><#if spring.status.value?is_number>${spring.status.value?c}<#else>${spring.status.value?html}</#if></#if>"/>

            </td>
            <td>
            </td>
        </tr>
        <!-- userName: User Name -->
        <@spring.bind "user.userName" />
        <tr>
            <th>User Name</th>
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
           <!--
        <tr>
            <th>Old Password</th>
            <td>

                <input type="password" class="text" name="oldPassWord" value=""/>

            </td>
            <td>
            </td>
        </tr>            -->

        <!-- passWord: Pass Word -->
        <@spring.bind "user.passWord" />
        <!--
        <tr>
            <th>New Password</th>
            <td>

                <input type="password" class="text" name="thePassWord"
                       value=""/>

            </td>
            <td>

                <#list spring.status.errorMessages as error>
                <b>${error}</b><br/>
                </#list>
            </td>
        </tr>
        <tr>
            <th>Retype Password</th>
            <td>

                <input type="password" class="text" name="retypePassWord" value=""/>

            </td>
            <td>

            </td>
        </tr>               -->
        <!-- firstName: First Name -->
        <@spring.bind "user.firstName" />
        <tr>
            <th>First Name</th>
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
        <!-- lastName: Last Name -->
        <@spring.bind "user.lastName" />
        <tr>
            <th>Last Name</th>
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
        <!-- email: Email Address -->
        <@spring.bind "user.email" />
        <tr>
            <th>Email Address</th>
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
    function checkForm(theForm) {
        // username, firstname, lastname and email address can't be empty
        if (theForm.userName.value.length == 0) {
            alert("Please input User Name");
            theForm.userName.focus();
            return false;
        }
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
        return true;
    }
    $(function() {
        $('form[name=userForm]').submit(function() {
            <#-- TODO: rewrite checkForm -->
            if (checkForm(document.userForm))
                this.submit();
        });
    });

    //-->
</script>
