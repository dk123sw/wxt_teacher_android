package cn.xiaocool.wxtteacher.bean;


import cn.xiaocool.wxtteacher.bean.bean.TreeNodeId;
import cn.xiaocool.wxtteacher.bean.bean.TreeNodeLabel;
import cn.xiaocool.wxtteacher.bean.bean.TreeNodePid;

public class FileBean
{
	@TreeNodeId
	private int _id;
	@TreeNodePid
	private int parentId;
	@TreeNodeLabel
	private String name;
	private long length;
	private String avatar;
	private String telephone;

	public FileBean(int _id, int parentId, String name,String avatar,String telephone)
	{
		super();
		this._id = _id;
		this.parentId = parentId;
		this.name = name;
		this.avatar = avatar;
		this.telephone = telephone;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}
