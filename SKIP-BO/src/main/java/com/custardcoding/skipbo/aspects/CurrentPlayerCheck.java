package com.custardcoding.skipbo.aspects;

import com.custardcoding.skipbo.service.SynchronizerService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Custard
 */
@Aspect
@Component
public class CurrentPlayerCheck {
    
    @Autowired
    private SynchronizerService synchronizerService;
    
    @Pointcut("execution(* com.custardcoding.skipbo.controller.*.*(..)) && args(gameId,..)")
    public void businessMethods(Long gameId) { }
 
    @Before("businessMethods(gameId)")
    public void before(Long gameId) {
        System.out.println("Before ~ Game id: " + gameId);
        
        try {
            synchronizerService.lock(gameId);
        } catch (InterruptedException ex) {
            synchronizerService.release(gameId);
        }
    }
 
    @After("businessMethods(gameId)")
    public void after(Long gameId) {
        System.out.println("After ~ Game id: " + gameId);
        
        synchronizerService.release(gameId);
    }
}
