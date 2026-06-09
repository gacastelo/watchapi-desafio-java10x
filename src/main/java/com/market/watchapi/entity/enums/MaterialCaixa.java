package com.market.watchapi.entity.enums;

public enum MaterialCaixa {
    STEEL, TITANIUM, RESIN, BRONZE, CERAMIC;


    public static MaterialCaixa fromApi(String value) {
        if (value == null || value.isBlank()) return null;
        return switch (value.toLowerCase()) {
            case "steel" -> STEEL;
            case "titanium" -> TITANIUM;
            case "resin" -> RESIN;
            case "bronze" -> BRONZE;
            case "ceramic" -> CERAMIC;
            default -> throw new IllegalArgumentException("Tipo de MaterialCaixa Inválido: " + value);
        };
    }

    public String toApi(){
        return switch (this){
            case STEEL -> "steel";
            case TITANIUM -> "titanium";
            case RESIN -> "resin";
            case BRONZE -> "bronze";
            case CERAMIC -> "ceramic";
        };
    }
}
