package com.example.lmsserver.kontroleri.request;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

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
    @Pattern(regexp = "\\d{13}", message = "JMBG u neispravnom formatu (proverite dužinu i prisustvo karaktera koji nisu cifre")
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
