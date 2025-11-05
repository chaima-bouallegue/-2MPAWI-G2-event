package tn.esprit.eventsproject.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class PerformanceAspect {

    // ✅ Pointcut pour toutes les classes du package services
    @Pointcut("execution(* tn.esprit.eventsproject.services.*.*(..))")
    public void serviceMethods() {}

    @Around("serviceMethods()")
    public Object profileExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed(); // exécution de la méthode

        long elapsedTime = System.currentTimeMillis() - startTime;

        log.info("⏱ PERFORMANCE | {} executed in {} ms",
                joinPoint.getSignature().toShortString(),
                elapsedTime);

        return result;
    }
}
