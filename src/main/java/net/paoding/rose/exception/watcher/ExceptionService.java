package net.paoding.rose.exception.watcher;

/**
 * Daniel Abel
 * weili209072
 * 2016/1/28.
 */
public interface ExceptionService {

    ExceptionHandleResult handle( final Throwable exception, final Object argument);

    ExceptionService registerHandler(final ExceptionHandler handler);

}
