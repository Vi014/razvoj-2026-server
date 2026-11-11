package com.example.lmsserver.kontroleri.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoeniRequest
{
    @NotNull
    @DecimalMin("0.0")
    @Digits(integer = 3, fraction = 2)
    private BigDecimal poeni;
}
