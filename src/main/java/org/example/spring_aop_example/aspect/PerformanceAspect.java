package org.example.spring_aop_example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Component
@Aspect
public class PerformanceAspect {

    private final Logger logger = Logger.getLogger(getClass().getName());

    @Pointcut("within(org.example.spring_aop_example.controller.*)")
    public void pointCut(){}

    private String getMethodName(JoinPoint joinPoint){
        return joinPoint.getSignature().getName();
    }

    @Before("pointCut()")
    public void beforeMethodExecution(JoinPoint joinPoint) {
        logger.info("From "+getClass().getName()+"Begin: "+getMethodName(joinPoint));
    }

    @After("pointCut()")
    public void afterMethodExecution(JoinPoint joinPoint) {
        logger.info("From "+getClass().getName()+"End: "+getMethodName(joinPoint));
    }

    @Around("pointCut()")
    public Object measureRequestTime(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.nanoTime();
        Object result = joinPoint.proceed();
        long end = System.nanoTime();
        String methodName = joinPoint.getSignature().getName();
        logger.info("Execution of "+ methodName + " took "
            + TimeUnit.NANOSECONDS.toMillis(end-start)+" ms"
        );

        return result;
    }
}
