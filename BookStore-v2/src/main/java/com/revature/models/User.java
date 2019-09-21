package com.revature.models;

import java.util.Objects;

/*
 * POJO Design Pattern
 * 
 *         POJO = Plain Old Java Object
 *             - Synonyms: model, bean
 * 
 *         Characteristics of the design pattern:
 *             - proper encapsulation of instance fields
 *             - external access/manipulation to private fields is done through accessor and mutator methods
 *             - various constructors for flexible class construction
 *             - overridden Object methods: .hashCode(), .equals(Object o), and .toString()
 *         
 *         Note:
 *             - This is a class that only defines the User itself, application behaviors that a User may
 *               perform on our application are not included here (such as login/logout). These are behaviors
 *               which we will write into the flow of our application. This User class is simply a static 
 *               template for which we can create User objects, it is NOT responsible for the logic of dynamically
 *               manipulating the state of the application.
 */
public class User {

	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private Role role;
	
	public User() {
		super();
	}
	
	public User(String username, String password, String firstName, String lastName, Role role) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
	}

	public User(int id, String username, String password, String firstName, String lastName, Role role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, id, lastName, password, role, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		return Objects.equals(firstName, other.firstName) && id == other.id && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password) && Objects.equals(role, other.role)
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", role=" + role + "]";
	}
	
}
