package com.lemnos.server.services;

import com.lemnos.server.models.dtos.responses.auth.LoginReponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class OAuth2Service {

    public ResponseEntity<LoginReponse> login(OAuth2AuthenticationToken token) {
        if(token.getAuthorizedClientRegistrationId().equals("google")){
            //Generate token
        }
        System.out.println(token.getPrincipal().getAttributes());


        return ResponseEntity.ok(new LoginReponse("Ainda não hehehe"));
    }
}
