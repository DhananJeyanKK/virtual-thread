package org.scopedvalue.sec11;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.function.Supplier;

public class DocumentController {

    private static final Logger log = LoggerFactory.getLogger(DocumentController.class);

    private final Supplier<SecurityContext> securityContextSupplier;

    public DocumentController(Supplier<SecurityContext> securityContextSupplier){
        this.securityContextSupplier = securityContextSupplier;
    }

    public void read(){
        log.info("Inside read");
        this.validateUserRoles(UserRole.VIEWER);
        log.info("Able to read");

    }

    public void edit(){
        log.info("Inside EDIT");
        this.validateUserRoles(UserRole.EDITOR);
        log.info("able to edit");

    }

    public void delete(){
        log.info("Inside delete");
        this.validateUserRoles(UserRole.ADMIN);
        log.info("able to delete");

    }

    private void validateUserRoles(UserRole requiredRole){
        var securityContext = this.securityContextSupplier.get();
        if(!securityContext.hasPermission(requiredRole)){
            log.error("user {} does not have {} permission", securityContext.userId(), securityContext.userRole());
            throw  new SecurityException("Unahtorized access, required role" + requiredRole);
        }

    }

}
