package com.revature.models;

import java.util.Objects;

public class Principal {
	
	private int id;
	private String username;
	private String role;
	
	public Principal() {
		super();
	}

	public Principal(int id, String username, String role) {
		super();
		this.id = id;
		this.username = username;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, role, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Principal))
			return false;
		Principal other = (Principal) obj;
		return id == other.id && Objects.equals(role, other.role) && Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "Principal [id=" + id + ", username=" + username + ", role=" + role + "]";
	}

}
