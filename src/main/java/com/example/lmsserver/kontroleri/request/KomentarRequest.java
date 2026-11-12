package com.example.lmsserver.kontroleri.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KomentarRequest
{
    @NotNull
    private Long resenjeId;

    @NotNull
    private Long predavacId;

    @NotBlank
    @Size(max = 2000)
    private String tekst;
}
