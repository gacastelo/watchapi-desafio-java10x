package com.market.watchapi.entity.enums;

public enum TipoVidro {
    MINERAL, SAPPHIRE, ACRYLIC;

    public static TipoVidro fromApi(String value) {
        if (value == null || value.isBlank()) return null;

        return switch (value) {
            case "MINERAL" -> MINERAL;
            case "SAPPHIRE" -> SAPPHIRE;
            case "ACRYLIC" -> ACRYLIC;
            default -> throw new IllegalArgumentException("Tipo de Vidro Inválido: " + value);
        };
    }

    public String toApi() {
        return switch (this){
            case MINERAL -> "MINERAL";
            case SAPPHIRE -> "SAPPHIRE";
            case ACRYLIC -> "ACRYLIC";
        };
    }
}
