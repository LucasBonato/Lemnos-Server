package com.lemnos.server.controllers;

import com.lemnos.server.models.dtos.requests.AlterarStatusRequest;
import com.lemnos.server.models.dtos.requests.PedidoRequest;
import com.lemnos.server.models.dtos.responses.PedidoResponse;
import com.lemnos.server.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    @Autowired private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> getAllByEmail(JwtAuthenticationToken token) {
        return pedidoService.getAll(token);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> getOneById(@PathVariable Integer id) {
        return pedidoService.getOne(id);
    }

    @PostMapping
    public ResponseEntity<Void> novoPedido(@RequestBody PedidoRequest pedidoRequest, JwtAuthenticationToken token) {
        return pedidoService.novoPedido(pedidoRequest, token);
    }

    @PutMapping
    public ResponseEntity<Void> alterarStatus(@RequestBody AlterarStatusRequest request) {
        return  pedidoService.alterarStatus(request);
    }

}
