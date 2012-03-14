<p>&nbsp;</p>
<#if deleteList?has_content>
<h2>All media files whose original file is deleted</h2>
<ul>
    <#list deleteList as d>
        <li><a href="${baseUrl}/view?m=${d.accessCode?html}"> ${d.title?html} </a></li>
    </#list>
</ul>
</#if>
<div id="pie">
</div>
<#if list?has_content>
<div id="pie_num">
    <table>
        <thead>
        <tr>
            <th>Type</th>
            <th>No.</th>
            <th>Percent</th>
        </tr>
        </thead>
        <tbody>
            <#list list as l>
                <#if l.num &gt; 0>
                <tr>
                    <td>${l.name?html}</td>
                    <td class="num">${l.num}</td>
                    <td class="percent">${(l.num*100/total)?string("0.#")}%</td>
                </tr>
                </#if>
            </#list>
        <tr>
            <th>Total</th>
            <td class="num">${total}</td>
            <td>&nbsp; </td>
        </tr>
        </tbody>
    </table>
</div>
</#if>
<div id="bar">
</div>
<div id="viewBar">
</div>
<#--
<#if stats?has_content>
<h2>Uploads, Updates, Deletions and Views in Year ${y?c}<#if m?has_content>, Month ${m+1}</#if></h2>
<table>
    <thead>
    <tr>
        <th><#if m?has_content>Day<#else>Month</#if></th>
        <th>Upload</th>
        <th>Update</th>
        <th>Delete</th>
        <th>View</th>
    </tr>
    </thead>
    <tbody>
        <#list stats as s>
        <tr>
            <td><#if m?has_content><a href="?y=${y?c}"><#else>
            <a href="?y=${y?c}&amp;m=${s_index}"></#if>${s_index+1}</a></td>
            <#list s as ss>
                <td class="num">${ss}</td>
            </#list>
        </tr>
        </#list>
    </tbody>
</table>
</#if>
-->
<script type="text/javascript">
    <!--
    $(function() {
        log("jqPlot version: " + $.jqplot.version);
    <#if list?has_content>
        // prepare pie data
        var pie_data = [
            <#list list as l><#if l.num &gt; 0>
                ['${l.name?html}', ${l.num?c}]<#if l_has_next>,</#if>
            </#if></#list>
        ];
        // display pie
        var plot1 = $.jqplot('pie', [pie_data], {
            seriesDefaults: {
                renderer: $.jqplot.PieRenderer,
                rendererOptions: {
                    showDataLabels: true,
                    dataLabels: 'value'
                }
            },
            title: 'All Existing Media Files',
            legend: { show: true, location: 'ne' }
        });
    </#if>

    <#if stats?has_content>
        // prepare bar data
        var allMonth = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
        var ticks = new Array();
        var series = [
            {label:'Upload'},
            {label:'Update'},
            {label:'Delete'}
        ];
        var sss = new Array(series.length);
        for (var i = 0; i < series.length; i++)
            sss[i] = new Array(${stats?size});
        <#list stats as s>
            <#list s as ss>
                <#if ss_has_next>
                    sss[${ss_index}][${s_index}] = ${ss?c};
                    <#if !m?has_content>
                        ticks[${s_index}] = allMonth[${s_index}];
                    </#if>
                </#if>
            </#list>
        </#list>
        // display bar
        var bar = $.jqplot('bar', sss, {
            // The "seriesDefaults" option is an options object that will
            // be applied to all series in the chart.
            seriesDefaults:{
                renderer: $.jqplot.BarRenderer,
                pointLabels: {show: true, edgeTolerance: -15, hideZeros: true},
                rendererOptions: {fillToZero: true}
            },
            // Custom labels for the series are specified with the "label"
            // option on the series option.  Here a series option object
            // is specified for each series.
            series: series,
            // Show the legend and put it outside the grid, but inside the
            // plot container, shrinking the grid to accommodate the legend.
            // A value of "outside" would not shrink the grid and allow
            // the legend to overflow the container.
            legend: {
                show: true,
                location: 'nw',
                placement: 'insideGrid'
            },
            title: 'Uploads, Updates and Deletions in ' + <#if m?has_content>allMonth[${m}] + ' ' + </#if> '${y?c}',
            axes: {
                // Use a category axis on the x axis and use our custom ticks.
                xaxis: {
                    renderer: $.jqplot.CategoryAxisRenderer,
                    ticks: ticks
                },
                // Pad the y axis just a little so bars can get close to, but
                // not touch, the grid boundaries.  1.2 is the default padding.
                yaxis: {
                    pad: 1.01,
                    tickOptions: {formatString: '%d'}
                }
            }
        });
        var viewSeries = [
            {label:'View'}
        ];
        var ttt = new Array(viewSeries.length);
        for (var i = 0; i < viewSeries.length; i++)
            ttt[i] = new Array(${stats?size});
        <#list stats as s>
            <#list s as ss>
                <#if !ss_has_next>
                    ttt[0][${s_index}] = ${ss?c};
                </#if>
            </#list>
        </#list>
        // display view bar
        var viewBar = $.jqplot('viewBar', ttt, {
            // The "seriesDefaults" option is an options object that will
            // be applied to all series in the chart.
            seriesDefaults:{
                renderer: $.jqplot.BarRenderer,
                pointLabels: {show: true, edgeTolerance: -15, hideZeros: true},
                rendererOptions: {fillToZero: true}
            },
            // Custom labels for the series are specified with the "label"
            // option on the series option.  Here a series option object
            // is specified for each series.
            series: viewSeries,
            // Show the legend and put it outside the grid, but inside the
            // plot container, shrinking the grid to accommodate the legend.
            // A value of "outside" would not shrink the grid and allow
            // the legend to overflow the container.
            legend: {
                show: true,
                location: 'nw',
                placement: 'insideGrid'
            },
            title: 'Views in ' + <#if m?has_content>allMonth[${m}] + ' ' + </#if> '${y?c}' ,
            seriesColors: ["#579575"]  ,
            axes: {
                // Use a category axis on the x axis and use our custom ticks.
                xaxis: {
                    renderer: $.jqplot.CategoryAxisRenderer,
                    ticks: ticks
                },
                // Pad the y axis just a little so bars can get close to, but
                // not touch, the grid boundaries.  1.2 is the default padding.
                yaxis: {
                    pad: 1.01,
                    tickOptions: {formatString: '%d'}
                }
            }
        });

        $('#viewBar').bind('jqplotDataClick',
                function (ev, seriesIndex, pointIndex, data) {
                    jumpToPage(pointIndex);
                }
        );

        $('#bar').bind('jqplotDataClick',
                function (ev, seriesIndex, pointIndex, data) {
                    jumpToPage(pointIndex);
                }
        );

        function jumpToPage(pointIndex) {
            var url = "";
            <#if m?has_content>
                url = "?y=${y?c}";
                <#else>
                    url = "?y=${y?c}&m=" + pointIndex;
            </#if>
            log('url = ' + url);
            location.href = url;
        }
    </#if>

    });
    //-->
</script>
