
<#if obj??>
      <div>
      <div>ID</div>
      <div>${obj.id!?string?html}</div>
      </div>
      <div>
      <div>Album Name</div>
      <div>${obj.albumName!?string?html}</div>
      </div>
      <div>
      <div>Album Description</div>
      <div>${obj.description!?string?html}</div>
      </div>
      <div>
      <div>Random Code</div>
      <div>${obj.randomCode!?string?html}</div>
      </div>
      <div>
      <div>access type(0: public, 10: hidden, 20: private)</div>
      <div>${obj.accessType!?string?html}</div>
      </div>
      <div>
      <div>Owner of this album</div>
      <div>${obj.owner!?string?html}</div>
      </div>
      <div>
      <div>All AlbumMedias</div>
      <div>
      <#list obj.albumMedias as albumMedia>
        ${albumMedia} <br/>
      </#list>
      </div>
      </div>
      <div>
      <div>All UserAlbums</div>
      <div>
      <#list obj.userAlbums as userAlbum>
        ${userAlbum} <br/>
      </#list>
      </div>
      </div>
<#else>
no object found.
</#if>