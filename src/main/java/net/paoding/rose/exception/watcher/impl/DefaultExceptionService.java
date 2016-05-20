package net.paoding.rose.exception.watcher.impl;



import net.paoding.rose.exception.watcher.ExceptionHandleResult;
import net.paoding.rose.exception.watcher.ExceptionHandler;
import net.paoding.rose.exception.watcher.ExceptionService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Daniel Abel
 * weili209072
 * 2016/1/28.
 */

@Component
public final class DefaultExceptionService implements ExceptionService {

    private final ArrayList<ExceptionHandler> handlers = new ArrayList<ExceptionHandler>();

    @Override
    public final ExceptionHandleResult handle(final Throwable exception, final Object argument) {
        DefaultExceptionHandlerChain chain = new DefaultExceptionHandlerChain(
                this.getPolicyHandlers(), exception,argument);
        chain.proceed();

        return chain.createExceptionHandleResult();
    }

    public final ExceptionService registerHandler(final ExceptionHandler handler) {
        this.getPolicyHandlers().add(handler);

        return this;
    }

    private final ArrayList<ExceptionHandler> getPolicyHandlers() {


        return this.handlers;
    }


}
