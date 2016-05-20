package net.paoding.rose.exception.core;


import com.alibaba.fastjson.JSONObject;
import net.paoding.rose.exception.watcher.ExceptionService;
import net.paoding.rose.exception.watcher.impl.DefaultExceptionService;
import net.paoding.rose.exception.watcher.springframework.ExceptionHandlerDefination;
import net.paoding.rose.exception.watcher.springframework.HandleException;
import net.paoding.rose.web.ControllerErrorHandler;
import net.paoding.rose.web.Invocation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Daniel Abel
 * weili209072
 * 2016/3/10.
 */
public abstract class ErrorHanderProxy implements ControllerErrorHandler {


    private final ExceptionService exceptionService = new DefaultExceptionService();
    private static List<String> emailTos = new ArrayList<String>();

    public void setEamilTos() {
        List<String> receiver = initReceiver();
        if (CollectionUtils.isNotEmpty(receiver)) {
            emailTos = receiver;
        }
    }

    public abstract List<String> initReceiver();


    public static List<String> getReceiver() {
        return emailTos;
    }

    @Override
    public Object onError(Invocation inv, Throwable ex) throws Throwable {
        if (CollectionUtils.isEmpty(emailTos)) {
            setEamilTos();
        }
        HandleException handleException = inv.getAnnotation(HandleException.class);
        if (handleException == null) {
            throw ex;
        } else {
            exceptionService.handle(ex, inv);
        }
        JSONObject json = new JSONObject();
        json.put("errorCode", "9999");
        json.put("errorMessage", "系统错误");
        WebUtils.clearErrorRequestAttributes(inv.getRequest());
        return "@" + json.toString();
    }

    @Autowired
    private void setDefinations(List<ExceptionHandlerDefination> definations) {
        if (definations == null) {
            return;
        }

        for (ExceptionHandlerDefination defination : definations) {
            this.exceptionService.registerHandler(defination.getHandler());
        }
    }

    public static void addReceiver(List<String> emails) {


    }
}
