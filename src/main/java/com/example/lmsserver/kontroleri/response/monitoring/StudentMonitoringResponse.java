package com.example.lmsserver.kontroleri.response.monitoring;

import com.example.lmsserver.klase.enumi.StatusPolaganja;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class StudentMonitoringResponse
{
    private Long studentId;
    private String ime;
    private String prezime;
    private String brojIndeksa;
    private StatusPolaganja status;
    private LocalDateTime ulazak;
    private LocalDateTime predaja;
    private Long trajanjeSekunde;
    private List<ResenjeMonitoringResponse> resenja;
}
