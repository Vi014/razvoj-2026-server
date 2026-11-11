package com.example.lmsserver.kontroleri.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class StudentResponse {
    private String ime;
    private String prezime;
    private String email;
    private String jmbg;
    private Integer godina;
    private String brojIndeksa;
    private String grupaOznaka;
    private String smerNaziv;
}
