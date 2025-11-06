package org.scopedvalue.sec11;

import java.util.Map;

public class AuthenticationService {

    public static final String VALID_PASSWORD = "password";

    public static final Map<Integer, UserRole> USER_ROLES = Map.of(1, UserRole.ADMIN, 2, UserRole.EDITOR,
    3, UserRole.VIEWER);

    public static void loginAndExeucte(Integer userId, String password, Runnable runnable){

        if(!VALID_PASSWORD.equals(password)){
            throw new SecurityException("Invalid Password " + userId);
        }

        var securityContext = new SecurityContext(userId, USER_ROLES.getOrDefault(userId, UserRole.GUEST));
        ScopedValue.where(SecurityContextHolder.getScopedValue(), securityContext)
                .run(runnable);
    }



}
