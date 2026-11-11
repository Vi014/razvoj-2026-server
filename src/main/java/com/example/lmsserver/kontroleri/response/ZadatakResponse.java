package com.example.lmsserver.kontroleri.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ZadatakResponse
{
    private Long id;
    private Long terminId;
    private Integer redniBroj;
    private String opis;
    private String pocetniKodUrl;
    private BigDecimal maxPoena;
}
