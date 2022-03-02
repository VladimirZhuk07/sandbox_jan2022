package com.exadel.sandbox.team2.interceptor;

import com.exadel.sandbox.team2.exception.ResourceNotFoundException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Aspect
@Component
public class ResourceNotFoundExceptionInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceNotFoundExceptionInterceptor.class);


    @AfterThrowing(value = "execution(* *(..))", throwing = "e")
    public void log(JoinPoint thisJoinPoint, Throwable e) {
        System.out.println(thisJoinPoint + " ---------------> " + e);
    }

  /* Not Working
    @AfterThrowing(pointcut = "execution(* com.exadel.sandbox.team2.controller.*(..))",
            throwing = "e")
    public void errorInterceptor(Exception e) {
        if (e instanceof EntityNotFoundException) {
            throw new ResourceNotFoundException("Resource was not found");
        }
    }*/

    //TODO протестировать use presit and annotation
}
