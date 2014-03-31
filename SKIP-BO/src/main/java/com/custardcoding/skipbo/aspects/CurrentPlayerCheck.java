package com.custardcoding.skipbo.aspects;

import com.custardcoding.skipbo.service.SynchronizerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger log = LogManager.getLogger(CurrentPlayerCheck.class);
    
    @Autowired
    private SynchronizerService synchronizerService;
    
    @Pointcut("execution(* com.custardcoding.skipbo.controller.*.*(..)) && args(gameId,..)")
    public void businessMethods(Long gameId) { }
 
    @Before("businessMethods(gameId)")
    public void before(Long gameId) {
        log.debug("Game {} request start...", gameId);
        
        try {
            synchronizerService.lock(gameId);
        } catch (InterruptedException ex) {
            synchronizerService.release(gameId);
        }
    }
 
    @After("businessMethods(gameId)")
    public void after(Long gameId) {
        log.debug("...game {} request finish", gameId);
        
        synchronizerService.release(gameId);
    }
}
