package com.example.lmsserver.kontroleri.request;

import com.example.lmsserver.klase.Grupa;
import com.example.lmsserver.klase.Smer;
import jakarta.validation.constraints.*;
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

    // @Email
    // private String email; // potrebna validacija da je unique?
    // email se UVEK automatski generiše te uopšte nije potreban ni u kom requestu

    @NotNull
    //@Pattern(regexp = "\\d{13}", message = "JMBG u neispravnom formatu (proverite dužinu i prisustvo karaktera koji nisu cifre")
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
