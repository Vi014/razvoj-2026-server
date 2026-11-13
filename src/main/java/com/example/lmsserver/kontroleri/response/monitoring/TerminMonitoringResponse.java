package com.example.lmsserver.kontroleri.response.monitoring;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TerminMonitoringResponse
{
    private Long terminId;
    private LocalDateTime pocetak;
    private LocalDateTime kraj;
    private boolean terminUToku;
    private boolean terminZavrsen;
    private boolean sviPredali;
    private List<StudentMonitoringResponse> studenti;
}
