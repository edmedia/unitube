
<#if obj??>
      <div>
      <div>ID</div>
      <div>${obj.id!?string?html}</div>
      </div>
      <div>
      <div>Message</div>
      <div>${obj.msg!?string?html}</div>
      </div>
      <div>
      <div>Message Time</div>
      <div>${obj.msgTime!?string?html}</div>
      </div>
      <div>
      <div>Comment Credits</div>
      <div>${obj.credits!?string?html}</div>
      </div>
      <div>
      <div>Media</div>
      <div>${obj.media!?string?html}</div>
      </div>
      <div>
      <div>Author</div>
      <div>${obj.author!?string?html}</div>
      </div>
      <div>
      <div>Parent Comment</div>
      <div>${obj.comment!?string?html}</div>
      </div>
      <div>
      <div>Child Comments</div>
      <div>
      <#list obj.childComments as comment>
        ${comment} <br/>
      </#list>
      </div>
      </div>
<#else>
no object found.
</#if>