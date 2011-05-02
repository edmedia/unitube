
<#if obj??>
      <div>
      <div>ID</div>
      <div>${obj.id!?string?html}</div>
      </div>
      <div>
      <div>Title</div>
      <div>${obj.title!?string?html}</div>
      </div>
      <div>
      <div>Description</div>
      <div>${obj.description!?string?html}</div>
      </div>
      <div>
      <div>Upload File</div>
      <div>${obj.uploadFile!?string?html}</div>
      </div>
      <div>
      <div>Real File Name</div>
      <div>${obj.realFilename!?string?html}</div>
      </div>
      <div>
      <div>Thumbnail</div>
      <div>${obj.thumbnail!?string?html}</div>
      </div>
      <div>
      <div>Media Type(1: Other, 5: Image, 10: Audio, 20: Video)</div>
      <div>${obj.mediaType!?string?html}</div>
      </div>
      <div>
      <div>convert to a different format</div>
      <div>${obj.convertTo!?string?html}</div>
      </div>
      <div>
      <div>File name of other format</div>
      <div>${obj.otherFormatFilename!?string?html}</div>
      </div>
      <div>
      <div>Width</div>
      <div>${obj.width!?string?html}</div>
      </div>
      <div>
      <div>Height</div>
      <div>${obj.height!?string?html}</div>
      </div>
      <div>
      <div>Video or Audio Duration (in ms), Or image number</div>
      <div>${obj.duration!?string?html}</div>
      </div>
      <div>
      <div>Snapshot Position</div>
      <div>${obj.snapshotPosition!?string?html}</div>
      </div>
      <div>
      <div>Tags</div>
      <div>${obj.tags!?string?html}</div>
      </div>
      <div>
      <div>Status(0:waiting, 1:processing, 2:finished, 9:unrecognized)</div>
      <div>${obj.status!?string?html}</div>
      </div>
      <div>
      <div>Put this media on other server?</div>
      <div>${obj.isOnOtherServer!?string?html}</div>
      </div>
      <div>
      <div>access type(0: public, 10: hidden, 20: private)</div>
      <div>${obj.accessType!?string?html}</div>
      </div>
      <div>
      <div>Upload via Email?</div>
      <div>${obj.viaEmail!?string?html}</div>
      </div>
      <div>
      <div>Upload via MMS?</div>
      <div>${obj.viaMMS!?string?html}</div>
      </div>
      <div>
      <div>Upload Only?</div>
      <div>${obj.uploadOnly!?string?html}</div>
      </div>
      <div>
      <div>Random Code</div>
      <div>${obj.randomCode!?string?html}</div>
      </div>
      <div>
      <div>Location Code(where to put media file)</div>
      <div>${obj.locationCode!?string?html}</div>
      </div>
      <div>
      <div>Upload Time</div>
      <div>${obj.uploadTime!?string?html}</div>
      </div>
      <div>
      <div>Access Times</div>
      <div>${obj.accessTimes!?string?html}</div>
      </div>
      <div>
      <div>Process Times</div>
      <div>${obj.processTimes!?string?html}</div>
      </div>
      <div>
      <div>User</div>
      <div>${obj.user!?string?html}</div>
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
      <div>All User Comments</div>
      <div>
      <#list obj.comments as comment>
        ${comment} <br/>
      </#list>
      </div>
      </div>
      <div>
      <div>All User Annotations</div>
      <div>
      <#list obj.annotations as annotation>
        ${annotation} <br/>
      </#list>
      </div>
      </div>
      <div>
      <div>Access Rules</div>
      <div>
      <#list obj.accessRules as accessRule>
        ${accessRule} <br/>
      </#list>
      </div>
      </div>
<#else>
no object found.
</#if>