
<#if obj??>
      <div>
      <div>ID</div>
      <div>${obj.id!?string?html}</div>
      </div>
      <div>
      <div>Actual Width</div>
      <div>${obj.actualWidth!?string?html}</div>
      </div>
      <div>
      <div>Actual Width Unit</div>
      <div>${obj.acturalWidthUnit!?string?html}</div>
      </div>
      <div>
      <div>Minimum Zoom</div>
      <div>${obj.minZoom!?string?html}</div>
      </div>
      <div>
      <div>Maximum Zoom</div>
      <div>${obj.maxZoom!?string?html}</div>
      </div>
      <div>
      <div>Display Measurement tool and grid?</div>
      <div>${obj.displayMeasureTool!?string?html}</div>
      </div>
      <div>
      <div>Other can annotate this image?</div>
      <div>${obj.otherCanAnnotate!?string?html}</div>
      </div>
      <div>
      <div>Which Image for ImageViewer to use?</div>
      <div>${obj.whichImageForIV!?string?html}</div>
      </div>
      <div>
      <div>Media</div>
      <div>${obj.media!?string?html}</div>
      </div>
<#else>
no object found.
</#if>