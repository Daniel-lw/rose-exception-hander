package net.paoding.rose.exception.watcher.impl;


import net.paoding.rose.exception.core.ErrorHanderProxy;
import net.paoding.rose.exception.util.MailSendHelper;
import net.paoding.rose.exception.watcher.ExceptionHandler;
import net.paoding.rose.exception.watcher.ExceptionHandlerChain;
import net.paoding.rose.web.Invocation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * Daniel Abel
 * weili209072
 * 2016/1/28.
 */
public final class ExceptionEmailNotificationHandler implements ExceptionHandler {


    @Autowired
    private MailSendHelper mailSendHelper;


    @Override
    public final void handle(final ExceptionHandlerChain chain) {
        Object argument = chain.getArgument();

        Map<String, Object> model = new HashMap<String, Object>();
        if (argument instanceof Invocation) {
            Invocation inv = (Invocation) argument;
            HttpServletRequest request = inv.getRequest();
            Map<String, String> paramMap = toparamMap(request.getParameterMap());
            model.put("paramMap", paramMap);
            model.put("requestURI", request.getRequestURI());
        }
        model.put("exception", getDetailExceptionMessage(chain.getException()));

        mailSendHelper.sendMail("mail/expectionWarning.vm", model, "监控系统异常", ErrorHanderProxy.getReceiver());
        chain.setExceptionHandled(true);
        chain.proceed();
    }

    private Map<String, String> toparamMap(Map<String, String[]> map) {
        if (map == null) {
            return Collections.emptyMap();
        }
        Map<String, String> result = new HashMap<String, String>();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            String value;
            if (StringUtils.equals(entry.getKey(), "password")) {
                value = "******";
            } else {
                value = entry.getValue()[0];
            }
            result.put(entry.getKey(), value);
        }
        return result;
    }

    private String getDetailExceptionMessage(Throwable ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }
}
