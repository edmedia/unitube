<h2>Statistics Page</h2>

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
            <td></td>
        </tr>
        </tbody>
    </table>
</div>
</#if>

<div id="bar">

</div>

<script type="text/javascript">
    <!--
    $(function() {
        // prepare pie data
        var pie_data = [
        <#list list as l><#if l.num &gt; 0>
            ['${l.name?html}', ${l.num}]<#if l_has_next>,</#if>
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
            legend: { show: true, location: 'ne' }
        });

        // prepare bar data
        var allMonth = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
        var ticks = new Array();
        var series = [
            {label:'Upload'},
            {label:'Update'},
            {label:'View'},
            {label:'Delete'}
        ];
        var sss = new Array(series.length);
        for (var i = 0; i < series.length; i++)
            sss[i] = new Array(${stats?size});
    <#list stats as s>
        <#list s as ss>
            sss[${ss_index}][${s_index}] = ${ss};
            ticks[${s_index}] = allMonth[${s_index}];
        </#list>
    </#list>

        log("sss = " + sss);
        // display bar
        var bar = $.jqplot('bar', sss, {
            // The "seriesDefaults" option is an options object that will
            // be applied to all series in the chart.
            seriesDefaults:{
                renderer: $.jqplot.BarRenderer,
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

    });
    //-->
</script>

<#if stats?has_content>
<table>
    <thead>
    <tr>
        <th>Month</th>
        <th>Upload</th>
        <th>Update</th>
        <th>View</th>
        <th>Delete</th>
    </tr>
    </thead>
    <tbody>
        <#list stats as s>
        <tr>
            <td>${s_index+1}</td>
            <#list s as ss>
                <td>${ss}</td>
            </#list>
        </tr>
        </#list>
    </tbody>
</table>
</#if>