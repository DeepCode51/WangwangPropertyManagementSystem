package com.kum.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

@Component
@Log4j2
public class MailUtils {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @Value("${spring.mail.username}")
//    private String from;
//
//    /**
//     * 发送邮件
//     *
//     * @param to      目标
//     * @param subject 标题
//     * @param context 内容
//     */
//    @Async
//    public void sendMail(String to, String subject, String context) {
//        try {
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message);
//            helper.setFrom(from);
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(context, false);
//            mailSender.send(helper.getMimeMessage());
//        } catch (Exception e) {
//            log.error("发送邮件失败：" + e.getMessage());
//        }
//
//    }
//

}