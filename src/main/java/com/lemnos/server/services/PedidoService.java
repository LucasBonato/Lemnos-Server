package com.lemnos.server.services;

import com.lemnos.server.exceptions.carrinho.CarrinhoVazioException;
import com.lemnos.server.exceptions.entidades.cliente.ClienteNotFoundException;
import com.lemnos.server.exceptions.pedido.EntregaJaRealizadaException;
import com.lemnos.server.exceptions.pedido.PedidoNotFoundException;
import com.lemnos.server.models.cadastro.Cadastro;
import com.lemnos.server.models.carrinho.Carrinho;
import com.lemnos.server.models.dtos.requests.AlterarStatusRequest;
import com.lemnos.server.models.dtos.requests.PedidoRequest;
import com.lemnos.server.models.dtos.responses.PedidoResponse;
import com.lemnos.server.models.enums.Status;
import com.lemnos.server.models.pedido.Entrega;
import com.lemnos.server.models.pedido.Pedido;
import com.lemnos.server.repositories.CarrinhoRepository;
import com.lemnos.server.repositories.EntregaRepository;
import com.lemnos.server.repositories.PedidoRepository;
import com.lemnos.server.repositories.cadastro.CadastroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private CadastroRepository cadastroRepository;
    @Autowired private CarrinhoRepository carrinhoRepository;
    @Autowired private EntregaRepository entregaRepository;

    public ResponseEntity<List<PedidoResponse>> getAll(String email){
        List<Pedido> pedidos = pedidoRepository.findByCadastro(getCadastroByEmail(email));
        List<PedidoResponse> dto = new ArrayList<>();
        pedidos.forEach(pedido -> dto.add(getPedidoResponse(pedido)));

        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<PedidoResponse> getOne(Integer id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(PedidoNotFoundException::new);
        return ResponseEntity.ok(getPedidoResponse(pedido));
    }

    public ResponseEntity<Void> novoPedido(PedidoRequest pedidoRequest) {
        Cadastro cadastro = getCadastroByEmail(pedidoRequest.email());
        Carrinho carrinho = carrinhoRepository.findByCadastro(cadastro).orElseThrow(CarrinhoVazioException::new);
        pedidoRepository.save(new Pedido(carrinho.getValor(), pedidoRequest.metodoPagamento(), carrinho.getValor(),carrinho.getQuantidadeProdutos(), getDescricao(carrinho),  pedidoRequest.valorFrete(), cadastro));
        carrinhoRepository.delete(carrinho);
        return ResponseEntity.ok().build();
    }
    
    public ResponseEntity<Void> alterarStatus(AlterarStatusRequest request) {
        Pedido pedido = pedidoRepository.findById(request.id()).orElseThrow(PedidoNotFoundException::new);
        Status status = proximoStatus(pedido.getStatus());
        if(status == Status.ENTREGUE) {
            Optional<Entrega> entregaOptional = entregaRepository.findByPedido(pedido);
            if(entregaOptional.isPresent()) throw new EntregaJaRealizadaException();
            entregaRepository.save(new Entrega(pedido));
            return ResponseEntity.ok().build();
        }
        pedido.setStatus(status.getStatus());
        pedidoRepository.save(pedido);

        return ResponseEntity.ok().build();
    }

    private Status proximoStatus(String status) {
        return switch (status) {
            case "Em processamento" -> Status.EVIADO_TRANSPORTADORA;
            case "Enviado para a transportadora" -> Status.RECEBIDO_TRANSPORTADORA;
            case "Recebido pela transportadora" -> Status.MERCADORIA_EM_TRANSITO;
            case "Mercadoria em trânsito" -> Status.ROTA_DE_ENTREGA;
            case "Mercadoria em rota de entrega", "Pedido entregue" -> Status.ENTREGUE;
            default -> Status.EM_PROCESSAMENTO;
        };
    }

    private String getDescricao(Carrinho carrinho) {
        String[] response = {""};
        carrinho.getItens().forEach(item -> {
            response[0] += item.getProduto().getId().toString() + ", " + item.getQuantidade() + "\n";
        });
        return response[0];
    }

    private Cadastro getCadastroByEmail(String email) {
        return cadastroRepository.findByEmail(email.replace("%40", "@")).orElseThrow(ClienteNotFoundException::new);
    }

    private static PedidoResponse getPedidoResponse(Pedido pedido) {
        return new PedidoResponse(
                pedido.getId(),
                pedido.getValorPedido(),
                pedido.getMetodoPagamento(),
                pedido.getDataPedido(),
                pedido.getValorPagamento(),
                pedido.getQntdProdutos(),
                pedido.getDataPagamento(),
                pedido.getDescricao(),
                pedido.getValorFrete(),
                pedido.getStatus()
        );
    }
}
