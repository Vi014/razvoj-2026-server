package com.example.lmsserver.kontroleri.request;

import com.example.lmsserver.klase.Grupa;
import com.example.lmsserver.klase.Smer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@RequiredArgsConstructor
@Data // lombok
public class StudentRequest {
    @NotBlank
    private String ime;

    @NotBlank
    private String prezime;

    @Email
    private String email; // potrebna validacija da je unique?

    @NotNull
    @Length(min = 13, max = 13)
    private String jmbg;

    @PositiveOrZero
    @NotNull
    private Integer godina;

    @NotBlank
    private String brojIndeksa;

    private Long grupaId;

    @NotNull
    private Long smerId;
}
