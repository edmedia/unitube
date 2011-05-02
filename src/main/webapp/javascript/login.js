function validate_form(form) {
    if (form.userName.value.length == 0 || form.thePassWord.value.length == 0) {
        alert("Enter a username and password.");
        return false;
    }

    var md5_password = hex_md5(form.thePassWord.value);
    if (form.oneTimeToken.value.length > 0)
        md5_password = hex_md5(md5_password + form.oneTimeToken.value);
    form.passWord.value = md5_password;
    form.thePassWord.value = "";
    return true;
}

function validate_reset_form(form) {
    if (form.userName.value.length == 0 || form.thePassWord.value.length == 0) {
        alert("Enter a username and password.");
        return false;
    }

    var md5_password = hex_md5(form.thePassWord.value);
    if (form.oneTimeToken.value.length > 0)
        md5_password = hex_md5(md5_password + form.oneTimeToken.value);
    form.passWord.value = md5_password;
    form.thePassWord.value = "";
    return true;
}

function checkForm(theForm) {
    // username, firstname, lastname and email address can't be empty
    /**
     if (theForm.userName.value.length == 0) {
     alert("Please input User Name");
     theForm.userName.focus();
     return false;
     }  //*/
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
    theForm.userName.value = theForm.email.value;
    return true;
}

// script for logout
var popUp = null;
var logoutUrl;
function logout(url, bbLogoutUrl) {
    logoutUrl = url;
    if (bbLogoutUrl && (bbLogoutUrl.length > 0)) {
        if ((popUp != null) && (!popUp.closed))
            popUp.location.href = bbLogoutUrl;
        else
            popUp = window.open(bbLogoutUrl, 'logout', 'left=10000,top=10000,width=10,height=10,toolbar=no,resize=no,status=no');
        if (window.self)
            window.self.focus();
        window.setTimeout(goOn, 1000);
    } else
        goOn();
}

// close Blackboard logout window, and redirect to home page
function goOn() {
    if (popUp != null)
        popUp.close();
    location.href = logoutUrl;
}
