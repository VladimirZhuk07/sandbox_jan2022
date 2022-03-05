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

    @AfterThrowing(value = "execution(* com.exadel.sandbox.team2.controller.UserRestController.*(..))",
            throwing = "e")
    public void errorInterceptorForUserRestController(JoinPoint thisJoinPoint, Throwable e) {
        if (e instanceof EntityNotFoundException) {
            throw new ResourceNotFoundException("Resource was not found: " + thisJoinPoint.getTarget().getClass());
        }
    }

    @AfterThrowing(value = "execution(* com.exadel.sandbox.team2.controller.BookingRestController.*(..))",
            throwing = "e")
    public void errorInterceptorForBookingRestController(JoinPoint thisJoinPoint, Throwable e) {
        if (e instanceof EntityNotFoundException) {
            throw new ResourceNotFoundException("Resource was not found: " + thisJoinPoint.getTarget().getClass());
        }
    }

    @AfterThrowing(value = "execution(* com.exadel.sandbox.team2.controller.CountryRestController.*(..))",
            throwing = "e")
    public void errorInterceptorForCountryRestController(JoinPoint thisJoinPoint, Throwable e) {
        if (e instanceof EntityNotFoundException) {
            throw new ResourceNotFoundException("Resource was not found: " + thisJoinPoint.getTarget().getClass());
        }
    }

    @AfterThrowing(value = "execution(* com.exadel.sandbox.team2.controller.MapRestController.*(..))",
            throwing = "e")
    public void errorInterceptorForMapRestController(JoinPoint thisJoinPoint, Throwable e) {
        if (e instanceof EntityNotFoundException) {
            throw new ResourceNotFoundException("Resource was not found: " + thisJoinPoint.getTarget().getClass());
        }
    }

    @AfterThrowing(value = "execution(* com.exadel.sandbox.team2.controller.OfficeRestController.*(..))",
            throwing = "e")
    public void errorInterceptorForOfficeRestController(JoinPoint thisJoinPoint, Throwable e) {
        if (e instanceof EntityNotFoundException) {
            throw new ResourceNotFoundException("Resource was not found: " + thisJoinPoint.getTarget().getClass());
        }
    }

    @AfterThrowing(value = "execution(* com.exadel.sandbox.team2.controller.WorkplaceRestController.*(..))",
            throwing = "e")
    public void errorInterceptorForWorkplaceRestController(JoinPoint thisJoinPoint, Throwable e) {
        if (e instanceof EntityNotFoundException) {
            throw new ResourceNotFoundException("Resource was not found: " + thisJoinPoint.getTarget().getClass());
        }
    }
}
