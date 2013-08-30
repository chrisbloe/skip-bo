package com.custardcoding.skipbo.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 *
 * @author Custard
 */
@Aspect
@Component
public class CurrentPlayerCheck {
    
    @Pointcut("execution(* com.custardcoding.skipbo.controller.*.*(..)) && args(gameId,..)")
    public void businessMethods(Long gameId) { }
 
    @Before("businessMethods(gameId)")
    public void before(Long gameId) {
        System.out.println("Game id: " + gameId);
    }
}
