package com.caiy.learn.animation.patch;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AspectSpecControler {

    private static final String TAG = "AspectSpecControler";

    @Pointcut(value = "execution(* com.caiy.learn.animation..*.*(..)) && !execution(* com.caiy.learn.animation.patch..*.*(..))")
    private void learnPointcut() {
    }

    @Around(value = "learnPointcut()")
    public Object weaveApp(ProceedingJoinPoint joinPoint) throws Throwable {
        return weavePatchLogic(joinPoint);
    }

    public Object weavePatchLogic(ProceedingJoinPoint joinPoint) throws Throwable {

        long startT = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();

        long consume = System.currentTimeMillis() - startT;
        Log.i(TAG,joinPoint.getSignature().toString() + "cost time：" + consume);
        return proceed;
    }

}
