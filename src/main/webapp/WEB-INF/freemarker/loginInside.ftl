<#include "loginInfo.ftl" />

<div class="mediaArea">

<#if fromUrl?has_content>
    <p style="color: red">Please Login before access.</p>
    <#if fromUrl?contains("?")>
        <#assign fromUrl>${fromUrl}&amp;cas=true&amp;login=true</#assign>
        <#else>
            <#assign fromUrl>${fromUrl}?cas=true&amp;login=true</#assign>
    </#if>
    <#else>
        <#assign fromUrl>${baseUrl}/myTube/list.do?cas=true&amp;login=true</#assign>
</#if>

    <div>
    <#if appInfo.usingCAS>
        <h1>University of Otago Member?</h1>

        <a href="${fromUrl}" class="bold">Log in here</a><br/>
    <#--
        <a href="${fromUrl}"><img src="${baseUrl}/images/bb_logo.gif" width="152" height="29" alt=""
                                  style="margin:10px auto 30px auto;"/></a>
      -->
        <hr/>
    </#if>
        <#--
        <h2 title="Click here to login using your UniTube username and password">
            Non University of Otago Member?
            <a href="#">Click here to login.</a>
        </h2>

        <div id="uniTubeLogin">
            <img src="${baseUrl}/images/unitube_logo.png" alt=""/><br/>
        <#if error??>
            <p style="color: red">${error}</p>
        </#if>
            <form method="post" action="" name="loginForm">
                <input value="${oneTimeToken!}" name="oneTimeToken" type="hidden"/>
                <input value="" name="passWord" type="hidden"/>
                <table summary="">
                    <tr>
                        <td width="100">Username:</td>
                        <td>
                            <input type="text" name="userName" class="required"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td>
                            <input name="thePassWord" type="password" class="required"/>
                        </td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td><input type="submit" value="Log in"/></td>
                    </tr>
                </table>
            </form>
        </div>

        <h2 title="Click here to register">
            New? <a href="#">Register here.</a>
        </h2>

        <div id="newRegister">

            <form action="${baseUrl}/register.do" name="userForm" method="post">
                <input type="hidden" name="userName" value=""/>
                <table summary="">
                    <tr>
                        <td width="100">First Name:</td>
                        <td>
                            <input name="firstName" type="text" class="required"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Last Name:</td>
                        <td>
                            <input name="lastName" type="text" class="required"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Email:</td>
                        <td>
                            <input name="email" type="text" class="required email"/>
                        </td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>
                            <input type="submit" value="Register"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>

        <h2 title="Click here if you forgot your UniTube password">
            Forgot your UniTube password?
            <a href="#">Click here to reset.</a>
        </h2>

        <div id="forgotPassword">

            <form method="post" name="resetPasswordForm" action="${baseUrl}/resetPassword.do">
                <table summary="">
                    <tr>
                        <td width="100">Username or Email Address:
                        </td>
                        <td>
                            <input type="text" id="u" name="u" class="required"/>
                        </td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>
                            <input type="submit" name="resetPassword" value="Reset password"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>     -->
    </div>

</div>
<!--  end of mediaArea -->

<script type="text/javascript">
    <!--
    $(function() {
    <#-- hide UniTube login form when there is no error -->
    <#if !error??>
        $('#uniTubeLogin').hide();
    </#if>
    <#-- display UniTube login form when not using CAS -->
    <#if !appInfo.usingCAS>
        $('#uniTubeLogin').show();
        if ($('#uniTubeLogin input[name=userName]').val().length == 0)
            $('#uniTubeLogin input[name=userName]').focus();
    </#if>
        // hide new registration form by default
        $('#newRegister').hide();
        // hide forgot password form by default
        $('#forgotPassword').hide();
        // when h2 clicks
        $('h2').click(function() {
            $(this).next().toggle();
            if ($(this).next().is(":visible"))
                $("input[name=userName]", $(this).next()).focus();
        });
        // when a tag inside h2 clicks
        $('h2 a').click(function() {
            $(this).parent().next().toggle();
            if ($(this).parent().next().is(":visible"))
                $("input[name=userName]", $(this).parent().next()).focus();
            return false;
        });

        $('form[name=loginForm]').submit(function() {
            var theForm = $(this);
            if (theForm.valid()) {
                var md5_password = hex_md5($('input[name=thePassWord]', theForm).val().trim());
                if ($('input[name=oneTimeToken]', theForm).val().length > 0)
                    md5_password = hex_md5(md5_password + $('input[name=oneTimeToken]', theForm).val());
                $('input[name=passWord]', theForm).val(md5_password);
                $('input[name=thePassWord]', theForm).val();
                return true;
            } else
                return false;
        });

        $('form[name=userForm]').submit(function() {
            var theForm = $(this);
            if (theForm.valid())
                theForm.ajaxSubmit({
                            success: function(xml) {
                                if ($("action", xml).attr("success") == "true")
                                    $('input:submit', theForm).hide();
                                alert($("action", xml).attr("detail"));
                            }
                        });
            return false;
        });

        $('form[name=resetPasswordForm]').submit(function() {
            var theForm = $(this);
            if (theForm.valid())
                theForm.ajaxSubmit({
                            success: function(xml) {
                                if ($("action", xml).attr("success") == "true")
                                    $('input:submit', theForm).hide();
                                alert($("action", xml).attr("detail"));
                            }
                        });
            return false;
        });

    });

    //-->
</script>