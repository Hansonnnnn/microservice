package cn.edu.nju.user.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * https://blog.csdn.net/qq_23167527/article/details/78623434
 */
@Aspect
@Component
public class PerformanceInterceptor {
    private final Logger LOGGER = LoggerFactory.getLogger(PerformanceInterceptor.class);

    @Pointcut("execution(* cn.edu.nju.user.util.UserGenerator.generateUserRest(..))")
    public void generateUser(){}

    @Around("generateUser()")
    public boolean timeStatistic(ProceedingJoinPoint pjp) {
        String methodName = pjp.getSignature().getName();
        try {
            long start = System.currentTimeMillis();
            boolean result = (Boolean) pjp.proceed();
            long end = System.currentTimeMillis();
            LOGGER.info(String.format("method[%s] execution time: %d milliseconds.", methodName, (end - start)));
            System.out.println(String.format("method[%s] execution time: %d milliseconds.", methodName, (end - start)));
            return result;
        } catch (Throwable e) {
            LOGGER.error(String.format("method[%s] execution error.", methodName));
            return false;
        }

    }
}
