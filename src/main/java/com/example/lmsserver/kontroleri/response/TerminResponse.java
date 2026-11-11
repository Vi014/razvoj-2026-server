package com.example.lmsserver.kontroleri.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TerminResponse
{
    private Long id;
    private Long proveraId;
    private String proveraNaziv;
    private String predmetNaziv;
    private Long predavacId;
    private String predavacImePrezime;
    private LocalDate datum;
    private LocalDateTime pocetak;
    private LocalDateTime kraj;
}
