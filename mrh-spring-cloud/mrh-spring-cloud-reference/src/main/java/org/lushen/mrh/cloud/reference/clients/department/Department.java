package org.lushen.mrh.cloud.reference.clients.department;

public class Department {
	
	private long id;
	
	private String name;
	
	private long orgId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Department [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", orgId=");
		builder.append(orgId);
		builder.append("]");
		return builder.toString();
	}

}
