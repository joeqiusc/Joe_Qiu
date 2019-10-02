package com.revature.models;

public class Role {
	private int roleId;
	private String roleName;
	
	public Role(int id) {
		
		roleId = id;
		
		switch(id) {
		
		case 1:
		roleName = "ADMIN";
		
		case 2:
			roleName = "DEV";
			
		case 3:
			roleName = "USER";
			
		case 4:
			roleName = "LOCKED";
		}
		
		}
		
		public Role(String name) {
			
			roleName = name;
			
			switch(name) {
			
			case "ADMIN":
				
			roleId = 1;
			
			case "DEV":
				roleId = 2;
				
			case "USER":
				roleId = 3;
				
			case "LOCKED":
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
			final int prime = 31;
			int result = 1;
			result = prime * result + roleId;
			result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Role other = (Role) obj;
			if (roleId != other.roleId)
				return false;
			if (roleName == null) {
				if (other.roleName != null)
					return false;
			} else if (!roleName.equals(other.roleName))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Role [roleId=" + roleId + ", roleName=" + roleName + "]";
		}
		
		
		
	

}
