package com.project.mausam.enums;

public enum UserRole {
	
	USER("User"),
	ADMIN("Admin"),
	GUEST("Guest");
	
	private String roleName;
	
	private UserRole(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}
	
	
}
