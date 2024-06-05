package com.lemnos.server.services;

import com.lemnos.server.exceptions.pedido.PedidoNotFound;
import com.lemnos.server.models.dtos.responses.PedidoResponse;
import com.lemnos.server.models.dtos.responses.ProdutoResponse;
import com.lemnos.server.models.pedido.Pedido;
import com.lemnos.server.models.produto.Produto;
import com.lemnos.server.models.produto.imagens.Imagem;
import com.lemnos.server.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {
    @Autowired private PedidoRepository pedidoRepository;

    public ResponseEntity<List<PedidoResponse>> getAll(){
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<PedidoResponse> dto = new ArrayList<>();

        for(Pedido pedido : pedidos){
            dto.add(getPedidoResponse(pedido));
        }

        return ResponseEntity.ok(dto);
    }

    private ResponseEntity<PedidoResponse> getOneById(Integer id){
        return ResponseEntity.ok(getPedidoResponse(getPedidoById(id)));
    }

    private static PedidoResponse getPedidoResponse(Pedido pedido) {
        return new PedidoResponse(
                pedido.getValorPedido(),
                pedido.getMetodoPagamento(),
                pedido.getDataPedido(),
                pedido.getValorPagamento(),
                pedido.getQtdProdutos(),
                pedido.getDataPagamento(),
                pedido.getDescricao()
        );
    }

    private Pedido getPedidoById(Integer id){
        return pedidoRepository.findById(id).orElseThrow(PedidoNotFound::new);
    }
}
