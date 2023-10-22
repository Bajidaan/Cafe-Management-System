package com.bajidan.cafe_ms.util;

import com.bajidan.cafe_ms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;

@Service
public class EmailUtil {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text, List<String> list) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("bajidev@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        if(list != null && list.size() > 0) {
            //String[] adminArray = list.toArray(new String[0]);
            //message.setCc(adminArray);
            message.setCc(getCcArray(list));
        }
        emailSender.send(message);

    }

    private String[] getCcArray(List<String> ccList) {
        String[] cc = new String[ccList.size()];
        for(int i = 0; i < ccList.size(); i++) {
            cc[i] = ccList.get(i);
        }
        return cc;
    }


}
