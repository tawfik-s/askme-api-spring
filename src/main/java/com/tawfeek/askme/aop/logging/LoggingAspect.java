package com.tawfeek.askme.aop.logging;


import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging execution of service and repository Spring components.
 *
 * By default, it only runs with the "dev" profile.
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
              " || within(@org.springframework.stereotype.Service *)" +
              " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut
    }

    @Pointcut("within(com.tawfeek.askme.repository..*)" +
              " || within(com.tawfeek.askme.service..*)" +
              " || within(com.tawfeek.askme.controller..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut
    }

    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error(
                "Exception in {}() with cause = {} and message = {}",
                joinPoint.getSignature().getName(),
                e.getCause() != null ? e.getCause() : "NULL",
                e.getMessage(),
                e
        );
    }

    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isDebugEnabled()) {
            log.debug("Enter: {}() with argument[s] = {}",
                    joinPoint.getSignature().getName(),
                    Arrays.toString(joinPoint.getArgs()));
        }

        try {
            Object result = joinPoint.proceed();

            if (log.isDebugEnabled()) {
                log.debug("Exit: {}() with result = {}",
                        joinPoint.getSignature().getName(),
                        result);
            }

            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}()",
                    Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getName());
            throw e;
        }
    }
}