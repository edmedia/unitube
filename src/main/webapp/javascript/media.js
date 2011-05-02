function removeAlbumFromMedia(albumMediaID) {
    new Ajax.Request('removeAlbumMedia.do?id=' + albumMediaID, {
        onSuccess: function(transport) {
            if (transport.responseText.match(/success="true"/)) {
                if ($('albumMediaID_' + albumMediaID))
                    $('albumMediaID_' + albumMediaID).hide();
            } else {
                if (window.alertAjaxMsg)
                    alertAjaxMsg();
            }
        }
    });
    return false;
}

function removeUserFromAlbum(userAlbumID) {
    new Ajax.Request('removeUserAlbum.do?id=' + userAlbumID, {
        onSuccess: function(transport) {
            if (transport.responseText.match(/success="true"/)) {
                if ($('userAlbumID_' + userAlbumID))
                    $('userAlbumID_' + userAlbumID).hide();
            } else {
                if (window.alertAjaxMsg)
                    alertAjaxMsg();
            }
        }
    });
    return false;
}

function sendFeedback() {
    if ($('status').value.length == 0) {
        alert("Please input your feedback.");
        $('status').focus();
        return;
    }
    $('feedbackButton').disable();
    new Ajax.Request('sendFeedback.do', {
        method: 'post',
        parameters: $('feedback').serialize(true),
        onSuccess: function(transport) {
            if (transport.responseText.match(/success="true"/)) {
                alert("Thank you very much for your feedback");
                $('status').clear();
                $('feedbackButton').enable();
                location.reload(true);
            } else {
                if (window.alertAjaxMsg)
                    alertAjaxMsg();
                $('feedbackButton').enable();
            }
        },
        onFailure: function() {
            if (window.alertAjaxMsg)
                alertAjaxMsg();
            $('feedbackButton').enable();
        }
    });
}

function resetPassword() {
    if ($('u').value.length == 0) {
        alert("Please input your username or email address.");
        $('u').focus();
        return;
    }
    $('resetPasswordButton').disable();
    new Ajax.Request('resetPassword.do', {
        method: 'post',
        parameters: $('resetPasswordForm').serialize(true),
        onSuccess: function(transport) {
            if (transport.responseText.match(/success="true"/)) {
                alert("You will receive an email with your username and new password.");
                $('resetPasswordButton').hide();
            } else {
                if (window.alertAjaxMsg)
                    alertAjaxMsg();
                $('resetPasswordButton').enable();
            }
        },
        onFailure: function() {
            if (window.alertAjaxMsg)
                alertAjaxMsg();
            $('resetPasswordButton').enable();
        }
    });
}

function postComment(theForm) {
    if ($F(theForm.msg).length == 0) {
        alert("Please enter a comment!");
        $(theForm.msg).focus();
        return;
    }
    $(theForm.postCommentButton).disable();
    new Ajax.Request('myTube/postComment.do', {
        method: 'post',
        parameters: $(theForm).serialize(true),
        onSuccess: function(transport) {
            if (transport.responseText.match(/success="true"/)) {
                alert("Thank you very much for your comment.");
                $(theForm.msg).clear();
                location.reload(true);
            } else {
                if (window.alertAjaxMsg)
                    alertAjaxMsg();
                $(theForm.postCommentButton).enable();
            }
        },
        onFailure: function() {
            if (window.alertAjaxMsg)
                alertAjaxMsg();
            $(theForm.postCommentButton).enable();
        }
    });
}

// script for open new window
var popupWin = null;
function newWin(url) {
    if ((popupWin != null) && (!popupWin.closed))
        popupWin.location.href = url;
    else {
        var width = 1000;
        var height = 800;
        var left = (screen.width - width)/2;
        var top = (screen.height- height)/2;
        var features = 'left=' + left + ',top=' + top + ',width=' + width + ',height=' + height + ',menubar=yes,location=yes,toolbar=yes,resize=yes,status=yes';
        // alert(features);
        popupWin = window.open(url, 'haha', features);
    }
    if(popupWin)
        popupWin.focus();
}
