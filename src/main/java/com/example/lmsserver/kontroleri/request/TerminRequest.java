package com.example.lmsserver.kontroleri.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TerminRequest
{
    @NotNull
    private Long proveraId;

    @NotNull
    private Long predavacId;  // opciono: ko drzi / nadgleda termin

    @NotNull
    private LocalDate datum;

    @NotNull
    private LocalDateTime pocetak;

    @NotNull
    private LocalDateTime kraj;
}
