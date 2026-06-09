package com.market.watchapi.mapper;

import com.market.watchapi.dto.RelogioDTO;
import com.market.watchapi.entity.Relogio;
import com.market.watchapi.entity.enums.MaterialCaixa;
import com.market.watchapi.entity.enums.TipoMovimento;
import com.market.watchapi.entity.enums.TipoVidro;
import org.springframework.stereotype.Component;

@Component
public class RelogioMapper {

    public RelogioDTO toDTO(Relogio r){
        return RelogioDTO.builder()
                .id(r.getId())
                .marca(r.getMarca())
                .modelo(r.getModelo())
                .referencia(r.getReferencia())
                .tipoMovimento(r.getTipoMovimento().toApi())
                .materialCaixa(r.getMaterialCaixa().toApi())
                .tipoVidro(r.getTipoVidro().toApi())
                .resistenciaAguaM(r.getResistenciaAguaM())
                .diametroMn(r.getDiametroMm())
                .lugToLugMm(r.getLugToLugMm())
                .espessuraMm(r.getEspessuraMm())
                .larguraLugMm(r.getLarguraLugMm())
                .precoEmCentavos(r.getPrecoEmCentavos())
                .urlImagem(r.getUrlImagem())
                .etiquetaResistenciaAgua(getEtiquetaResistenciaAgua(r.getResistenciaAguaM()))
                .pontuacaoColecionador(getPontuacaoColecionador(r))
                .build();
    }

    private String getEtiquetaResistenciaAgua(int resistenciaAguaM){
        if (resistenciaAguaM < 50) return "respingos";
        if (resistenciaAguaM < 100) return "uso_diario";
        if (resistenciaAguaM < 200) return "natacao";
        return "mergulho";
    }

    private int getPontuacaoColecionador (Relogio r){
        int pontos = 0;

        if (r.getTipoVidro() == TipoVidro.SAPPHIRE) pontos += 25;

        if (r.getResistenciaAguaM() >= 100)pontos += 15;
        if (r.getResistenciaAguaM() >= 200)pontos += 10;


        if (r.getTipoMovimento() == TipoMovimento.AUTOMATIC) pontos += 20;

        if (r.getMaterialCaixa() == MaterialCaixa.STEEL) pontos += 10;
        if (r.getMaterialCaixa() == MaterialCaixa.TITANIUM) pontos += 12;

        if (r.getDiametroMm() >= 38 && r.getDiametroMm() <= 42) pontos += 8;

        return pontos;
    }
}
