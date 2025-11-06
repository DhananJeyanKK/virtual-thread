package org.threadlocal.sec10.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threadlocal.sec10.security.theadlocal.AuthenticationService;
import org.threadlocal.sec10.security.theadlocal.SecurityContextHolder;

public class DocumentAccessWithThreadLocal {

    private static final Logger log = LoggerFactory.getLogger(DocumentAccessWithThreadLocal.class);

    public static final DocumentController documentController = new DocumentController(SecurityContextHolder::getContext);

    private static void documentAccessWorkFlow(Integer userId, String password){
        AuthenticationService.loginAndExeucte(userId, password, ()-> {
            documentController.read();
            documentController.edit();
            documentController.delete();
        });
    }

    static void main() throws InterruptedException {
        //documentAccessWorkFlow(1, "password");
        Thread.ofVirtual().name("admin").start(()->documentAccessWorkFlow(1, "password"));
        Thread.ofVirtual().name("editor").start(()->documentAccessWorkFlow(2, "password"));
        Thread.sleep(5);
    }


}
