package com.revature.models;

import java.util.Objects;

public class Role {

	private int roleId;
	private String roleName;
	
	public Role(int id) {
		roleId = id;
		
		switch(id) {
		case 1:
			roleName = "ADMIN";
			break;
		case 2:
			roleName = "DEV";
			break;
		case 3:
			roleName = "USER";
			break;
		default:
			roleName = "LOCKED";
		}
	}
	
	public Role(String name) {
		roleName = name;
		
		switch(name.toUpperCase()) {
		case "ADMIN":
			roleId = 1;
			break;
		case "DEV":
			roleId = 2;
			break;
		case "USER":
			roleId = 3;
			break;
		default:
			roleId = 4;	
		}
	}
	
	public int getRoleId() {
		return roleId;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(roleId, roleName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Role))
			return false;
		Role other = (Role) obj;
		return roleId == other.roleId && Objects.equals(roleName, other.roleName);
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleName=" + roleName + "]";
	}
	
}
