package com.example.lmsserver.kontroleri.response;

import com.example.lmsserver.klase.enumi.StatusPolaganja;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UlazakResponse
{
    private Long terminId;
    private String proveraNaziv;
    private String predmetNaziv;
    private StatusPolaganja status;
    private LocalDateTime ulazak;
    private LocalDateTime krajTermina;
    private Long preostaloSekundi;
    private List<ZadatakResponse> zadaci;
}
