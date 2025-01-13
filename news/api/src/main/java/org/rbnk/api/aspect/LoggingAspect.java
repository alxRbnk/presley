package org.rbnk.api.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* org.rbnk.api.controller.NewsController.*(..))")
    public void controllerMethods() {}

    @Before("controllerMethods()")
    public void logRequest() {
        log.info("Request received: {}", Thread.currentThread().getStackTrace()[2]);
    }

    @AfterReturning(value = "controllerMethods()", returning = "result")
    public void logResponse(Object result) {
        log.info("Response: {}", result);
    }

    @AfterThrowing(value = "controllerMethods()", throwing = "exception")
    public void logException(Throwable exception) {
        log.error("Exception: {}", exception.getMessage());
    }
}
