FCKConfig.ToolbarSets["CUSTOM"] = [
        ['Source'],
        ['Cut','Copy','Paste','PasteText','PasteWord'],
        ['Bold','Italic','Underline'],
        ['Undo','Redo'],
        ['Table','Rule','SpecialChar'],
        ['Subscript','Superscript'],
        ['OrderedList','UnorderedList','-','Outdent','Indent'],
        // ['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],
        ['TextColor','BGColor'],
        ['Link','Unlink','Anchor'],
        // ['Style'],
        ['About']
        ];

FCKConfig.ToolbarSets["simple"] = [
        ['Source'],
        ['Bold','Italic','Underline'],
        ['Subscript','Superscript'],
        ['About']
        ];

FCKConfig.EditorAreaCSS = FCKConfig.BasePath + '../custom/style.css';
FCKConfig.StylesXmlPath = FCKConfig.BasePath + '../custom/styles.xml';

FCKConfig.FormatOutput = true;
FCKConfig.FormatSource = true;

// hide toolbar
// FCKConfig.ToolbarStartExpanded = false;

// do not show the upload tab in link dialog
FCKConfig.LinkUpload = false;
// do not show the advanced tab
FCKConfig.LinkDlgHideAdvanced = true;
// do not show the target tab
FCKConfig.LinkDlgHideTarget = true;

// do not show the upload tab in image dialog
FCKConfig.ImageUpload = false;
// do not show the advanced tab
FCKConfig.ImageDlgHideAdvanced = true;
// disable browser button
FCKConfig.ImageBrowser = false;

// new option for FCKeditor 2.4
FCKConfig.EnterMode = 'p';
// p | div | br
FCKConfig.ShiftEnterMode = 'br';
// p | div | br
