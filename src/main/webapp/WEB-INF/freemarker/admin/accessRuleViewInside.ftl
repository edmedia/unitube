
<#if obj??>
      <div>
      <div>ID</div>
      <div>${obj.id!?string?html}</div>
      </div>
      <div>
      <div>Media</div>
      <div>${obj.media!?string?html}</div>
      </div>
      <div>
      <div>User</div>
      <div>${obj.user!?string?html}</div>
      </div>
      <div>
      <div>Group Name</div>
      <div>${obj.groupName!?string?html}</div>
      </div>
      <div>
      <div>Group Users Link</div>
      <div>${obj.groupUsersLink!?string?html}</div>
      </div>
      <div>
      <div>User Input</div>
      <div>${obj.userInput!?string?html}</div>
      </div>
<#else>
no object found.
</#if>