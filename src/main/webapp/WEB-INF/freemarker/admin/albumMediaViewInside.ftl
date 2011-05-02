
<#if obj??>
      <div>
      <div>ID</div>
      <div>${obj.id!?string?html}</div>
      </div>
      <div>
      <div>Album</div>
      <div>${obj.album!?string?html}</div>
      </div>
      <div>
      <div>Media</div>
      <div>${obj.media!?string?html}</div>
      </div>
<#else>
no object found.
</#if>