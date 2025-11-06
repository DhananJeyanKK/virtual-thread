package org.threadlocal.sec10.security.theadlocal;

import org.threadlocal.sec10.security.SecurityContext;
import org.threadlocal.sec10.security.UserRole;

public class SecurityContextHolder {

    private static final SecurityContext ANONYMOUS_CONTEXT = new SecurityContext(0, UserRole.GUEST);

    private static final ThreadLocal<SecurityContext> contextHolder = ThreadLocal.withInitial(()->ANONYMOUS_CONTEXT);

    static void setContext(SecurityContext securityContext){
        contextHolder.set(securityContext);
    }

    static void clear(){
        contextHolder.remove();
    }

    public static SecurityContext getContext(){
        return contextHolder.get();
    }


}
