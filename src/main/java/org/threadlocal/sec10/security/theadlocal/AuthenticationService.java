package org.threadlocal.sec10.security.theadlocal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threadlocal.sec10.TheadLocal;
import org.threadlocal.sec10.security.SecurityContext;
import org.threadlocal.sec10.security.UserRole;

import java.util.Map;

public class AuthenticationService {



    public static final String VALID_PASSWORD = "password";

    public static final Map<Integer, UserRole> USER_ROLES = Map.of(1, UserRole.ADMIN, 2, UserRole.EDITOR,
    3, UserRole.VIEWER);

    public static void loginAndExeucte(Integer userId, String password, Runnable runnable){
        if(!VALID_PASSWORD.equals(password)){
            throw new SecurityException("Invalid Password " + userId);
        }

        try{
            var securityContext = new SecurityContext(userId, USER_ROLES.getOrDefault(userId, UserRole.GUEST));
            SecurityContextHolder.setContext(securityContext);
            runnable.run();
        }finally {
            SecurityContextHolder.clear();
        }
    }


}
