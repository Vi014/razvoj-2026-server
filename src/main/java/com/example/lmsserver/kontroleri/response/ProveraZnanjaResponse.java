package com.example.lmsserver.kontroleri.response;

import com.example.lmsserver.klase.enumi.TipProvere;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProveraZnanjaResponse
{
    private Long id;
    private Long predmetId;
    private String predmetSifra;
    private String predmetNaziv;
    private String naziv;
    private TipProvere tip;
    private BigDecimal pragPoena;
}
