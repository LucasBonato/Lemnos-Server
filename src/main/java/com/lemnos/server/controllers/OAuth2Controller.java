package com.lemnos.server.controllers;

import com.lemnos.server.models.dtos.responses.auth.LoginReponse;
import com.lemnos.server.services.OAuth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/")
public class OAuth2Controller {
    @Autowired private OAuth2Service oAuth2Service;

    @GetMapping
    public ResponseEntity<LoginReponse> google(String token) {
        return oAuth2Service.login();
    }
}
