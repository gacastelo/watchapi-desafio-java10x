package com.market.watchapi.service;

public enum OrdenacaoRelogios {
    NEWEST, PRICE_ASC, PRICE_DESC, DIAMETER_ASC, WR_DESC;

    public static OrdenacaoRelogios fromApi(String valor){
        if (valor == null || valor.isBlank()) return NEWEST;

        return switch (valor){
            case "newest" -> NEWEST;
            case "price_asc" -> PRICE_ASC;
            case "price_desc" -> PRICE_DESC;
            case "diameter_asc" -> DIAMETER_ASC;
            case "wr_desc" -> WR_DESC;
            default -> throw new IllegalArgumentException("E" + valor);
        };
    }
}
