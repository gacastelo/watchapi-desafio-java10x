package com.market.watchapi.dto;

import java.util.List;

public record PaginaRelogioDTO(List<RelogioDTO> items, long total) {
}
