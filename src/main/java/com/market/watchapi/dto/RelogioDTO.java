package com.market.watchapi.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record RelogioDTO(
        UUID id,
        String marca,
        String modelo,
        String referencia,
        String tipoMovimento,
        String materialCaixa,
        String tipoVidro,
        int resistenciaAguaM,
        int diametroMn,
        int lugToLugMm,
        int espessuraMm,
        int larguraLugMm,
        int precoEmCentavos,
        String urlImagem,
        String etiquetaResistenciaAgua,
        int pontuacaoColecionador

){
}
