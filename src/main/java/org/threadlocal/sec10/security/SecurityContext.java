package org.threadlocal.sec10.security;

public record SecurityContext(Integer userId, UserRole userRole) {

    public boolean hasPermission(UserRole requiredRole){
        return this.userRole.ordinal() <= requiredRole.ordinal();
    }
}
