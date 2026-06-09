package com.market.watchapi.entity.enums;

public enum TipoMovimento {
    QUARTZ, AUTOMATIC, MANUAL;

    public static TipoMovimento fromApi(String value) {
        if(value == null || value.isBlank()) return null;

        return switch (value.toLowerCase()){
            case "quartz" -> QUARTZ;
            case "automatic" -> AUTOMATIC;
            case "manual" -> MANUAL;
            default -> throw  new IllegalArgumentException("Tipo de Movimento Inválido: " + value);
        };
    }

    public String toApi() {
        return switch (this){
            case QUARTZ -> "quartz";
            case AUTOMATIC -> "automatic";
            case MANUAL -> "manual";
        };
    }
}
