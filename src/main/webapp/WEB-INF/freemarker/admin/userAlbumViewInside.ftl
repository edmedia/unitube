
<#if obj??>
      <div>
      <div>ID</div>
      <div>${obj.id!?string?html}</div>
      </div>
      <div>
      <div>User</div>
      <div>${obj.user!?string?html}</div>
      </div>
      <div>
      <div>Album</div>
      <div>${obj.album!?string?html}</div>
      </div>
<#else>
no object found.
</#if>