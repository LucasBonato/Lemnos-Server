package com.lemnos.server.controllers;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class TesteController {
    @GetMapping("/login/oauth2/code/google")
    public Map<String, Object> currentUser(OAuth2AuthenticationToken token) {
        return token.getPrincipal().getAttributes();
    }
    @GetMapping("/")
    public Map<String, Object> google(OAuth2AuthenticationToken token) {
        return (token != null) ? token.getPrincipal().getAttributes() : new HashMap<String, Object>()
        {
            {
                put("token", "null");
            }
        };
    }
}
