package org.scopedvalue.sec11;

public class SecurityContextHolder {

    private static final SecurityContext ANONYMOUS_CONTEXT = new SecurityContext(0, UserRole.GUEST);

    private static final ScopedValue<SecurityContext> CONTEXT = ScopedValue.newInstance();

    static ScopedValue<SecurityContext> getScopedValue(){
        return CONTEXT;
    }

    public static SecurityContext getContext(){
        return CONTEXT.orElse(ANONYMOUS_CONTEXT);
    }


}
