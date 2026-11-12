package com.example.lmsserver.kontroleri.response;

import lombok.Data;

@Data
public class KomentarResponse
{
    private Long id;
    private Long resenjeId;
    private Long predavacId;
    private String predavacImePrezime;
    private String tekst;
}
