package jd.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by eduardom on 10/10/16.
 */
@Configuration
@ComponentScan(basePackages = { "jd.Util" })
@PropertySource("classpath:email.properties")
public class SpringMailConfig {

    @Autowired
    private Environment env;

    @Bean
    public JavaMailSenderImpl javaMailSenderImpl() {

        final JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();

        mailSenderImpl.setHost(env.getProperty("smtp.host"));
        mailSenderImpl.setPort(env.getProperty("smtp.port", Integer.class));
        mailSenderImpl.setProtocol(env.getProperty("smtp.protocol"));
        mailSenderImpl.setUsername(env.getProperty("smtp.username"));
        mailSenderImpl.setPassword(env.getProperty("smtp.password"));

        final Properties javaMailProps = new Properties();

        javaMailProps.put("mail.smtp.auth", true);
        javaMailProps.put("mail.smtp.starttls.enable", true);

        mailSenderImpl.setJavaMailProperties(javaMailProps);

        return mailSenderImpl;
    }



}

