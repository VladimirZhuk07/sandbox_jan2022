package com.exadel.sandbox.team2.interceptor;

import com.exadel.sandbox.team2.exception.ResourceNotFoundException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Aspect
@Component
public class ResourceNotFoundExceptionInterceptor {

    @AfterThrowing(pointcut = "execution(* com.exadel.sandbox.team2..*(..))",
            throwing = "e")
    public void errorInterceptor(Exception e) {
        if (e instanceof EntityNotFoundException) {
            throw new ResourceNotFoundException("Resource was not found");
        }
    }

    //TODO протестировать use presit and annotation
}
