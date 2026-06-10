package com.market.watchapi.docs;

import com.market.watchapi.dto.AtualizarRelogioRequest;
import com.market.watchapi.dto.CriarRelogioRequest;
import com.market.watchapi.dto.PaginaRelogioDTO;
import com.market.watchapi.dto.RelogioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;
@Tag(name = "Relogios",
description = "Endpoint para gerenciamento de Relógios")
public interface RelogioControllerDocs {

    @Operation(summary = "Faz uma pesquisa com filtros de Relógios",
            description = "Faz uma pesquisa de todos os Relógiso que se encaixem nos parametros(filtros) listados, caso nenhum seja preenchido trará todos."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Listagem feita com sucesso"
    )
    PaginaRelogioDTO listarRelogios(@RequestParam(defaultValue = "1") Integer pagina,
                                           @RequestParam(defaultValue = "12") Integer porPagina,
                                           @RequestParam(required = false) String busca,
                                           @RequestParam(required = false) String marca,
                                           @RequestParam(required = false) String tipoMovimento,
                                           @RequestParam(required = false) String materialCaixa,
                                           @RequestParam(required = false) String tipoVidro,
                                           @RequestParam(required = false) Integer resistenciaMin,
                                           @RequestParam(required = false) Integer resistenciaMax,
                                           @RequestParam(required = false) Long precoMin,
                                           @RequestParam(required = false) Long precoMax,
                                           @RequestParam(required = false) Integer diametroMin,
                                           @RequestParam(required = false) Integer diametroMax,
                                           @RequestParam(required = false) String ordenar);

    @Operation(summary = "Busca Relógio pelo ID",
            description = "Busca Relógio pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Sucesso ao encontrar Relógio"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Relógio não Encontrado"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição Inválida"
            )
    })
    RelogioDTO buscarRelogio(@PathVariable UUID id);

    @Operation(summary = "Cria um relógio",
                description = "Recebe uma requisição via POST, cria e salva um Relógio")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Criado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição Inválida"
            )
    })
    RelogioDTO criarRelogio(@Valid @RequestBody CriarRelogioRequest request);

    @Operation(summary = "Atualiza um Relógio",
                description = "Atualiza os atributos de um relógio com base em seu ID." )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Relógio atualizado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Relógio não Encontrado"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição Inválida"
            )
    })
    RelogioDTO atualizarRelogio(@Valid @RequestBody AtualizarRelogioRequest request, @PathVariable UUID id);

    @Operation(summary = "Deleta um Relógio pelo ID",
                description = "Verifica se um Relógio existe, se sim deleta-o")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Relógio deletado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Relógio não Encontrado"
            )
    })
    void deletarRelogio(@PathVariable UUID id);
}
