package com.project.mausam.enums;

import java.util.Set;

public enum UserRole {
	
	USER(Set.of(UserPermission.FETCH, UserPermission.DBOPERATIONS)),
	ADMIN(Set.of(UserPermission.REGISTER ,  UserPermission.FETCH, UserPermission.DBOPERATIONS)),
	GUEST(Set.of(UserPermission.FETCH));
	
	private Set<UserPermission> userPermissions;

	private UserRole(Set<UserPermission> userPermissions) {
		this.userPermissions = userPermissions;
	}

	public Set<UserPermission> getUserPermissions() {
		return userPermissions;
	}
}
