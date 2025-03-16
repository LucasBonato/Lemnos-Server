package com.lemnos.server.configurations.security;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FireBaseConfiguration {
    @Value("${firebase.credentials.service.account.key}")
    private String credentials;

    @Bean
    public FirebaseApp initialize() throws IOException {
        if(credentials == null) {
            throw new BaseException(HttpStatus.NOT_FOUND, new ExceptionResponse(0, "Não possui as credenciais"));
        }

        InputStream serviceAccount = new ByteArrayInputStream(credentials.getBytes());

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.initializeApp(options);
        }
        return FirebaseApp.getInstance();
    }
}
