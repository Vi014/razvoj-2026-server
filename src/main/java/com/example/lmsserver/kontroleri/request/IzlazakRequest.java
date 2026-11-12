package com.example.lmsserver.kontroleri.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IzlazakRequest
{
    @NotNull
    private Long terminId;

    @NotNull
    private Long studentId;
}
