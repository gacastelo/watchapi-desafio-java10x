package com.market.watchapi.service;

import com.market.watchapi.entity.Relogio;
import com.market.watchapi.entity.enums.MaterialCaixa;
import com.market.watchapi.entity.enums.TipoMovimento;
import com.market.watchapi.entity.enums.TipoVidro;
import org.springframework.data.jpa.domain.Specification;

public class RelogioSpecs{

    private RelogioSpecs (){
    }

    public static Specification<Relogio> all(){
        return (root, query, cb) -> cb.conjunction();
    }

    private static boolean blank(String str){
        return str == null || str.isBlank();
    }


    /*
      WHERE
      LOWER(marca) LIKE LOWER('%' || :termo || '%')
      OR
      LOWER(modelo) LIKE '%termo%'
      OR
      LOWER(referencia) LIKE '%termo%'
     */
    public static Specification<Relogio> busca(String termo){
        if (blank(termo)) return all();
        String like = "%" + termo.toLowerCase() + "%";
        return (root, query, cb) -> cb.or(
                cb.like(cb.lower(root.get("marca")), like),
                cb.like(cb.lower(root.get("modelo")), like),
                cb.like(cb.lower(root.get("referencia")), like)
        );
    }

    public static Specification<Relogio> marcaIgual(String marca){
        if (blank(marca)) return all();
        return (root, query, cb) -> cb.equal(root.get("marca"), marca);
    }

    public static Specification<Relogio> referenciaIgual(String referencia){
        if (blank(referencia)) return all();
        return (root, query, cb) -> cb.equal(root.get("referencia"), referencia);
    }

    public static Specification<Relogio> tipoMovimentoIgual(TipoMovimento tipoMovimento){
        if (tipoMovimento == null) return all();
        return (root, query, cb) -> cb.equal(root.get("tipoMovimento"), tipoMovimento);
    }

    public static Specification<Relogio> materialCaixaIgual(MaterialCaixa materialCaixa){
        if (materialCaixa == null) return all();
        return (root, query, cb) -> cb.equal(root.get("materialCaixa"), materialCaixa);
    }

    public static Specification<Relogio> tipoVidroIgual(TipoVidro tipoVidro){
        if (tipoVidro == null) return all();
        return (root, query, cb) -> cb.equal(root.get("tipoVidro"), tipoVidro);
    }

    public static Specification<Relogio> resistenciaAguaEntre(Integer min, Integer max){
        if (min == null && max == null) return all();
        return (root, query, cb) -> {
            if (min != null && max != null) return cb.between(root.get("resistenciaAguaM"), min, max);
            if (min != null) return cb.greaterThanOrEqualTo(root.get("resistenciaAguaM"), min);
            return cb.lessThanOrEqualTo(root.get("resistenciaAguaM"), max);
        };
    }

    public static Specification<Relogio> precoEmCentavosEntre(Long min, Long max){
        if (min == null && max == null) return all();
        return (root, query, cb) -> {
            if (min != null && max != null) return cb.between(root.get("precoEmCentavos"), min, max);
            if (min != null) return cb.greaterThanOrEqualTo(root.get("precoEmCentavos"), min);
            return cb.lessThanOrEqualTo(root.get("precoEmCentavos"), max);
        };
    }

    public static Specification<Relogio> diametroEntre(Integer min, Integer max){
        if (min == null && max == null) return all();
        return (root, query, cb) -> {
            if (min != null && max != null) return cb.between(root.get("diametroMm"), min, max);
            if (min != null) return cb.greaterThanOrEqualTo(root.get("diametroMm"), min);
            return cb.lessThanOrEqualTo(root.get("diametroMm"), max);
        };
    }

}
