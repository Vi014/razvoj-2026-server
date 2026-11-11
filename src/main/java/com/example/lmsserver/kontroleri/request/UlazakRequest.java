package com.example.lmsserver.kontroleri.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UlazakRequest
{
    @NotNull
    private Long terminId;

    // TODO: iz prijavljenog korisnika (STUDENT) umesto iz tela zahteva, ovako bi svako mogao da "udje" umesto drugog
    @NotNull
    private Long studentId;
}
