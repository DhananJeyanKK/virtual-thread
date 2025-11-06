package org.scopedvalue.sec11;

public record SecurityContext(Integer userId, UserRole userRole) {

    public boolean hasPermission(UserRole requiredRole){
        return this.userRole.ordinal() <= requiredRole.ordinal();
    }
}
