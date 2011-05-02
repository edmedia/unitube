
<#if obj??>
      <div>
      <div>ID</div>
      <div>${obj.id!?string?html}</div>
      </div>
      <div>
      <div>Annotation Name</div>
      <div>${obj.annotName!?string?html}</div>
      </div>
      <div>
      <div>Annotation Description</div>
      <div>${obj.description!?string?html}</div>
      </div>
      <div>
      <div>Annotation File</div>
      <div>${obj.annotFile!?string?html}</div>
      </div>
      <div>
      <div>Annotation Time</div>
      <div>${obj.annotTime!?string?html}</div>
      </div>
      <div>
      <div>Random Code</div>
      <div>${obj.randomCode!?string?html}</div>
      </div>
      <div>
      <div>Media</div>
      <div>${obj.media!?string?html}</div>
      </div>
      <div>
      <div>Author</div>
      <div>${obj.author!?string?html}</div>
      </div>
<#else>
no object found.
</#if>