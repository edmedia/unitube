<@spring.bind "iVOption.media" />
<h2>${spring.status.value.title?html}</h2>
        
<form action="${this_url}" name="iVOptionForm" method="post">
<input type="hidden" name="mediaID" value="${spring.status.value.id?c}"/>
<@spring.bind "iVOption.id" />
<input type="hidden" class="text" name="${spring.status.expression}" value="<#if spring.status.value??><#if spring.status.value?is_number>${spring.status.value?c}<#else>${spring.status.value?html}</#if></#if>" />
<#if pageNumber??>
<input type="hidden" name="p" value="${pageNumber?c}"/>
</#if>
<#if pageSize??>
<input type="hidden" name="s" value="${pageSize?c}"/>
</#if>
<table summary="">

<!-- actualWidth: Actual Width -->
<@spring.bind "iVOption.actualWidth" />
<tr>
<th>Actual Width</th>
<td>

<input type="text" class="text" name="${spring.status.expression}" value="<#if spring.status.value??><#if spring.status.value?is_number>${spring.status.value?c}<#else>${spring.status.value?html}</#if></#if>" />

</td>
<td>

<#if spring.status.errorMessages?has_content>
<div class="errorMessage">
<#list spring.status.errorMessages as error>
<p>${error}</p>
</#list>
</div>
</#if>
</td>
</tr>
<!-- acturalWidthUnit: Actual Width Unit -->
<@spring.bind "iVOption.acturalWidthUnit" />
<tr>
<th>Actual Width Unit</th>
<td>

<input type="text" class="text" name="${spring.status.expression}" value="<#if spring.status.value??><#if spring.status.value?is_number>${spring.status.value?c}<#else>${spring.status.value?html}</#if></#if>" />

</td>
<td>
inch, cm, µm, pixel, etc.
<#if spring.status.errorMessages?has_content>
<div class="errorMessage">
<#list spring.status.errorMessages as error>
<p>${error}</p>
</#list>
</div>
</#if>
</td>
</tr>
<!-- minZoom: Minimum Zoom -->
<@spring.bind "iVOption.minZoom" />
<tr>
<th>Minimum Zoom</th>
<td>

<input type="text" class="text" name="${spring.status.expression}" value="<#if spring.status.value??><#if spring.status.value?is_number>${spring.status.value?c}<#else>${spring.status.value?html}</#if></#if>" />%

</td>
<td>

<#if spring.status.errorMessages?has_content>
<div class="errorMessage">
<#list spring.status.errorMessages as error>
<p>${error}</p>
</#list>
</div>
</#if>
</td>
</tr>
<!-- maxZoom: Maximum Zoom -->
<@spring.bind "iVOption.maxZoom" />
<tr>
<th>Maximum Zoom</th>
<td>

<input type="text" class="text" name="${spring.status.expression}" value="<#if spring.status.value??><#if spring.status.value?is_number>${spring.status.value?c}<#else>${spring.status.value?html}</#if></#if>" />%

</td>
<td>

<#if spring.status.errorMessages?has_content>
<div class="errorMessage">
<#list spring.status.errorMessages as error>
<p>${error}</p>
</#list>
</div>
</#if>
</td>
</tr>
<!-- displayMeasureTool: Display Measurement tool and grid? -->
<@spring.bind "iVOption.displayMeasureTool" />
<tr>
<th>Display Measurement tool and grid?</th>
<td>
<input type="radio" name="${spring.status.expression}" value="true"<#if spring.status.value??><#if spring.status.value> checked="checked"</#if></#if> /> Yes
<input type="radio" name="${spring.status.expression}" value="false"<#if spring.status.value??><#if !spring.status.value> checked="checked"</#if></#if> /> No
</td>
<td>

<#if spring.status.errorMessages?has_content>
<div class="errorMessage">
<#list spring.status.errorMessages as error>
<p>${error}</p>
</#list>
</div>
</#if>
</td>
</tr>
<!-- otherCanAnnotate: Other can annotate this image? -->
<@spring.bind "iVOption.otherCanAnnotate" />
<tr>
<th>Other can annotate this image?</th>
<td>
<input type="radio" name="${spring.status.expression}" value="true"<#if spring.status.value??><#if spring.status.value> checked="checked"</#if></#if> /> Yes
<input type="radio" name="${spring.status.expression}" value="false"<#if spring.status.value??><#if !spring.status.value> checked="checked"</#if></#if> /> No
</td>
<td>

<#if spring.status.errorMessages?has_content>
<div class="errorMessage">
<#list spring.status.errorMessages as error>
<p>${error}</p>
</#list>
</div>
</#if>
</td>
</tr>
<!-- whichImageForIV: Which Image for ImageViewer to use? -->
<@spring.bind "iVOption.whichImageForIV" />
<tr>
<th>Which Image for ImageViewer to use?</th>
<td>

<#assign whichImageForIV>${spring.status.value!}</#assign>
<@spring.bind "iVOption.media" />
<#assign maxSize = 1024/>
<#assign theFilename = "image-l.jpg"/>
<#if (spring.status.value.width &gt; maxSize) || (spring.status.value.height &gt; maxSize)>
<input type="radio" name="whichImageForIV" value="${theFilename}"<#if "${theFilename}" = whichImageForIV> checked="checked"</#if>/> Large (<#if spring.status.value.width &gt; spring.status.value.height>${maxSize?c}x${(spring.status.value.height*maxSize/spring.status.value.width)?round?c}<#else>${(spring.status.value.width*maxSize/spring.status.value.height)?round?c}x${maxSize?c}</#if>)
</#if>
<#assign maxSize = 2048/>
<#assign theFilename = "image-v.jpg"/>
<#if (spring.status.value.width &gt; maxSize) || (spring.status.value.height &gt; maxSize)>
<input type="radio" name="whichImageForIV" value="${theFilename}"<#if "${theFilename}" = whichImageForIV> checked="checked"</#if>/> Very Large (<#if spring.status.value.width &gt; spring.status.value.height>${maxSize?c}x${(spring.status.value.height*maxSize/spring.status.value.width)?round?c}<#else>${(spring.status.value.width*maxSize/spring.status.value.height)?round?c}x${maxSize?c}</#if>)
</#if>
<#assign maxSize = 4096/>
<#assign theFilename = "image-e.jpg"/>
<#if (spring.status.value.width &gt; maxSize) || (spring.status.value.height &gt; maxSize)>
<input type="radio" name="whichImageForIV" value="${theFilename}"<#if "${theFilename}" = whichImageForIV> checked="checked"</#if>/> Extra Large (<#if spring.status.value.width &gt; spring.status.value.height>${maxSize?c}x${(spring.status.value.height*maxSize/spring.status.value.width)?round?c}<#else>${(spring.status.value.width*maxSize/spring.status.value.height)?round?c}x${maxSize?c}</#if>)
</#if>
<input type="radio" name="whichImageForIV" value="${spring.status.value.realFilename}"<#if spring.status.value.realFilename = whichImageForIV> checked="checked"</#if>/> Original (${spring.status.value.width?c}x${spring.status.value.height?c})

</td>
<td>

<#if spring.status.errorMessages?has_content>
<div class="errorMessage">
<#list spring.status.errorMessages as error>
<p>${error}</p>
</#list>
</div>
</#if>
</td>
</tr>
<tr>
<td colspan="3">
<input name="submit" type="submit" value="Save" />
<input name="reset" type="reset" value="Reset" />
</td>
</tr>

</table>
</form>

