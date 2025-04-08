package com.example.batch.aspect;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TenantServiceAspect {

    @PersistenceContext
    public EntityManager entityManager;

    @Pointcut("execution(* org.springframework.data.jpa.repository.JpaRepository.*(..))")
    void isRepository() {
    }

    @Pointcut("execution(public * com.example..*.repository..*(..))")
    void isCustomRepository() {
    }

    @Before("(isCustomRepository() || isRepository())")
    public void beforeExecution(JoinPoint joinPoint) {
        Filter filter = entityManager.unwrap(Session.class)
                .enableFilter("companyFilter")
                .setParameter("companyId", 1l);
        filter.validate();
    }

}
