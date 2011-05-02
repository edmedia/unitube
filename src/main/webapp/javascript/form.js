/* Some JavaScript functions about form, by richard.zeng@otago.ac.nz, July 2008 */

/** Returns true if any one in a radio/checkbox group has been checked */
function isChecked(formElement) {
    if (formElement) {
        if (formElement.length) {
            for (var x = 0; x < formElement.length; x++) {
                if (formElement[x].checked)
                    return true;
            }
        } else {
            if (formElement.checked)
                return true;
        }
    }
    return false;
}

/** Returns element type of a form element */
function getElementType(formElement) {
    var type;
    if (formElement) {
        if (formElement.length)
            type = formElement[0].type;
        if ((typeof(type) == 'undefined') || (type == 0))
            type = formElement.type;
    }
    return type;
}

/** Sets focus to a form element */
function setFocus(formElement) {
    if (formElement) {
        if (formElement.length)
            formElement[0].focus();
        else
            formElement.focus();
    }
}

/** Returns the value of a form element */
function getElementValue(formElement) {
    var value = "";
    var x, myArray;
    if (formElement) {
        var type = getElementType(formElement);
        switch (type) {

            case 'undefined': break;

            case 'radio':
                if (formElement.length) {
                    for (x = 0; x < formElement.length; x++) {
                        if (formElement[x].checked) {
                            value = formElement[x].value;
                            break;
                        }
                    }
                } else {
                    if (formElement.checked) {
                        value = formElement.value;
                        break;
                    }
                }
                break;

            case 'select-multiple':
                if (formElement.length) {
                    myArray = new Array();
                    for (x = 0; x < formElement.length; x++) {
                        if (formElement[x].selected) {
                            myArray[myArray.length] = formElement[x].value;
                        }
                    }
                    value = myArray;
                } else {
                    if (formElement.selected)
                        value = formElement.value;
                }
                break;

            case 'checkbox':
                if (formElement.length) {
                    myArray = new Array();
                    for (x = 0; x < formElement.length; x++) {
                        if (formElement[x].checked) {
                            myArray[myArray.length] = formElement[x].value;
                        }
                    }
                    value = myArray;
                } else {
                    if (formElement.checked)
                        value = formElement.value;
                }
                break;

            default: value = formElement.value;
        }
    }
    return value;
}

/** Sets the value of a form element */
function setElementValue(formElement, value) {
    if (formElement) {
        var type = getElementType(formElement);
        var x;
        switch (type) {

            case 'undefined': break;

            case 'radio':
                if (formElement.length) {
                    for (x = 0; x < formElement.length; x++) {
                        if (formElement[x].value == value) {
                            formElement[x].checked = true;
                            break;
                        }
                    }
                } else {
                    if (formElement.value == value) {
                        formElement.checked = true;
                    }
                }
                break;

            case 'checkbox':
                if (formElement.length) {
                    for (x = 0; x < formElement.length; x++) {
                        if (formElement[x].value == value) {
                            formElement[x].checked = true;
                        }
                    }
                } else {
                    if (formElement.value == value) {
                        formElement.checked = true;
                    }
                }
                break;

            case 'select-one':
                for (x = 0; x < formElement.length; x++) {
                    if (formElement[x].value == value) {
                        formElement[x].selected = true;
                        break;
                    }
                }
                break;

            case 'select-multiple':
                for (x = 0; x < formElement.length; x++) {
                    if (formElement[x].value == value) {
                        formElement[x].selected = true;
                    }
                }
                break;

            default: formElement.value = value;
        }
    }
}

/** Clears the value of a form element */
function clearElementValue(formElement) {
    if (formElement) {
        var type = getElementType(formElement);
        var x;
        switch (type) {

            case 'undefined': break;

            case 'radio':
            case 'checkbox':
                if (formElement.length) {
                    for (x = 0; x < formElement.length; x++) {
                        formElement[x].checked = false;
                    }
                } else {
                    formElement.checked = false;
                }
                break;

            case 'select-one':
            case 'select-multiple':
                if (formElement.length) {
                    for (x = 0; x < formElement.length; x++) {
                        formElement[x].selected = false;
                    }
                } else {
                    formElement.selected = false;
                }
                break;

            default: formElement.value = '';
        }
    }
}

/** Checks all checkboxes or select-multiple. */
function checkElement(formElement) {
    if (formElement) {
        var type = getElementType(formElement);
        var x;
        switch (type) {

            case 'checkbox':
                if (formElement.length) {
                    for (x = 0; x < formElement.length; x++) {
                        formElement[x].checked = true;
                    }
                } else {
                    formElement.checked = true;
                }
                break;

            case 'select-multiple':
                if (formElement.length) {
                    for (x = 0; x < formElement.length; x++) {
                        formElement[x].selected = true;
                    }
                } else {
                    formElement.selected = true;
                }
                break;

        }
    }
}

/** returns form object of a form element */
function getForm(formElement) {
    var myForm;
    if (formElement) {
        if (formElement.form) {
            myForm = formElement.form;
        } else if (formElement.length) {
            if (formElement[0].form) {
                myForm = formElement[0].form;
            }
        }
    }
    return myForm;
}

/** Returns the value of a radio group. Deprecated. Using getElementValue instead. */
function getValue(formItem) {
    for (var x = 0; x < formItem.length; x++) {
        if (formItem[x].checked) {
            return formItem[x].value;
        }
    }
    return "";
}

/** Sets value of a radio group. Deprecated. Using setElementValue instead. */
function setValue(formItem, strValue) {
    for (var x = 0; x < formItem.length; x++) {
        if (formItem[x].value == strValue) {
            formItem[x].checked = true;
            break;
        }
    }
}

/** Clears all checked elements in a check box group. Deprecated. Using clearElementValue instead. */
function clearCheckBox(formItem) {
    for (var x = 0; x < formItem.length; x++) {
        formItem[x].checked = false;
    }
}

/** Select from a drop down list. Deprecated. Using setElementValue instead. */
function selectOption(formItem, strValue) {
    for (var x = 0; x < formItem.options.length; x++) {
        if (formItem.options[x].value == strValue) {
            formItem[x].selected = true;
            break;
        }
    }
}

/** Delays for some time. It seems it does not work in IE. */
function delay(gap) { /* gap is in millisecs */
    var then,now;
    then = new Date().getTime();
    now = then;
    while ((now - then) < gap) {
        now = new Date().getTime();
    }
}
