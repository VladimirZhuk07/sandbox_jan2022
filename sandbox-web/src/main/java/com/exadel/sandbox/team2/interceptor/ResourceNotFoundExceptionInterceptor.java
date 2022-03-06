package com.exadel.sandbox.team2.interceptor;

import com.exadel.sandbox.team2.exception.ResourceNotFoundException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Aspect
@Component
public class ResourceNotFoundExceptionInterceptor {

    @AfterThrowing(value = "execution(* com.exadel.sandbox.team2.controller.*.*(..))",
            throwing = "e")
    public void errorInterceptorForWebRestControllers(JoinPoint thisJoinPoint, Throwable e) {
        if (e instanceof EntityNotFoundException) {
            throw new ResourceNotFoundException("Resource was not found: "
                    + thisJoinPoint.getTarget().getClass());
        }
    }
}
