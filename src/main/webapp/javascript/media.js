var inDebugMode = true;

if (!('trim' in String.prototype)) {
    String.prototype.trim = function() {
        return this.replace(/^\s+|\s+$/g, "");
    };
}

function removeAlbumFromMedia(albumMediaID) {
    $.get('removeAlbumMedia.do?id=' + albumMediaID, function(xml) {
        if ($("action", xml).attr("success") == "true")
            if ($('#albumMediaID_' + albumMediaID))
                $('#albumMediaID_' + albumMediaID).hide();
            else
                alert($("action", xml).attr("detail"));
    });
    return false;
}

function removeUserFromAlbum(userAlbumID) {
    $.get('removeUserAlbum.do?id=' + userAlbumID, function(xml) {
        if ($("action", xml).attr("success") == "true") {
            if ($('#userAlbumID_' + userAlbumID))
                $('#userAlbumID_' + userAlbumID).hide();
        } else
            alert($("action", xml).attr("detail"));
    });
    return false;
}

// each slideInfo has start time, end time, which slide (1-based), title(optional)
function slideInfo(sTime, eTime, num, title) {
    this.sTime = sTime;
    this.eTime = eTime;
    this.num = num;
    this.title = title;
}

function convertSecondsToTimecode(seconds) {
    var ss = Math.round(seconds);
    minutes = Math.floor(ss / 60);
    minutes = (minutes >= 10) ? minutes : "0" + minutes;
    ss = Math.floor(ss % 60);
    ss = (ss >= 10) ? ss : "0" + ss;
    return minutes + ":" + ss;
}

function convertTimecodeToSeconds(timecode) {
    var seconds = 0;
    var ss = timecode.split(":");
    for (var i = ss.length; i > 0; i--)
        seconds = seconds * 60 + parseFloat(ss[ss.length - i]);
    return seconds;
}

// script for open new window
var popupWin = null;
function newWin(url) {
    if ((popupWin != null) && (!popupWin.closed))
        popupWin.location.href = url;
    else {
        var width = 1000;
        var height = 800;
        var left = (screen.width - width) / 2;
        var top = (screen.height - height) / 2;
        var features = 'left=' + left + ',top=' + top + ',width=' + width + ',height=' + height + ',menubar=yes,location=yes,toolbar=yes,resize=yes,status=yes';
        // alert(features);
        popupWin = window.open(url, 'haha', features);
    }
    if (popupWin)
        popupWin.focus();
}


// script for logout
var popUp = null;
var logoutUrl;
function logout(url, casLogoutUrl) {
    logoutUrl = url;
    if (casLogoutUrl && (casLogoutUrl.length > 0)) {
        var msg = 'As uniTube is protected by a Single Sign On service, you will be logged out of other applications as well. Do you really want to do this?';
        if (confirm(msg)) {
            if (casLogoutUrl.indexOf('zita') > 0) {         // if CAS
                logoutUrl = casLogoutUrl + '?service=' + encodeURIComponent(url);
                goOn();
            } else if (casLogoutUrl.indexOf('idm') > 0) {   // if Oracle SSO
                logoutUrl = casLogoutUrl + '?end_url=' + encodeURIComponent(url);
                goOn();
            } else {
                // otherwise, access cas logout url in a pop-up window
                // then redirect to UniTube logout page
                if ((popUp != null) && (!popUp.closed))
                    popUp.location.href = casLogoutUrl;
                else
                    popUp = window.open(casLogoutUrl, 'logout', 'left=10000,top=10000,width=10,height=10,toolbar=no,resize=no,status=no');
                if (window.self)
                    window.self.focus();
                window.setTimeout(goOn, 1000);
            }
        }
    } else
        goOn();
}

// close cas logout window, and redirect to UniTube logout page
function goOn() {
    if (popUp != null)
        popUp.close();
    location.href = logoutUrl;
}

function log(msg) {
    if (inDebugMode && window.console && window.console.log) window.console.log(msg);
}