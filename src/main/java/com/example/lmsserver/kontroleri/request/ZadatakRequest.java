package com.example.lmsserver.kontroleri.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZadatakRequest
{
    @NotNull
    private Long terminId;

    @NotNull
    @Positive
    private Integer redniBroj;

    @Size(max = 20000)
    private String opis;

    @NotBlank
    @Size(max = 500)
    @Pattern(regexp = "^https?://\\S+$", message = "Url mora poceti sa http:// ili https://")
    private String pocetniKodUrl;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal maxPoena;
}
