package com.example.lmsserver.kontroleri.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrijavaRequest
{
    @NotNull
    private Long terminId;

    // TODO: kada se uvede autentifikacija, student se uzima iz prijavljenog korisnika (STUDENT), a ovo polje ostaje samo za PREDAVAC koji prijavljuju u ime studenta.
    @NotNull
    private Long studentId;
}
