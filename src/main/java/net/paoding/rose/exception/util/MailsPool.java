package net.paoding.rose.exception.util;

import com.alibaba.fastjson.JSON;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Daniel Abel
 * weili209072
 * 2015/12/24.
 */
@Component
public class MailsPool {

    ThreadPoolExecutor mailPool = new ThreadPoolExecutor(10, 50, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20), new ThreadPoolExecutor.DiscardOldestPolicy());
    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private VelocityEngine velocityEngine;


    public void submit(final BaseMail mail) {

        mailPool.execute(new Runnable() {
            @Override
            public void run() {
                if (mail != null) {
                    try {
                        logger.info("after settle mail poll send mail-->tos" + JSON.toJSONString(mail.getTos()) + "|mail title:" + mail.getTitle());
                        MailUtil.sendMail(mail.getTitle(), mail.getContent(), mail.getTos());
                    } catch (UnsupportedEncodingException e) {
                        logger.error("send Mail" + "" + e);
                    }

                }
            }
        });
    }

    public void sumbitVelocity(final String template, final Map<String, Object> model, final String title, final List<String> tos) {
        mailPool.execute(new Runnable() {
            @Override
            public void run() {
                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, "UTF-8", model);
                try {
                    MailUtil.sendMail(title, text, tos);
                } catch (UnsupportedEncodingException e) {
                    logger.error("send Mail" + "" + e);
                }
            }
        });
    }

}
