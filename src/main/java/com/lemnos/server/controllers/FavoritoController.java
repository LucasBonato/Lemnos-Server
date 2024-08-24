package com.lemnos.server.controllers;

import com.lemnos.server.models.dtos.responses.FavoritoResponse;
import com.lemnos.server.services.FavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorito")
public class FavoritoController {
    @Autowired private FavoritoService favoritoService;

    @GetMapping
    public ResponseEntity<List<FavoritoResponse>> getFavoritos(JwtAuthenticationToken token) {
        return favoritoService.getFavoritos(token);
    }

    @PostMapping
    public ResponseEntity<Void> favoritar(JwtAuthenticationToken token, @RequestParam(name = "id_prod") String idProd) {
        return favoritoService.favoritar(token, idProd);
    }

    @DeleteMapping
    public ResponseEntity<Void> desfavoritar(JwtAuthenticationToken token, @RequestParam(name = "id_prod") String idProd) {
        return favoritoService.desfavoritar(token, idProd);
    }
}
