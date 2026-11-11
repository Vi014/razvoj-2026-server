package com.example.lmsserver.kontroleri.request;

import com.example.lmsserver.klase.enumi.TipProvere;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProveraZnanjaRequest
{
    @NotNull
    private Long predmetId;

    @NotBlank
    @Size(max = 150)
    private String naziv;  // npr. "Prvi kolokvijum"

    @NotNull
    private TipProvere tip;

    @DecimalMin("0.0")
    @Digits(integer = 3, fraction = 2)
    private BigDecimal pragPoena;  // opciono: prag za prolaz
}
