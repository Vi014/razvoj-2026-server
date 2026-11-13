package com.example.lmsserver.kontroleri.response;


import lombok.Data;

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
