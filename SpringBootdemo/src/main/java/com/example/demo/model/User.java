package com.example.demo.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
	private UUID id;
	private String name;
	private int nameLen;

	public User(@JsonProperty("id") UUID id, @JsonProperty("name") String name, @JsonProperty("nameLen") int nameLen) {
		super();
		this.id = id;
		this.name = name;
		this.nameLen = nameLen;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNameLen() {
		return nameLen;
	}

	public void setNameLen(int nameLen) {
		this.nameLen = nameLen;
	}

}
