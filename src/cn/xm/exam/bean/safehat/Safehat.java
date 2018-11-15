package cn.xm.exam.bean.safehat;

public class Safehat {
	private String id;

	/**
	 * 编号(前缀+数字)
	 */
	private String safehatnum;

	private String creatorfullname;

	private String creatorusername;

	private String creatordepartid;

	/**
	 * 短委员工ID
	 */
	private String userhaulempid;

	/**
	 * 使用者身份证号
	 */
	private String useridcard;

	/**
	 * 是否删除:1是 0否
	 */
	private String isdelete;

	/**
	 * 变更日志(包含创建信息、修改信息以及更换信息)
	 */
	private String changelog;

	/**
	 * 标记是否删除
	 */
	private boolean isDeleted;

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

	public String getUserhaulempid() {
		return userhaulempid;
	}

	public void setUserhaulempid(String userhaulempid) {
		this.userhaulempid = userhaulempid == null ? null : userhaulempid.trim();
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