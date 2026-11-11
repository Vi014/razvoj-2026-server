package com.example.lmsserver.kontroleri.response;

import com.example.lmsserver.klase.enumi.StatusPolaganja;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PrijavaResponse
{
    private Long id;
    private Long terminId;
    private Long studentId;
    private String brojIndeksa;
    private String studentIme;
    private String studentPrezime;
    private StatusPolaganja status;
    private LocalDateTime ulazak;
    private LocalDateTime predaja;
}
