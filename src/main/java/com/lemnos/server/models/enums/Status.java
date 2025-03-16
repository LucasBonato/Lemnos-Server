package com.lemnos.server.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
    EM_PROCESSAMENTO("Em processamento"),
    ENVIADO_TRANSPORTADORA("Enviado para a transportadora"),
    RECEBIDO_TRANSPORTADORA("Recebido pela transportadora"),
    MERCADORIA_EM_TRANSITO("Mercadoria em trânsito"),
    ROTA_DE_ENTREGA("Mercadoria em rota de entrega"),
    ENTREGUE("Entregue");

    private final String status;
}
