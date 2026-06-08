package com.market.watchapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CriarRelogioRequest(
        @NotBlank @Size(max = 80)String marca,
        @NotBlank @Size(max = 125)String modelo,
        @NotBlank String referencia,
        @NotBlank String tipoMovimento,
        @NotBlank String materialCaixa,
        @NotBlank String tipoVidro,

        @Min(0) int resistenciaAguaM,
        @Min(1) int diametroMn,
        @Min(1) int lugToLugMm,
        @Min(1) int espessuraMm,
        @Min(1) int larguraLugMm,
        @Min(1) int precoEmCentavos,
        @NotNull @Size(max = 600) String urlImagem
){
}
