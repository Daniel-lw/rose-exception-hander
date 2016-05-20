package net.paoding.rose.exception.watcher.impl;


import net.paoding.rose.exception.watcher.ExceptionHandler;
import net.paoding.rose.exception.watcher.ExceptionHandlerChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Daniel Abel
 * weili209072
 * 2016/1/28.
 */
final class ExceptionDAOLoggingHandler implements ExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(ExceptionDAOLoggingHandler.class);

    @Override
    public final void handle(final ExceptionHandlerChain chain) {
        System.out.println("记录日志：" + chain.getException());
        chain.getException().printStackTrace();
        chain.proceed();
    }

}
