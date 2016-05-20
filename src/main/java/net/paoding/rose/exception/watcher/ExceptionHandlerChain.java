package net.paoding.rose.exception.watcher;
/**
 * Daniel Abel
 * weili209072
 * 2016/1/28.
 */
public interface ExceptionHandlerChain {

	Throwable getException();

	void setNewException(final Throwable newException);

	boolean isExceptionHandled();

	void setExceptionHandled(final boolean exceptionHandled);

	void proceed();
	Object getArgument();

}