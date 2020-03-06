package model;

public class PO {
	private String id;
	private int total;
	private String owner;
	private String status;
	private String freightId;
	private String freightName;
	private String createUser;
	private String createTime;
	private String updateUser;
	private String updateTime;
	private PODetail[] poDetail;
	
	
	public PODetail[] getPoDetail() {
		return poDetail;
	}
	public void setPoDetail(PODetail[] poDetail) {
		this.poDetail = poDetail;
	}
	public String getFreightId() {
		return freightId;
	}
	public void setFreightId(String freightId) {
		this.freightId = freightId;
	}
	public String getFreightName() {
		return freightName;
	}
	public void setFreightName(String freightName) {
		this.freightName = freightName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
