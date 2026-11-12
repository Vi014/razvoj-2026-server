package com.example.lmsserver.kontroleri.response;

import com.example.lmsserver.klase.enumi.StatusPolaganja;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class IzlazakResponse
{
    private Long terminId;
    private Long studentId;
    private StatusPolaganja status;
    private LocalDateTime ulazak;
    private LocalDateTime predaja;
    private List<ResenjeResponse> resenja;
}
