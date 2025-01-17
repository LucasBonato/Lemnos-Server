package com.lemnos.server.models.enums;

import org.springframework.beans.factory.annotation.Value;

public class AdminEmails {
    @Value("${jwt.spring.email.admin1}")
    public static String firstEmail;

    @Value("${jwt.spring.email.admin2}")
    public static String secondEmail;

    @Value("${jwt.spring.email.admin3}")
    public static String thirdEmail;
}
