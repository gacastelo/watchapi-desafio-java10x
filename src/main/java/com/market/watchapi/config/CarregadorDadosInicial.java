package com.market.watchapi.config;

import com.market.watchapi.entity.Relogio;
import com.market.watchapi.entity.enums.MaterialCaixa;
import com.market.watchapi.entity.enums.TipoMovimento;
import com.market.watchapi.entity.enums.TipoVidro;
import com.market.watchapi.repository.RelogioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Configuration
public class CarregadorDadosInicial {

    private final RelogioRepository relogioRepository;

    public CarregadorDadosInicial(RelogioRepository relogioRepository) {
        this.relogioRepository = relogioRepository;
    }

    @Bean
    CommandLineRunner seedRelogios(){
        return args -> {
          if (!relogioRepository.findAll().isEmpty()) return;

          Instant agora = Instant.now();
            List<Relogio> relogios = List.of(
                    Relogio.builder()
                            .id(UUID.randomUUID())
                            .marca("CasteloWatch")
                            .modelo("F-10xT")
                            .referencia("1234")
                            .tipoMovimento(TipoMovimento.QUARTZ)
                            .materialCaixa(MaterialCaixa.TITANIUM)
                            .tipoVidro(TipoVidro.SAPPHIRE)
                            .resistenciaAguaM(30)
                            .diametroMm(35)
                            .lugToLugMm(38)
                            .espessuraMm(9)
                            .larguraLugMm(18)
                            .precoEmCentavos(18880)
                            .urlImagem("url123")
                            .createdAt(agora.minusSeconds(50000))
                            .updatedAt(agora.minusSeconds(50000))
                            .build(),
                    Relogio.builder()
                            .id(UUID.randomUUID())
                            .marca("Seiko")
                            .modelo("G-123cT")
                            .referencia("5678")
                            .tipoMovimento(TipoMovimento.MANUAL)
                            .materialCaixa(MaterialCaixa.STEEL)
                            .tipoVidro(TipoVidro.MINERAL)
                            .resistenciaAguaM(200)
                            .diametroMm(40)
                            .lugToLugMm(40)
                            .espessuraMm(12)
                            .larguraLugMm(16)
                            .precoEmCentavos(25080)
                            .urlImagem("url124")
                            .createdAt(agora.minusSeconds(23000))
                            .updatedAt(agora.minusSeconds(23000))
                            .build(),
                    Relogio.builder()
                            .id(UUID.randomUUID())
                            .marca("Casio")
                            .modelo("C-ter23")
                            .referencia("8888")
                            .tipoMovimento(TipoMovimento.AUTOMATIC)
                            .materialCaixa(MaterialCaixa.CERAMIC)
                            .tipoVidro(TipoVidro.MINERAL)
                            .resistenciaAguaM(199)
                            .diametroMm(40)
                            .lugToLugMm(40)
                            .espessuraMm(13)
                            .larguraLugMm(16)
                            .precoEmCentavos(15998)
                            .urlImagem("url124")
                            .createdAt(agora.minusSeconds(23400))
                            .updatedAt(agora.minusSeconds(23400))
                            .build()
            );
            relogioRepository.saveAll(relogios);
        };
    }


}
