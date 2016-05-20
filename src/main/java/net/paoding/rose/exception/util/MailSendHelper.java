package net.paoding.rose.exception.util;


import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Daniel Abel
 * weili209072
 * 2015/12/24.
 */
@Service
public class MailSendHelper {

    @Autowired
    private MailsPool mailsPool;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    public void sendMail(String title, String content, List<String> tos) {
        if (CollectionUtils.isNotEmpty(tos)) {
            mailsPool.submit(new BaseMail(title, content, tos));
        }
    }

    public void sendMail(String template, final Map<String, Object> model, String title, List<String> tos) {
        if (CollectionUtils.isNotEmpty(tos)) {
            mailsPool.sumbitVelocity(template, model, title, tos);
        }
    }
}
