package com.example.lmsserver.kontroleri.response.monitoring;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ResenjeMonitoringResponse
{
    private Long resenjeId;
    private Long zadatakId;
    private String resenjeUrl;
    private BigDecimal poeni;
}
