package com.example.lmsserver.kontroleri.response.monitoring;

import lombok.Data;

@Data
public class TerminStatistikaResponse
{
    private Long terminId;
    private long brojPrijavljenih;
    private long brojUslo;
    private long brojPredalo;
    private long brojNisuUsli;
    private double procenatUslo;
    private double procenatPredalo;
    private double procenatNisuUsli;
    private String usliFormat;
    private String predaliFormat;
}
