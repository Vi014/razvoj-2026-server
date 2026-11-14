package com.example.lmsserver.kontroleri.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PoeniResponse {
    private BigDecimal poeni;
}
