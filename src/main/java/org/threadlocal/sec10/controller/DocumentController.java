package org.threadlocal.sec10.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threadlocal.sec10.security.SecurityContext;
import org.threadlocal.sec10.security.UserRole;
import org.threadlocal.sec10.security.theadlocal.AuthenticationService;

import java.util.function.Supplier;

public class DocumentController {

    private static final Logger log = LoggerFactory.getLogger(DocumentController.class);

    private final Supplier<SecurityContext> securityContextSupplier;

    public DocumentController(Supplier<SecurityContext> securityContextSupplier){
        this.securityContextSupplier = securityContextSupplier;
    }

    public void read(){
        this.validateUserRoles(UserRole.VIEWER);

    }

    public void edit(){
        this.validateUserRoles(UserRole.EDITOR);

    }

    public void delete(){
        this.validateUserRoles(UserRole.ADMIN);

    }

    private void validateUserRoles(UserRole requiredRole){
        var securityContext = this.securityContextSupplier.get();
        if(!securityContext.hasPermission(requiredRole)){
            log.error("user {} does not have {} permission", securityContext.userId(), securityContext.userRole());
            throw  new SecurityException("Unahtorized access, required role" + requiredRole);
        }

    }

}
