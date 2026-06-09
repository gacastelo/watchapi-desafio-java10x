package com.market.watchapi.service;

import com.market.watchapi.dto.AtualizarRelogioRequest;
import com.market.watchapi.dto.CriarRelogioRequest;
import com.market.watchapi.dto.PaginaRelogioDTO;
import com.market.watchapi.dto.RelogioDTO;
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

import java.time.Instant;
import java.util.UUID;

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
      Long precoMin,
      Long precoMax,
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

    public RelogioDTO buscarPorId(UUID id){
        Relogio r = relogioRepository.findById(id).orElseThrow(() -> new NaoEncontradoException("Relógio não encontrado: " + id));
        return relogioMapper.toDTO(r);
    }

    public RelogioDTO criar(CriarRelogioRequest req){
        Relogio r = Relogio.builder()
                .id(UUID.randomUUID())
                .marca(req.marca())
                .modelo(req.modelo())
                .referencia(req.referencia())
                .tipoMovimento(TipoMovimento.fromApi(req.tipoMovimento()))
                .materialCaixa(MaterialCaixa.fromApi(req.materialCaixa()))
                .tipoVidro(TipoVidro.fromApi(req.tipoVidro()))
                .resistenciaAguaM(req.resistenciaAguaM())
                .diametroMn(req.diametroMn())
                .lugToLugMm(req.lugToLugMm())
                .espessuraMm(req.espessuraMm())
                .larguraLugMm(req.larguraLugMm())
                .precoEmCentavos(req.precoEmCentavos())
                .urlImagem(req.urlImagem())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        return relogioMapper.toDTO(r);
    }

    public RelogioDTO atualizar(UUID id, AtualizarRelogioRequest req){
        Relogio r = relogioRepository.findById(id).orElseThrow(() -> new NaoEncontradoException("Relógio não encontrado: " + id));

        r.setMarca(req.marca());
        r.setModelo(req.modelo());
        r.setReferencia(req.referencia());
        r.setTipoMovimento(TipoMovimento.fromApi(req.tipoMovimento()));
        r.setMaterialCaixa(MaterialCaixa.fromApi(req.materialCaixa()));
        r.setTipoVidro(TipoVidro.fromApi(req.tipoVidro()));
        r.setResistenciaAguaM(req.resistenciaAguaM());
        r.setDiametroMn(req.diametroMn());
        r.setLugToLugMm(req.lugToLugMm());
        r.setEspessuraMm(req.espessuraMm());
        r.setLarguraLugMm(req.larguraLugMm());
        r.setPrecoEmCentavos(req.precoEmCentavos());
        r.setUrlImagem(req.urlImagem());
        r.setUpdatedAt(Instant.now());

        relogioRepository.save(r);
        return relogioMapper.toDTO(r);
    }

    public void remover(UUID id){
        if (!relogioRepository.existsById(id)){
            throw new NaoEncontradoException("Relógio não encontrado: " + id);
        }
        relogioRepository.deleteById(id);
    }
}
