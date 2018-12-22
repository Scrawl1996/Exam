package cn.xm.exam.bean.safehat;

import org.apache.commons.lang3.StringUtils;

public class SafehatIn {
	private String id;

	private String safehatnum;

	private String creatorfullname;

	private String creatorusername;

	private String creatordepartid;

	private String userempid;

	private String useridcard;

	private String isdelete;

	private String changelog;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getSafehatnum() {
		return safehatnum;
	}

	public void setSafehatnum(String safehatnum) {
		this.safehatnum = safehatnum == null ? null : safehatnum.trim();
	}

	public String getCreatorfullname() {
		return creatorfullname;
	}

	public void setCreatorfullname(String creatorfullname) {
		this.creatorfullname = creatorfullname == null ? null : creatorfullname.trim();
	}

	public String getCreatorusername() {
		return creatorusername;
	}

	public void setCreatorusername(String creatorusername) {
		this.creatorusername = creatorusername == null ? null : creatorusername.trim();
	}

	public String getCreatordepartid() {
		return creatordepartid;
	}

	public void setCreatordepartid(String creatordepartid) {
		this.creatordepartid = creatordepartid == null ? null : creatordepartid.trim();
	}

	public String getUserempid() {
		return userempid;
	}

	public void setUserempid(String userempid) {
		this.userempid = userempid == null ? null : userempid.trim();
	}

	public String getUseridcard() {
		return useridcard;
	}

	public void setUseridcard(String useridcard) {
		this.useridcard = useridcard == null ? null : useridcard.trim();
	}

	public String getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete == null ? null : isdelete.trim();
	}

	public String getChangelog() {
		return changelog;
	}

	public void appendChangeLog(String changelog) {
		if (StringUtils.isBlank(this.changelog)) {
			setChangelog(changelog);
		} else {
			this.changelog += "," + changelog;
		}
	}

	public void setChangelog(String changelog) {
		this.changelog = changelog == null ? null : changelog.trim();
	}

	public boolean isDeleted() {
		if ("1".equals(isdelete)) {
			return true;
		}
		return false;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.setIsdelete(isDeleted ? "1" : "0");
	}
}