package com.example.Lab5AppDev.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.example.Lab5AppDev.service.HouseholdService.*(..))")
    public void logBeforeServiceMethods() {
        System.out.println("Executing a HouseholdService method...");
    }
}
