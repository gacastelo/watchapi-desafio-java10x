package com.market.watchapi.service;

import com.market.watchapi.dto.PaginaRelogioDTO;
import com.market.watchapi.entity.Relogio;
import com.market.watchapi.entity.enums.MaterialCaixa;
import com.market.watchapi.entity.enums.TipoMovimento;
import com.market.watchapi.entity.enums.TipoVidro;
import com.market.watchapi.mapper.RelogioMapper;
import com.market.watchapi.repository.RelogioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static com.market.watchapi.service.RelogioSpecs.*;

@Service
public class RelogioService {
    private final RelogioRepository relogioRepository;
    private final RelogioMapper relogioMapper;

    public RelogioService(RelogioRepository relogioRepository, RelogioMapper relogioMapper) {
        this.relogioRepository = relogioRepository;
        this.relogioMapper = relogioMapper;
    }

    public PaginaRelogioDTO listar(
      int pagina,
      int porPagina,
      String busca,
      String marca,
      String tipoMovimento,
      String materialCaixa,
      String tipoVidro,
      Integer resistenciaMin,
      Integer resistenciaMax,
      Integer precoMin,
      Integer precoMax,
      Integer diametroMin,
      Integer diametroMax,
      String ordenar
    ){
        int paginaSegura = Math.max(1, pagina);
        int porPaginaSeguro = Math.max(1, Math.min(60, porPagina));
        TipoMovimento movimento = TipoMovimento.fromApi(tipoMovimento);
        MaterialCaixa material = MaterialCaixa.fromApi(materialCaixa);
        TipoVidro vidro = TipoVidro.fromApi(tipoVidro);

        OrdenacaoRelogios ordenacao = OrdenacaoRelogios.fromApi(ordenar);

        Sort sort = switch (ordenacao){
            case NEWEST -> Sort.by(Sort.Direction.DESC, "created_at");
            case PRICE_ASC -> Sort.by(Sort.Direction.ASC, "precoEmCentavos");
            case PRICE_DESC -> Sort.by(Sort.Direction.DESC, "precoEmCentavos");
            case DIAMETER_ASC -> Sort.by(Sort.Direction.ASC, "diametroMm");
            case WR_DESC -> Sort.by(Sort.Direction.DESC, "resistenciaAguaM");
        };

        Pageable pageable = PageRequest.of(paginaSegura - 1, porPaginaSeguro, sort);

        Specification<Relogio> spec = Specification.where(busca(busca))
                .and(marcaIgual(marca))
                .and(tipoMovimentoIgual(movimento))
                .and(materialCaixaIgual(material))
                .and(tipoVidroIgual(vidro))
                .and(resistenciaAguaEntre(resistenciaMin, resistenciaMax))
                .and(precoEmCentavosEntre(precoMin, precoMax))
                .and(diametroEntre(diametroMin, diametroMax));

        Page<Relogio> resultado = relogioRepository.findAll(spec, pageable);

        return new PaginaRelogioDTO(
                resultado.getContent().stream().map(relogioMapper::toDTO).toList(),
                resultado.getTotalElements()
        );
    }
}
