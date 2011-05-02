
<#if obj??>
      <div>
      <div>ID</div>
      <div>${obj.id!?string?html}</div>
      </div>
      <div>
      <div>User Name</div>
      <div>${obj.userName!?string?html}</div>
      </div>
      <div>
      <div>Pass Word</div>
      <div>${obj.passWord!?string?html}</div>
      </div>
      <div>
      <div>Where Are You From</div>
      <div>${obj.wayf!?string?html}</div>
      </div>
      <div>
      <div>First Name</div>
      <div>${obj.firstName!?string?html}</div>
      </div>
      <div>
      <div>Last Name</div>
      <div>${obj.lastName!?string?html}</div>
      </div>
      <div>
      <div>Email Address</div>
      <div>${obj.email!?string?html}</div>
      </div>
      <div>
      <div>Mobile Number</div>
      <div>${obj.mobile!?string?html}</div>
      </div>
      <div>
      <div>Default Upload Access Type</div>
      <div>${obj.uploadAccessType!?string?html}</div>
      </div>
      <div>
      <div>Default Email Upload Access Type</div>
      <div>${obj.emailUploadAccessType!?string?html}</div>
      </div>
      <div>
      <div>Login Times</div>
      <div>${obj.loginTimes!?string?html}</div>
      </div>
      <div>
      <div>First Login Time</div>
      <div>${obj.firstLoginTime!?string?html}</div>
      </div>
      <div>
      <div>Last Login Time</div>
      <div>${obj.lastLoginTime!?string?html}</div>
      </div>
      <div>
      <div>Last Login IP</div>
      <div>${obj.lastLoginIP!?string?html}</div>
      </div>
      <div>
      <div>Online Time(ms)</div>
      <div>${obj.onlineTime!?string?html}</div>
      </div>
      <div>
      <div>Is this user a guest?</div>
      <div>${obj.isGuest!?string?html}</div>
      </div>
      <div>
      <div>Disabled access?</div>
      <div>${obj.disabled!?string?html}</div>
      </div>
      <div>
      <div>Random Code</div>
      <div>${obj.randomCode!?string?html}</div>
      </div>
      <div>
      <div>All Medias</div>
      <div>
      <#list obj.medias as media>
        ${media} <br/>
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