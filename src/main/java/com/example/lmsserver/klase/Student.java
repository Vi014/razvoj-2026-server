package com.example.lmsserver.klase;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
public class Student
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String ime;
    @NotBlank
    @Column(nullable = false)
    private String prezime;
    @Email
    private String email;
    @Column(nullable = false, unique = true, length = 13)
    @Pattern(regexp = "\\d{13}")
    private String jmbg;
    @PositiveOrZero
    @Column(nullable = false)
    private Integer godina;
    @Column(name = "broj_indeksa", nullable = false, unique = true)
    private String brojIndeksa;
    @ManyToOne(fetch = FetchType.LAZY)
    private Grupa grupa;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Smer smer;


    /*private LocalDate datumRodjenja;
    private String mestoRodjenja;
    private String mestoPrebivalista;
    private String drzavaRodjenja;
    private String drzavljanstvo;
    private String nacionalnost;
    private Character pol;
    private String adresa;
    private String brojTelefonaMobilni;
    private String brojLicneKarte;*/
}
