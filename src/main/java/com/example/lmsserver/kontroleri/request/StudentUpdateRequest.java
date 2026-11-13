package com.example.lmsserver.kontroleri.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentUpdateRequest {
    @Pattern(regexp = ".*\\S.*", message = "Ime ne sme biti prazno.")
    private String ime;

    @Pattern(regexp = ".*\\S.*", message = "Prezime ne sme biti prazno.")
    private String prezime;

    @Pattern(regexp = "\\d{13}", message = "JMBG u neispravnom formatu (proverite dužinu i prisustvo karaktera koji nisu cifre")
    private String jmbg;

    @PositiveOrZero
    private Integer godina;

    @Pattern(regexp = ".*\\S.*", message = "Broj indeksa ne sme biti prazan.")
    private String brojIndeksa;

    private Long grupaId;
    private Long smerId;
}
