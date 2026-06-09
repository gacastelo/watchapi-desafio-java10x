package com.market.watchapi.controller;

import com.market.watchapi.dto.AtualizarRelogioRequest;
import com.market.watchapi.dto.CriarRelogioRequest;
import com.market.watchapi.dto.PaginaRelogioDTO;
import com.market.watchapi.dto.RelogioDTO;
import com.market.watchapi.service.RelogioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/relogios")
@CrossOrigin(origins = "*")
public class RelogioController {

    private final RelogioService relogioService;

    public RelogioController(RelogioService relogioService) {
        this.relogioService = relogioService;
    }


    /*
    * pagina 	int 	1 	Página atual
porPagina 	int 	12 	Itens por página (máx 60)
busca 	string 	- 	Busca em marca/modelo/referência
marca 	string 	- 	Filtra por marca (case-insensitive)
tipoMovimento 	string 	- 	quartz
materialCaixa 	string 	- 	steel
tipoVidro 	string 	- 	mineral
resistenciaMin 	int 	- 	Resistência mínima (m)
resistenciaMax 	int 	- 	Resistência máxima (m)
precoMin 	long 	- 	Preço mínimo (centavos)
precoMax 	long 	- 	Preço máximo (centavos)
diametroMin 	int 	- 	Diâmetro mínimo (mm)
diametroMax 	int 	- 	Diâmetro máximo (mm)
ordenar 	string
    * */

    @GetMapping()
    public PaginaRelogioDTO listarRelogios(
            @RequestParam(defaultValue = "1") Integer pagina,
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
            @RequestParam(required = false) String ordenar
    ){
        return relogioService.listar(
                pagina,
                porPagina,
                busca,
                marca,
                tipoMovimento,
                materialCaixa,
                tipoVidro,
                resistenciaMin,
                resistenciaMax,
                precoMin,
                precoMax,
                diametroMin,
                diametroMax,
                ordenar);
    }

    @GetMapping("/{id}")
    public RelogioDTO buscarRelogio(@PathVariable UUID id){
        return relogioService.buscarPorId(id);
    }

    @PostMapping
    public RelogioDTO criarRelogio(@Valid @RequestBody CriarRelogioRequest request){
        return relogioService.criar(request);
    }

    @PutMapping("/{id}")
    public RelogioDTO atualizarRelogio(@Valid @RequestBody AtualizarRelogioRequest request, @PathVariable UUID id){
        return relogioService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void deletarRelogio(@PathVariable UUID id){
        relogioService.remover(id);
    }
}
