package com.example.lmsserver.kontroleri.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ResenjeResponse
{
    private Long id;
    private Long terminId;
    private Long zadatakId;
    private Integer zadatakRedniBroj;
    private String zadatakNaslov;
    private BigDecimal maxPoena;
    private Long studentId;
    private String brojIndeksa;
    private String resenjeUrl;
    private LocalDateTime krenuo;
    private LocalDateTime predao;
    private BigDecimal poeni;
}
